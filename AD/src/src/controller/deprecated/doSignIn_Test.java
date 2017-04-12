package controller.deprecated;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class doSignIn_Test extends HttpServlet {

	protected void doGP(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\nlog : doGP..doSignIn_TestController");
		
		final int SESSION_TIME = 1000 * 60 * 30; // 밀리초 * 초 * 분 
		
		String indexPath = (String)req.getAttribute("indexPath");
		String pagePath = "/headerClick.ad";
		
		String viewPath = "";
		String clientID = "";
		String clientPW = "";
		
		try {
			clientID = req.getParameter("clientID");
			clientPW = req.getParameter("clientPW");
			viewPath = req.getParameter("viewPath");
			
			ArrayList<HashMap<String, Object>> ADList = new ArrayList<HashMap<String, Object>>();
			for (int i=0; i<5 ; i++) {
				HashMap<String, Object> AD = new HashMap<String, Object>();
				AD.put("ADCtt", "AD No."+i);
				AD.put("ADPoint", Math.random()*9999);
				AD.put("ADBonus", Math.random()*100/10);
				ADList.add(AD);
			}
			HashMap<String, Object> signInResultSet = new HashMap<String, Object>();
			
			signInResultSet.put("clientID", clientID);
			signInResultSet.put("clientPoint", 123456);
			signInResultSet.put("ADList", ADList);
			
			HttpSession session = req.getSession();
			session.setAttribute("clientID", clientID);
			session.setMaxInactiveInterval(SESSION_TIME);
			
			req.setAttribute("viewPath", viewPath);
			req.setAttribute("signInResultSet", signInResultSet);
			req.setAttribute("clientID", clientID);
			
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
