<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko" >
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Script-Type" content="text/javascript" />
<meta http-equiv="Content-Style-Type" content="text/css" />

<!-- Desktop -->

<link rel="stylesheet" type="text/css" href="css/base.css" media="all" />
<link rel="stylesheet" type="text/css" href="css/main.css" media="all" />

<!-- 부트스트랩 -->
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
<script type="text/javascript" src="js/bootstrap.js"></script>

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
<style>
html { min-width:1200px; }
* { overflow:hidden; }
.pointer { cursor: pointer; }
</style>
<title>ASTK AD</title>
<%

final String ATTR_NAME = "view";
boolean hasViewPath = false;
String view = "";

Enumeration<String> e = request.getAttributeNames();
while (e.hasMoreElements()) {
	String s = (String) e.nextElement();
	if (s.startsWith(ATTR_NAME)) { view = (String)request.getAttribute(s); }
}
%>

</head>

<body id="body_index" >
<div id="div_index" style="margin-top: 40px;">
	<div id="div_index_header" class="div_index"> <jsp:include page="common/header.jsp" flush="false" /> </div>
	<div id="div_index_body" class="div_index"> <jsp:include page="<%=view %>" flush="false" /></div>
	<div id="div_index_footer" class="div_index"> <jsp:include page="common/footer.jsp" flush="false" /></div>
</div>
</body>
</html>