<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 / 비밀번호 찾기</title>
</head>
<body>

<%
	String msg = "내용이 없습니다.";
	String img = "../../images/logo.png";
	Enumeration e = request.getAttributeNames();
	while (e.hasMoreElements()) {
		String s = (String)e.nextElement();
		if (s.equals("msg")) { msg = (String)request.getAttribute(s); }
		else if (s.equals("img")) { img = (String) request.getAttribute(s); }
	}

%>
<a id="a_msg"><%=msg %></a><br>
<img src="<%=img %>" height="200px" width="200px" />
</body>
</html>