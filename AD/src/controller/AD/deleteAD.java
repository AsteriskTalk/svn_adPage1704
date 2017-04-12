package controller.AD;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.ADManager;
import util.ASTKLogManager;

public class deleteAD extends HttpServlet {

	protected void doGP(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\nlog : doGP.." + ASTKLogManager.getClassName_now());
		ServletContext sc = req.getServletContext();
		String pagePath = (String)req.getAttribute("indexPath");
		String viewPath = "";
		
		long clientCode = 0;
		long ADCode = 0;
		
		ADManager am = (ADManager) sc.getAttribute("am");
		
		boolean result = false;
		
		try {
			clientCode = Long.parseLong(req.getParameter("clientCode"));
			ADCode = Long.parseLong(req.getParameter("ADCode"));
			
			result = am.disconnectAD(ADCode, clientCode);
			if (result) { viewPath = "common/success.jsp"; }
			else { viewPath = "common/failed.jsp"; }
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ ASTKLogManager.getClassName_now() +"\n"+ex);
			result = false;
			viewPath = "common/error.jsp";
			
		} finally {
			req.setAttribute("viewPath", viewPath);
			req.setAttribute("result", result);
			
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
