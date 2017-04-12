<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, DTO.*" %>
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
HttpSession ses = request.getSession();
HashMap<String, Object> tmp = (HashMap<String, Object>)ses.getAttribute("allADHistory");

int maxPage = 0; 
final int MAX = 30;
final int HIST_PAGE = (Integer)request.getAttribute("histPage");
final int START = (HIST_PAGE - 1) * MAX;
final int END = HIST_PAGE * MAX;
//System.out.println("Start - "+ START + " & END -" + END ); 

if (tmp.get("result").equals("T")) {
	ArrayList<ADHistory> ADHistoryList = (ArrayList<ADHistory>) tmp.get("ADHistoryList");
	maxPage = (ADHistoryList.size() / MAX) + ( (ADHistoryList.size() % MAX) > 0 ? 1 : 0 );
	//System.out.println("list - "+ ADHistoryList.size() + " max - "+ maxPage);
	
	for (int i=START ; i<ADHistoryList.size() && i<END ; i++) {
		ADHistory a = ADHistoryList.get(i);
		out.println(a.toString() + "<br>");
	}
	
	for (int i=1 ; i<=maxPage ; i++) {
		if (i != HIST_PAGE) {	%><a class="pageBtn" onclick="goPage(<%=i %>);" ><%=i %></a> <% } 
		else { %><b><%=i %></b><% }
		if (i != maxPage) { %> &nbsp;<% }
	}
	
} else {
	%> failed <%
}

%>

<form id="goPageForm" action="/historyClick.ad" method="post">
	<input type="hidden" name="historyPath" value="all.jsp">
	<input type="hidden" id="histPage" name="histPage">
</form>
</body>
</html>