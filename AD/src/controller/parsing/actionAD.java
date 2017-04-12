package controller.parsing;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.FunctionManager;
import util.ADTools;
import util.ASTKLogManager;

public class actionAD  extends HttpServlet {
	protected void doGP(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\nlog : doGP.." + ASTKLogManager.getClassName_now());
		ServletContext sc = req.getServletContext();
		String pagePath = "parsing/attrToJSON.jsp";
		
		long sendedCode = 0;
		long point = 0;
		String action = "";
		
		FunctionManager fm = (FunctionManager) sc.getAttribute("fm");
		
		boolean result = false;
		
		try {
			Enumeration e = req.getParameterNames();
			while (e.hasMoreElements()) {
				String s = (String) e.nextElement();
				if (s.equals("sendedCode")) { sendedCode = Long.parseLong(req.getParameter(s)); }
				else if (s.equals("action")) { action = req.getParameter(s); }
				else if (s.equals("point")) { point = Long.parseLong(req.getParameter(s)); }
			}
			
			if (action.equals("V")) { result = fm.viewAD(sendedCode); } 
			else if (action.equals("T")) { result = fm.touchAD(sendedCode, point); }
			else { result = false; }
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ ASTKLogManager.getClassName_now() + "\n"+ex );
			result = false;
			
		} finally {
			req.setAttribute("result", result);
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
