<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<!-- Desktop -->
<style>
html { min-width: 100%; min-height: 100%; margin: 0; padding: 0; }
div {  display: inline-block; }
#body_index { height: 100%; margin: 0; padding: 0; }
.div_index { width:calc(100% - 50 * 2px); margin: 10px 50px 10px 50px; }
#div_index { height: 100%; }
#div_index_body { height: calc(100% - 120px - 10px - 20 * 3px - 100px); }
#div_index_header { height: 120px; padding: 0 0 10px 0; border-bottom:3px solid #dc3232; }
#div_index_footer { height: 100px; }
</style>
<style>
.pointBtn { cursor: pointer;}
</style>
<title>ASTK AD</title>
<%

final String ATTR_NAME = "viewPath";
boolean hasViewPath = false;
String viewPath = "";

viewPath = (String)request.getAttribute(ATTR_NAME);
%>

</head>

<body id="body_index">
<div id="div_index">
	<div id="div_index_header" class="div_index"> <jsp:include page="common/header.jsp" flush="false" /> </div>
	<div id="div_index_body" class="div_index"> <jsp:include page="<%=viewPath %>" flush="false" /></div>
	<div id="div_index_footer" class="div_index"> <jsp:include page="common/footer.jsp" flush="false" /></div>
</div>
</body>
</html>