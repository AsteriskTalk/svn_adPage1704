package util;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.oreilly.servlet.MultipartRequest;

import DAO.ADManager;
import DAO.ClientManager;

public class ADTools {
	
//	public static void refreshInfo(HttpSession ses, ServletContext sc) {
//		boolean isSignIn = false; 
//		long lastUpdateTime = 0;
//		String clientID = "";
//		final long NOW = System.currentTimeMillis();
//		final long UPDATE_INTERVAL = 1000 * 60 * 5;
//		
//		ClientManager cm = (ClientManager) sc.getAttribute("cm");
//		ADManager am = (ADManager) sc.getAttribute("am");
//		
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		
//		Enumeration e = ses.getAttributeNames();
//		while (e.hasMoreElements()) {
//			String s = (String) e.nextElement();
//			if (s.equals("clientID")) { isSignIn = true; clientID = (String) ses.getAttribute(s); }
//			if (s.equals("lastUpdateTime")) { lastUpdateTime = (Long) ses.getAttribute(s); }
//		}
//		
//		if (isSignIn) {
//			if ( lastUpdateTime < (NOW-UPDATE_INTERVAL) ) {
//				map = cm.getClientProfile_someClient_all(clientID);
//				ses.setAttribute("clientInfoSet", map);
//				
//			}
//		}
//	}
	
	public static void printRequest(HttpServletRequest req) {
		Enumeration e = req.getParameterNames();
		while (e.hasMoreElements()) {
			String s = (String)e.nextElement();
			System.out.println("log : param .. " + s +" - "+ req.getParameter(s) );
		}
		e = req.getAttributeNames();
		while (e.hasMoreElements()) {
			String s= (String)e.nextElement();
			System.out.println("log : attr .. "+ s +" - "+ req.getAttribute(s) );
		}
	}
	
	public static String toNumString(int a) {
		final String INPUT_VALUE = "" + a;
		final int STR_LENGTH = INPUT_VALUE.length();
		final int COMMA_POSITION = 3;
		
		String result = "";
		int i = STR_LENGTH;
		do {
			String tmp = "";
			
			if ( ( !(i>COMMA_POSITION) ) && i==STR_LENGTH ) { tmp = INPUT_VALUE; }
			else if ( !(i > COMMA_POSITION) ) { tmp = INPUT_VALUE.substring(0, i) + ","; }	
			else if (i == STR_LENGTH) { tmp = INPUT_VALUE.substring(i-COMMA_POSITION); }		
			else { tmp = INPUT_VALUE.substring(i-COMMA_POSITION, i) + ","; }
			tmp += result;
			result = tmp;
			i -= COMMA_POSITION;
		} while (i>0);
		
		return result;		
	}
	
	public static long createOTP() {
		final String NOW_STRING = "" + (System.currentTimeMillis() + Math.random()*999999999);
		final int LENGTH = NOW_STRING.length()-1;
		long result = 0;
		for (int i=0 ; i<8 ; i++) {
			long tmpResult = Long.parseLong(NOW_STRING.substring(LENGTH-(i+1), LENGTH-i));
			long tmpLong = 1;
			for (int j=i ; j>0 ; j--) { tmpLong *= 10; }
			result += tmpResult * tmpLong;
		}
		return result;
	}
	
	public static boolean isNull(String tmp) {
		return (tmp.equals("") || tmp==null || tmp.equals(null) || tmp=="" || tmp.equals("null") || tmp == "null" );
	}
	
	public static void paramCheck(MultipartRequest multi) {
		//Parameter check
		Enumeration e = multi.getParameterNames();
		while (e.hasMoreElements()) {
			String name = e.nextElement().toString();
			String[] values = multi.getParameterValues(name);
			for (String value : values) {
				System.out.println("log - "+ name + " : " + value);
			}
		}
	}
	
	public static void paramCheck(HttpServletRequest req) {
		Enumeration e = req.getParameterNames();
		while (e.hasMoreElements()) {
			String name = e.nextElement().toString();
			String[] values = req.getParameterValues(name);
			for (String value : values) {
				System.out.println("log - "+ name + " : " + value);
			}
		}
	}
	
	
	public static JSONObject toJSONObject(String str) throws Exception {
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(str);
		JSONObject json = (JSONObject)obj;
		return json;
	}
	
	public static long getNowYear() {
		final long NOW = System.currentTimeMillis();
		java.util.Date date = new java.util.Date();
		date.setTime(NOW);
		String str = date.toString();
		
		String[] tmpArr = str.split(" ");
		long year = Long.parseLong(tmpArr[5]);
		
		return year;
	}
	
	public static boolean isSignIn(HttpSession ses) {
		final String SESSION_VALUE_NAME = "clientInfoMap";
		boolean hasId= false;
		boolean hasInfo = false;
		
		String clientId = "";
		HashMap<String, Object> resultSet = new HashMap<String, Object>();
		
		Enumeration<String> e = ses.getAttributeNames();
		ses :
		while (e.hasMoreElements()) {
			String s = e.nextElement();
			if (s.equals("clientID")) { hasId = true; }
			else if (s.equals(SESSION_VALUE_NAME)) { hasInfo = true; }
		}
		
		if (hasId) { clientId = (String)ses.getAttribute("clientID"); }
		if (hasInfo) { resultSet = (HashMap<String, Object>)ses.getAttribute(SESSION_VALUE_NAME); }
		
		if (isNull(clientId) || resultSet.size() == 0) {  ses.invalidate(); return false; }
		return true;
		
	}
	
	public static boolean hasSession(String str, HttpSession ses) {
		boolean hasAttr = false;
		String clientId = "";
		Enumeration<String> e = ses.getAttributeNames();
		ses :
		while (e.hasMoreElements()) {
			String names = e.nextElement();
			if (names.equals(str)) { hasAttr = true; break ses; }
		}
		if (hasAttr) { clientId = (String)ses.getAttribute("clientID"); }
		if (ADTools.isNull(clientId)) { return false; }
		return true;
	}
	
	
}
