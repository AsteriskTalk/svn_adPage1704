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

public class signInClick  extends HttpServlet {

	protected void doGP(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\nlog : doGP..headerClickController");
		ServletContext sc = req.getServletContext();
		String pagePath = (String)sc.getAttribute("INDEX_PAGE");
		String viewPath = "management/signIn.jsp";
		
		String savedViewPath = "main.jsp";
		
		try {
			req.setAttribute("savedViewPath", savedViewPath);
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ ASTKLogManager.getClassName_now() +"\n"+ex);
			viewPath = (String)sc.getAttribute("ERROR_PAGE");
			
		} finally {
			req.setAttribute("viewPath", viewPath);
			RequestDispatcher rd = req.getRequestDispatcher(pagePath);
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
