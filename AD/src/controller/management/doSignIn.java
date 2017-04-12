package controller.management;

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
import util.ADTools;
import util.ASTKLogManager;
import util.CharManager;

public class doSignIn extends HttpServlet {

	protected void doGP(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\nlog : doGP..doSignInController");
		ServletContext sc = req.getServletContext();
		HttpSession ses = req.getSession();
		
//		String pagePath = (String)sc.getAttribute("INDEX_PAGE");
		String pagePath = "";

		String viewPath = "";
		final String HISTORY_MAIN_PAGE = "all.jsp";
		final String STATICS_MAIN_PAGE = "all.jsp";
		final String AD_MAIN_PAGE = "main.jsp";
		String insidePage = "";
		
		String clientID = "";
		String clientPW = "";
		
		ClientManager cm = (ClientManager) sc.getAttribute("cm");
		ADManager am = (ADManager) sc.getAttribute("am");
		
		boolean useInside = false;
		boolean result = false;
		
		try {
			clientID = CharManager.beforeOracle_removeSpace(req.getParameter("clientID"));
			clientPW = CharManager.beforeOracle_removeSpace(req.getParameter("clientPW"));
			
			
			viewPath = req.getParameter("savedViewPath");
			pagePath = req.getParameter("savedPagePath");
			if (ADTools.isNull(viewPath)) { viewPath = "main.jsp"; }
			
			result = cm.doSignIn(clientID, clientPW);

			if (result) {
				final long NOW = System.currentTimeMillis();
				final long CLIENT_CODE = cm.getClientCode(clientID);
				
				HashMap<String, Object> clientInfoSet = cm.getClientProfile_someClient_all(CLIENT_CODE);
				HashMap<String, Object> allADInfo = am.selectAD_allAD(CLIENT_CODE);
				HashMap<String, Object> todayADHistory = am.selectADHistory_today(CLIENT_CODE);
				HashMap<String, Object> allADHistory = am.selectADHistory_all(CLIENT_CODE);
				
				if (viewPath.startsWith("adMain")) { insidePage = AD_MAIN_PAGE; useInside = true; } 
				else if (viewPath.startsWith("history")) { insidePage = HISTORY_MAIN_PAGE; useInside = true; } 
				else if (viewPath.startsWith("statics")) { insidePage = STATICS_MAIN_PAGE; useInside = true; }
				
				if (useInside) { req.setAttribute("insidePage", insidePage); }
				
				ses.setAttribute("clientID", clientID);
				ses.setAttribute("updateTime", NOW);
				
				ses.setAttribute("clientInfoSet", clientInfoSet);
				
				ses.setAttribute("allADInfo", allADInfo);
				
				ses.setAttribute("todayADHistory", todayADHistory);
				ses.setAttribute("allADHistory", allADHistory);
				
			} else { viewPath = "management/signInFailed.html"; }
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ASTKLogManager.getClassName_now() +"\n"+ex);
			viewPath = (String) sc.getAttribute("ERROR_PAGE");
			
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
