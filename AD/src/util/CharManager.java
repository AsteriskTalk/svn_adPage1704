package util;

import java.util.StringTokenizer;

public class CharManager {
	
	public static String beforeOracle_withSpace(String inputStr) {
		if (ADTools.isNull(inputStr)) { return "null"; }
		String outputStr = "";
		StringTokenizer strToken = new StringTokenizer(inputStr, "'");
		strToken.hasMoreTokens();
		outputStr = strToken.nextToken();
		while (strToken.hasMoreTokens()) {
			outputStr += "''";
			outputStr += strToken.nextToken();
		}
		return outputStr;
	}
	
	public static String beforOracle_lengthLimit(String inputStr) {
		String outputStr = "";
		if (inputStr.length() > 3) {
			outputStr += inputStr + "ASTK";
		}
		return outputStr;
	}
	
	public static String beforeOracle_removeSpace(String inputStr) {
		String outputStr = "";
		String tmp = "";
		
		StringTokenizer strToken = new StringTokenizer(inputStr, "'");
		strToken.hasMoreTokens();
		tmp = strToken.nextToken();
		while (strToken.hasMoreTokens()) {
			tmp += "''";
			tmp += strToken.nextToken();
		}
		
		strToken = new StringTokenizer(tmp, " ");
		strToken.hasMoreTokens();
		outputStr = strToken.nextToken();
		while (strToken.hasMoreTokens()) {
			outputStr += strToken.nextToken();
		}	
		return outputStr;
	}
	
	
	public static String asterisking(String inputStr) {
		String outputStr = "";
		StringTokenizer strToken = new StringTokenizer(inputStr, " ");
		while (strToken.hasMoreTokens()) {
			String tmp = strToken.nextToken();
			for (int i=0 ; i<tmp.length() ; i++) {
				outputStr += "*";
			}
			outputStr += " ";
		}
		return outputStr;
	}
	
	/** BeforeSearch...DELETE WildCard And Space */
	public static String beforeSearch_space(String inputStr) {
		String outputStr = "";
		String tmp = "";

		tmp = inputStr.trim();
		
		StringTokenizer strToken = new StringTokenizer(tmp, "%");
		strToken.hasMoreTokens();
		tmp = strToken.nextToken();
		while (strToken.hasMoreTokens()) {
			tmp += "";
			tmp += strToken.nextToken();
		}
		
		strToken = new StringTokenizer(tmp, "_");
			strToken.hasMoreTokens();
			tmp = strToken.nextToken();
			while (strToken.hasMoreTokens()) {
				tmp += "";
				tmp += strToken.nextToken();
		}
			
		strToken = new StringTokenizer(tmp, " ");
		strToken.hasMoreTokens();
		tmp = strToken.nextToken();
		while (strToken.hasMoreTokens()) {
			tmp += "";
			tmp += strToken.nextToken();
		}	
		
		outputStr = tmp;
		
		return outputStr;
		
	}
	
	/** BeforeSearch...DELETE WildCard(%, _)  */
	public static String beforeSearch_oracle(String inputStr) {
		String outputStr = "";
		String tmp = "";
		
		tmp = inputStr.trim();
		
		StringTokenizer strToken = new StringTokenizer(tmp, "%");
		strToken.hasMoreTokens();
		tmp = strToken.nextToken();
		while (strToken.hasMoreTokens()) {
			tmp += "";
			tmp += strToken.nextToken();
		}
		
		strToken = new StringTokenizer(tmp, "_");
			strToken.hasMoreTokens();
			tmp = strToken.nextToken();
			while (strToken.hasMoreTokens()) {
				tmp += "";
				tmp += strToken.nextToken();
		}
			
		outputStr = tmp;
		
		/*
		strToken = new StringTokenizer(tmp, " ");
		strToken.hasMoreTokens();
		outputStr = strToken.nextToken();
		while (strToken.hasMoreTokens()) {
			outputStr += "";
			outputStr += strToken.nextToken();
		}	
		*/
		
		return outputStr;
		
	}
	
}


