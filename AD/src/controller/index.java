package controller;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.ASTKLogManager;

public class index extends HttpServlet {
	protected void doGP(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\nlog : doGP..indexController");
		ServletContext sc = req.getServletContext();
		final String INDEX_PAGE = (String)sc.getAttribute("INDEX_PAGE");
		String viewPath = "main.jsp";
		
		try {			
			Enumeration e = req.getAttributeNames();
			attr :
			while (e.hasMoreElements()) {
				String name = (String)e.nextElement();
				if (name.equals("viewPath")) { viewPath = (String)req.getAttribute(name); break attr; }
			}
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ ASTKLogManager.getClassName_now() + "\n"+ ex);
			viewPath = (String) sc.getAttribute("ERROR_PAGE");
			
		} finally {
			req.setAttribute("viewPath", viewPath);
			RequestDispatcher rd = req.getRequestDispatcher(INDEX_PAGE);
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
