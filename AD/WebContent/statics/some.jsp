<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>some AD Statics</title>
</head>
<body>
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

</body>
</html>