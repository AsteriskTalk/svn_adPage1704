<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String insidePage = (String)request.getAttribute("insidePage");
%>
<center><jsp:include page="header.jsp" flush="false" /></center><br>
<center><jsp:include page="<%=insidePage %>" flush="false" /></center>
