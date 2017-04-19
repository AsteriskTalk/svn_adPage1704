<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  import="java.util.*" %>
<%
HashMap<String, Object> tmp = new HashMap<String, Object>();
String ADCtt = "";
String ADImgAddr = "";
long sendCnt = 0;
long touchCnt = 0;
long viewCnt = 0;

long graphWidth = 700;

tmp = (HashMap<String, Object>) request.getAttribute("ADStaticsMap_some");
Iterator<String> i = tmp.keySet().iterator();

while (i.hasNext()) {
	String s =  i.next();
	if (s.equals("S")) { sendCnt = (Long)tmp.get(s); }
	else if (s.equals("V")) { viewCnt = (Long)tmp.get(s); }
	else if (s.equals("T")) { touchCnt = (Long)tmp.get(s); }
}
double failed = ( (double)(sendCnt-viewCnt) / (double)sendCnt);
double touch = ( (double)(touchCnt) / (double)(viewCnt) );

String failedStr = String.format("%.2f", (failed*100) );
String successStr = String.format("%.2f", ( (1-failed)*100) );
String touchStr = String.format("%.2f", (touch*100) );

%>
<style>
#div_graph { display: block; text-align: left; }
.astk_graph { dispaly: block; font-size: 0.7em; }
</style>

<div id="div_graph" >
	<div id="div_send" class="astk_graph" 
	style="width:<%=graphWidth*1.00 %>px; height:10px; background-color: #aaa; "> &nbsp; </div>
	총 발송 수 : <%=sendCnt %> 회<br><br>
	<div id="div_success" class="astk_graph" 
	style="width:<%=graphWidth*(1-failed)%>px; height:10px; background-color:#797; margin:0; padding:0;"> &nbsp; </div>
	노출 : <%=viewCnt %>회, (<%=successStr %>%)<br><br>
	<div id="div_touch" class="astk_graph" 
	style="width:<%=graphWidth*( 1- failed ) * touch %>px; height: 10px; background-color:#faf; margin:0; padding:0;"> &nbsp; </div>
	터치 : <%=touchCnt %>회 (<%=touchStr %>%)<br><br>
</div>