<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" import="org.json.simple.*, java.io.*, java.util.*" %>

<%
	response.setContentType("application/JSON");
	PrintWriter output = response.getWriter();

	JSONObject obj = new JSONObject();
	//JSONArray arr = new JSONArray();
	
	Enumeration e = request.getAttributeNames();
	
	while (e.hasMoreElements()) {
		String s = (String) e.nextElement();
		if (s.startsWith("javax")) { continue; }
		obj.put(s, request.getAttribute(s));
	}

	output.println( obj.toJSONString() );
	output.flush();
%>
