<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, DTO.*, DAO.*"%>
<script type="text/javascript">
function goADList() {
	var form = document.getElementById("ADClickForm");
	var insidePage = document.getElementById("insidePage");
	insidePage.value = "main";
	form.submit();
}

function goADInsert() {
	var form = document.getElementById("ADClickForm");
//	form.innerHTML = "<input type='hidden' id='insidePage' name='insidePage' value='insert.jsp'>";
	var insidePage = document.getElementById("insidePage");
	insidePage.value = "insert";
	form.submit();
}

</script>

<button onclick="goADList();">광고 목록</button>
<button onclick="goADInsert();">광고 삽입</button>

<form id="ADClickForm" method="post" action="myAD.ad"> 
	<input type="hidden" id="insidePage" name="insidePage" value="insert">
</form>
