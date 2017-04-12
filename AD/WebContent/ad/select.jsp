<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, DTO.*" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<script type="text/javascript" >
function editAD() {
	var form = document.getElementById("form_functionAD");
	var ADFunction = document.getElementById("input_function");
	ADFunction.value = "edit";
	form.submit();
}

function deleteAD() {
	var form = document.getElementById("form_functionAD");
	var ADFunction = document.getElementById("input_function");
	if (confirm("정말 삭제하시겠습니까?") == true) {
		ADFunction.value = "delete";
		form.submit();
	} else {
		return;
	}
}

function stopAD() {
	var form = document.getElementById("form_functionAD");
	var ADFunction = document.getElementById("input_function");
	if (confirm("정말 중지하시겠습니까?") == true) {
		ADFunction.value = "stop";
		form.submit();
	} else {
		return;
	}
}

function awakenAD() {
	var form = document.getElementById("form_functionAD");
	var ADFunction = document.getElementById("input_function");
	if (confirm("광고를 재개하시겠습니까?") == true) {
		ADFunction.value = "awaken";
		form.submit();
	} else {
		return;
	}
}

function collectAD(isADing) {
	var form = document.getElementById("form_functionAD");
	var ADFunction = document.getElementById("input_function");
	var msg = "포인트를 회수하시겠습니까?";
	if (isADing) { msg+="\n광고는 자동으로 중지됩니다.";	} 
	
	if (confirm(msg) == true) {
		ADFunction.value = "collect";
		form.submit();
	} else {
		return;
	}
}

</script>
<%
	final long ADCode;
	final boolean isADing;
	String HTMLTag = "";

	HashMap<String, Object> tmp = new HashMap<String ,Object>();
	ArrayList<ADTarget> ADTargetList = new ArrayList<ADTarget>();
	ADInfo adInfo = new ADInfo();
	
	tmp = (HashMap<String, Object>)request.getAttribute("ADInfoMap_some");
	if(tmp.get("result").equals("T")) { adInfo = (ADInfo)tmp.get("ADInfo"); }
	
	tmp = (HashMap<String, Object>)request.getAttribute("ADTargetMap_some");
	if(tmp.get("result").equals("T"))	{
		ADTargetList = (ArrayList<ADTarget>) tmp.get("ADTargetList");  
		for (ADTarget target : ADTargetList) {
			HTMLTag 
				+= "<a class='ADTarget'>" + target.getTargetType() +"-"+ target.getTargetValue() + "</a><br>";
		}
	}
	
	ADCode = adInfo.getADCode();
	isADing = adInfo.isADing();
	
%>
<div class="ADInfoRow" >
	<span class="ADInfoCol_header" >
		광고 내용
	</span>
	<span class="ADInfoCol_value" >
		<textarea rows="4" cols="40" readonly="readonly"><%=adInfo.getADCtt() %></textarea>
	</span>
</div>
<div class="ADInfoRow" >
	<span class="ADInfoCol_header" >
		광고 URL
	</span>
	<span class="ADInfoCol_value" >
		<%=adInfo.getADURL() %>
	</span>
</div>
<div class="ADInfoRow" >
	<span class="ADInfoCol_header" >
		보너스
	</span>
	<span class="ADInfoCol_value" >
		<%=adInfo.getADBonus() %>
	</span>
</div>
<div class="ADInfoRow" >
	<span class="ADInfoCol_header" >
		잔여 포인트
	</span>
	<span class="ADInfoCol_value" >
		<%=adInfo.getADRemainPoint() %>
	</span>
</div>
<div class="ADInfoRow" >
	<span class="ADInfoCol_header" >
		잔여 횟수
	</span>
	<span class="ADInfoCol_value" >
		<%=adInfo.getADRemainCount() %>
	</span>
</div>
<div class="ADInfoRow" >
	<span class="ADInfoCol_header" >
		광고 이미지
	</span>
	<span class="ADInfoCol_value" >
		<img src="http://<%=adInfo.getADImgAddr() %>" width="100px" height="100px" />
	</span>
</div>
<br>
<%=HTMLTag %>
<br>
<button id="btn_editAD" onclick="editAD();" > 광고 수정 </button>
<button id="btn_collectAD" onclick="collectAD(<%=isADing %>);" > 포인트 회수 </button>
<% 
if(isADing) { %><button id="btn_stopAD" onclick="stopAD();" > 광고 중단 </button><% } 
else { %><button id="btn_awakenAD" onclick="awakenAD();" > 광고 재개 </button><% }
%>
<button id="btn_deleteAD" onclick="deleteAD();" > 광고 삭제 </button><br>

<form id="form_functionAD" action="functionAD.ad" method="post" >
	<input id="input_ADCode" name="ADCode" type="hidden" value="<%=ADCode %>" />
	<input id="input_function" name="ADFunction" type="hidden" value="" />
</form>

</body>
</html>