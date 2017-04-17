<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, DTO.*"%>
<style>
.editProfileRow { display: inline-block; width: 100%; }
.editProfileCol_header { width: 120px; text-align: right; }
.editProfileCol_value { width: 500px; background:#78ffff; }
.div_editMode { display: none; }
</style>
<script type="text/javascript">
function doEditProfile() {
	location.href="";
}
function goEditMode() {
	var div_viewMode_tags = document.getElementsByClassName("div_viewMode");
	var div_editMode_tags = document.getElementsByClassName("div_editMode");
	for (var i=0 ; i<div_viewMode_tags.length ; i++) { div_viewMode_tags[i].style.display = "none"; }
	for (var i=0 ; i<div_editMode_tags.length ; i++) { div_editMode_tags[i].style.display = "block"; }
}

function liftEditMode() {
	var div_viewMode_tags = document.getElementsByClassName("div_viewMode");
	var div_editMode_tags = document.getElementsByClassName("div_editMode");
	for (var i=0 ; i<div_viewMode_tags.length ; i++) { div_viewMode_tags[i].style.display = "block"; }
	for (var i=0 ; i<div_editMode_tags.length ; i++) { div_editMode_tags[i].style.display = "none"; }
}

function changePassword() {
	window.open(
			"management/changePassword.jsp", "비밀번호 변경",
			"width=400, height=500, toolbar=no, location=no, status=no, menubar=no, scrollbars=no, resizable=no, left=150, top=150"
	);
}

</script>
<%
	HashMap<String, Object> resultSet = (HashMap<String, Object>)request.getAttribute("resultSet");
	ClientInfo cInfo = null;
	ClientProfile cProfile = null;
	ClientPoint cPoint = null;
	ArrayList<ADInfo> ADInfoList = null;
	
	if (resultSet.get("result").equals("T")) { cInfo = (ClientInfo)resultSet.get("clientInfo"); }
	if (resultSet.get("profileResult").equals("T")) { cProfile = (ClientProfile)resultSet.get("clientProfile");  }
	if (resultSet.get("pointResult").equals("T") ) { cPoint = (ClientPoint)resultSet.get("clientPoint"); }
	if (resultSet.get("ADResult").equals("T")) { ADInfoList = (ArrayList<ADInfo>)resultSet.get("ADInfoList"); }
	
%>

<form action="/doEditProfile.ad" method="post" enctype="multipart/form-data">
	<div class="editProfileRow" > 
		<span class="editProfileCol_header"> 
			ID 
		</span>
		<span class="editProfileCol_value" >
			<div class="div_viewMode">
				<%=cInfo.getClientID() %>
			</div>
			<div class="div_editMode">
				<input type="text" placeholder="" readonly />
				<button type="button" onclick="changePassword();"> 비밀번호 변경 </button>
			</div>
		</span>
	</div>
	<div class="editProfileRow" > 
		<span class="editProfileCol_header"> 
			E-mail
		</span>
		<span class="editProfileCol_value" >
			<div class="div_viewMode">
				<%=cProfile.getClientEmail() %>
			</div>
			<div class="div_editMode">
				<input type="text" placeholder="<%=cProfile.getClientEmail() %>" readonly />
			</div>
		</span>
	</div>
	<div class="editProfileRow">
		<span class="editProfileCol_header">
			Name 
		</span>
		<span class="editProfileCol_value">
			<div class="div_viewMode">
				<%=cProfile.getClientName() %>
			</div>
			<div class="div_editMode">
				<input type="text" name="clientName" placeholder="<%=cProfile.getClientName() %>" />
			</div>
		</span>
	</div>
	
	<div class="eidtProfileRow">
		<span class="editProfileCol_header">
			Logo
		</span>
		<span class="editProfileCol_value">
			<img src="<%=cProfile.getClientLogoAddr() %>" id="clientLogo" style="width:150px; height:150px;" >
			<div class="div_viewMode">
			</div>
			<div class="div_editMode">
				<input type="file" name="clientLogo" />
			</div>
		</span>
	</div>
	
	<div class="editProfileRow">
		<span class="editProfileCol_header">
			Content
		</span>
		<span class="editProfileCol_value">
			<div class="div_viewMode">
				<% out.print("<textarea rows='5' cols='40' readonly='readonly'>" + cProfile.getClientCtt() + "</textarea>"); %>
			</div>
			<div class="div_editMode">
				<% out.print("<textarea rows='5' cols='40'>" + cProfile.getClientCtt() + "</textarea>"); %>
			</div>
		</span>
	</div>

</form>
	
	<center>
		<div class="div_viewMode" >
			<button id="editModeBtn" onclick="goEditMode();"> 정보 수정하기 </button>
		</div>
		<div class="div_editMode">
			<button id="liftEditBtn" onclick="liftEditMode();"> 돌아가기 </button>
			<button id="doEditBtn" onclick="doEditProfile();" > 수정 </button>
		</div>
	</center>
