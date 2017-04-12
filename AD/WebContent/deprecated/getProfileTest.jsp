<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="org.json.simple.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>getProfileTest</title>
</head>
<body>
<%
JSONObject json = new JSONObject();
try {
	json = (JSONObject)request.getAttribute("json");
	out.print(json.toJSONString());
} catch (Exception ex) {
	out.print(ex);
}
%>

</body>
</html>