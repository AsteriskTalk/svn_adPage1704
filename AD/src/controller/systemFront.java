package controller;

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

public class systemFront extends HttpServlet {
	protected void doGP(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");
		req.setCharacterEncoding("utf-8");
		ServletContext sc = req.getServletContext();
		HttpSession ses = req.getSession();
		
		String reqPath = req.getServletPath();
		String[] tmp = reqPath.split("\\.");
		String servletPath = tmp[0].substring(1); //맨 앞의 /(slash) 제거
		
		String remoteAddr = "";
		String forwardAddr = "";
		
		try {
			remoteAddr = req.getRemoteAddr();
			forwardAddr = req.getHeader("x-forwarded_for");
			
		} catch (Exception ex) {
			System.out.println("log : SYSTEM_FRONT\n"+ex);
			servletPath = "/index";
			
		} finally {
//			System.out.println("front..rd : " + servletPath);
			RequestDispatcher rd = req.getRequestDispatcher(servletPath);
			rd.forward(req, resp);
			
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGP(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGP(req, resp);
	}

}
