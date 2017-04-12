package controller.management;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import DAO.ETCManager;
import util.ASTKLogManager;
import util.FileManager;

public class sendNotice extends HttpServlet {

	protected void doGP(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\nlog : doGP.." + ASTKLogManager.getClassName_now());
		ServletContext sc = req.getServletContext();
		String pagePath = (String)req.getAttribute("indexPath");
		final String NOTICE_PATH = (String)sc.getAttribute("filePath") + "/notice";
		final int MAX_SIZE = 1024 * 20;
		final String ENC_TYPE = "UTF-8";
		final String DEFAULT_IMG_ADDR = (String) sc.getAttribute("DIA");
		String viewPath =	"management/noticeResult.jsp";
		
		String noticeSbj = "";
		String noticeCtt = "";
		String noticeImgAddr = "";
		String fileName = "";
		
		MultipartRequest multi = new MultipartRequest(req, NOTICE_PATH, MAX_SIZE, ENC_TYPE, new DefaultFileRenamePolicy() );
		
		ETCManager etc = (ETCManager)sc.getAttribute("etc");
		
		boolean result = false;
		
		try {
			noticeSbj = multi.getParameter("noticeSbj");
			noticeCtt = multi.getParameter("noticeCtt");
			fileName = multi.getFilesystemName("noticeImg");
			
			result = FileManager.renameFile(NOTICE_PATH, fileName);
			if (!result ) { noticeImgAddr = DEFAULT_IMG_ADDR; }
			
			result = etc.insertNotice(noticeSbj, noticeCtt, noticeImgAddr);
			
			req.setAttribute("result", result);
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ASTKLogManager.getClassName_now()+"\n"+ex);
			viewPath ="error.jsp";
			
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
