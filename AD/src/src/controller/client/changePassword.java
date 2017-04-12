package controller.client;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.ClientManager;
import util.ASTKLogManager;

public class changePassword extends HttpServlet {

	protected void doGP(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\nlog : doGP.." + ASTKLogManager.getClassName_now());
		ServletContext sc = req.getServletContext();
		HttpSession session = req.getSession();
		String pagePath = "/management/changePasswordResult.jsp";
		
		long clientCode = 0;
		String clientID = "";
		String oldPW = "";
		String newPW = "";
		
		ClientManager cm = (ClientManager) sc.getAttribute("cm");
		boolean result = false;
		
		try {
			clientID = (String)session.getAttribute("clientID");
			newPW = req.getParameter("newPW");
			oldPW = req.getParameter("oldPW");
			
			req.setAttribute("result", "T");
			req.setAttribute("oldPW", oldPW);
			req.setAttribute("newPW", newPW);
		
		} catch (Exception ex) {
			req.setAttribute("result", "E");
			
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
