package controller.management;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.ClientManager;
import DTO.ClientProfile;
import util.ADTools;
import util.ASTKLogManager;
import util.CharManager;
import util.MailManager;

public class doFindPW extends HttpServlet {

	protected void doGP(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\nlog : doGP.." + ASTKLogManager.getClassName_now());
		ServletContext sc = req.getServletContext();
		String page = "management/findResult.jsp";
		
		long clientCode = 0;
		String clientID = "";
		long question = 0;
		String answer = "";
		
		ClientManager cm = (ClientManager)sc.getAttribute("cm");
		
		boolean result = false;
		String msg = "";
		final String NO_MATCH = "아이디, 질문, 답변이 매칭되는 결과가 없습니다.";
		final String ERROR_MSG = "서버에 문제가 발생하였습니다.<br>잠시 후 다시 시도해주세요.";
		
		try {
			clientID = CharManager.beforeOracle_removeSpace(req.getParameter("clientID"));
			question = Integer.parseInt(req.getParameter("question"));
			answer = CharManager.beforeOracle_removeSpace(req.getParameter("answer"));
			
			clientCode = cm.getClientCode(clientID);
			if (clientCode != -1) {
				result = cm.matchClientQnA(clientCode, question, answer);
				if (result) { //아이디, 질문, 답변이 일치할 경우
					result = cm.changeClientPW_withEmail(clientCode);
					if (result) { msg = "가입하신 메일로 임시 비밀번호를 발송하였습니다."; }  //메일 성공
					else { msg = ERROR_MSG; } //메일 실패
					
				}	else { //아이디, 질문 답변이 일치하지 않는 경우.
					msg = NO_MATCH;
				}
				
			} else { //아이디가 존재하지 않는 경우
				msg = NO_MATCH;
				
			}					
			
			req.setAttribute("msg", msg);	
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ASTKLogManager.getClassName_now()+"\n"+ex);
			page = (String) sc.getAttribute("ERROR_SERVLET_PATH");
			req.setAttribute("ex", ex);
			
		} finally {
			RequestDispatcher rd = req.getRequestDispatcher(page);
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
