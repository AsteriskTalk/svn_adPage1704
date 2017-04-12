<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" import="org.json.simple.*, java.io.*, java.util.*" %>

<%
	response.setContentType("application/JSON");
	PrintWriter output = response.getWriter();
	
	JSONObject obj = new JSONObject();
	//JSONArray arr = new JSONArray();
	
	boolean result = (Boolean) request.getAttribute("result");
	String html = (String) request.getAttribute("html");
	
	obj.put("html", html);
	obj.put("result", result);

	output.println( obj.toJSONString() );
	output.flush();
	
%>
