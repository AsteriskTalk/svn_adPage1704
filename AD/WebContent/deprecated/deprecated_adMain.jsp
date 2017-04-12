<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>AD</title>
</head>
<body>
<%
System.out.println("plag8");
//String ADPath = "ad/" + (String)request.getAttribute("ADPath");
String ADPath = "ad/main.jsp";
System.out.println("plag9 _ "+ ADPath);
%>
<jsp:include page="ad/header.jsp" flush="false" />
<jsp:include page="<%=ADPath %>" flush="false" />
</body>
</html>