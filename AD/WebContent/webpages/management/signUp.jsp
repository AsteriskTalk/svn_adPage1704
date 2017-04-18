<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, DTO.*" %>
<%
	HashMap<String, Object> map = new HashMap<String, Object>();
  String questions = "<option value='0' selected='selected'> 질문을 선택해주세요. </option>";
	
  map = (HashMap<String, Object>)request.getAttribute("questions");
	if (map.get("result").equals("T")) {
		ArrayList<PasswordQuestion> list = (ArrayList<PasswordQuestion>)map.get("PQList");
		for (PasswordQuestion pq : list) {
			questions += "<option value='"+ pq.getqNo() +"'>"+ pq.getQuestion() +"</option>";
		}
	}
%>

<!-- form style -->
<style>
.signUpRow { display: inline-block; width: 90% }
.signUpCol_header { display: inline-block; width: 150px; text-align: right; }
.signUpCol_value { display: inline-block;  }
.warningMsg { font-size: 0.7em; margin:0; padding: 0; color: red; };
</style>

<script type="text/javascript" src="js/astk/signUpCheckModule.js" > </script>
<div id="div_signUp_rule" style="display:block;" >

이용약관<br>

<label for="ruleCheck" ><input type="checkbox" id="ruleCheck" onclick="ruleCheck();" /> 약관에 동의합니다. </label>
</div>
<div id="div_signUp_form" style="display:none;" >

