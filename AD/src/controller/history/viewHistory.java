package controller.history;

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

public class viewHistory  extends HttpServlet {

	protected void doGP(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\nlog : doGP..historyClickController");
		ServletContext sc = req.getServletContext();
		HttpSession ses = req.getSession();
		String page = (String)sc.getAttribute("INDEX_PAGE");
		String view = "history/main.jsp"; //view
		String insidePage = "clientHistory.jsp"; // client OR AD
		String insidePage_AD = "all.jsp"; // only used in AD..all OR some
		String goTo = "all";

		String clientID = "";
		long clientCode = 0;
		long ADCode = 0;
		int histPage = 1;
		
		HashMap<String, Object> tmp = new HashMap<String ,Object>();
		boolean hasAllADHistory = false;
		boolean selected = false;
		
		ClientManager cm = (ClientManager)sc.getAttribute("cm");
		ADManager am = (ADManager) sc.getAttribute("am");

		try {
			clientID = (String)ses.getAttribute("clientID");
			clientCode = cm.getClientCode(clientID);
			
			Enumeration<String> e = req.getParameterNames();
			while (e.hasMoreElements()) {
				String s = (String)e.nextElement();
				if (s.equals("insidePage")) { insidePage = req.getParameter(s); }
				else if (s.equals("histPage")) { histPage = Integer.parseInt(req.getParameter(s)); }
				else if (s.equals("insidePage_AD")) { insidePage_AD = req.getParameter(s); }
				else if (s.equals("goTo")) { goTo = req.getParameter(s); }
				
			}

			if(goTo.equals("select")) {
				insidePage_AD = "select.jsp";
				insidePage = "ADHistory.jsp";
			}
			

			/** Case..insidePage == ADHistory */
			if (insidePage.startsWith("AD")) {
				
				if (insidePage_AD.startsWith("all")) {
						insidePage_AD = "all.jsp";
						e = ses.getAttributeNames();
						while (e.hasMoreElements()) {
							String s = (String) e.nextElement();
							if (s.equals("ADHistoryMap_all")) { tmp=(HashMap<String, Object>)ses.getAttribute(s); hasAllADHistory = true; break; }
						}
						
						if (!hasAllADHistory) { tmp = am.selectADHistory_all(clientCode); }
						ses.setAttribute("ADHistoryMap_all", tmp);
						
				} else if (insidePage_AD.startsWith("list") ) { //ADList.jsp 에서 사용할 것들 req.setAttr
					insidePage_AD = "../common/ADList.jsp";
					String servletPath = "myHistory.ad";
					req.setAttribute("servletPath", servletPath);
					
				} else if (insidePage_AD.startsWith("some")) {
					insidePage_AD = "some.jsp";
					HashMap<String, Object> map = new HashMap<String ,Object>();
					ADCode = Long.parseLong(req.getParameter("ADCode"));
					
					map = am.selectAD_someAD(ADCode, clientCode);
					req.setAttribute("ADInfoMap_some", map);
					
					map = am.selectADHistory_someAD(ADCode, clientCode);
					req.setAttribute("ADHistoryMap_some", map);
				}
				req.setAttribute("insidePage_AD", insidePage_AD);
			}
			
			else if (insidePage.startsWith("client")) { 
				
			}
			
			
			req.setAttribute("insidePage", insidePage);
			req.setAttribute("histPage", histPage);
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ ASTKLogManager.getClassName_now() +"\n"+ex);
			view = (String)sc.getAttribute("ERROR_PATH");
			
		} finally {
			req.setAttribute("view", view);
			RequestDispatcher rd = req.getRequestDispatcher(page);
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
