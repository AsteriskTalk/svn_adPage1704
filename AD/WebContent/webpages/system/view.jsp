<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*" %>
<%
String html = "<div>비정상적인 접근입니다. </div>";
Enumeration e = request.getAttributeNames();
while (e.hasMoreElements()) {
	String s = (String) e.nextElement();
	if (s.equals("html")) { html = (String)request.getAttribute(s); }
}

out.print(html);

%>
