package controller.AD;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.ADManager;
import util.ASTKLogManager;

public class readyAD  extends HttpServlet {

	protected void doGP(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\nlog : doGP.." + ASTKLogManager.getClassName_now());
		ServletContext sc = req.getServletContext();
		String pagePath = (String)sc.getAttribute("INDEX_PAGE");
		String viewPath =	"ad/main.jsp";
		
		long ADCode = 0;
		long clientCode = 0;
		
		ADManager am = (ADManager) sc.getAttribute("am");
		
		HashMap<String, Object> map = new HashMap<String ,Object>();
		
		try {
			ADCode = Long.parseLong(req.getParameter("ADCode"));
			clientCode = Long.parseLong(req.getParameter("clientCode"));
			viewPath = req.getParameter("viewPage");
			
			map = am.selectAD_someAD(ADCode, clientCode);
			req.setAttribute("ADInfoMap_some", map);
			map = am.selectADTarget_someAD_allTarget(ADCode, clientCode);
			req.setAttribute("ADTargetMap_some", map);
			map = am.selectADHistory_someAD(ADCode, clientCode);
			req.setAttribute("ADHistoryMap_some", map);
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ASTKLogManager.getClassName_now()+"\n"+ex);
			viewPath = (String) sc.getAttribute("ERROR_PAGE");
			req.setAttribute("ex", ex);
			
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
