package services.auth;

import javax.servlet.http.HttpServletRequest;

import beans.ActionBean;

public class Auth {
	private HttpServletRequest request;
	
	
	public Auth(HttpServletRequest request) {
		this.request = request;
	}

public ActionBean backController(int serviceCode) {
	ActionBean action = null;
	
	if(serviceCode == 1) {
	action = this.loginCtl();
		
	}else if(serviceCode == -1) {
	action = this.loginoutCtl();
	}
	return null;
}
	
private ActionBean loginCtl() {
	
	return null;
}
private ActionBean loginoutCtl() {
	
	return null;
}	
	
	
	
	
}
