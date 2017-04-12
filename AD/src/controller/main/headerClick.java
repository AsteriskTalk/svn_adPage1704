package controller.main;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.ClientManager;
import util.ADTools;
import util.ASTKLogManager;

public class headerClick  extends HttpServlet {

	protected void doGP(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\nlog : doGP..headerClickController");
		ServletContext sc = req.getServletContext();
		HttpSession ses = req.getSession();
		
//		String pagePath = (String)sc.getAttribute("INDEX_PAGE");
		String pagePath = "";
		String viewPath = "";
		final String HISTORY_INSIDE_PAGE = "clientHistory.jsp";
		final String STATICS_INSIDE_PAGE = "all.jsp";
		final String AD_INSIDE_PAGE = "main.jsp";
		String insidePage = "";
		
		boolean isSignIn = false;
		
		try {
			pagePath = req.getParameter("pagePath");
			viewPath = req.getParameter("viewPath");
			isSignIn = ADTools.isSignIn(ses);
			
			/** SignIn 이 필요한 Page의 경우에 대한 처리 */
			if (viewPath.equals("adMain.jsp") || viewPath.equals("history.jsp") || viewPath.equals("statics.jsp") || viewPath.equals("mypage.jsp") ) {

				/** signIn에 대한 ResultSet 이 없는 경우에는 signIn 으로 보낸다. 
				 * 이때, 기존의 path 값을 보존하기 위해 setAttribute 해둔다.*/
				if (!isSignIn) {
					req.setAttribute("savedPagePath", pagePath);
					req.setAttribute("savedViewPath", viewPath);
					pagePath = (String)sc.getAttribute("INDEX_PAGE");
					viewPath = "management/signIn.jsp";
				}
				
				/** signIn 되어 있는 경우 
				 * */
				if (isSignIn) { 
					if (viewPath.startsWith("adMain")) { insidePage = AD_INSIDE_PAGE;}
					else if (viewPath.startsWith("history")) { insidePage = HISTORY_INSIDE_PAGE; }
					else if (viewPath.startsWith("statics")) { insidePage = STATICS_INSIDE_PAGE; }
					else {  }
					
					req.setAttribute("insidePage", insidePage);
				}
			}
			
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
