<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, DTO.*, DAO.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> AD_header </title>
</head>
<body>

<%

%>

<script type="text/javascript">
function ADmain() {
	var form = document.getElementById("ADClickForm");
	var ADPath = document.getElementById("ADPath");
	ADPath.value = "main.jsp";
	form.submit();
}

function insert() {
	var form = document.getElementById("ADClickForm");
	var ADPath = document.getElementById("ADPath");
	ADPath.value = "insert.jsp";
	form.submit();
}

</script>

<button onclick="ADmain();">ad main</button>
<button onclick="insert();">ad insert</button>

<form id="ADClickForm" method="post" action="ADClick.ad">
	<input type="hidden" id="ADPath" name="ADPath">
	<input type="hidden" id="ADSelected_param" name="ADCode" value="0">
</form>
</body>
</html>