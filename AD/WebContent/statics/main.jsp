<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
String insidePage = "statics/" + (String)request.getAttribute("insidePage");
%>
<center><jsp:include page="header.jsp" flush="false" /></center><br>
<center><jsp:include page="<%=insidePage %>" flush="false" /></center>
</body>
</html>