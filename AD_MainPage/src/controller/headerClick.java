package controller;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Tools;

public class headerClick extends HttpServlet {

	protected void doGP(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\nlog : doGP..headerClickController");
		
		String pagePath = (String)req.getAttribute("indexPath");
		
		String clickPath = "";
		
		boolean hasParam = false;		
		try {
			Enumeration<String> e = req.getParameterNames();
			
			while (e.hasMoreElements()) {
				String paramName = e.nextElement();
				if (paramName.equals("clickPath")) { hasParam = true; }
			}

			if (hasParam) {
				clickPath = req.getParameter("clickPath");
				
				if (Tools.isNull(clickPath)) {
					clickPath = "common/error.jsp";
					req.setAttribute("error", "Parameter Value is null");
				}
				
			} else {
				clickPath = "common/error.jsp";
				req.setAttribute("error", "no Parameter");
			}
			
		} catch (Exception ex) {
			clickPath = "common/error.jsp";
			req.setAttribute("error", ex);
			
		} finally {
			req.setAttribute("clickPath", clickPath);
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
