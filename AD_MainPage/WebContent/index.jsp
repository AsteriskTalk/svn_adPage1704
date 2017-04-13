
<%@ page import="java.io.*, java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Script-Type" content="text/javascript" />
<meta http-equiv="Content-Style-Type" content="text/css" />
<title>AsteriskSoft Korea</title>


<link rel="stylesheet" type="text/css" href="css/base.css" media="all" />
<link rel="stylesheet" type="text/css" href="css/main.css" media="all" />
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<!-- 제이쿼리 라이이브러리 연동 -->

<link rel="stylesheet" type="text/css" href="js/style.css" />
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
/*
	String str = "";
	String path = application.getRealPath("C:\\visit.txt");

	try {
		BufferedReader br = new BufferedReader(new FileReader(path));
		int county = Integer.parseInt(br.readLine().trim());
		br.close();
		br = null;

		if (session.getAttribute("memeber") == null) {
			session.setAttribute("memeber", ";;");
			county++;
			BufferedWriter bw = new BufferedWriter(new FileWriter(path));
			bw.write("" + county);
			bw.flush();
			bw.close();
			bw = null;
		}
		out.print("총 방문자수 :" + county);

	} catch (IOException ex) {
		out.print("예외발생" + ex);

	}
	*/
%>


<%
	//Enumeration e = request.getAttributeNames();
	//while (e.hasMoreElements()) {
	//	String name = (String)e.nextElement();
	//	out.print(name + " : "+ request.getAttribute(name));
	//}

	int county = (Integer) request.getAttribute("county");
	String clickPath = (String) request.getAttribute("clickPath");
	boolean isSignIn = false;
%>

</head>
<body>

	<jsp:include page="common/header.html" flush="false" />
	<jsp:include page="<%=clickPath%>" flush="false" />
	<jsp:include page="common/footer.html" flush="false" />
	</br><center> 총 방문자 수 : <%=county %> </center>

</body>
</html>