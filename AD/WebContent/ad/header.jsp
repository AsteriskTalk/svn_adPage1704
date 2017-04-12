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
HttpSession ses = request.getSession();
String option = "";

HashMap<String, Object> resultSet = (HashMap<String, Object>)ses.getAttribute("allADInfo");
ArrayList<ADInfo> ADInfoList = new ArrayList<ADInfo>();

if (resultSet.get("result").equals("T")) {
	ADInfoList = (ArrayList<ADInfo>)resultSet.get("ADInfoList"); 
	for (ADInfo a : ADInfoList) {
		option += "<option value='"+ a.getADCode() +"'>" + (  a.getADCtt().length()>10 ? a.getADCtt().substring(0, 8) : a.getADCtt() ) + "</option>";
	}
} 

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

function selected() {
	var form = document.getElementById("ADClickForm");
	var ADPath = document.getElementById("ADPath");
	var ADSelected = document.getElementById("ADSelected");
	var ADSelected_param = document.getElementById("ADSelected_param");
	ADSelected_param.value = ADSelected.value;
	ADPath.value = "select.jsp";
	form.submit();
}

function status() {
	var form = document.getElementById("ADClickForm");
	var ADPath = document.getElementById("ADPath");
	ADPath.value = "status.html";
	form.submit();
}

</script>

<button onclick="ADmain();">ad main</button>
<button onclick="insert();">ad insert</button>
<button onclick="status();">ad status</button>
	<select id="ADSelected" onchange="selected();" >
	<option value="-1">---- AD List ---- </option>
		<%=option %>
	</select>
<form id="ADClickForm" method="post" action="ADClick.ad">
	
	<input type="hidden" id="ADPath" name="ADPath">
	<input type="hidden" id="ADSelected_param" name="ADCode" value="0">
</form>
</body>
</html>