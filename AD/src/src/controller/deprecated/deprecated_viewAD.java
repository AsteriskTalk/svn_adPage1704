package controller.deprecated;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.ADManager;
import DTO.ADInfo;
import util.ASTKLogManager;

public class deprecated_viewAD  extends HttpServlet {
	protected void doGP(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\nlog : doGP.." + ASTKLogManager.getClassName_now());
		ServletContext sc = req.getServletContext();
		String pagePath = "parsing/viewAD.jsp";
		
		long userCode = 0;
		long chatGrpCode = 0;
		long ADCode = 0;
		long clientCode = 0;
		
		ADManager am = (ADManager) sc.getAttribute("am");

		boolean result = false;
		
		try {
			userCode = Long.parseLong(req.getParameter("userCode"));
			chatGrpCode = Long.parseLong(req.getParameter("chatGrpCode"));
			ADCode = Long.parseLong(req.getParameter("ADCode"));
			clientCode = Long.parseLong(req.getParameter("clientCode"));
			
			req.setAttribute("result", result);
			
		} catch (Exception ex) {
			
		} finally {
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
