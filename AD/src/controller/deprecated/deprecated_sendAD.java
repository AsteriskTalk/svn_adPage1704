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

import DAO.ADManager;
import DTO.ADInfo;
import util.ASTKLogManager;
import util.FCMPushManager;

public class deprecated_sendAD extends HttpServlet {

	protected void doGP(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\nlog : doGP.." + ASTKLogManager.getClassName_now());
		ServletContext sc = req.getServletContext();
		HttpSession ses = req.getSession();
		
		final String ERROR_PATH = (String)sc.getAttribute("errorPath");
		String pagePath = "parsing/requestAD.jsp";//parsing page //has no Redirect..
		
		long userCode = 0;
		long chatGrpCode = 0;
		
		String userSex = "";
		long userAge = 0;
		String userLocal = "";
		
		ADManager am = (ADManager)sc.getAttribute("am");
		
		boolean isSended = false;
		boolean isReceivable = false;
		
		try {
			userCode = Long.parseLong(req.getParameter("userCode"));
			chatGrpCode = (Long)ses.getAttribute(""+userCode);
			
			userSex = req.getParameter("userSex");
			userAge = Long.parseLong(req.getParameter("userAge"));
			userLocal = req.getParameter("userLocal");
			
			isReceivable = am.isADReceivable(userCode);
			
			fcm : if (isReceivable) {
				ArrayList<ADInfo> ADInfoList = new ArrayList<ADInfo>();
				HashMap<String, Object> tmp = new HashMap<String, Object>();
				
				tmp = am.selectAD_condition(userSex, userAge, userLocal);
				if (tmp.get("result").equals("F")) { break fcm; } //Load Failed
				
				else if (tmp.get("result").equals("N")) { // has no conditioned AD
					tmp = am.selectAD_all_sendable(); 
					if (tmp.get("result").equals("T")) { ADInfoList = (ArrayList<ADInfo>)tmp.get("ADInfoList"); } //get All AD(Sendable)
					else { break fcm; } //failed get AD
				}
				else if(tmp.get("result").equals("T")) { ADInfoList = (ArrayList<ADInfo>)tmp.get("ADInfoList"); }
				else { break fcm; }
				ADInfo randomAD = ADInfoList.get( (int)Math.random() * ADInfoList.size() );
				
				isSended = am.insertSendedAD(randomAD, userCode, chatGrpCode);
				if (isSended) { new FCMPushManager().ADPush(userCode, randomAD);  }
				
			}
			
		} catch (Exception ex) {
			System.out.println("try-catch.."+ ASTKLogManager.getClassName_now() +"\n"+ex);
			pagePath = ERROR_PATH;
			
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
