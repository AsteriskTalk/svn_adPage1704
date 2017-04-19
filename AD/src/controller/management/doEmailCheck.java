package controller.management;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.FunctionManager;
import util.ASTKLogManager;

public class doEmailCheck extends HttpServlet {

	protected void doGP(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\nlog : doGP.." + ASTKLogManager.getClassName_now());
		ServletContext sc = req.getServletContext();
		String page = "webpages/management/check.jsp";
		
		String OTC = "";
		
		FunctionManager fm = (FunctionManager) sc.getAttribute("fm");
		
		String result = "";
		
		try {
			OTC = req.getParameter("OTC");
			
			result = fm.doEmailCheck(OTC);
			
			req.setAttribute("result", result);

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
