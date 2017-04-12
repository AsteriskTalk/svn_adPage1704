package controller.main;

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
import util.ASTKLogManager;

public class historyClick  extends HttpServlet {

	protected void doGP(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\nlog : doGP..historyClickController");
		ServletContext sc = req.getServletContext();
		HttpSession session = req.getSession();
		
		String pagePath = (String)sc.getAttribute("INDEX_PAGE");
		String viewPath = "history.jsp";

		String insidePage = "all.jsp";

		String clientID = "";
		long clientCode = 0;
		long ADCode = 0;
		int histPage = 1;
		
		ClientManager cm = (ClientManager)sc.getAttribute("cm");
		ADManager am = (ADManager) sc.getAttribute("am");

		try {
			clientID = (String)session.getAttribute("clientID");
			clientCode = cm.getClientCode(clientID);
			
			Enumeration<String> e = req.getParameterNames();
			while (e.hasMoreElements()) {
				String s = (String)e.nextElement();
				if (s.equals("historyPath")) { insidePage = req.getParameter(s); }
				if (s.equals("histPage")) { histPage = Integer.parseInt(req.getParameter(s)); }
			}
			
			if (insidePage.startsWith("all")) {
				
			} else if (insidePage.startsWith("some")) {
				ADCode = Long.parseLong(req.getParameter("ADCode"));
				HashMap<String, Object> someADInfo = am.selectAD_someAD(ADCode, clientCode);
				HashMap<String, Object> someADHistory = am.selectADHistory_someAD(ADCode, clientCode);
				req.setAttribute("someADHistory", someADHistory);
				req.setAttribute("someADInfo", someADInfo);
			}

			req.setAttribute("insidePage", insidePage);
			req.setAttribute("histPage", histPage);
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ ASTKLogManager.getClassName_now() +"\n"+ex);
			viewPath = (String)sc.getAttribute("ERROR_PATH");
			
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
