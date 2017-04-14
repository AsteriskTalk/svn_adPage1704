<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
String ADPath = (String)request.getAttribute("insidePage");
%>
<jsp:include page="header.jsp" flush="flush" />
<jsp:include page="<%=ADPath %>" flush="flush" />

</body>
</html>