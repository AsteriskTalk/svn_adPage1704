package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Tools;

public class index extends HttpServlet {
	protected void doGP(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\nlog: doGP..indexController");

		String pagePath = (String) req.getAttribute("pagePath");

		String clickPath = "main/main.html";
		int county = 0;

		try {
			county = Tools.getCount();
			Tools.addCount();
			
			req.setAttribute("county", county);
			req.setAttribute("clickPath", clickPath);
		} catch (Exception ex) {
			System.out.println("log : try-catch..index.start\n"+ ex);

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
		// TODO Auto-generated method stub
		this.doGP(req, resp);
	}

}
