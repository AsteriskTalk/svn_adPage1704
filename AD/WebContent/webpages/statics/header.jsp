<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, DTO.*" %>
<script type="text/javascript">
function getAll() {
	var form = document.getElementById("form_statics");
	var goTo = document.getElementById("goTo");
	goTo.value = "all.jsp";
	form.submit();
}

function getSome() {
	var form = document.getElementById("form_statics");
	var insidePage = document.getElementById("goTo");
	goTo.value = "list";
	form.submit();
}
</script>

<button onclick="getAll();">전체 통계</button>
<button onclick="getSome();">개별 통계</button>

<form id="form_statics" method="post" action="myStatics.ad">
	<input type="hidden" id="goTo" name="goTo">
</form>
