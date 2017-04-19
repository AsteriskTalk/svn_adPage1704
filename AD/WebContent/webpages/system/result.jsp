<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
<%
	Enumeration e = request.getAttributeNames();
	while (e.hasMoreElements()) {
		String s = (String)e.nextElement();
		out.print("<br>"+ s +" : "+ request.getAttribute(s));
	}

%>