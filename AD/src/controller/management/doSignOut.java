package controller.management;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.ASTKLogManager;

public class doSignOut extends HttpServlet {

	protected void doGP(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\nlog : doGP..doSignOutController");
		ServletContext sc = req.getServletContext();
		String indexPath = (String) sc.getAttribute("INDEX_PAGE");
//		String pagePath = "/clearSessionData.jsp";
		String viewPath = "main.jsp";
		
		try {
			HttpSession session = req.getSession();
			session.invalidate();
			
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ ASTKLogManager.getClassName_now() +"\n"+ex);
			viewPath = (String)sc.getAttribute("ERROR_PAGE");
			req.setAttribute("ex", ex);
			
		} finally {
			req.setAttribute("viewPath", viewPath);
			RequestDispatcher rd = req.getRequestDispatcher(indexPath);
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
