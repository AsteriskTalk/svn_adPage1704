<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>header</title>
<!--  desktop Setting -->

</head>
<body id="body_index_header" class="homepage" >
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


	<header id="header">
		<nav id="main-menu" class="navbar navbar-default"
			role="banner">
			<div class="container">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse"
						data-target=".navbar-collapse">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="#" onclick="goTitle();"><img
						src="#" alt="logo"></a>
				</div>

				<div id="util_meun">
					<div id="google_translate_element"></div>
					<div class="util_first navbar-right">
						<ul>

							<li id="header_signIn_signOut">
								<%
									HttpSession ses = request.getSession();
									String clientID = (String) session.getAttribute("clientID");
									if (clientID == null || clientID.equals("") || clientID == ""
											|| clientID.equals(null)) {
										out.println("<a href='#' id='signInBtn' onclick='doSignIn();' > 로그인 </a>");
									} else {
										out.println("<a href='#' id='welcome'>" + clientID + "님, 환영합니다. </a>");
										out.println("<a href='#' id='myInfoBtn' onclick='myInfo();'> 내 정보 </a>");
										out.println("<a href='#' id='signOutBtn' onclick='doSignOut();''> 로그아웃 </a>");
									}
								%>
							</li>
							<li><a href="#">회원가입</a></li>
							<li><a href="#">사이트맵</a></li>

						</ul>

					</div>

				</div>



				<div class="collapse navbar-collapse navbar-right" >

					<ul class="nav navbar-nav">
						<li class="scroll"><a onclick="main();">메인</a></li>
						<li class="scroll"><a onclick="ad();">광고관리</a></li>
						<li class="scroll"><a onclick="goHistory();">나의
								기록</a></li>
						<li class="scroll"><a onclick="statistics();">나의
								통계</a></li>
						<li class="scroll"><a onclick="pointRecharge();">포인트
								충전</a></li>
						<li class="scroll"><a onclick="mypage();">마이 페이지</a></li>
					</ul>
				</div>
			</div>
		</nav>
	</header>
</body>
</html>