<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, DTO.*" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
HttpSession ses = request.getSession();
String option = "";

HashMap<String, Object> resultSet = (HashMap<String, Object>)ses.getAttribute("ADInfoMap_all");
ArrayList<ADInfo> ADInfoList = new ArrayList<ADInfo>();

if (resultSet.get("result").equals("T")) {
	ADInfoList = (ArrayList<ADInfo>)resultSet.get("ADInfoList"); 
	for (ADInfo a : ADInfoList) {
		option += "<option value='"+ a.getADCode() +"'>" + (  a.getADCtt().length()>10 ? a.getADCtt().substring(0, 8) : a.getADCtt() ) + "</option>";
	}
} 
%>

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
	var selected = document.getElementById("selected");
	var selected_form = document.getElementById("selected_form");
	selected_form.value = selected.value;
	staticsPath.value = "some.jsp";
	form.submit();
}
</script>

<button onclick="getAll();">ALL statics</button>
<select id="selected" onchange="getSome(); ">
<option>---- AD List ---- </option>
	<%=option %>
</select>

<form id="staticsClick" method="post" action="staticsClick.ad">
	<input type="hidden" id="staticsPath" name="staticsPath">
	<input type="hidden" id="selected_form" name="ADCode">
</form>
</body>
</html>