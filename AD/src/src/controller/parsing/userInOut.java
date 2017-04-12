package controller.parsing;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.ETCManager;
import DAO.FunctionManager;
import util.ASTKLogManager;

public class userInOut  extends HttpServlet {

	protected void doGP(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\nlog : doGP.." + ASTKLogManager.getClassName_now());
		ServletContext sc = req.getServletContext();
		String pagePath = "parsing/attrToJSON.jsp"; //parsing page
		
		long userCode = 0;
		long chatGrpCode = 0;
		String receiveType = "";
		
		ETCManager etc = (ETCManager) sc.getAttribute("etc");
		FunctionManager fm = (FunctionManager) sc.getAttribute("fm");
		
		boolean result = false;
		String sendResult = "C"; //Can't .. 보내는것 접근도 못한 경우
		
		try {
			Enumeration e = req.getParameterNames();
			while (e.hasMoreElements()) {
				String s = (String)e.nextElement();
				if (s.equals("userCode") || s.equals("userId")) { userCode = Long.parseLong(req.getParameter(s)); }
				if (s.equals("chatGrpCode") || s.equals("chatRoomId")) { chatGrpCode = Long.parseLong(req.getParameter(s)); }
			}
			
			receiveType = req.getParameter("type");
			
			if (receiveType.equals("I")) { 
				result = etc.insertOCG(userCode, chatGrpCode); //우선 채팅방을 열림 상태로 두고
				if (result) {	sendResult = fm.sendAD(userCode, chatGrpCode); } //열림상태 성공시 광고를 즉시 하나 발송한다.
				req.setAttribute("sendResult", sendResult);
				
			} else if (receiveType.equals("O")) {
				result = etc.deleteOCG(userCode, chatGrpCode); //채팅방을 닫는다.
			}
			
//			System.out.println("log : GP time - "+ (System.currentTimeMillis()-NOW));
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ ASTKLogManager.getClassName_now() +"\n"+ ex);
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
