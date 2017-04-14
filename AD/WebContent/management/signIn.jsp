<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- Desktop -->
<style>
#signIn_logo {
width: 200px; padding: 50px 50px 20px 50px;

}
#signIn_form > input { width: 200px; font-size: 1em; padding: 1px 1px 1px 1px; }
.signIn_Btn { 
width: 205px; height: 2.2em; padding: 1px 1px 1px 1px; 
font-size: 1em; font-weight: 500; color:white; 
background-color:#be3535; 
}
</style>

<!-- Tablet PC -->
<style>
</style>

<!-- SmartPhone -->
<style>
</style>

</head>
<body id="signInBody">
<script type="text/javascript">
	function formCheck() {
		var form = document.getElementById("signIn_form");
		var clientID = document.getElementById("signIn_ID");
		var clientPW = document.getElementById("signIn_PW");
		
		if (clientID.value == "" || clientID.value == null) {
			alert("아이디를 입력해주세요.");
			clientID.focus();
			return;
		}
		if (clientPW.value == "" || clientPW.value == null) {
			alert("비밀번호를 입력해주세요.");
			clientPW.focus();
			return;
		}
		form.submit();	
	}
	
	function goSignUp() {
		location.href="signUp.ad";
	}
</script>

<%
	String servletPath = "index.jsp";
  
  Enumeration e = request.getAttributeNames();
  while (e.hasMoreElements()) {
  	String s = (String) e.nextElement();
  	if (s.equals("servletPath")) { servletPath = (String)request.getAttribute(s); }
  }
  
	out.println("<input type='hidden' name='servletPath' value='"+servletPath+"'>");
%>

<center > 
	<img id="signIn_logo" src="image/logo.png" >
</center >
<center >
	<form id="signIn_form" method="post" action="doSignIn.ad" >
		<input type="text" id="signIn_ID" name="clientID" ><br>
		<input type="password" id="signIn_PW" name="clientPW"><br>
		<input type="text" name="servletPath" value="<%=servletPath %>" readonly >
	</form><br>
		<button class="signIn_Btn" id="signIn_summit" onclick="formCheck();">로그인</button><br>
		<button class="signIn_Btn" id="signIn_goSignUp" onclick="goSignUp();">회원가입</button><br>
		<button class="signIn_Btn" id="signIn_forget" onclick="goHelp();">아이디/비밀번호 찾기</button>
</center>
</body>
</html>