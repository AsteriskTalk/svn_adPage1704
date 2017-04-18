<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*" %>
비정상적인 접근입니다. 잠시 후 다시 시도해주세요.<br>
만일 같은 문제가 지속되면 문의해주시기 바랍니다.<br>

<%
String msg = "";
Exception ex = null;

Enumeration e = request.getAttributeNames();
while (e.hasMoreElements()) {
	String s = (String) e.nextElement();
	if (s.equals("msg")) { msg = (String) request.getAttribute(s); }
	else if (s.equals("ex")) { ex = (Exception)request.getAttribute(s); }
}
out.print("Message : " + msg + "<br>");
if (ex != null) { out.print("Exception : "+ ex.toString() + "<br>"); }

%>