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
import util.ADTools;

public class front extends HttpServlet {
	protected void doGP(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");
		req.setCharacterEncoding("utf-8");
		ServletContext sc = req.getServletContext();
		HttpSession ses = req.getSession();
		
		String reqPath = req.getServletPath();
		String[] tmp = reqPath.split("\\.");
		String servletPath = tmp[0].substring(1); //맨 앞의 /(slash) 제거
		
		HashMap<String, Object> map = new HashMap<String, Object>();
			
		ClientManager cm = (ClientManager) sc.getAttribute("cm");
		ADManager am = (ADManager) sc.getAttribute("am");
		
		String clientID = "";
		boolean isSignIn = false;
		boolean hasUpdate = false;

		String remoteAddr = "";
		String forwardAddr = "";
		
		try {
			remoteAddr = req.getRemoteAddr();
			forwardAddr = req.getHeader("x-forwarded_for");
			
			
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

			
		} catch (Exception ex) {
			System.out.println("log : FRONT\n"+ex);
			servletPath = "/index";
			
		} finally {
//			System.out.println("front..rd : " + servletPath);
			RequestDispatcher rd = req.getRequestDispatcher(servletPath);
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
