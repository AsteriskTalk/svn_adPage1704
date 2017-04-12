<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Put Session Data</title>
</head>
<body>
<%
	String clientID = (String)request.getAttribute("clientID");
	out.println("<script type='text/javascript'>");
	out.println("window.onload=function() { ");
	out.println("putSessionData();");
	out.println("}");
	out.println("function putSessionData() {");
	out.println(	"var session = window.sessionStorage;");
	out.println(	"session.setItem('clientID','"+ clientID +"');");
	out.println(	"alert('clientID =" + clientID +"');");
	out.println(	"location.href='index.ad';");
	out.println("}");
	out.println("</script>");
	
%>
</body>
</html>