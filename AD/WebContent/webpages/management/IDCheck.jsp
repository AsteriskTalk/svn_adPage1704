<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="DAO.*, util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ID 중복 확인</title>
</head>
<body>
<%
String alertMsg = "";
String colorValue = "";
boolean isUsed = false;
isUsed = (Boolean)request.getAttribute("result");
if (isUsed) {
	alertMsg += "이미 사용중인 아이디입니다.";
	colorValue="#ffbbbb";
} else {
	alertMsg += "사용할 수 있는 아이디입니다.";
	colorValue="#bbffbb";
}
%>

<script type="text/javascript" >
	var thisOpener = window.opener;
	window.onload = function() {
		self.opener=self;
		alert('<%=alertMsg %>');
		thisOpener.document.getElementById("clientIDCheck").value="<%=!isUsed %>";
		thisOpener.document.getElementById("clientID").style.backgroundColor="<%=colorValue %>";
		window.close();
	}

</script>
</body>
</html>