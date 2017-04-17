<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="google-translate-customization"
	content="6f1073ba568f1202-9c8990a4b3025b3e-ga74e3ea243d3f01d-14"></meta>
<!-- 세계 언어 선택 메타 태그 -->
<title>ASTK AD</title>
<link rel="stylesheet" type="text/css" href="css/base.css" media="all" />
<link rel="stylesheet" type="text/css" href="css/main.css" media="all" />

<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<!-- 제이쿼리 라이이브러리 연동 -->

<link rel="stylesheet" type="text/css" href="css/style.css" />
<!-- 터치 슬라이드 스타일(CSS) 연동 -->
<script type="text/javascript" src="js/swipe.js"></script>
<!-- 터치 슬라이드 플러그인 연동 -->
<script type="text/javascript" src="js/jquery.bxslider.min.js"></script>
<!-- bxSlider 플러그인 연동 -->
<script type="text/javascript" src="js/jquery-ui-1.10.4.custom.min.js"></script>
<!-- UI 플러그인 연동 -->
<script type="text/javascript" src="js/jquery.cookie.js"></script>
<!-- 쿠키 플러그인 연동 -->
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/main.js"></script>

<!-- 구글 세계 언어 선택 -->
<script type="text/javascript">
	function googleTranslateElementInit() {
		new google.translate.TranslateElement({
			pageLanguage : 'ko',
			layout : google.translate.TranslateElement.InlineLayout.SIMPLE
		}, 'google_translate_element');
	}
</script>
<script type="text/javascript"
	src="//translate.google.com/translate_a/element.js?cb=googleTranslateElementInit"></script>


<%

final String ATTR_NAME = "viewPath";
boolean hasViewPath = false;
String viewPath = "";

viewPath = (String)request.getAttribute(ATTR_NAME);
%>

</head>

<body id="body_index" >
<div id="div_index" style="margin-top: 40px;">
	<div id="div_index_header" class="div_index"> <jsp:include page="common/header.jsp" flush="false" /> </div>
	<div id="div_index_body" class="div_index"> <jsp:include page="<%=viewPath %>" flush="false" /></div>
	<div id="div_index_footer" class="div_index"> <jsp:include page="common/footer.jsp" flush="false" /></div>
</div>
</body>
</html>