<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, DTO.*, util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
.pageBtn { cursor: pointer;}
</style>
</head>
<body>
<script type="text/javascript">
function goPage(i) {
	var form = document.getElementById("goPageForm");
	var histPage = document.getElementById("histPage");
	histPage.value = i;
	form.submit();
}

</script>
<%
	HashMap<String, Object> info = (HashMap<String, Object> ) request.getAttribute("someADInfo");
	HashMap<String, Object> hist = (HashMap<String ,Object> ) request.getAttribute("someADHistory");
	ArrayList<ADHistory> list = new ArrayList<ADHistory>();
	ADInfo adInfo = new ADInfo();
	
	if (hist.get("result").equals("T") && info.get("result").equals("T")) { 
		list = (ArrayList<ADHistory>)hist.get("ADHistoryList");
		adInfo = (ADInfo) info.get("ADInfo");
		
		String ADURL = adInfo.getADURL();
		String ADCtt = adInfo.getADCtt();
		String ADImgURL = adInfo.getADImgAddr();
		long ADBonus = adInfo.getADBonus();
		long ADRemainPoint = adInfo.getADRemainPoint();
		long ADRemainCount = adInfo.getADRemainCount();
		long ADCode = adInfo.getADCode();
		
	%>
				
	<%
		int maxPage = 0; 
		final int MAX = 20;
		final int HIST_PAGE = (Integer)request.getAttribute("histPage");
		final int START = (HIST_PAGE - 1) * MAX;
		final int END = HIST_PAGE * MAX;
		
		maxPage = (list.size() / MAX) + ( (list.size() % MAX) > 0 ? 1 : 0 );
		//System.out.println("list - "+ ADHistoryList.size() + " max - "+ maxPage);
		
		for (int i=START ; i<list.size() && i<END ; i++) {
			ADHistory h = list.get(i);
			%> <%=h.toString() %><br> <%
		}

		for (int i=1 ; i<=maxPage ; i++) {
			if (i != HIST_PAGE) { %><a class="pageBtn" onclick="goPage(<%=i %>);"><%=i %> </a> <% } 
			else { %><b><%=i %> </b> <% }
			if (i != maxPage) { %>&nbsp;<% }
		}
		%>
		
		<form id="goPageForm" action="/historyClick.ad" method="post">
			<input type="hidden" name="historyPath" value="some.jsp">
			<input type="hidden" id="histPage" name="histPage">
			<input type="hidden" name="ADCode" value="<%=ADCode %>">
		</form>
		
		<%
		
	}

%>


</body>
</html>