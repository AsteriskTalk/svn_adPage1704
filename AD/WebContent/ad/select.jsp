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
	String HTMLTag = "";
	
	boolean useSex = false;
	boolean useAge = false;
	boolean useLocal = false;

	HashMap<String, Object> ADInfoSet = (HashMap<String, Object>)request.getAttribute("ADInfoSet");
	ArrayList<ADTarget> ADTargetList = new ArrayList<ADTarget>();
	ADInfo adInfo = new ADInfo().setExam();
	
	if(ADInfoSet.get("result").equals("T")) { adInfo = (ADInfo)ADInfoSet.get("ADInfo"); }
	if(ADInfoSet.get("ADTargetResult").equals("T"))	{
		ADTargetList = (ArrayList<ADTarget>) ADInfoSet.get("ADTargetList");  
		
		for (ADTarget target : ADTargetList) {
			HTMLTag 
				+= "<a class='ADTarget'>" + target.getTargetType() +"-"+ target.getTargetValue() + "</a><br>";
		}
	}
	
	
	
	
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
		<img src="http://<%=adInfo.getADImgAddr() %>" width="200px" height="200px" />
	</span>
</div>
<br>
<%=HTMLTag %>
</body>
</html>