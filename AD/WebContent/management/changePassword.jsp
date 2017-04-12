<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>비밀번호 변경</title>
</head>
<body>
<script type="text/javascript">
  function checkPW() {
  	var PW1 = document.getElementById("newPW1");
  	var PW2 = document.getElementById("newPW2");
  	var form = document.getElementById("changePWForm");
  	if (PW1.value == PW2.value) { form.submit(); }
  	else { alert("새 비밀번호가 일치하지 않습니다."); }
  }
  
  function keyPress() {
  	var PW1 = document.getElementById("newPW1");
  	var PW2 = document.getElementById("newPW2");
  	if (PW1.value == PW2.value) { 
  		PW1.style.backgroundColor = "RGB(100, 255, 77)"; 
  		PW2.style.backgroundColor = "RGB(100, 255, 77)";
  	} else {
  		PW1.style.backgroundColor = "RGB(255, 77, 100)"; 
  		PW2.style.backgroundColor = "RGB(255, 77, 100)";
  		
  	}
  }
  
</script>
<form id="changePWForm" action="changePassword.ad" method="post">
	<input type="password" name="oldPW"  placeholder="기존 비밀번호"/><br>
	<input type="password" name="newPW" id="newPW1" placeholder="변경 후 비밀번호." onkeyup="keyPress()"/><br>
	<input type="password" id="newPW2" placeholder="변경 후 비밀번호를 한번 더 입력하세요." onkeyup="keyPress()"/> <br>
	<button type="button" onclick="checkPW();" > 변경 </button>
</form>
</body>
</html>