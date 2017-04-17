package controller.management;

import java.io.IOException;
import java.util.Enumeration;
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
		String servletPath = (String)sc.getAttribute("INDEX_SERVLET_PATH");

		String clientID = "";
		String clientPW = "";
		
		ClientManager cm = (ClientManager) sc.getAttribute("cm");
		ADManager am = (ADManager) sc.getAttribute("am");
		
		boolean result = false;
		
		try {
			clientID = CharManager.beforeOracle_removeSpace(req.getParameter("clientID"));
			clientPW = CharManager.beforeOracle_removeSpace(req.getParameter("clientPW"));
			
			result = cm.doSignIn(clientID, clientPW);

			if (result) {
				Enumeration<String> e = req.getParameterNames();
				while (e.hasMoreElements()) {
					String s = (String) e.nextElement();
					if ( s.equals("servletPath") ) { servletPath = req.getParameter(s); }
				}
				
				HashMap<String, Object> tmp = new HashMap<String, Object>();
				
				final long NOW = System.currentTimeMillis();
				final long CLIENT_CODE = cm.getClientCode(clientID);

				ses.setAttribute("clientID", clientID);
				ses.setAttribute("updateTime", NOW);
				
				tmp = cm.getClientProfile_someClient_all(CLIENT_CODE);
				ses.setAttribute("clientInfoMap", tmp);
				
				tmp = am.selectAD_allAD(CLIENT_CODE);
				ses.setAttribute("ADInfoMap_all", tmp);

				tmp = am.selectADHistory_all_today(CLIENT_CODE);
				ses.setAttribute("ADHistoryMap_all_today", tmp);
				
				tmp = am.selectADHistory_all(CLIENT_CODE);
				ses.setAttribute("ADHistoryMap_all", tmp);
				
			} else {
				servletPath = "index.ad"; 
				req.setAttribute("clientID", clientID);
				
			}
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ASTKLogManager.getClassName_now() +"\n"+ex);
			servletPath = (String) sc.getAttribute("ERROR_SERVLET_PATH");
			req.setAttribute("ex", ex);
			
		} finally {
			RequestDispatcher rd = req.getRequestDispatcher(servletPath);
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
