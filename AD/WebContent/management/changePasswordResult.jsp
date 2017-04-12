<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>
<%
	String result = (String)request.getAttribute("result");
	String oldPW = (String) request.getAttribute("oldPW");
	String newPW = (String) request.getAttribute("newPW");
	out.println("<script type='text/javascript'>");
	out.println("window.onload = function(){");
	out.print("alert(");
	if (result.equals("T")) { out.print(" '변경 성공.."+ oldPW +" : "+ newPW +"'"); } 
	else { 	out.print(" '변경 실패' "); }
	out.println(");");
	out.println("self.opener=self;");
	out.println("window.close();");
	out.println("}");
	out.println("</script>");
%>

</body>
</html>