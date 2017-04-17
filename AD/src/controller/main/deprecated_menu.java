package controller.main;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.ADTools;
import util.ASTKLogManager;

public class deprecated_menu  extends HttpServlet {

	protected void doGP(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\nlog : doGP.."+ ASTKLogManager.getClassName_now());
		ServletContext sc = req.getServletContext();
		HttpSession ses = req.getSession();
		
		String servletPath = (String)sc.getAttribute("INDEX_SERVLET_PATH");
		boolean isSignIn = false;
		
		try {
			Enumeration<String> e = req.getParameterNames();
			while (e.hasMoreElements()) {
				String s = (String) e.nextElement();
				if (s.equals("servletPath")) { servletPath = req.getParameter(s); }
			} 
			isSignIn = ADTools.isSignIn(ses);
			System.out.println("menu : servlet - " + servletPath );
			
			/** SignIn 이 필요한 Page의 경우에 대한 처리 */
			if ( servletPath.startsWith("my")) {
				if (!isSignIn) {
					req.setAttribute("servletPath", servletPath);
					servletPath = "signIn.ad";
				}
				
				/** signIn 되어 있는 경우 
				 * */
				if (isSignIn) { 
					
				}
			}
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ ASTKLogManager.getClassName_now() +"\n"+ex);
			servletPath = (String) sc.getAttribute("ERROR_SERVLET_PATH");
			req.setAttribute("ex", ex);
			
		} finally {
			System.out.println(servletPath);
			RequestDispatcher rd = req.getRequestDispatcher(servletPath);
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
