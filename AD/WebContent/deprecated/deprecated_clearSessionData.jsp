<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
out.println("<script type='text/javascript'>");
out.println("window.onload=function() { ");
out.println("clearSessionData();");
out.println("}");
out.println("function clearSessionData() {");
out.println(	"var session = window.sessionStorage;");
out.println(	"session.removeItem('clientID');");
out.println(	"location.href='index.ad';");
out.println("}");
out.println("</script>");
%>
</body>
</html>