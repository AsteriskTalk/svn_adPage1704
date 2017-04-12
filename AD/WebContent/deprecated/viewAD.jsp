<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="org.json.simple.*, java.io.*, DTO.*, java.util.*" %>
<%
  response.setContentType("application/JSON");
  PrintWriter output = response.getWriter();
  
  JSONObject objResultSet = new JSONObject();
  //JSONObject objTmp = new JSONObject();
  //JSONArray arrResultSet = new JSONArray();
  ADInfo a = (ADInfo)request.getAttribute("ADInfo");
  
  objResultSet.put("ADCode", a.getADCode());
  objResultSet.put("clientCode", a.getClientCode());
  //View = 기본 제공만 지급되므로 ADCode와 clientCode만 준비된다.
  
  output.println(objResultSet.toJSONString());
  output.flush();  
%>
