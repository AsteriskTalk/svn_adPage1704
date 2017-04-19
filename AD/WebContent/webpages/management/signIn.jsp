<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

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
		location.href = "signUp.ad";
	}
</script>

<%
	String servletPath = "index.ad";

	Enumeration e = request.getAttributeNames();
	while (e.hasMoreElements()) {
		String s = (String) e.nextElement();
		if (s.equals("servletPath")) {
			servletPath = (String) request.getAttribute(s);
		}
	}

	out.println("<input type='hidden' name='servletPath' value='"
			+ servletPath + "'>");
%>


</head>
<body>

	<div id="wrap">
		<!-- <center>
			<img id="signIn_logo" src="images/logo.png">
		</center> -->
		<center>
			<div class="login_wrap">
				<form id="signIn_form" name="log_f" method="post"
					action="doSignIn.ad">
					<!-- <fieldset>로그인</fieldset> -->
					<p class="user_id">
						<label for="user_id"></label> <input type="text" id="signIn_ID" name="clientID" placeholder="아이디"/>
					</p>
					<p class="user_pw">
						<label for="user_pw"></label> <input type="password" id="signIn_PW"
							name="clientPW" placeholder="비밀번호" />
					</p>
					<p class="">
						<input type="checkbox" name="save_id" id="save_id" /> <label
							for="save_id"> 비밀번호 저장</label>
					</p>
					<p class="log_btn">
						<input type="image" src="images/login_btn.gif" class="signIn_Btn" id="signIn_summit" onclick="formCheck();" alt="로그인버튼" />
					</p>
					<p class="join_btn_wrap">
						<a href="#" class="signIn_Btn" id="signIn_goSignUp" onclick="goSignUp();">회원가입</a> <a href="#" class="signIn_Btn" id="signIn_forget" onclick="goHelp();">아이디/비밀번호
							찾기</a>
					</p>
					<p class="servlet_path">
						<input type="hidden" name="servletPath" value="<%=servletPath%>"
							readonly>
					</p>
				</form>

				<!-- <button class="signIn_Btn" id="signIn_summit" onclick="formCheck();">로그인</button>
		<br>
		<button class="signIn_Btn" id="signIn_goSignUp" onclick="goSignUp();">회원가입</button>
		<br>
		<button class="signIn_Btn" id="signIn_forget" onclick="goHelp();">아이디/비밀번호
			찾기</button> -->
			</div>
		</center>
	</div>
</body>
</html>