<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
.span_mypage { display:inline-block;}
</style>
</head>
<body id="body_mypage">
	<div id="div_mypage_top" class="div_mypage"> 
		<jsp:include page="mypage/today.jsp" flush="false" />	
	</div>
	<div id="div_mypage_mid" class="div_mypage"> 
		<span id="span_mypage_mid_left" class="span_mypage">
			<jsp:include page="mypage/status.html" flush="false" /> 
		</span>
		<span id="span_mypage_mid_right" class="span_mypage">
			<jsp:include page="mypage/history.html" flush="false" />
		</span>
	</div>
	<div id="div_mypage_bottom" class="div_mypage"> 
		<jsp:include page="common/download.html" flush="false" />
	</div>
</body>
</html>