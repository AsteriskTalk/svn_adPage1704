package controller.AD;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import DAO.ADManager;
import DAO.ClientManager;
import DTO.ADTarget;
import util.ASTKLogManager;
import util.CharManager;
import util.FileManager;
import util.TimeManager;

public class addAD  extends HttpServlet {
	protected void doGP(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\nlog : doGP..addAD");
		ServletContext sc = req.getServletContext();
		HttpSession ses = req.getSession();

		String pagePath =(String) sc.getAttribute("INDEX_PAGE");
		String viewPath = "";
		
		final String SUCCESS_PATH = "common/success.jsp";
		final String FAILED_PATH = "common/failed.jsp";
		String outPrintMsg = "";

		final String ASTK_URL = (String) sc.getAttribute("ASTK_URL");
		final String IMG_PATH = "/client";
		
		final String DEFAULT_IMG_NAME = (String) sc.getAttribute("DEFAULT_IMG_NAME");
		final String DEFAULT_FOLDER_URL = (String) sc.getAttribute("DEFAULT_FOLDER_URL");
		final String FULL_URL = DEFAULT_FOLDER_URL + IMG_PATH;
		
		final String DEFAULT_FOLDER_URI = (String) sc.getAttribute("DEFAULT_FOLDER_URI");
		final String FULL_URI = DEFAULT_FOLDER_URI + IMG_PATH;
		
		final int KB = 1024;
		final int MB = 1024 * 1024;
		final int FILE_SIZE = 50 * MB;
		final String ENC_TYPE = "UTF-8";
		
		String clientID = "";
		long clientCode = 0;		
		String ADCtt = "";
		String ADURL = "";
		String ADImgFileName = "";
		long ADCount = 0;
		long ADBonus = 0;
		File ADImg = null;
		
		String oldFilePath = "";
		String oldFileName = "";
		String newFilePath = "";
		String newFileName = "";
		
		String ADImgURL = "";
		
		ClientManager cm = (ClientManager)sc.getAttribute("cm");
		ADManager am = (ADManager)sc.getAttribute("am");
		CharManager cc = new CharManager();
		
		ArrayList<ADTarget> ADTargetList = new ArrayList<ADTarget>();
		HashMap<String, Object> tmp = new HashMap<String, Object>();
		
		String result = "F";
		
		if (!FileManager.createFolder(FULL_URI)) { }
		MultipartRequest multi = new MultipartRequest(req, FULL_URI, FILE_SIZE, ENC_TYPE, new DefaultFileRenamePolicy());
		
		boolean fileResult = false;
		boolean hasImgFile = false;
		boolean hasTarget = false;
		
		try {
			clientID = (String)ses.getAttribute("clientID");
			clientCode = cm.getClientCode(clientID);
			
			ADCtt = cc.beforeOracle_withSpace(multi.getParameter("ADCtt"));
			ADURL = cc.beforeOracle_withSpace(multi.getParameter("ADURL"));
			ADImgFileName = multi.getFilesystemName("ADImg");
			ADCount = Long.parseLong(multi.getParameter("ADCount"));
			ADBonus = Long.parseLong(multi.getParameter("ADBonus"));
			
			if (ADURL == null) { ADURL = ASTK_URL; }
			hasImgFile = (ADImgFileName != null);

			if (clientCode == -1 || clientCode == 0) {
				result = "F"; 
				
			}	else {
				if (hasImgFile) {
					oldFilePath = FULL_URI;
					oldFileName = ADImgFileName;
					final String EXT = FileManager.getEXT(oldFileName);
					
					newFilePath = FULL_URI +"/"+ clientCode;
					newFileName = TimeManager.getTime_forFileName("yyMMdd_hhmm") +"."+ EXT;
					
					fileResult = FileManager.moveFile(oldFilePath, oldFileName, newFilePath, newFileName );
				}

				if (fileResult) { ADImgURL = FULL_URL +"/"+ clientCode +"/"+ newFileName; }
				else { ADImgURL = "null" ; }
				
//				if (fileResult || !hasImgFile) { /** 업로드 성공 혹은 파일 이미지 없는 경우..일단 업로드 실패시에도 등록되게 처리한다. */
					Enumeration e = multi.getParameterNames();
					while (e.hasMoreElements()) {
						String s = (String)e.nextElement();
						if (s.equals("ADTarget")) { hasTarget = true; break; }
					}
					
					if (hasTarget) { ADTargetList = am.getADTargetList_multipart(multi); } 
					
					result = am.addAD(clientCode, ADCtt, ADURL, ADImgURL, ADCount, ADBonus, ADTargetList);
					
					if (result.equals("T")) { 
						outPrintMsg = "업로드에 성공하였습니다.";
						viewPath = SUCCESS_PATH; 
						HashMap<String, Object> map = am.selectAD_allAD(clientCode);
						ses.setAttribute("ADInfoMap_all", map); //업뎃 직후 인포셋 교체
						
					} else {
						if (result.equals("N")) { outPrintMsg = "포인트가 부족합니다."; }
						else if (result.equals("E")) { outPrintMsg = "에러가 발생하였습니다."; }
						else { outPrintMsg = "실패하였습니다."; }
					}
//				} else {
//					outPrintMsg = "이미지 업로드에 실패하였습니다.";
//				}
			}
			
			req.setAttribute("outPrint", outPrintMsg);
			req.setAttribute("result", result);
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ASTKLogManager.getClassName_now()+"\n"+ex);
			viewPath = (String) sc.getAttribute("ERROR_PAGE");
			
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
