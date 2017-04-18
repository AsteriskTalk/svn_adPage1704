package controller.management;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.UserManager;
import util.ASTKLogManager;
import util.CharManager;

public class doFindID extends HttpServlet {

	protected void doGP(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\nlog : doGP.." + ASTKLogManager.getClassName_now());
		ServletContext sc = req.getServletContext();
		String page = "management/findResult.jsp";
		
		String email = "";
		String phone = "";
		
		UserManager um = (UserManager) sc.getAttribute("um");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		String userId = "";
		String msg = "";
		
		boolean result = false;
		
		try {
			email = CharManager.beforeOracle_removeSpace(req.getParameter("email"));
			phone = CharManager.beforeOracle_removeSpace(req.getParameter("phone"));
			
			map = um.findUserID(email, phone);
			result = (Boolean)map.get("result");
			
			if (result) { userId = (String) map.get("userId"); msg = " 아이디는 "+ userId +" 입니다."; } 
			else { msg = "조건에 맞는 아이디가 없습니다."; }
			
			req.setAttribute("msg", msg); 
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ASTKLogManager.getClassName_now()+"\n"+ex);
			page = (String) sc.getAttribute("ERROR_SERVLET_PATH");
			req.setAttribute("ex", ex);
			
		} finally {
			RequestDispatcher rd = req.getRequestDispatcher(page);
			rd.forward(req, resp);
			
		}
		
	}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGP(req, resp);
		
	}
	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGP(req, resp);
		
	}

}
