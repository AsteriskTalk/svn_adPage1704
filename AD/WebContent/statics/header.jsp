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
function getAll() {
	var form = document.getElementById("staticsClick");
	var staticsPath = document.getElementById("staticsPath");
	staticsPath.value = "all.jsp";
	form.submit();
}

function getSome() {
	var form = document.getElementById("staticsClick");
	var staticsPath = document.getElementById("staticsPath");
	selected_form.value = selected.value;
	staticsPath.value = "common/ADList.jsp";
	form.submit();
}
</script>

<button onclick="getAll();">전체 통계</button>
<button onclick="getSome();">개별 통계</button>

<form id="staticsClick" method="post" action="staticsClick.ad">
	<input type="hidden" id="staticsPath" name="staticsPath">
	<input type="hidden" id="selected_form" name="ADCode">
</form>
</body>
</html>