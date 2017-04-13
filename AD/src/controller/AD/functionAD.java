package controller.AD;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.ClientManager;
import DAO.FunctionManager;
import util.ASTKLogManager;

public class functionAD extends HttpServlet {

	protected void doGP(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\nlog : doGP.." + ASTKLogManager.getClassName_now());
		ServletContext sc = req.getServletContext();
		HttpSession ses = req.getSession();
		String pagePath = (String)sc.getAttribute("INDEX_PAGE");
		String viewPath =	"common/result.jsp";
		
		long ADCode = 0;
		long clientCode = 0;
		String clientID = "";
		String ADFunction = "";
		
		FunctionManager fm = (FunctionManager) sc.getAttribute("fm");
		ClientManager cm = (ClientManager) sc.getAttribute("cm");
		
		boolean result = false;
		
		try {
			clientID = (String) ses.getAttribute("clientID");
			clientCode = cm.getClientCode(clientID);
			ADCode = Long.parseLong(req.getParameter("ADCode"));
			ADFunction = req.getParameter("ADFunction");
			
//			result = fm.functionAD(ADCode, clientCode, ADFunction);
			
			req.setAttribute("result", result);
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ASTKLogManager.getClassName_now()+"\n"+ex);
			viewPath = (String) sc.getAttribute("ERROR_PAGE");
			req.setAttribute("ex", ex);
			
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
