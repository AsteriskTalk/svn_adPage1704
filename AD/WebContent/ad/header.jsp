<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, DTO.*, DAO.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> AD_header </title>
</head>
<body>

<script type="text/javascript">
function goADList() {
	var form = document.getElementById("ADClickForm");
	var insidePage = document.getElementById("insidePage");
	insidePage.value = "main";
	form.submit();
}

function goADInsert() {
	var form = document.getElementById("ADClickForm");
//	form.innerHTML = "<input type='hidden' id='insidePage' name='insidePage' value='insert.jsp'>";
	var insidePage = document.getElementById("insidePage");
	insidePage.value = "insert";
	form.submit();
}

</script>

<button onclick="goADList();">광고 목록</button>
<button onclick="goADInsert();">광고 삽입</button>

<form id="ADClickForm" method="post" action="myAD.ad"> 
	<input type="hidden" id="insidePage" name="insidePage" value="insert">
</form>
</body>
</html>