<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*" %>

<%
String ADPath = (String)request.getAttribute("insidePage");
%>
<jsp:include page="header.jsp" flush="flush" />
<jsp:include page="<%=ADPath %>" flush="flush" />
