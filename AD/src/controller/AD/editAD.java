package controller.AD;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.ADManager;
import util.ASTKLogManager;

public class editAD  extends HttpServlet {

	protected void doGP(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\nlog : doGP.." + ASTKLogManager.getClassName_now());
		ServletContext sc = req.getServletContext();
		String pagePath = (String)sc.getAttribute("INDEX_PAGE");
		String viewPath =	"ad/editAD.jsp";
		
		long ADCode = 0;
		long clientCode = 0;
		
		ADManager am = (ADManager) sc.getAttribute("am");
		
		HashMap<String, Object> ADInfoSet = new HashMap<String ,Object>();
		HashMap<String ,Object> ADTargetSet = new HashMap<String, Object>();
		
		try {
			ADCode = Long.parseLong(req.getParameter("ADCode"));
			clientCode = Long.parseLong(req.getParameter("clientCode"));
			
			ADInfoSet = am.selectAD_someAD(ADCode, clientCode);
			ADTargetSet = am.selectADTarget_someAD_allTarget(ADCode, clientCode);
			
			req.setAttribute("ADInfoMap_some", ADInfoSet);
			req.setAttribute("ADTargetMap_some", ADTargetSet);
			
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
