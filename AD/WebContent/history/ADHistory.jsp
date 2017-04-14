<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> AD History </title>
</head>
<body>
<script type="text/javascript">
function selectHistory_all() {
	var form = document.getElementById("form_ADHistory");
	var input_insidePage_AD = document.getElementById("input_insidePage_AD");
	input_insidePage_AD.value = "all";
	form.submit();
}

function selectHistory_some() {
	var form = document.getElementById("form_ADHistory");
	var input_insidePage_AD = document.getElementById("input_insidePage_AD");
	input_insidePage_AD.value = "list";
	form.submit();
}

</script>

<%
	String insidePage_AD = "all.jsp";
	Enumeration<String> e = request.getAttributeNames();
	while (e.hasMoreElements()) {
		String s = (String)e.nextElement();
		if ( s.equals("insidePage_AD") ) { insidePage_AD = (String)request.getAttribute(s); }
	}
	
%>

<button onclick="selectHistory_all();" > 전체 기록 </button>
<button onclick="selectHistory_some();" > 개별 기록 </button>

<form id="form_ADHistory" action="myHistory.ad" method="post" >
	<input name="insidePage" type="hidden" value="ADHistory.jsp" />
	<input id="input_insidePage_AD" name="insidePage_AD" type="hidden" value="all" />
</form>

<jsp:include page="<%=insidePage_AD %>" />

</body>
</html>