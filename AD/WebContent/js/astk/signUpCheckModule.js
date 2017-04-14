/**
 * 
 */
<!-- Common script -->


function checkString(str, min, max) {
	if (str.match(/\W/) && !str.match(/\./) ) { return false; }
	if (!str.match(/^\w/)) { return false; }
	if (str.length < min) {  return false; }
	if (str.length > max) { return false; }
	return true;
}



<!-- ID  -->

var min = 4;
var max = 16;

function changeID () {
	
	var warningMsg_ID1 = document.getElementById("warningMsg_ID1");
	var warningMsg_ID2 = document.getElementById("warningMsg_ID2");
	var warningMsg_ID3 = document.getElementById("warningMsg_ID3");
	var warningMsg_ID4 = document.getElementById("warningMsg_ID4");
	
	var IDCheckBtn = document.getElementById("IDCheckBtn");
	
	var b1 = false;
	var b2 = false;
	var b3 = false;
	var b4 = false;
	
	var clientIDCheck = document.getElementById("clientIDCheck");
	var clientID = document.getElementById("clientID");
	clientIDCheck.value = "false";
	clientID.style.backgroundColor = "#ffbbbb";
	
	if (clientID.value.length == 0) {
		warningMsg_ID1.style.color = "red";
		warningMsg_ID2.style.color = "red";
		warningMsg_ID3.style.color = "red";
		warningMsg_ID4.style.color = "red";
		IDCheckBtn.disabled = "true";
		return;
	}
	
	if ( clientID.value.match(/[A-Z]/) || clientID.value.match(/\W/) ) {
		warningMsg_ID1.style.color = "red";
		b1 = false;
	} else {
		warningMsg_ID1.style.color = "green";
		b1 = true;
	}
	
	if (clientID.value.match(/^\d/)) {
		warningMsg_ID2.style.color = "red";
		b2 = false;
	} else {
		warningMsg_ID2.style.color = "green";
		b2 = true;
	}
	
	if (!clientID.value.match(/[a-z]/) ) {
		warningMsg_ID3.style.color = "red";
		b3 = false;
	} else {
		warningMsg_ID3.style.color = "green";
		b3 = true;
	}
	
	if (clientID.value.length < min || clientID.value.length > max ) {
		warningMsg_ID4.style.color = "red";
		b4 = false;
	} else {
		warningMsg_ID4.style.color = "green";
		b4 = true;
	}
	
	if (!b1 || !b2 || !b3 || !b4) { IDCheckBtn.disabled = "true"; } 
	else { 	IDCheckBtn.removeAttribute('disabled'); }
	
	
}

function checkID() {
	var clientID = document.getElementById("clientID");
	
	if ( clientID.value.length == 0) { return; }
	if ( clientID.value.match(/[A-Z]/) || clientID.value.match(/\W/) ) { return; } 
	if ((clientID.value.charAt(0)).match(/\d/)) { return; }
	if (!clientID.value.match(/[a-z]/) ) { return; }
	if (clientID.value.length < min || clientID.value.length > max ) { return; }
	
	window.name="signUpForm";
	window.open("IDCheck.ad?clientID="+clientID.value, "ID 중복 확인", "width=1, height=1, resizable=no, scrollbars=no");

}




<!-- PW -->


