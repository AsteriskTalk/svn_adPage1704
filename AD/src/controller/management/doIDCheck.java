package controller.management;

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

public class doIDCheck  extends HttpServlet {

	protected void doGP(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\nlog : doGP.." + ASTKLogManager.getClassName_now());
		ServletContext sc = req.getServletContext();
		String pagePath = "webpages/management/IDCheck.jsp";
		String viewPath =	"";
		
		String clientID = "";
		
		ClientManager cm = (ClientManager) sc.getAttribute("cm");
		
		boolean result = false;
		
		try {
			clientID = CharManager.beforeOracle_removeSpace(req.getParameter("clientID"));
			result = cm.checkClientID(clientID);
			
			req.setAttribute("result", result);
		} catch (Exception ex) {
			viewPath ="error.jsp";
			
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
