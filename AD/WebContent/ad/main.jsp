<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, DTO.*, util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
.ADInfo_row { display: inline-block; width: 70% }
.ADCount_col { width: 150px; text-align: right; }
.ADCtt_col { width: 400px; }
.ADBonus_col { width: 100px; text-align: center; } 
.ADStat_col { width: 100px; text-align: center; }
.ADURL_col { width: 100px; }
.ADImgURL_col { width: 100px; text-align: center; }
.ADEdit_col { width: 50px; text-align: center; }
.pointerBtn { cursor: pointer; }
.AD_main_li { display:inline-block; }
</style>
</head>
<body >
<%
  final String SELECT_AD_SERVLET_URL = "selectAD.ad";
%>

<script type="text/javascript" >
function goSelect(i) {
	var form = document.getElementById("goSelectForm");
	var ADCodeInput = document.getElementById("ADCodeInput");
	ADCodeInput.value = i;
	form.submit();
}

</script>
<br>
	<ul class="ADInfo_row" > 
		<li class="ADCount_col AD_main_li">잔여 광고 횟수</li> 
		<li class="ADImgURL_col AD_main_li"> 첨부 이미지 </li>
		<li class="ADCtt_col AD_main_li">광고 내용</li>
		<li class="ADBonus_col AD_main_li">보너스 </li>
		<li class="ADStat_col AD_main_li"> 광고 상태 </li>
		<li class="ADEdit_col AD_main_li">&nbsp; </li>
	</ul>
<%
HttpSession ses = request.getSession();
HashMap<String, Object> tmp = (HashMap<String ,Object>)ses.getAttribute("ADInfoMap_all");
ArrayList<ADInfo> list = new ArrayList<ADInfo>();
//System.out.println("log : "+ tmp.toString());
if (tmp.get("result").equals("T")) { 
	list = (ArrayList<ADInfo>)tmp.get("ADInfoList"); 
	for (ADInfo a : list) {
		long ADCode = a.getADCode();
		long clientCode = a.getClientCode();
		String ADCtt = a.getADCtt();
		ADCtt = ADCtt.length() > 15 ? ADCtt.substring(0, 15)+"..." : ADCtt;
		long ADBonus = a.getADBonus();
		long ADRemainPoint = a.getADRemainPoint();
		long ADRemainCount = a.getADRemainCount();
		String isADing = a.isADing() ? "광고중" : "중단";
		String ADImgURL = a.getADImgAddr();
		String ADURL = a.getADURL();
		if (!ADURL.startsWith("http") && !ADTools.isNull(ADURL)) { ADURL = "http://"+ADURL; }
		if (ADTools.isNull(ADImgURL)) { ADImgURL = "image/default.png"; }
		else if (!ADImgURL.startsWith("http")) { ADImgURL = "http://"+ADImgURL; }
		%>
		 <ul class="ADInfo_row" > 
			<li class="ADCount_col AD_main_li"><%=ADRemainCount %></li> 
			<li class="ADImgURL_col AD_main_li"> <img src="<%=ADImgURL %>" width="50px" height="50px" > </li>
			<li class="ADCtt_col AD_main_li"><ul>
				<a class="pointerBtn" onclick="goSelect(<%=ADCode%>);" ><%=ADCtt %></a><br>
				<% if (!ADTools.isNull(ADURL)) { %> <a href="<%=ADURL %>"><font size="0.7em" >링크 확인</font></a> <%} %>
			</ul></li>
			<li class="ADBonus_col AD_main_li"><%=ADBonus %> </li>
			<li class="ADStat_col AD_main_li"> <%=isADing %></li>
		</ul>
		<%
	}
}
%>
<br>

<form id="goSelectForm" action="<%=SELECT_AD_SERVLET_URL %>" method="post" >
	<input id="ADCodeInput" name="ADCode" type="hidden" value="-1" />
</form>

</body>
</html>