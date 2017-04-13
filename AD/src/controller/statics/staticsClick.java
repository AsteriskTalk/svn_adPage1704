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

public class staticsClick  extends HttpServlet {

	protected void doGP(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\nlog : doGP..historyClickController");
		ServletContext sc = req.getServletContext();
		HttpSession session = req.getSession();
		
		String pagePath = (String)sc.getAttribute("INDEX_PAGE");
		String viewPath = "statics.jsp";

		String insidePage = "all.jsp";

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
				if (s.equals("staticsPath")) { insidePage = req.getParameter(s); break; }
			}
			
			if (insidePage.startsWith("all")) {
				tmp = am.selectAD_forStatics(clientCode);
				req.setAttribute("ADStaticsMap_all", tmp);
			} else if (insidePage.startsWith("some")) {
				ADCode = Long.parseLong(req.getParameter("ADCode"));
				tmp = am.selectAD_forStatics(ADCode, clientCode);
				req.setAttribute("ADStaticsMap_some", tmp);
			}

			req.setAttribute("insidePage", insidePage);
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ ASTKLogManager.getClassName_now() +"\n"+ex);
			viewPath = (String)sc.getAttribute("ERROR_PAGE");
			
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
