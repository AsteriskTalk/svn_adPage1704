package controller.management;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import DAO.ClientManager;
import DAO.ETCManager;
import DAO.FunctionManager;
import util.ASTKLogManager;
import util.CharManager;
import util.FileManager;
import util.MailManager;
import util.TimeManager;

public class doSignUp extends HttpServlet {

	protected void doGP(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\nlog : doGP.." + ASTKLogManager.getClassName_now());
		ServletContext sc = req.getServletContext();
		String pagePath = (String)sc.getAttribute("INDEX_PAGE");
		String viewPath =	"";

		final String IMG_PATH = "/client";
		
		final String DEFAULT_FOLDER_URI = (String) sc.getAttribute("DEFAULT_FOLDER_URI");
		final String FULL_URI = DEFAULT_FOLDER_URI + IMG_PATH;
		
		final String DEFAULT_IMG_NAME = (String) sc.getAttribute("DEFAULT_IMG_NAME");
		final String DEFAULT_FOLDER_URL = (String) sc.getAttribute("DEFAULT_FOLDER_URL");
		final String FULL_URL = DEFAULT_FOLDER_URL + IMG_PATH;
		
		final int KB = 1024;
		final int MB = 1024 * 1024;
		final int FILE_SIZE = 50 * MB;
		final String ENC_TYPE = "UTF-8";
		
		long clientCode = 0;
		String clientID = "";
		String clientPW = "";
		String clientEmail = "";
		String clientName = "";
		String clientPhone = "";
		String clientCtt = "";
		String clientLogo = "";
		
		String oldFilePath = "";
		String oldFileName = "";
		String newFileName = "";
		String newFilePath = "";
		String clientLogoURL = "";
		
		if (!FileManager.createFolder(FULL_URI)) { }
		MultipartRequest multi = new MultipartRequest(req, FULL_URI, FILE_SIZE, ENC_TYPE, new DefaultFileRenamePolicy());
		
		ClientManager cm = (ClientManager)sc.getAttribute("cm");
		FunctionManager fm = (FunctionManager) sc.getAttribute("fm");
		ETCManager etc = (ETCManager) sc.getAttribute("etc");
		
		boolean fileResult = false;
		boolean result = false;
		boolean useLogo = false;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try {
			clientID = CharManager.beforeOracle_removeSpace(multi.getParameter("clientID"));
			clientPW = multi.getParameter("clientPW");
			clientEmail = CharManager.beforeOracle_removeSpace(multi.getParameter("clientEmail"));
			clientName = CharManager.beforeOracle_removeSpace(multi.getParameter("clientName"));
			clientPhone = CharManager.beforeOracle_removeSpace(multi.getParameter("clientPhone"));
			clientCtt = CharManager.beforeOracle_withSpace(multi.getParameter("clientCtt"));
			clientLogo = multi.getFilesystemName("clientLogo"); //로고 파일 이름
			
		  useLogo = clientLogo!=null;
		  
		  /* Step 1. 회원가입 - 포인트, 프로필, 인포 작성 및 가입 승인 대기 쿼리 작성 */
			map = cm.doSignUp(clientID, clientPW, clientName, clientEmail, clientPhone, clientCtt);
			result = map.get("result").equals("T");
			
			/* Step 2. 가입 성공 및 로고가 있을 경우와 없을경우에 따른 DB내 경로 수정 */
			if (result) { 
				if (useLogo) {
					clientCode = (Long)map.get("clientCode");
					oldFilePath = FULL_URI;
					oldFileName = clientLogo;
					final String EXT = FileManager.getEXT(oldFileName);
					newFilePath = FULL_URI +"/"+ clientCode;
					newFileName = TimeManager.getTime_forFileName("yyMMdd_hhmm") +"."+EXT;
					fileResult = FileManager.moveFile(oldFilePath, oldFileName, newFilePath, newFileName); //내부에서 해당 클라이언트의 폴더 생성부터 시작함.
				} 
				if (fileResult) { clientLogoURL =  FULL_URL +"/"+ clientCode+"/"+newFileName; }
				else { clientLogoURL = DEFAULT_FOLDER_URL+"/"+DEFAULT_IMG_NAME ; }
				
				/* DB 업데이트 */
				if (fileResult) { result = cm.updateClientProfile_logo(clientCode, clientLogoURL); }
			} 

			/* Step 2. 가입 실패 혹은 가입 성공했으나 파일 이미지 업데이트 실패 => 기존 해당 유저 정보 삭제 
			 * 이미지 업로드 실패의 경우, 파일 기준 아닌 DB 기준이므로 DB 입력 실패는 회원가입 실패로 간주
			 * */
			if (result) { 
				String OTC = (String)map.get("OTC");
				result = MailManager.sendCheckEmail(OTC); 
			}
			
			
			if ( result) { viewPath = "management/successSignUp.html"; }
			else { cm.removeSignUp(clientCode); viewPath ="management/failedSignUp.html"; }
			
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ASTKLogManager.getClassName_now() + "\n"+ex);
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
