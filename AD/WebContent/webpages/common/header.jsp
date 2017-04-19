<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>header</title>
<!--  desktop Setting -->
</head>
<body id="body_index_header" class="homepage">
<script type="text/javascript" src="js/astk/headerMenuClick.js"></script>
<script type="text/javascript">

function goMyProfile() {
	location.href = "myProfile.ad";
}

function goSignIn() {
	location.href = "signIn.ad";
}

function goSignOut() {
	location.href = "doSignOut.ad";
}

function goSignUp() {
	location.href="signUp.ad";
}
</script>
	<!-- btn function - header -->

	<form id="servletPath" method="post" action="#"> </form>

	<div id="wrap">
		<div id="header">
			<h1>
				<a class="pointer" onclick="goHome();"><img  alt="logo"></a>
			</h1>
			<dl id="util_menu">
				<!-- <dt class="hide">유틸메뉴</dt> -->
				<dd class="util_first">
					<ul>
						<li id="header_signIn_signOut">
							<%
								HttpSession ses = request.getSession();
								String clientID = (String) session.getAttribute("clientID");
								if (clientID == null || clientID.equals("") || clientID == ""
										|| clientID.equals(null)) {
									out.println("<a id='signInBtn' class='pointer' onclick='goSignIn();' > 로그인 </a>");
								} else {
									out.println("<a id='welcome' class='pointer' >" + clientID
											+ "님, 환영합니다. </a>");
									out.println("<a id='myInfoBtn' class='pointer' onclick='myInfo();'> 내 정보 </a>");
									out.println("<a id='signOutBtn' class='pointer' onclick='goSignOut();''> 로그아웃 </a>");
								}
							%>
						</li>
						<li><a class="pointer" onclick="goSignUp();" >회원가입</a></li>
						<li><a class="pointer">사이트맵</a></li>
					</ul>
				</dd>
				<dd>
					<div id="google_translate_element"></div>
				</dd>
			</dl>
			<form action="#" method="get" name="sch_f" id="sch_f">
				<fieldset>
					<legend>검색폼</legend>
					<p>
						<input type="text" name="keyword" id="keyword" title="검색어입력" /><input
							type="image" src="images/header_sch_btn.gif" alt="검색" />
					</p>
				</fieldset>
			</form>
			<h2 class="hide">메인메뉴</h2>
			<ul id="gnb">
				<li class="scroll"><a class='pointer' onclick="goHome();">메인</a></li>
				<li class="scroll"><a class='pointer' onclick="goAD();">광고관리</a></li>
				<li class="scroll"><a class='pointer' onclick="goHistory();">나의
						기록</a></li>
				<li class="scroll"><a class='pointer' onclick="goStatics();">나의
						통계</a></li>
				<li class="scroll"><a class='pointer' onclick="goPoint();">포인트 충전
				</a></li>
				<li class="scroll"><a class='pointer' onclick="goNews();">마이
						페이지</a></li>
			</ul>
			
		</div>
	</div>
</body>
</html>