function checkPW(clientID, clientPW, clientPW2) {
	var failedMsg_base = "비밀번호는 6~20자리의 문자, 숫자, 특수문자(!@#$^&*+-)의 조합만 가능합니다.";
	var failedMsg_space = "비밀번호에 공백을 사용할 수 없습니다.";
	var failedMsg_id = "비밀번호에 아이디를 사용할 수 없습니다.";
	var failedMsg_null = "비밀번호를 입력해주세요.";
	var failedMsg_compare = "비밀번호가 서로 다릅니다.";
	
	if (clientPW.value.length == 0) {
		alert(failedMsg_null);
		clientPW.focus();
		return false;
	}
	
	if (clientPW.value != clientPW2.value) {
		alert(failedMsg_compare);
		clientPW.focus();
		return false;
	}
	
	if (clientID.value == clientPW.value ) {
		alert(failedMsg_id);
		clientPW.focus();
		return false;
	}
	
	if (clientPW.value.match(/\s/)) {
		alter(failedMsg_space);
		clientPW.focus();
		return false;
	}

	
	if (clientPW.value.length < 6 || clientPW.value.length > 20) {
		alert(failedMsg_base);
		clientPW.focus();
		return false;
	}
	
	if (!( clientPW.value.match(/[!@#$^&*+-]/) && clientPW.value.match(/[a-zA-Z]/) && clientPW.value.match(/\d/) ) 
			|| clientPW.value.match(/[\{\}\/?,;:|~`_%\\]/) 
			|| clientPW.value.match(/\s/) ) {
		alert(failedMsg_base);
		clientPW.focus();
		return false;
	}
	return true;
}

function comparePW() {
	var warningMsg_PW = document.getElementById("warningMsg_PW");
	var PW1 = document.getElementById("clientPW1");
	var PW2 = document.getElementById("clientPW2");
	var colorValue = "";
	
	if (!( PW1.value.match(/[!@#$^&*+-]/) && PW1.value.match(/[a-zA-Z]/) && PW1.value.match(/\d/) ) 
			|| PW1.value.length < 6 || PW1.value.length > 20 
			|| PW1.value.match(/[\{\}\/?,;:|~`_%\\]/) 
			|| PW1.value.match(/\s/) ) { 
		colorValue="#ffbbbb"; 
		warningMsg_PW.innerHTML = "비밀번호는 6~20자리의 문자, 숫자, 특수문자(!@#$^&*+-)의 조합만 가능합니다." ;
		warningMsg_PW.style.color="red";
	} else if (PW1.value != PW2.value) { 
		colorValue="#ffbbbb"; 
		warningMsg_PW.innerHTML = "비밀번호가 일치하지 않습니다." ;
		warningMsg_PW.style.color="red";
	} else { 
		colorValue ="#bbffbb" 
		warningMsg_PW.innerHTML = " " ;
		warningMsg_PW.style.color="green";
	}
	PW1.style.backgroundColor = colorValue;
	PW2.style.backgroundColor = colorValue;
}


<!-- Email -->

function checkEmail() {
	var clientEmailHeader = document.getElementById("clientEmail_header");
	var clientEmailFooter = document.getElementById("clientEmail_footer");
	var clientEmail = document.getElementById("clientEmail");
	var warningMsg_email = document.getElementById("warningMsg_email");
	
	if (checkString(clientEmailHeader.value, 4, 16)) {
		clientEmailHeader.style.backgroundColor = "#bbffbb";
		clientEmail.value = clientEmailHeader.value +"@"+ clientEmailFooter.value;
		warningMsg_email.innerHTML = " "; 
	} else {
		clientEmailHeader.style.backgroundColor = "#ffbbbb";
		clientEmail.value = "nope";
		warningMsg_email.innerHTML = "이메일은 영 대/소문자와 숫자, 마침표(.) 만 사용할 수 있습니다."; 
		warningMsg_email.style.color = "red";
	}
}



<!-- Phone -->

function checkPhone() {
	var phoneHeader = document.getElementById("phoneHeader");
	var phoneMid = document.getElementById("phoneMid");
	var phoneFooter = document.getElementById("phoneFooter");
	var clientPhone = document.getElementById("clientPhone");
	var warningMsg_phone = document.getElementById("warningMsg_phone");
	var clientPhoneCheck = document.getElementById("clientPhoneCheck");
	
	clientPhoneCheck.value = "false";
	var b1 = false;
	var b2 = false;
	
	var midVal = phoneMid.value;
	var footVal = phoneFooter.value;

	if ( midVal<0 || midVal.match(/\D/)) { phoneMid.value = ""; }
	if ( footVal<0 || footVal.match(/\D/) ) { phoneFooter.value =""; }
	
	if (!midVal.match(/\d/) || midVal.length > 4 || midVal.length < 3 ) {
		phoneMid.style.backgroundColor="#ffbbbb";
	} else {
		phoneMid.style.backgroundColor="#bbffbb";
		b1 = true;
	}
	
	if (!footVal.match(/[0-9]/) || footVal.length > 4 || footVal.length < 3 ) {
		phoneFooter.style.backgroundColor="#ffbbbb";
	} else {
		phoneFooter.style.backgroundColor="#bbffbbb";
		b2 = true;
	}
	if (midVal.length>4) {
		var midValCut = midVal.substring(0, 4);
		phoneMid.value = midValCut;
		midVal = phoneMid.value;
	} 
	
	if (footVal.length>4) {
		var footValCut = footVal.substring(0, 4);
		phoneFooter.value = footValCut;
		footVal = phoneFooter.value;
	}
	if ( (midVal.length + footVal.length)== 7 || (midVal.length + footVal.length) == 8 ) {
		warningMsg_phone.style.color="green";
		warningMsg_phone.innerHTML = " ";
		phoneMid.style.backgroundColor="#bbffbb";
		phoneFooter.style.backgroundColor="#bbffbb";
	} else {
		warningMsg_phone.style.color="red";
		warningMsg_phone.innerHTML = "연락처는 10자 혹은 11자만 가능합니다.";
		phoneMid.style.backgroundColor="#ffbbbb";
		phoneFooter.style.backgroundColor="#ffbbbb";
	}
	
	if (midVal.length<3 || footVal.length<3) {
		warningMsg_phone.style.color="red";
		return;
	}
	
	if (b1 && b2) { 
		clientPhone.value = phoneHeader.value +""+ midVal +""+ footVal;
		clientPhoneCheck.value = "true";
	}
	
}


<!-- Name -->

function checkName() {
	var clientName = document.getElementById("clientName");
	var warningMsg_name = document.getElementById("warningMsg_name");
	var clientNameCheck = document.getElementById("clientNameCheck");
	var nameVal = clientName.value;
	
	if (nameVal.length == 0 ) {
		clientName.style.backgroundColor = "#ffbbbb";
		warningMsg_name.style.color="red";
		warningMsg_name.innerHTML = "별명/상호는 최소 한글 2글자 이상, 영문 3글자 이상이어야 합니다.";
		clientNameCheck.value ="false";
		return;
	}
	
	if (nameVal.match(/\w/)) {
		if (nameVal.length < 3) {
			clientName.style.backgroundColor = "#ffbbbb";
			warningMsg_name.style.color="red";
			warningMsg_name.innerHTML = "별명/상호는 최소 한글 2글자 이상, 영문 3글자 이상이어야 합니다.";
			clientNameCheck.value ="false";
			return;
		}
	}
	if (nameVal.match(/[가-힣]/)) {
		if (nameVal.length < 2 ) {
			clientName.style.backgroundColor = "#ffbbbb";
			warningMsg_name.style.color="red";
			warningMsg_name.innerHTML = "별명/상호는 최소 한글 2글자 이상, 영문 3글자 이상이어야 합니다.";
			clientNameCheck.value ="false";
			return;
		}
	}
	
	if ( clientName.value.match(/[\{\}\/?,;:|*~`!^_!#$%&\\]/) || clientName.value.match(/\s/)) {
		clientName.style.backgroundColor = "#ffbbbb";
		warningMsg_name.style.color="red";
		warningMsg_name.innerHTML = "별명/상호는 한글과 영 대/소문자, 숫자 및 일부 특수문자(@, 괄호, 대괄호, -, + ) 만 입력하실 수 있습니다..";
		clientNameCheck.value ="false";
		return;
		
	}
	
	clientName.style.backgroundColor ="#bbffbb";
	warningMsg_name.style.color="green";
	warningMsg_name.innerHTML = " ";
	clientNameCheck.value ="true";
	
}


<!-- Do SignUp -->


function checkNum() {
	
}

function doSignUp() {
	var signUpForm = document.getElementById("signUpForm");
	var clientIDCheck = document.getElementById("clientIDCheck");
	var clientID = document.getElementById("clientID");
	var clientPW = document.getElementById("clientPW1");
	var clientPW2 = document.getElementById("clientPW2");
	var clientEmail = document.getElementById("clientEmail_header");
	var clientPhone_mid = document.getElementById("clientPhone_mid");
	var clientPhone_footer = document.getElementById("clientPhone_footer");
	var clientName = document.getElementById("clientName");
	var clientCtt_area = document.getElementById("clientCtt_area");
	var clientCtt= document.getElementById("clientCtt");
	
	var PWResult = false;
	var IDResult = "false";
	var EmailResult = false;
	
	
	IDResult = clientIDCheck.value;
	if (IDResult == "false") { 
		alert("아이디 확인을 완료해주세요.");
		clientID.focus(); return;
	}
	PWResult = checkPW(clientID, clientPW, clientPW2);
	
	EmailResult = checkString(clientEmail.value, 4, 16);
	
	if (clientCtt_area.value.length ==0 ){ clientCtt.value= "nope"; } 
	else { clientCtt.value = clientCtt_area.value; }

	if (IDResult == "true" && PWResult && EmailResult ) { signUpForm.submit(); }
	
}

function ruleCheck() {
	var div_signUp_rule = document.getElementById("div_signUp_rule");
	var div_signUp_form = document.getElementById("div_signUp_form");
	div_signUp_rule.style.display = "none";
	div_signUp_form.style.display = "block";
}
