package controller.client;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.ClientManager;
import util.ASTKLogManager;
import util.CharManager;

public class requestChangeEmail extends HttpServlet {
	protected void doGP(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\nlog : doGP.." + ASTKLogManager.getClassName_now());
		ServletContext sc = req.getServletContext();
		
		long clientCode = 0;
		String newEmail = "";
		
		ClientManager cm = (ClientManager)sc.getAttribute("cm");
		
		boolean result = false;
		
		try {
			clientCode = Long.parseLong(req.getParameter("clientCode"));
			newEmail = CharManager.beforeOracle_removeSpace(req.getParameter("newEmail"));
			
			result = cm.changeEmail_ready(clientCode, newEmail);
			
			req.setAttribute("result", result);
		
		} catch (Exception ex) {
			
		} finally {
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
