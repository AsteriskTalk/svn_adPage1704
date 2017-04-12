package controller;

import java.util.Calendar;

public class test {
	public static void main(String[] args) {
		long time = System.currentTimeMillis();
		String str = "";
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		str = cal.toString();
		System.out.println(str);
		
	}

}
