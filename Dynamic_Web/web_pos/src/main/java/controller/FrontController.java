package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.ActionBean;
import services.auth.Auth;
import services.registration.Registration;

@WebServlet({ "/MemberJoin","/Group", "/Regstore", "/Regemp", "/Login", "/Logout" })
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FrontController() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String jobCode = request.getRequestURI().substring(request.getContextPath().length() + 1);
		ActionBean action = null;
		Registration reg;
		Auth auth;
	
		if(jobCode.equals("Group")) {
			reg = new Registration(request);
			action = reg.backController(10);
		}

		
		if (action.isRedirect()) {
			response.sendRedirect(action.getPage());
		}else {
			RequestDispatcher dispatcher = request.getRequestDispatcher(action.getPage());
			dispatcher.forward(request, response);
		}
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String jobCode = request.getRequestURI().substring(request.getContextPath().length() + 1);
		ActionBean action = null;
		Registration reg;
		Auth auth;

		if (jobCode.equals("MemberJoin")) {
			reg = new Registration(request);
			action = reg.backController(11);

		} else if (jobCode.equals("Regstore")) {
			reg = new Registration(request);
			action = reg.backController(12);

		} else if (jobCode.equals("Regemp")) {
			reg = new Registration(request);
			action = reg.backController(13);

		} else if (jobCode.equals("Login")) {
			auth = new Auth(request);
			action = auth.backController(1);

		} else if (jobCode.equals("Logout")) {
			auth = new Auth(request);
			action = auth.backController(-1);
		}
//			else if(jobCode.equals("Group")) {
//			reg = new Registration(request);
//			action = reg.backController(10);
//		}

		
		/* action값으로 정해지는 페이지와 보내어지는 방식을 결정하는 구문 */
		
		if (action.isRedirect()) {
			response.sendRedirect(action.getPage());
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher(action.getPage());
			dispatcher.forward(request, response);
		}

	}

}
