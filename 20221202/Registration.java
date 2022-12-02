package services.registration;

import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import beans.ActionBean;
import beans.GroupBean;

/* Registration Class
 * - 대표자등록
 * - 상점등록
 * - 직원등록
 * - 분류등록 
 * */
public class Registration {
	private HttpServletRequest req;
	private RegDataAccessObject dao;
	
	public Registration(HttpServletRequest req) {
		this.req = req;
	}
	
	
	public ActionBean backController(int serviceCode) {
		ActionBean action = null;
		switch(serviceCode) {
		case 0:
			action = this.groupNameDuplicateCheckCtl();
			break;
		case 1:
			action = this.memberJoinCtl();
			break;
		case 2:
			action = this.regStoreCtl();
			break;
		case 3:
			action = this.regEmpCtl();
			break;
		}
		return action;
	}
	
	private ActionBean groupNameDuplicateCheckCtl() {
		ActionBean action = new ActionBean();
		String page;
		Boolean forward;
		ArrayList<GroupBean> groupList = null;
		GroupBean group = new GroupBean();
		group.setGroupName(this.req.getParameter("groupName"));
				
		this.dao = new RegDataAccessObject();
		Connection connection = dao.openConnection();
		
		groupList = dao.getGroupName(connection, group);
		
		dao.closeConnection(connection);
		this.dao = null;
		
		if(groupList == null) {
			this.req.setAttribute("groupName", group.getGroupName());
			page = "group-step2.jsp";
			forward = false;
		}else {
			page = "group-step1.html";
			forward = true;
		}		
		
		action.setPage(page);
		action.setRedirect(forward);
		
		return action;
	}
	
	private ActionBean memberJoinCtl() {
		ActionBean action = new ActionBean();
		/* 1. req --> GroupBean */
		GroupBean group = new GroupBean();
		group.setGroupName(this.req.getParameter("groupName"));
		group.setGroupCeo(this.req.getParameter("groupCeo"));
		group.setGroupPin(this.req.getParameter("groupPin"));
		
		/* 2-0. Variable Declaration */
		boolean tran = false;
		ArrayList<GroupBean> groupList = null;
		/* 2. DAO Allocation & DAO Open */
		this.dao = new RegDataAccessObject();
		Connection connection = this.dao.openConnection();
		/* 2-1. Transaction Start */
		this.dao.modifyTranStatus(connection, false);
		/* 2-2. [INS] STOREGROUP */
		tran = this.convertToBoolean(this.dao.insStoreGroup(connection, group));
		/* 2-3. [SEL] GROUPCODE */
		groupList = this.dao.getGroupCode(connection, group);
		/* 2-4. Transaction End */
		this.dao.setTransaction(tran, connection);
		this.dao.modifyTranStatus(connection, true);
		/* 2-5. DAO Close */
		this.dao.closeConnection(connection);
		this.dao = null;
		
		req.setAttribute("groupCode", groupList.get(0).getGroupCode());
		action.setPage(tran?"step2.jsp":"step1.html");
		action.setRedirect(!tran);
				
		return action;
	}
	
	private ActionBean regStoreCtl() {
		
		return null;
	}
	private ActionBean regEmpCtl() {
		
		return null;
	}
	private ActionBean regCategoriesCtl() {
		
		return null;
	}
	
	/* Convert to Boolean */
	private boolean convertToBoolean(int value) {
		return value>0? true:false;
	}
}
