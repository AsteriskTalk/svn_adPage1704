<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, DTO.*" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>

<body>

<script type="text/javascript">
function goClientHistory() {
	var form = document.getElementById("historyClick");
	var historyPath = document.getElementById("insidePage");
	historyPath.value = "clientHistory.jsp";
	form.submit();
}

function goADHistory() {
	var form = document.getElementById("historyClick");
	var historyPath = document.getElementById("insidePage");
	historyPath.value = "ADHistory.jsp";
	form.submit();
}
</script>

<button onclick="goClientHistory();" > 내 기록 </button>
<button onclick="goADHistory();" > 광고 기록 </button>
	
<form id="historyClick" method="post" action="historyClick.ad">
	<input type="hidden" id="insidePage" name="insidePage">
</form>
</body>
</html>