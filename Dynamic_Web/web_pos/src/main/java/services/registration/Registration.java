package services.registration;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;


import beans.ActionBean;
import beans.GroupBean;

public class Registration {
	private HttpServletRequest request;
	
	
	public Registration(HttpServletRequest request) {
     this.request = request;
	}
	
	
	public ActionBean backController(int serviceCode) {
		ActionBean action = null;
		if(serviceCode == 11) {
			action = this.MemberJoinCtl();
		}else if(serviceCode ==12) {
			action = this.RegStoreCtl();
		}else if(serviceCode == 13) { 
			action = this.RegempCtl();
		}else if(serviceCode == 10) {
			action = this.DupGroupNameCheckCtl();
		}
		
		return action;
	}
	
	
	private ActionBean DupGroupNameCheckCtl() {
		ActionBean action = new ActionBean();
		GroupBean group = new GroupBean();
		ArrayList<GroupBean> groupList = null;
		
		//가져온 정보를 빈에 저장하여 중복체크에 사용하려한다
		group.setGroupName(this.request.getParameter("groupName"));
		
		//server와 소통하고 검사할 dao클래스를 생성자로 할당
		//통신을 위한 connection
		RegDataAccessObject dao = new RegDataAccessObject();
		Connection connection = dao.openConnection();
		
		//Client로부터 넘어온 groupName의 중복검사
		groupList = dao.getDupGroupName(connection, group);
		
		dao.closeConnection(connection);
		
		
		String message;
		if(groupList == null) {
			System.out.println(this.getRefererPage());
			this.request.setAttribute("groupName", group.getGroupName());
			action.setPage("group-step2.jsp?previous="+this.getRefererPage());
			action.setRedirect(false);
		}else {
				message = "사용중인 그룹명입니다.";
				try {
					message = ("?message=")+URLEncoder.encode(message, "UTF-8");
				} catch (UnsupportedEncodingException e) {e.printStackTrace();}
					
				action.setPage("group-step1.jsp"+message);
				action.setRedirect(true);
			
		}
		return action;
	}

	//FrontController에서 serviceCode별로 분기시켜
	private ActionBean MemberJoinCtl() {
		ActionBean action = new ActionBean();
		GroupBean group = new GroupBean();
		
		/* 1. request --> GroupBean 
		 * request의 3가지 정보를 group이라는 Bean에 담아 사용하려함.
		 * */
		group.setGroupName(this.request.getParameter("groupName"));
		group.setGroupCeo(this.request.getParameter("groupCeo"));
		group.setGroupPin(this.request.getParameter("groupPin"));
		
		/*
		 * 2-0. Variable Declaration */
		boolean transaction = false;
		ArrayList<GroupBean> groupList = null;
		/* 2. DAO Allocation & DAO Open 
		 * server와의 연결을 하기위해 dao를 생성자로 만들고 통신을 위한 connection을 연결한다.
		 * */
		RegDataAccessObject dao = new RegDataAccessObject();
		Connection connection = dao.openConnection();
		/* 2-1. Transaction Start */
		dao.modifyTranStatus(connection, false);
		/* 2-2. [INS] STOREGROUP */
		transaction = this.convertToBoolean(dao.insStoreGroup(connection, group));
		/* 2-3. [SEL] GROUPCODE */
		groupList = dao.getGroupCode(connection, group);
		/* 2-4. Transaction End */
		dao.setTransaction(transaction, connection);
		dao.modifyTranStatus(connection, true);
		/* 2-5. DAO Close */
		dao.closeConnection(connection);
		
		/*등록이 끝난 후 step2로 넘어갈지 말지 결정하는 소스
		 *  + 이전 주소값과 Message를 추가하여 
		 * */
		request.setAttribute("groupCode", groupList.get(0).getGroupCode());
		action.setPage(transaction? "step2.jsp":"step1.jsp");
		action.setRedirect(!transaction);
		
		return action;
	}

	private ActionBean RegStoreCtl() {
 
		return null;
	}

	private ActionBean RegempCtl() {

		return null;
	}

	private ActionBean RegCategoriesCtl() {

		return null;
	}
	
	/* Convert to Boolean */
	private boolean convertToBoolean(int value) {
		return value>0? true:false;
	}

	/* Referer Page 추출 */
	private String getRefererPage() {
		
		return this.request.getHeader("referer").substring(this.request.getHeader("referer").lastIndexOf('/')+1);
	}
	
}
