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
	<!-- btn function - header -->
	<script type="text/javascript">
		function main() {
			var form = document.getElementById("headerClick");
			var viewPath = document.getElementById("viewPath");
			var pagePath = document.getElementById("pagePath");
			pagePath.value = "index.jsp";
			viewPath.value = "main.jsp";
			form.submit();
		}

		function mypage() {
			var form = document.getElementById("headerClick");
			var viewPath = document.getElementById("viewPath");
			var pagePath = document.getElementById("pagePath");
			pagePath.value = "index.jsp";
			viewPath.value = "mypage.jsp";
			form.submit();
		}
		function ad() {
			var form = document.getElementById("headerClick");
			var viewPath = document.getElementById("viewPath");
			var pagePath = document.getElementById("pagePath");
			pagePath.value = "ADClick.ad";
			viewPath.value = "adMain.jsp";
			form.submit();

		}
		function goHistory() {
			var form = document.getElementById("headerClick");
			var viewPath = document.getElementById("viewPath");
			var pagePath = document.getElementById("pagePath");
			pagePath.value = "historyClick.ad";
			viewPath.value = "history.jsp";
			form.submit();

		}
		function statistics() {
			var form = document.getElementById("headerClick");
			var viewPath = document.getElementById("viewPath");
			var pagePath = document.getElementById("pagePath");
			pagePath.value = "staticsClick.ad";
			viewPath.value = "statics.jsp";
			form.submit();

		}

		function pointRecharge() {
			var form = document.getElementById("headerClick");
			var viewPath = document.getElementById("viewPath");
			var pagePath = document.getElementById("pagePath");
			pagePath.value = "index.jsp";
			viewPath.value = "point.jsp"
			form.submit();
		}
	</script>

	<!-- btn function - signIn-Out -->
	<script type="text/javascript">
		function doSignIn() {
			location.href = "signInClick.ad";
		}

		function doSignOut() {
			location.href = "doSignOut.ad";
		}

		function myInfo() {
			location.href = "myProfile.ad";
		}
		function goTitle() {
			location.href = "index.ad";
		}
	</script>


	<form id="headerClick" method="post" action="menu.ad">
		<input type="hidden" id="viewPath" name="viewPath"> <input
			type="hidden" id="pagePath" name="pagePath">
	</form>


	<div id="wrap">
		<div id="header">
			<h1>
				<a class="pointer" onclick="goTitle();"><img src="#" alt="logo"></a>
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
									out.println("<a id='signInBtn' class='pointer' onclick='doSignIn();' > 로그인 </a>");
								} else {
									out.println("<a id='welcome' class='pointer' >" + clientID
											+ "님, 환영합니다. </a>");
									out.println("<a id='myInfoBtn' class='pointer' onclick='myInfo();'> 내 정보 </a>");
									out.println("<a id='signOutBtn' class='pointer' onclick='doSignOut();''> 로그아웃 </a>");
								}
							%>
						</li>
						<li><a class="pointer">회원가입</a></li>
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
				<li class="scroll"><a class='pointer' onclick="main();">메인</a></li>
				<li class="scroll"><a class='pointer' onclick="ad();">광고관리</a></li>
				<li class="scroll"><a class='pointer' onclick="goHistory();">나의
						기록</a></li>
				<li class="scroll"><a class='pointer' onclick="statistics();">나의
						통계</a></li>
				<li class="scroll"><a class='pointer'
					onclick="pointRecharge();">포인트 충전</a></li>
				<li class="scroll"><a class='pointer' onclick="mypage();">마이
						페이지</a></li>
			</ul>

		</div>
	</div>
</body>
</html>