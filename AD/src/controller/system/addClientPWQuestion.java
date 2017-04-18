package controller.system;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.SystemManager;
import util.ASTKLogManager;

public class addClientPWQuestion extends HttpServlet {

	protected void doGP(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\nlog : doGP.." + ASTKLogManager.getClassName_now());
		ServletContext sc = req.getServletContext();
		String page = (String)sc.getAttribute("INDEX_PAGE");
		String view = "system/result.jsp";
		
		
		String question = "";
		
		SystemManager sm = (SystemManager) sc.getAttribute("sm");
		
		boolean result = false;
		
		try {
			question = req.getParameter("question");
			
			result = sm.insertClientPasswordQuestion(question);
			
			req.setAttribute("result", result);
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ASTKLogManager.getClassName_now()+"\n"+ex);
			view = (String) sc.getAttribute("ERROR_PAGE");
			req.setAttribute("ex", ex);
			
		} finally {
			req.setAttribute("view", view);
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
