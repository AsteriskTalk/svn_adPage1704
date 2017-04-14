<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!--  desktop Setting -->

<body id="body_index_header" class="homepage" >
	<!-- btn function - header -->
	<script type="text/javascript" src="js/astk/headerMenuClick.js" > </script>

	<header id="header">
		<nav id="main-menu" class="navbar navbar-default "
			role="banner">
			<div class="container">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse"
						data-target=".navbar-collapse">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand pointer" onclick="goHome();"><img
						src="images/logo.png" alt="logo" width="50px" height="50px"></a>
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
										out.println("<a id='signInBtn' class='pointer' onclick='doSignIn();' > 로그인 </a>");
									} else {
										out.println("<a id='welcome' >" + clientID + "님, 환영합니다. </a>");
										out.println("<a id='myInfoBtn' class='pointer' onclick='goMyProfile();'> 내 정보 </a>");
										out.println("<a id='signOutBtn' class='pointer' onclick='doSignOut();''> 로그아웃 </a>");
									}
								%>
							</li>
							<li><a class="pointer">회원가입</a></li>
							<li><a class="pointer">사이트맵</a></li>

						</ul>

					</div>

				</div>

				<div class="collapse navbar-collapse navbar-right" >

					<ul class="nav navbar-nav">
						<li class="scroll"><a class='pointer' onclick="goHome();">홈</a></li>
						<li class="scroll"><a class='pointer' onclick="goNews();">새 소식</a></li>
						<li class="scroll"><a class='pointer' onclick="goAD();">광고 관리</a></li>
						<li class="scroll"><a class='pointer' onclick="goPoint();">포인트 관리</a></li>
						<li class="scroll"><a class='pointer' onclick="goHistory();">나의 기록</a></li>
						<li class="scroll"><a class='pointer' onclick="goStatics();">나의 통계</a></li>
					</ul>
				</div>
			</div>
		</nav>
	</header>
	
	
	<form id="servletPath" method="post" action="index.ad">
		<!-- <input type="hidden" id="servletPath" name="servletPath"> -->
	</form>
</body>
