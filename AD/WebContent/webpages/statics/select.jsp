<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  import="java.util.*" %>
<%
HashMap<String, Object> tmp = new HashMap<String, Object>();
String ADCtt = "";
String ADImgAddr = "";
long touchCount = 0;
long viewCount = 0;

tmp = (HashMap<String, Object>) request.getAttribute("ADStaticsMap_some");
Iterator<String> i = tmp.keySet().iterator();

while (i.hasNext()) {
	String s =  i.next();
	out.println("Key : "+ s +" , Value : "+ tmp.get(s)+ "<br>");
}

%>