<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> header </title>
<!--  desktop Setting -->
<style>
#body_index_header { }
span { display: inline-block; }
#header_logo { font-size: 2.5em; }
#header_menu { display:block; padding:5% 0 3% 0; }
#header_signIn_signOut { float: right; margin : 0 0 0 50px; }
#header_menu_button { float: right; }
#header_logo_title > .title_point { color: red; }
#header_logo_title > a { font-weight: bold; }
.span_title_menu { padding: 0 9px 2px 4px; marign :0; border-right: 1px solid #777; }
.span_title_menu:nth-last-child(1) { border:none; }
</style>

</head>
<body id="body_index_header">
<!-- btn function - header -->
<script type="text/javascript" >

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
	location.href="signInClick.ad";
}

function doSignOut() {
	location.href="doSignOut.ad";
}

function myInfo() {
	location.href="myProfile.ad";
}
function goTitle() {
	location.href="index.ad";
}
</script>


<span id="header_logo" > 
	<span id="header_logo_title" class="pointBtn" onclick="goTitle();"> <a class="title_point ">A</a>sterisk<a class="title_point" >So</a>ft<a class="title_point">Ko</a>rea </span> 
</span>

<span id="header_signIn_signOut">

<%
HttpSession ses = request.getSession();
String clientID = (String)session.getAttribute("clientID");
if (clientID==null || clientID.equals("") || clientID=="" || clientID.equals(null)) {
	out.println("<button id='signInBtn' onclick='doSignIn();' > 로그인 </button>");
} else {
	out.println("<a id='welcome'>"+ clientID + "님, 환영합니다. </a>");
	out.println("<button id='myInfoBtn' onclick='myInfo();'> 내 정보 </button>");
	out.println("<button id='signOutBtn' onclick='doSignOut();''> 로그아웃 </button>");
}
%>	
</span>

<form id="headerClick" method="post" action="menu.ad">
	<input type="hidden" id="viewPath" name="viewPath" >
	<input type="hidden" id="pagePath" name="pagePath" >
</form>

<div id="header_menu" > 
	<div id="header_menu_button">
		<span class="span_title_menu pointBtn" onclick="main();" > 메인 </span>
		<span class="span_title_menu pointBtn" onclick="mypage();" > 마이페이지 </span>
		<span class="span_title_menu pointBtn" onclick="ad();" > 광고관리 </span>
		<span class="span_title_menu pointBtn" onclick="goHistory();"> 기록 </span>
		<span class="span_title_menu pointBtn" onclick="statistics();"> 통계 </span>
		<span class="span_title_menu pointBtn" onclick="pointRecharge();"> 포인트 충전 </span>
	</div>
</div>
</body>
</html>