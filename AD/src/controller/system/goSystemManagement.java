package controller.system;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.ASTKLogManager;
import util.CharManager;

public class goSystemManagement extends HttpServlet {

	protected void doGP(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\nlog : doGP.." + ASTKLogManager.getClassName_now());
		ServletContext sc = req.getServletContext();
		String page = (String)sc.getAttribute("INDEX_PAGE");
		String view = "system/view.jsp";
		
		final String PW = "*astk1027talk!";
		String systemPW = "";
		
		boolean auth = false;
		
		try {
			auth = (Boolean) req.getAttribute("auth");
			systemPW = CharManager.beforeOracle_removeSpace(req.getParameter("systemPW"));
			
			if (systemPW.equals(PW)) {
				String html = "";
				
				html += "비밀번호 찾기 질문 추가<br>";
				html += "<form action='addClientPWQuestion.sys' method='post' >";
				html += "<input type='text' name='question' placeholder='질문을 입력하세요.' required /> ";
				html += "<input type='submit' value='추가하기' />";
				html += "</form><br>";
				
				html += "공지사항<br>";
				html += "<form action='insertNotice.sys' method='post' >";
				html += "<input type='text' name='noticeSbj' placeholder='공지사항 제목' required />";
				html += "<input type='text' name='noticeCtt' placeholder='공지사항 내용' required />";
				html += "전달 일자 : <input type='date' name='noticeDate' requeired />";
				html += "전달 시각 : <input type='time' name='noticeTime'  required />";
				html += "<input type='file' name='noticeImg' placeholder='공지 첨부 이미지' required />";
				html += "</form>";
				req.setAttribute("html", html);
				req.setAttribute("view", view);
			} else {
				page = (String) sc.getAttribute("INDEX_SERVLET_PATH");
			}
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ASTKLogManager.getClassName_now()+"\n"+ex);
			page = (String) sc.getAttribute("ERROR_SERVLET_PATH");
			req.setAttribute("ex", ex);
			req.setAttribute("msg", "인증되지 않은 접근");
			
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
