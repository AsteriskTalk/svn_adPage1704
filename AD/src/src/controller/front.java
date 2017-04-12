package controller;

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

public class front extends HttpServlet {
	protected void doGP(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");
		req.setCharacterEncoding("utf-8");
		ServletContext sc = req.getServletContext();
		HttpSession ses = req.getSession();
		String pagePath = (String) sc.getAttribute("INDEX_PAGE");
		
		String reqPath = req.getServletPath();
		String[] tmp = reqPath.split("\\.");
		String SERVLET_PATH = tmp[0];
			
		ClientManager cm = (ClientManager) sc.getAttribute("cm");
		ADManager am = (ADManager) sc.getAttribute("am");
		
		String clientID = "";
		boolean isSignIn = false;
		
		try {
			Enumeration e = ses.getAttributeNames();
			while (e.hasMoreElements()) {
				String s = (String)e.nextElement();
				if (s.equals("clientID")) { isSignIn = true; break; } 
			}
			
			if (isSignIn) {
				final long lastUpdate = (Long) ses.getAttribute("updateTime");
				final long NOW = System.currentTimeMillis();
				final long UPDATE_INTERVAL = 1000 * 60 * 5;
				if ( ( lastUpdate - NOW ) > UPDATE_INTERVAL ) {
					clientID = (String) ses.getAttribute("clientID");
					final long CLIENT_CODE= cm.getClientCode(clientID);
					
					HashMap<String, Object> clientInfoSet = cm.getClientProfile_someClient_all(CLIENT_CODE);
					HashMap<String, Object> allADInfo = am.selectAD_allAD(CLIENT_CODE);
					HashMap<String, Object> todayADHistory = am.selectADHistory_today(CLIENT_CODE);
					HashMap<String, Object> allADHistory = am.selectADHistory_all(CLIENT_CODE);

					ses.setAttribute("updateTime", NOW);
					ses.setAttribute("clientInfoSet", clientInfoSet);
					ses.setAttribute("allADInfo", allADInfo);
					ses.setAttribute("todayADHistory", todayADHistory);
					ses.setAttribute("allADHistory", allADHistory);
				}
				
			} else {
				ses.invalidate();
			}

			
		} catch (Exception ex) {
			System.out.println("log : FRONT\n"+ex);
			SERVLET_PATH = "/index.jsp";
			
		} finally {
			RequestDispatcher rd = req.getRequestDispatcher(SERVLET_PATH);
			rd.forward(req, resp);
			
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGP(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGP(req, resp);
	}
	

}
