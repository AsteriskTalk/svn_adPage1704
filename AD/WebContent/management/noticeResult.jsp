<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" import="org.json.simple.*, java.io.*, java.util.*" %>

<%
	response.setContentType("application/JSON");
	PrintWriter output = response.getWriter();

	boolean result = false;
	JSONObject obj = new JSONObject();
	//JSONArray arr = new JSONArray();

	result = (Boolean) request.getAttribute("result");
	obj.put("result", result);
	
	output.println( obj.toJSONString() );
	output.flush();
%>
