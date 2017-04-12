package controller.client;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.ClientManager;
import util.ASTKLogManager;

public class getClientProfile extends HttpServlet {

	protected void doGP(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\nlog : doGP.." + ASTKLogManager.getClassName_now());
		ServletContext sc = req.getServletContext();
		HttpSession session = req.getSession();
		String pagePath = (String)req.getAttribute("indexPath");
		String viewPath = "mypage/myProfile.jsp";
		
		String clientID = "";
		long clientCode = 0;
		
		ClientManager cm = (ClientManager)sc.getAttribute("cm");
		HashMap<String, Object> resultSet = new HashMap<String, Object>();
		
		try {
			clientID = (String)session.getAttribute("clientID");
			clientCode = cm.getClientCode(clientID);
			
			resultSet = cm.getClientProfile_someClient_all(clientCode);
			
			req.setAttribute("resultSet", resultSet);
			req.setAttribute("viewPath", viewPath);
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ ASTKLogManager.getClassName_now() +"\n"+ ex);
			viewPath = "/error.jsp";
			
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
