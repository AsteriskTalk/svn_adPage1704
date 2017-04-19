package controller.statics;

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

public class viewStatics  extends HttpServlet {

	protected void doGP(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\nlog : doGP.."+ ASTKLogManager.getClassName_now());
		ServletContext sc = req.getServletContext();
		HttpSession session = req.getSession();
		 
		String page = (String)sc.getAttribute("INDEX_PAGE");
		String view = "statics/main.jsp";
		String insidePage = "all.jsp";
		String goTo = "all";

		String clientID = "";
		long clientCode = 0;
		long ADCode = 0;
		
		ClientManager cm = (ClientManager)sc.getAttribute("cm");
		ADManager am = (ADManager) sc.getAttribute("am");
		
		HashMap<String, Object> tmp = new HashMap<String, Object>();
		
		try {
			clientID = (String)session.getAttribute("clientID");
			clientCode = cm.getClientCode(clientID);
			
			Enumeration<String> e = req.getParameterNames();
			while (e.hasMoreElements()) {
				String s = (String)e.nextElement();
				if (s.equals("goTo")) { goTo = req.getParameter(s); }
				else if (s.equals("ADCode")) { ADCode = Long.parseLong(req.getParameter(s)); }
			}
			
			if (goTo.startsWith("all")) {
				insidePage = "all.jsp";
				tmp = am.selectAD_forStatics(clientCode);
				req.setAttribute("ADStaticsMap_all", tmp);
				
			} else if (goTo.startsWith("list")) {
				insidePage = "../common/ADList.jsp";
				req.setAttribute("servletPath", "myStatics.ad");
				
			} else if (goTo.startsWith("select")) {
				insidePage = "select.jsp";
				tmp = am.selectAD_forStatics(ADCode, clientCode);
				req.setAttribute("ADStaticsMap_some", tmp);
				
			}

			req.setAttribute("insidePage", insidePage);
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ ASTKLogManager.getClassName_now() +"\n"+ex);
			view = (String)sc.getAttribute("ERROR_PAGE");
			
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
