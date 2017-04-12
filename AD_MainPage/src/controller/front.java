package controller;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

public class front extends HttpServlet {
	private void doGP(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html; charset=utf-8");
		req.setCharacterEncoding("utf-8");
		ServletContext sc = req.getServletContext();

		String reqPath = req.getServletPath();
		String[] tmp = reqPath.split("\\.");
		final String SERVLET_PATH = tmp[0];
		final String PAGE_PATH = "/" + SERVLET_PATH + ".jsp";
		final String INDEX_PATH = "index.jsp";

		req.setAttribute("pagePath", PAGE_PATH);
		req.setAttribute("indexPath", INDEX_PATH);

		RequestDispatcher rd = req.getRequestDispatcher(SERVLET_PATH);

		try {
			rd.forward(req, resp);
		} catch (Exception ex) {
			System.out.println("log:this is Front");
		}

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGP(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGP(req, resp);
	}

}
