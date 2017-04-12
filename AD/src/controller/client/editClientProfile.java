package controller.client;

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

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import DAO.ClientManager;
import util.ASTKLogManager;
import util.FileManager;

public class editClientProfile extends HttpServlet {

	protected void doGP(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\nlog : doGP.." + ASTKLogManager.getClassName_now());
		ServletContext sc = req.getServletContext();
		String pagePath = (String)req.getAttribute("indexPath");
		String viewPath = "/editProfile.jsp";
		
		final String FILE_PATH= (String)sc.getAttribute("filePath");
		final String IMG_PATH = "/client";
		final String FULL_PATH = FILE_PATH + IMG_PATH;
		final int KB = 1024;
		final int MB = 1024 * 1024;
		final int FILE_SIZE = 50 * MB;
		final String ENC_TYPE = "UTF-8";

		if (!FileManager.createFolder(FULL_PATH)) { }
		MultipartRequest multi = new MultipartRequest(req, FULL_PATH, FILE_SIZE, ENC_TYPE, new DefaultFileRenamePolicy());
		
		long clientCode = 0;
		String newName = "";
		String newCtt = "";
		String newLogoAddr = "";
		
		ClientManager cm = (ClientManager)sc.getAttribute("cm");
		
		HashMap<String, Object> fileResultSet = new HashMap<String, Object>();
		boolean result = false;
		
		try {
			clientCode = Long.parseLong(multi.getParameter("clientCode"));
			newName = multi.getParameter("newName");
			newCtt = multi.getParameter("newCtt");
			
			Enumeration tmpEnu = multi.getFileNames();
			if (!tmpEnu.hasMoreElements()) { newLogoAddr = "stay"; }
			else {
				String filePath = FULL_PATH + "/" + clientCode + "/logo";
				String fileName = "" + System.currentTimeMillis();
				fileResultSet = FileManager.moveFile(multi, FULL_PATH, filePath, fileName);
			}
			
			if (fileResultSet.get("result").equals("T")) { newLogoAddr = ((ArrayList<String>)fileResultSet.get("fileNames")).get(0); }
			else if (fileResultSet.get("result").equals("F")) { newLogoAddr = "error"; }
			
			result = cm.updateClientProfile(clientCode, newLogoAddr, newCtt, newName);
			
			req.setAttribute("viewPath", viewPath);
			req.setAttribute("result", result);
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ ASTKLogManager.getClassName_now() +"\n"+ ex);
			viewPath = "/error.jsp";
			
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
