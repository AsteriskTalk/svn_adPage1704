<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>이메일 검토 결과</title>
<script type="text/javascript">
window.onload = function() {
	var result = (document.getElementById("div_result").innerHTML);
	var msg = "";
	if (result == "T") {
		msg = "이메일 인증에 성공하였습니다." ;
	} else if (result == "U") {
		msg = "이미 인증이 완료되었습니다.";
	} else if (result == "N" ){
		msg = "잘못된 요청입니다.";
	} else {
		msg = "이메일 인증에 실패하였습니다.";
	}
	
	alert(msg);
	
	window.open("about:blank", "_self").close();
}
</script>
</head>
<body>
<%
	String result = (String) request.getAttribute("result");
%>
<div id="div_result" ><%=result %></div>

</body>
</html>