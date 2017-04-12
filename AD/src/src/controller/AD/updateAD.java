package controller.AD;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import DAO.ADManager;
import util.ASTKLogManager;
import util.FileManager;

public class updateAD  extends HttpServlet {
	protected void doGP(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\nlog : doGP.." + ASTKLogManager.getClassName_now());
		ServletContext sc = req.getServletContext();
		String pagePath = (String) sc.getAttribute("INDEX_PAGE");
		String viewPath = "common/result.jsp";

		final String IMG_PATH = "/image";
		
		final String DEFAULT_FOLDER_URI= (String)sc.getAttribute("DEFAULT_FOLDER_URI");
		final String FULL_URI = DEFAULT_FOLDER_URI + IMG_PATH;
		
		final String DEFAULT_FOLDER_URL = (String) sc.getAttribute("DEFAULT_FOLDER_URL");
		final String FULL_URL = DEFAULT_FOLDER_URL + IMG_PATH;
		
		final int KB = 1024;
		final int MB = 1024 * 1024;
		final int FILE_SIZE = 50 * MB;
		final String ENC_TYPE = "UTF-8";
		
		long ADCode = 0;
		long clientCode = 0;
		
		String newCtt = "";
		String newImgAddr = "";
		
		String oldURL = "";
		String newURL = "";
		
		long newBonus = 0;
		long oldBonus = 0;
		
		long newCount = 0;
		long remainCount = 0;
		
		
		HashMap<String, Object> ADTarget = new HashMap<String, Object>();
		
		boolean useSex = false;
		boolean useAge = false;
		boolean useLocal = false;

		ADManager am = (ADManager) sc.getAttribute("am");
		
		boolean result = false;
		
		if (!FileManager.createFolder(FULL_URI)) { }
		MultipartRequest multi = new MultipartRequest(req, FULL_URI, FILE_SIZE, ENC_TYPE, new DefaultFileRenamePolicy());
		
		try {
			ADCode = Long.parseLong(multi.getParameter("ADCode"));
			clientCode = Long.parseLong(multi.getParameter("clientCode"));
			
			newCtt = multi.getParameter("newCtt");
			newURL = multi.getParameter("newURL");
			oldURL = multi.getParameter("oldURL");
			newBonus = Long.parseLong(multi.getParameter("newBonus"));
			oldBonus = Long.parseLong(multi.getParameter("oldBonus"));
			newCount = Long.parseLong(multi.getParameter("newCount"));
			remainCount = Long.parseLong(multi.getParameter("remainCount"));
			useSex = multi.getParameter("useSex").equals("T") ? true : false;
			useAge = multi.getParameter("useAge").equals("T") ? true : false;
			useLocal = multi.getParameter("useLocal").equals("T") ? true : false;
			
			/*
			Enumeration tmpEnu = multi.getFileNames();
			if (!tmpEnu.hasMoreElements()) { newImgAddr = "stay"; }
			else { newImgAddr = FULL_URI + "/" + multi.getFilesystemName("ADImg"); }
			
			ADTarget.put("useSex", useSex);
			ADTarget.put("useAge", useAge);
			ADTarget.put("useLocal", useLocal);
			
			if (useSex) { ADTarget.put("sexValue", multi.getParameter("sexValue")); }
			if (useLocal) { ADTarget.put("localValues", multi.getParameterValues("localValues")); }
			if (useAge) {
				String ageType = multi.getParameter("ageType");
				ADTarget.put("ageType", ageType);
				if (ageType.equals("some")) { ADTarget.put("ageValue", multi.getParameter("ageValue")); }
				else if (ageType.equals("between")) { 
					ADTarget.put("ageValue1", multi.getParameter("ageValue1"));
					ADTarget.put("ageValue2", multi.getParameter("ageValue2"));
				}
			}
			
			result = am.updateAD(ADCode, clientCode, newCtt, oldURL, newURL, newImgAddr, newBonus, oldBonus, remainCount, newCount, ADTarget);
			*/
			
			req.setAttribute("result", result);
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ ASTKLogManager.getClassName_now() +"\n"+ex);
			viewPath = (String) sc.getAttribute("ERROR_PAGE");
			
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
