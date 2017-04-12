package controller.main;

import java.io.IOException;
import java.util.ArrayList;
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
import DTO.ADInfo;
import DTO.ADTarget;
import util.ASTKLogManager;

public class ADClick extends HttpServlet {

	protected void doGP(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\nlog : doGP..adClickController");
		ServletContext sc = req.getServletContext();
		HttpSession session = req.getSession();
		
		String indexPath = (String)sc.getAttribute("INDEX_PAGE");
		String viewPath = "adMain.jsp";
		
		String insidePage = "main.jsp";
		
		String clientID = "";
		long clientCode = 0;
		long ADCode = 0;
		
		ClientManager cm = (ClientManager)sc.getAttribute("cm");
		ADManager am = (ADManager)sc.getAttribute("am");
		
		try {
			clientID = (String)session.getAttribute("clientID");
			clientCode = cm.getClientCode(clientID);
			Enumeration<String> e = req.getParameterNames();
			while (e.hasMoreElements()) {
				String s = (String)e.nextElement();
				if (s.equals("ADPath")) { insidePage = req.getParameter(s); break; }
			}
			
			if(insidePage.startsWith("select")) {
				ADCode = Long.parseLong(req.getParameter("ADCode"));
				HashMap<String, Object> ADInfoSet = am.selectAD_someAD_withTarget(ADCode, clientCode);
				req.setAttribute("ADInfoSet", ADInfoSet);
				
			} else if (insidePage.startsWith("status")) {
			
			} else if (insidePage.startsWith("main")) {
				
			}
			
			req.setAttribute("insidePage", insidePage);
			
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ ASTKLogManager.getClassName_now() +"\n"+ex);
			viewPath = (String)sc.getAttribute("ERROR_PAGE");
			
		} finally {
			req.setAttribute("viewPath", viewPath);
			RequestDispatcher rd = req.getRequestDispatcher(indexPath);
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
