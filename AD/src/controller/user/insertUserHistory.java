package controller.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import DAO.UserManager;
import util.ASTKLogManager;

public class insertUserHistory extends HttpServlet {

	protected void doGP(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\nlog : doGP.." + ASTKLogManager.getClassName_now());
		ServletContext sc = req.getServletContext();
//		String pagePath = (String)sc.getAttribute("INDEX_PAGE");
//		String viewPath =	"parsing/attrToJSON.jsp";
		String pagePath = "parsing/attrToJSON.jsp";
		
		String type =	"";
		String spendType = "N";
		long userId = 0;
		long chatRoomId = 0;
		long point = 0;
		
		UserManager um = (UserManager) sc.getAttribute("um");
		
		boolean isSpend = false;
		boolean result = false;
		
		JSONObject json = new JSONObject();
		
		try {
			type = req.getParameter("type");
			userId = Long.parseLong(req.getParameter("userId"));
			chatRoomId = Long.parseLong(req.getParameter("chatRoomId"));
			point = Long.parseLong(req.getParameter("point"));
			isSpend = type.equals("S");
			
			if (isSpend) { 
				spendType = req.getParameter("spendType"); 
				result = um.insertUserHistory(userId, chatRoomId, type, spendType, point);
			} else { 
				result = um.insertUserHistory(userId, chatRoomId, type, point);
			}
			
			if (result) { json = um.updateUserPoint_astk(userId, chatRoomId, point); }
			
			result = json.get("result").equals("success");
			
			req.setAttribute("result", result);
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ASTKLogManager.getClassName_now()+"\n"+ex);
//			viewPath = (String)sc.getAttribute("ERROR_PAGE");
			
		} finally {
//			req.setAttribute("viewPath", viewPath);
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
