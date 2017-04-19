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
		
		HashMap<String, Object> map = new HashMap<String, Object>();
			
		ClientManager cm = (ClientManager) sc.getAttribute("cm");
		ADManager am = (ADManager) sc.getAttribute("am");
		
		String clientID = "";
		boolean isSignIn = false;
		boolean hasUpdate = false;
		
		try {
<<<<<<< HEAD
			Enumeration e = ses.getAttributeNames();
			while (e.hasMoreElements()) {
				String s = (String)e.nextElement();
				if (s.equals("clientID")) { isSignIn = true; } 
				else if (s.equals("updateTime")) { hasUpdate = true; }
			}
			
			if (isSignIn) {
				final long lastUpdate = hasUpdate ? (Long) ses.getAttribute("updateTime") : 0;
				final long NOW = System.currentTimeMillis();
				final long UPDATE_INTERVAL = 1000 * 60 * 5;
				
				if ( lastUpdate < ( NOW - UPDATE_INTERVAL ) || !hasUpdate ) {
					ses.setAttribute("updateTime", NOW);
					
					clientID = (String) ses.getAttribute("clientID");
					final long CLIENT_CODE= cm.getClientCode(clientID);
					
					map = cm.getClientProfile_someClient_all(CLIENT_CODE);
					ses.setAttribute("clientInfoMap", map);
					
					map = am.selectAD_allAD(CLIENT_CODE);
					ses.setAttribute("ADInfoMap_all", map);
					
					map = am.selectADHistory_all_today(CLIENT_CODE);
					ses.setAttribute("ADHistoryMap_all_today", map);
					
					map = am.selectADHistory_all(CLIENT_CODE);
					ses.setAttribute("ADHistoryMap_all", map);
					
				}
				
			} else {
				System.out.println("do invalidate!");
				ses.invalidate();
			}

=======
			remoteAddr = req.getRemoteAddr();
			forwardAddr = req.getHeader("x-forwarded_for");
			if (servletPath.startsWith("system")) { servletPath = "/index"; }
			
			if (!servletPath.equals("signOut")) {

				try { isSignIn = ADTools.isSignIn(ses); } catch (Exception ex) { } 
				
				if (isSignIn) {
					Enumeration e = ses.getAttributeNames();
					while (e.hasMoreElements()) {
						String s = (String)e.nextElement();
		//				if (s.equals("clientID")) { isSignIn = true; } 
						if (s.equals("updateTime")) { hasUpdate = true; }
					}
					
					final long lastUpdate = hasUpdate ? (Long) ses.getAttribute("updateTime") : 0;
					final long NOW = System.currentTimeMillis();
					final long UPDATE_INTERVAL = 1000 * 60 * 5;
					
					if ( lastUpdate < ( NOW - UPDATE_INTERVAL ) || !hasUpdate ) {
						ses.setAttribute("updateTime", NOW);
						
						clientID = (String) ses.getAttribute("clientID");
						final long CLIENT_CODE= cm.getClientCode(clientID);
						
						map = cm.getClientProfile_someClient_all(CLIENT_CODE);
						ses.setAttribute("clientInfoMap", map);
						
						map = am.selectAD_allAD(CLIENT_CODE);
						ses.setAttribute("ADInfoMap_all", map);
						
						map = am.selectADHistory_all_today(CLIENT_CODE);
						ses.setAttribute("ADHistoryMap_all_today", map);
						
						map = am.selectADHistory_all(CLIENT_CODE);
						ses.setAttribute("ADHistoryMap_all", map);
						
					}
					
				} else { //로그인 되어있지 않은 경우
					/** SignIn 이 필요한 Page의 경우에 대한 처리 */
					if ( servletPath.startsWith("my")) {
						req.setAttribute("servletPath", servletPath);
						servletPath = "signIn.ad";
						}
						
					try { ses.invalidate(); } catch (Exception ex) { }
				}
			} 
>>>>>>> branch 'solpi' of https://github.com/AsteriskTalk/svn_adPage1704
			
		} catch (Exception ex) {
			System.out.println("log : FRONT\n"+ex);
			SERVLET_PATH = "/index.jsp";
			
		} finally {
<<<<<<< HEAD
			RequestDispatcher rd = req.getRequestDispatcher(SERVLET_PATH);
=======
//			System.out.println("front..rd : " + servletPath);
			RequestDispatcher rd = req.getRequestDispatcher(servletPath);
>>>>>>> branch 'solpi' of https://github.com/AsteriskTalk/svn_adPage1704
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
