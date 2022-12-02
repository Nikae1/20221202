package services.registration;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.GroupBean;
import beans.StoreBean;

public class RegDataAccessObject extends services.DataAccessObject {

	public RegDataAccessObject() {
//		System.out.println("regdao");
	}

	/*
	 * StoreGroup테이블에 그룹코드를 제외한 나머지 값들을 insert하기 위한 메소드 최상위 부모인 DataAccessObject의
	 * 메소드를 활용하여
	 */
	public int insStoreGroup(Connection connection, GroupBean group) {
		System.out.println("insert");
		int result = 0;

		String ins = "INSERT INTO COOKDBA.SG(SG_CODE, SG_NAME, SG_CEO, SG_PIN) "
				+ "VALUES('S'||COOKDBA.HOHOHO.NEXTVAL,?,?,?)";

		try {
			this.ps = connection.prepareStatement(ins);
			this.ps.setNString(1, group.getGroupName());
			this.ps.setNString(2, group.getGroupCeo());
			this.ps.setNString(3, group.getGroupPin());

			result = this.ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}
	/* groupName은 Unique Key로 지정이 되어있기빼문에 오직 하나만이 존재하므로,
	 * 중복이 되어있는지 검사를 거친 후 진행이되기위한 메소드
	 * */
	public ArrayList<GroupBean> getDupGroupName(Connection connection, GroupBean group) {
		ArrayList<GroupBean> groupList = null;
		GroupBean groupBean = null;
		String query = "SELECT SG_NAME AS GROUPNAME FROM COOKDBA.SG WHERE SG_NAME = ?";

		try {
			this.ps = connection.prepareStatement(query);
			this.ps.setNString(1, group.getGroupName());
			this.rs = this.ps.executeQuery();

			
			/* isBeforeFirst는 ResultSet의 첫 레코드로 접근하기 전의
			 * 대기실 같은 영역으로 데이터값의 존재여부에 따라 존재가 결정되므로
			 * 여기에서는 값이 존재하는지를 판단하는 구문으로 사용 */
			if (this.rs.isBeforeFirst()) {
				groupList = new ArrayList<GroupBean>();
				while (this.rs.next()) {
					groupBean = new GroupBean();
					groupBean.setGroupName(this.rs.getNString("GROUPNAME"));
					groupList.add(groupBean);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return groupList;
	}

	/*
	 * insert한 storegroup에서 시퀀스를 이용하여 넣은 groupcode를 찾기위한 메소드 groupName만 사용하는 이유는
	 * unique키로 설정이 되어있기 때문이다.
	 */
	public ArrayList<GroupBean> getGroupCode(Connection connection, GroupBean group) {
		ArrayList<GroupBean> groupList = null;
		GroupBean groupcodeBean = null;
		String query = "SELECT SG_CODE AS GROUPCODE " + "FROM COOKDBA.SG WHERE SG_NAME = ?";

		try {
			this.ps = connection.prepareStatement(query);
			this.ps.setNString(1, group.getGroupName());

			this.rs = this.ps.executeQuery();

			if (this.rs != null) {
				groupList = new ArrayList<GroupBean>();
				while (this.rs.next()) {
					groupcodeBean = new GroupBean();
					groupcodeBean.setGroupCode(this.rs.getNString("GROUPCODE"));
					groupList.add(groupcodeBean);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return groupList;
	}

	/* [INS] STORES에 등록하기위한 메소드 */
	public int insStore(Connection connection) {
		int result = 0;

		return result;
	}

	/* [INS] STORESCATEGORIES에 등록하기위한 메소드 */
	public int insStoreLevel(Connection connection) {
		int result = 0;

		return result;
	}

	/*
	 * [SEL] ST+SC를 innerjoin하여 STORECODE LEVCODE LEVNAME 를 얻기위하여 만든 메소드
	 */
	public ArrayList<StoreBean> getStoreInfo(Connection connection) {

		return null;
	}

}
