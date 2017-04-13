<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> AD History </title>
</head>
<body>
<!-- 

History 에서 일어나는 모든 viewPath 는  history.jsp 이다. viewPath > insidePage > ADHistoryPage 순이다. 
만일 없을 경우 AD/all.jsp

-->
<script type="text/javascript">
function selectHistory_all() {
	var form = document.getElementById("form_ADHistory");
	var inputPage = document.getElementById("input_page");
	inputPage.value = "AD/allADHistory.jsp";
	form.submit();
}

function selectHistory_some() {
	var form = document.getElementById("form_ADHistory");
	var inputPage = document.getElementById("input_page");
	inputPage.value = "AD/someADHistory.jsp";
	form.submit();
}

</script>

<%
	String ADHistoryPage = "AD/allADHistory.jsp";
	Enumeration<String> e = request.getAttributeNames();
	while (e.hasMoreElements()) {
		String s = (String)e.nextElement();
		if ( s.equals("ADHistoryPage") ) { ADHistoryPage = (String)request.getAttribute(s); }
	}
	
%>
<button onclick="selectHistory_all();" > 전체 기록 </button>
<button onclick="selectHistory_some();" > 개별 기록 </button>

<form id="form_ADHistory" action="ADHistory.ad" method="post" >
	<input id="input_page" name="ADHistoryPage" type="hidden" />
</form>

<jsp:include page="<%=ADHistoryPage %>" />

</body>
</html>