가입 양식<br>
<form id="signUpForm" action="doSignUp.ad" method="post" enctype="multipart/form-data">
	<div class="signUpRow">
		<span class="signUpCol_header"> 
			아이디
		</span>
		<span class="signUpCol_value">  
			<input type="text" name="clientID" id="clientID" onkeyup="changeID();" onchange="changeID();"
			style="background-Color:#ffbbbb;" autocomplete="off"/> 
			<input type="text" name="clientIDCheck" id="clientIDCheck" value="false" />
			<button type="button" id="IDCheckBtn" onclick="checkID();" disabled="disabled"> 중복 확인 </button><br>
		</span>
	</div>
	<div class="signUpRow">
		<span class="signUpCol_header"> 
			&nbsp;
		</span>
		<span class="signUpCol_value"> 
			<a class="warningMsg" id="warningMsg_ID1"> 1. 아이디는 영 소문자와 숫자의 조합만 가능합니다. </a><br>
			<a class="warningMsg" id="warningMsg_ID2"> 2. 아이디의 첫 글자로 숫자를 사용할 수 없습니다. </a><br>
			<a class="warningMsg" id="warningMsg_ID3"> 3. 아이디는 최소한 한 글자의 영문자가 포함되어야 합니다. </a><br>
			<a class="warningMsg" id="warningMsg_ID4"> 4. 아이디의 길이는 4 ~ 16자 까지만 허용합니다.</a><br>
		</span>
	</div>
	
	<div class="signUpRow">
		<span class="signUpCol_header">  
			비밀번호
		</span>
		<span class="signUpCol_value">  
			<input type="password" name="clientPW" id="clientPW1" onkeyup="comparePW();" style="background-Color:#ffbbbb;"  /> <br>
		</span>
	</div>
	<div class="signUpRow">
		<span class="signUpCol_header">  
			비밀번호 재입력
		</span>
		<span class="signUpCol_value">  
			<input type="password" id="clientPW2" onkeyup="comparePW();" style="background-Color:#ffbbbb;" /> <br>
		</span>
	</div>
	<div class="signUpRow">
		<span class="signUpCol_header"> 
			&nbsp;
		</span>
		<span class="signUpCol_value">  
			<a class="warningMsg" id="warningMsg_PW" style="fontWight:bold; "> 비밀번호는 6~20자리의 문자, 숫자, 특수문자(!@#$^&*+-)의 조합만 가능합니다. </a>
		</span>
	</div>
	
	
	<div class="signUpRow">
		<span class="signUpCol_header">
			비밀번호 찾기 질문
		</span>
		<span class="signUpCol_value">
			<select id="select_question" name="clientQuestion"><%=questions %></select>
		</span>
	</div>
	<div class="signUpRow">
		<span class="signUpCol_header">
			비밀번호 찾기 답변
		</span>
		<span class="signUpCol_value">
			<input id="input_answer" type="text" name="clientAnswer" onkeyup="checkAnswer();" onchange="checkAnswer();" placeholder="예) 홍길동" required/>
		</span>
	</div>
	<div class="signUpRow">
		<span class="signUpCol_header"> 
			&nbsp;
		</span>
		<span class="signUpCol_value">  
			<a class="warningMsg" id="a_warningMsg_answer" style="fontWight:bold; "> 비밀번호 찾기 답변은 2~20자리의 문자, 숫자, 영문자만 입력 가능합니다. </a>
		</span>
	</div>
	
	<div class="signUpRow">
		<span class="signUpCol_header">
			이메일
		</span>
		<span class="signUpCol_value">  
			<input type="text" name="clientEmail_header" id="clientEmail_header" onkeyup="checkEmail();" onchange="checkEmail();" 
			style="background-color:#ffbbbb;"  autocomplete="off"/>@
			<select name="clientEmail_footer" id="clientEmail_footer" onchange="checkEmail();" >
				<option value="naver.com" > naver.com </option>
				<option value="daum.net" > daum.net </option>
				<option value="gmail.com" > gmail.com </option>
			</select>
			<input type="text" name="clientEmail" id="clientEmail" readonly />
		</span>
	</div>
	<div class="signUpRow">
		<span class="signUpCol_header"> 
			&nbsp;
		</span>
		<span class="signUpCol_value">  
			<a class="warningMsg" id="warningMsg_email" style="fontWight:bold; "> 이메일은 영 대/소문자와 숫자, 마침표(.) 만 사용할 수 있습니다. </a>
		</span>
	</div>
	<div class="signUpRow">
		<span class="signUpCol_header">  
			연락처
		</span>
		<span class="signUpCol_value">  
			<select name="clientPhone_header" id="phoneHeader" onchange="checkPhone();">
				<option value="010" > 010 </option>
				<option value="011" > 011 </option>
				<option value="016" > 016 </option>
				<option value="017" > 017 </option>
				<option value="018" > 018 </option>
				<option value="019" > 019 </option>
			</select>-
			<input type="text" name="clientPhone_mid" id="phoneMid" onkeyup="checkPhone();" onchange="checkPhone();"
				style="width:60px; background-color:#ffbbbb;" />-
			<input type="text" name="clientPhone_footer" id="phoneFooter" onkeyup="checkPhone();" onchange="checkPhone();"
				style="width:60px; background-color:#ffbbbb;" />
			<input type="text" name="clientPhone" id="clientPhone" readonly/>
			<input type="text" id="clientPhoneCheck" value="false" />
		</span>
	</div>
	<div class="signUpRow">
		<span class="signUpCol_header"> 
			&nbsp;
		</span>
		<span class="signUpCol_value">  
			<a class="warningMsg" id="warningMsg_phone" style="fontWight:bold; ">  </a>
		</span>
	</div>
	<div class="signUpRow">
		<span class="signUpCol_header">  
			별명/상호
		</span>
		<span class="signUpCol_value">
			<input type="text" name="clientName" id="clientName"  onkeyup="checkName();" onchange="checkName();"
			style="background-color:#ffbbbb;"  autocomplete="off" /> 
			<input type="text" id="clientNameCheck" value="false" />
		</span>
	</div>
	<div class="signUpRow">
		<span class="signUpCol_header"> 
			&nbsp;
		</span>
		<span class="signUpCol_value">  
			<a class="warningMsg" id="warningMsg_name" style="fontWight:bold; ">  </a>
		</span>
	</div>
	<div class="signUpRow">
		<span class="signUpCol_header">  
			로고
		</span>
		<span class="signUpCol_value">  
			<img style="width:150; height:150;" src="" />
			<input type="file" name="clientLogo" id="clientLogo" />
		</span>
	</div>
	<div class="signUpRow">
		<span class="signUpCol_header">  
			광고주 소개
		</span>
		<span class="signUpCol_value">  
			<textarea id="clientCtt_area" rows="5" cols="50" style="background-color:#ffffbb" ></textarea>
			<input type="text" name="clientCtt" id="clientCtt" readonly /> 
		</span>
	</div>
</form>

<button type="button" onclick="doSignUp();"> 확인 </button> <button type="button" onclick="cancelSignUp();" > 돌아가기 </button>

</div>