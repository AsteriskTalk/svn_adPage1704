package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeManager {
	public static String getTime_toLocalString(long time) {
		String[] arr;
		String[] times;
		
		Date d = new Date(time);
		arr = d.toString().split(" ");
		times = arr[3].split(":");
		
		final String YEAR = arr[5];
		final String MONTH_OF_YEAR = arr[1];
		final String MONTH = monthChange(MONTH_OF_YEAR);
		final String DAY = arr[2];
		final String DAY_OF_WEEK = arr[0];
		
		final String HOUR = times[0];
		final String MINUTE = times[1];
		final String SECOND = times[2];
		
		return YEAR+"-"+MONTH+"-"+DAY+" "+ HOUR+":"+MINUTE+":"+SECOND;
	}
	
	public static String getTime_forFileName(String type) {
		final long NOW = System.currentTimeMillis();
			return getTime_forFileName(type, NOW);
	}
	
	public static long getMillis_todayStart() {
		final long NOW = System.currentTimeMillis();
		return getMillis_dayStart(NOW);
	}
	
	public static long getMillis_dayStart(long time){
		long dayStart = 0;
		String date = "";
		String[] arr;
		String[] times;
		
		Date d = new Date(time);
		arr = d.toString().split(" ");
		times = arr[3].split(":");
		
		final String YEAR = arr[5];
		final String MONTH_OF_YEAR = arr[1];
		final String MONTH = monthChange(MONTH_OF_YEAR);
		final String DAY = arr[2];
		final String DAY_OF_WEEK = arr[0];
		
		final String HOUR = times[0];
		final String MINUTE = times[1];
		final String SECOND = times[2];
		
		
		date += YEAR +"-"+ MONTH +"-"+ DAY;
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			d = sdf.parse(date);
			dayStart = d.getTime();
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ ASTKLogManager.getMethodName_now() +"\n"+ex);
			return -1;
		}
		System.out.println("log : date - " + date + " and start - " + dayStart + " start_toString = " + d.toString());
		return dayStart;
		
	}
	
	public static String getTime_forFileName(String type, long time) {
		String result = "";
		
//		int cntYear = 0;
//		int cntMonth = 0;
//		int cntDay = 0;
//		int cntHour = 0;
//		int cntMinute = 0;
//		int cntSecond = 0;
//		
		boolean inYear = false;
		boolean inMonth = false;
		boolean inDay = false;
		boolean inHour = false;
		boolean inMinute = false;
		boolean inSecond = false;
		
//		String yearStr_4 = "";
		String yearStr = "";
		String monthStr = "";
		String dayStr = "";
		String hourStr = "";
		String minuteStr = "";
		String secondStr = "";
		
		java.util.Date d = new java.util.Date();
		d.setTime(time);
		String date = d.toString();
		String[] times = date.split(" ");
//		Thu Apr 06 16:30:22 KST 2017
		String[] hms = times[3].split(":");
		
		int h = Integer.parseInt(hms[0]);
		hourStr = h<10 ? "0"+h : ""+h;
		
		int m = Integer.parseInt(hms[1]);
		minuteStr = m<10 ? "0"+m : ""+m;
		
		int s = Integer.parseInt(hms[2]);
		secondStr = s<10 ? "0"+s : ""+s;
		
		yearStr = times[5]; //연도는 4글자이다.
		
		monthStr = times[1].toLowerCase();
		if (monthStr.equals("jan")) { monthStr="01"; }
		else if (monthStr.equals("feb")) { monthStr="02"; }
		else if (monthStr.equals("mar")) { monthStr="03"; }
		else if (monthStr.equals("apr")) { monthStr="04"; }
		else if (monthStr.equals("may")) { monthStr="05"; }
		else if (monthStr.equals("jun")) { monthStr="06"; }
		else if (monthStr.equals("jul")) { monthStr="07"; }
		else if (monthStr.equals("aug")) { monthStr="08"; }
		else if (monthStr.equals("sep")) { monthStr="09"; }
		else if (monthStr.equals("oct")) { monthStr="10"; }
		else if (monthStr.equals("nov")) { monthStr="11"; }
		else if (monthStr.equals("dec")) { monthStr="12"; }
		
		dayStr = times[2];

		for (int i=0 ; i<type.length() ; i++) {
			char c = type.charAt(i);
			if ( c == 'y' ) { if (!inYear) { result += yearStr; inYear = true; }  } 
			else if ( c == 'M' ) { if (!inMonth) { result += monthStr; inMonth = true; } } 
			else if ( c == 'd' ) { if (!inDay) {  result += dayStr; inDay = true; } } 
			else if ( c == 'H' || c == 'h' ) { if (!inHour) { result += hourStr; inHour = true; } }
			else if ( c == 'm' ) { if (!inMinute) { result += minuteStr; inMinute = true; } }
			else if ( c == 's' ) { if (!inSecond) { result += secondStr; inSecond = true; } }
			else { result += c; }
		}
		
//		System.out.println(type +"-"+ result);
		return result;
	}
	
	public static String monthChange(String mon) {
		String s = mon.toLowerCase(); 
		if (s.equals("jan")) { return "01"; }
		else if (s.equals("feb")) { return "02"; }
		else if (s.equals("mar")) { return "03"; }
		else if (s.equals("apr")) { return "04"; }
		else if (s.equals("may")) { return "05"; }
		else if (s.equals("jun")) { return "06"; }
		else if (s.equals("jul")) { return "07"; }
		else if (s.equals("aug"))	{ return "08"; }
		else if (s.equals("sep"))	{ return "09"; }
		else if (s.equals("oct")) { return "10"; }
		else if (s.equals("nov")) { return "11"; }
		else if (s.equals("dec")) { return "12"; }
		else { return "E"; }
	}
}
