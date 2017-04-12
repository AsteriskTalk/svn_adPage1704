package controller.AD;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.ADManager;
import DAO.ClientManager;
import util.ASTKLogManager;

public class selectAD extends HttpServlet {
	protected void doGP(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\nlog : doGP.." + ASTKLogManager.getClassName_now());
		ServletContext sc = req.getServletContext();
		HttpSession ses = req.getSession();
		String pagePath = (String)sc.getAttribute("INDEX_PAGE");
		String viewPath =	"adMain.jsp";
		String insidePage = "select.jsp";
		
		long ADCode = 0;
		long clientCode = 0;
		String clientID = "";
		
		ADManager am = (ADManager) sc.getAttribute("am");
		ClientManager cm = (ClientManager) sc.getAttribute("cm");
		
		HashMap<String, Object> tmp = new HashMap<String, Object>();
		
		try {
			clientID = (String) ses.getAttribute("clientID");
			ADCode = Long.parseLong(req.getParameter("ADCode"));
			clientCode = cm.getClientCode(clientID);
			
			tmp = am.selectAD_someAD(ADCode, clientCode);
			req.setAttribute("ADInfoMap_some", tmp);
			tmp = am.selectADHistory_someAD(ADCode, clientCode);
			req.setAttribute("ADHistoryMap_some", tmp);
			tmp = am.selectADTarget_someAD_allTarget(ADCode, clientCode);
			req.setAttribute("ADTargetMap_some", tmp);
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ASTKLogManager.getClassName_now()+"\n"+ex);
			viewPath = (String) sc.getAttribute("ERROR_PAGE");
			req.setAttribute("ex", ex);
			
		} finally {
			req.setAttribute("insidePage", insidePage);
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
