<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="util.*, java.util.*"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>AD - Insert</title>

<!-- init -->
<script type="text/javascript">
window.onload = function() {
	calcPoint();
}
</script>

<!-- To Num String -->
<script type="text/javascript">
function toNumString(a) {
	var INPUT_VALUE = "" + a;
	var STR_LENGTH = INPUT_VALUE.length;
	var COMMA_POSITION = 3;

	var result = "";
	var i = STR_LENGTH;
	do {
		var tmp = "";
		
		if ( ( !(i>COMMA_POSITION) ) && i==STR_LENGTH ) { tmp = INPUT_VALUE; }
		else if ( !(i > COMMA_POSITION) ) { tmp = INPUT_VALUE.substring(0, i) + ","; }	
		else if (i == STR_LENGTH) { tmp = INPUT_VALUE.substring(i-COMMA_POSITION); }		
		else { tmp = INPUT_VALUE.substring(i-COMMA_POSITION, i) + ","; }
		tmp += result;
		result = tmp;
		i -= COMMA_POSITION;
	} while (i>0);

	return result;		
}
</script>
<!-- option select -->
<script type="text/javascript">
function optionSex() {
	calcPoint();
	var checkboxSex = document.getElementById("ADTarget_sex");
	var targetSex = document.getElementById("targetSex");
	if (checkboxSex.checked) { targetSex.style.display="inline"; } 
	else { targetSex.style.display="none"; }
}

function optionAge() {
	calcPoint()
	var checkboxAge = document.getElementById("ADTarget_age");
	var radioAge = document.getElementById("radioAge");
	var radioAge_some = document.getElementById("radioAge_some");
	var targetAge_some = document.getElementById("targetAge_some");
	var targetAge_between = document.getElementById("targetAge_between");
	if (checkboxAge.checked) { 
		radioAge_some.checked="checked";
		radioAge.style.display="inline"; 
		targetAge_some.style.display="inline";
	} else { 
		radioAge.style.display="none"; 
		targetAge_some.style.display="none";
		targetAge_between.style.display="none";
	}
}

function optionLocal() {
	calcPoint()
	var checkboxLocal = document.getElementById("ADTarget_local");
	var targetLocal = document.getElementById("targetLocal");
	if (checkboxLocal.checked) { targetLocal.style.display="inline"; }
	else { targetLocal.style.display="none"; }
}

function optionAge_some() {
	calcPoint()
	var radioAge_some = document.getElementById("radioAge_some");
	var targetAge_some = document.getElementById("targetAge_some");
	var targetAge_between = document.getElementById("targetAge_between");
	if (radioAge_some.checked) { 
		targetAge_some.style.display="inline"; 
		targetAge_between.style.display="none"; 
	}
}

function optionAge_between() {
	calcPoint()
	var radioAge_between = document.getElementById("radioAge_between");
	var targetAge_some = document.getElementById("targetAge_some");
	var targetAge_between = document.getElementById("targetAge_between");
	if (radioAge_between.checked) { 
		targetAge_some.style.display="none"; 
		targetAge_between.style.display="inline"; 
	}
}

function insertAD() {
	var form = document.getElementById("insertADForm");
	var ADCtt = document.getElementById("ADCtt");
	var ADPoint = document.getElementById("ADPoint");
	var ADBonus = document.getElementById("ADBonus");
	
}


</script>

<!-- calc Point -->
<script type="text/javascript" >
function calcPoint() {
	var resultMap = { };	
	var resultPoint = 0;
	var resultString = "";
	
	var localCnt = 0;
	var targetCheck = false;
	
	var VIEW = 10;
	var TARGET_LOCAL = 10;
	var TARGET_SEX = 10;
	var TARGET_AGE = 10;
	
	var osa = "<a class='smallStr'>";
	var oba = "<a class='bigStr'>";
	var ca = "</a>"
	var ps = "<a class='normalStr'>&nbsp;&#43;&nbsp;</a>";
	var rs = "<a class='normalStr'>&nbsp;&#61;&nbsp;</a>";
	
	var ADCount = document.getElementById("ADCount");
	var ADBonus = document.getElementById("ADBonus");
	var ADTarget_sex = document.getElementById("ADTarget_sex");
	var ADTarget_age = document.getElementById("ADTarget_age");
	var ADTarget_local = document.getElementById("ADTarget_local");
	
	var resultView = document.getElementById("span_calcPoint");
	
	var pointView = ADCount.value * VIEW;
	resultString += toNumString(pointView) + osa + "(기본)" + ca;
	resultPoint += pointView;
	
	var pointBonus = ADCount.value * ADBonus.value;
	resultString += ps + toNumString(pointBonus) +  osa + "(보너스)" + ca + "<br>";
	resultPoint += pointBonus;
		
	if (ADTarget_sex.checked) { 
		var pointTarget = ADCount.value * TARGET_SEX;
		resultString += ps + toNumString(pointTarget) + osa + "(옵션 : 성별)" + ca;
		resultPoint += pointTarget;
		targetCheck = true;
	}
	
	if (ADTarget_age.checked) { 
		var pointTarget = ADCount.value * TARGET_AGE; 
		resultString += ps + toNumString(pointTarget) + osa + "(옵션 : 나이)" + ca;
		resultPoint += pointTarget;
		targetCheck = true;
	}
	
	if (ADTarget_local.checked) {
		var localValues = document.getElementsByName("localValue");
//		var labelValues = document.getElementsByClassName("label_local");
		for (var i=0 ; i<localValues.length ; i++) {
			if (localValues[i].checked) {
//				if (localCnt != 0 && localCnt % 3 == 0) { resultString += "<br>" }
				var pointTarget = ADCount.value * TARGET_LOCAL;
//				var labelValue = (labelValues[i].innerHTML.split(">"))[1];
//				resultString += ps + pointTarget + osa + "(옵션 : 지역 - " + labelValue +")" + ca;
				resultString += ps + toNumString(pointTarget) + osa + "(옵션 : 지역)" + ca;
				resultPoint += pointTarget;
				targetCheck = true;
				break;
//				resultPoint += pointTarget;
//				localChecked = true;
//				localCnt++;
			}
		}
	}
	if (targetCheck) { resultString+= "<br>"; }
	resultString += rs + oba + toNumString(resultPoint) + ca;
	
	resultView.innerHTML = resultString;
}

</script>

<!-- 양수만 입력 -->
<script type="text/javascript">
function checkNumberValue() {
	var ADBonus = document.getElementById("ADBonus");
	var ADCount = document.getElementById("ADCount");
	var a_ADBonus = document.getElementById("a_ADBonus");
	var a_ADCount = document.getElementById("a_ADCount");
	
	var message1 = "0이하의 숫자는 입력할 수 없습니다.";
	var message2 = "10단위로만 입력하실 수 있습니다.";
	
	if (ADBonus.value <= 0) {
		a_ADBonus.innerHTML = message1;
		ADBonus.value = 10;
	}
	
	if (ADCount.value <= 0) {
		a_ADCount.innerHTML = message1;
		ADCount.value = 10;
	}
	
	if (ADBonus.value % 10 != 0) {
		a_ADBonus.innerHTML = message2;
		var tmp = ADBonus.value;
		ADBonus.value = tmp - tmp%10;
	}
	
	if (ADCount.value % 10 != 0) {
		a_ADCount.innerHTML = message2;
		var tmp = ADCount.value;
		ADCount.value = tmp - tmp%10;
	}
	calcPoint();
}

</script>

<!-- add AD -->
<script type="text/javascript">
function addAD() {
	var form = document.getElementById("insertADForm");
	form.submit();
}

</script>

<!-- Desktop -->
<style>
span { display: inline-block; }
#ADInsertBody { height: 100%; overflow:hidden }
.div_selected_view {display:none; }
.label_local { padding: 0 5px 0 5px; border-right: 1px solid #bbbbbb; }
.label_local:nth-last-child(1) { border: none; }
.insertADRow { width: 100%; display: inline-block; }
.insertADCol_header { width: 100px; text-align: right; margin: 3px; }
.insertADCol_value { width: 500px; text-align: left; margin: 3px; }
#div_calcPoint { width: 100%; text-align:right; }
.bigStr { font-size: 2em; color: red; }
.smallStr { font-size: 1em; color: gray; }
.normalStr { font-size: 1.5em; color: black; }
.warningMsg { font-size: 1em; color: red; }
</style>

</head>
<body id="ADInsertBody">
<%

final String oo = "<option value='";
final String om = "'>";
final String oc = "</option>";
String option_ADPoint = "";
String option_ADBonus = "";
String checkbox_ADLocal = "";

HashMap<String, String> localMap = new HashMap<String, String>();

final int MAX_BONUS = 100;
final int BONUS_INTERVAL = 10;
final int MAX_POINT = 2000;
final int POINT_INTERVAL = 500;


//Create ADBonus Option
for (int i=BONUS_INTERVAL ; i<=MAX_BONUS ; i+=BONUS_INTERVAL) {
	String val = ADTools.toNumString(i);
	option_ADBonus += oo + i + om + val + oc;
}

//Create ADPoint Option
for (int i=POINT_INTERVAL ; i<=MAX_POINT ; i+=POINT_INTERVAL ) {
	String val = ADTools.toNumString(i);
	option_ADPoint += oo + i + om + val + oc;
}

localMap.put("서울", "SEOUL");
localMap.put("대구", "DEAGU");
localMap.put("대전", "DEAGEON");
localMap.put("광주", "GWANGJU");
localMap.put("부산", "BUSAN");
localMap.put("울산", "ULSAN");
localMap.put("인천", "INCHEON");

for(Map.Entry<String, String> tmp : localMap.entrySet()) {
	checkbox_ADLocal
	+= "<label class='label_local' for='local_"+ tmp.getValue() +"'>";
	checkbox_ADLocal 
		+= "<input type='checkbox' id='local_"+ tmp.getValue() +"'name='localValue'";
	checkbox_ADLocal
		+= " value='"+ tmp.getValue() +"' onclick='calcPoint();' />" + tmp.getKey() +"</label>";
}

%>
<form id="insertADForm" action="addAD.ad" method="post" enctype="multipart/form-data">
	<div class="insertADRow">
		<span class="insertADCol_header">
			광고 내용
		</span>
		<span class="insertADCol_value">
			<textarea id="ADCtt" name="ADCtt" cols="60" rows="3">광고 내용을 입력하세요. &#13;&#10;유저의 채팅 창에는 3줄까지만 표시됩니다.</textarea><br>
		</span>
	</div>
	
	<div class="insertADRow">
		<span class="insertADCol_header">
			첨부 이미지
		</span>
		<span class="insertADCol_value">
			<input type="file" id="ADImg" name="ADImg">
		</span>
	</div>
	
	<div class="insertADRow">
		<span class="insertADCol_header">
			URL 링크
		</span>
		<span class="insertADCol_value">
			<input type="text" id="ADURL" name="ADURL">
		</span>
	</div>
	
	<div class="insertADRow">
		<span class="insertADCol_header">
			광고 횟수
		</span>
		<span class="insertADCol_value">
			<input type="number" id="ADCount" name="ADCount" value="500" onchange="checkNumberValue();" />
			<a id="a_ADCount" class="warningMsg" ></a>
		</span>
	</div>
	
	<div class="insertADRow">
		<span class="insertADCol_header">
			보너스
		</span>
		<span class="insertADCol_value">
			<input type="number" id="ADBonus" name="ADBonus" value="10" onchange="checkNumberValue();" />
			<a id="a_ADBonus" class="warningMsg" ></a>
		</span>
	</div>
	
	<div class="insertADRow">
		<span class="insertADCol_header">
			광고 대상
		</span>
		<span class="insertADCol_value">
			<input type="hidden" name="ADTarget" value="a" />
			<label for="ADTarget_sex">
				<input type="checkbox" id="ADTarget_sex" name="ADTarget" value="useSex" onclick="optionSex();"> 성별&nbsp;
			</label>
			<label for="ADTarget_local">
				<input type="checkbox" id="ADTarget_local" name="ADTarget" value="useLocal" onclick="optionLocal();"> 지역&nbsp;
			</label>
			<label for="ADTarget_age">
				<input type="checkbox" id="ADTarget_age" name="ADTarget" value="useAge" onclick="optionAge();"> 연령&nbsp;
			</label>
		</span>
	</div>
	
	<div id="targetSex" class="div_selected_view" >
	<br>
		<label for="sexValue_male">
			<input type="radio" id="sexValue_male" name="sexValue" value="M" checked="checked"/> 남성&nbsp;
		</label>
		<label for="sexValue_female">
			<input type="radio" id="sexValue_female" name="sexValue" value="F"/> 여성&nbsp;
		</label>
		<label for="sexValue_none">
			<input type="radio" id="sexValue_none" name="sexValue" value="N" /> 비공개&nbsp;
		</label>
	<br>
	</div>
	
	<div id="targetLocal" class="div_selected_view" >
	<br>
		<%=checkbox_ADLocal %>
	<br>
	</div>
	
	<div id="radioAge" class="div_selected_view" >
	<br>
		<label for="radioAge_some">
			<input type="radio" id="radioAge_some" name="ageType" value="some" checked="checked" onclick="optionAge_some();"> 연령&nbsp;
		</label>
		<label for="radioAge_between">
			<input type="radio" id="radioAge_between" name="ageType" value="between" onclick="optionAge_between();" > 연령대&nbsp;
		</label>
	<br>
	</div>
	
	<div id="targetAge_some" class="div_selected_view" >
	<br>
		<input type="number" min="1900" max="2017" name="ageValue" id="ADAge" value="1980">년생
	<br>
	</div>
	
	
	<div id="targetAge_between" class="div_selected_view" >
	<br>
		<input type="number" min="1900" max="2017" name="ageValue1" id="ADAge_a" value="1970">년생 ~ 
		<input type="number" min="1900" max="2017" name="ageValue2" id="ADAge_b" value="1980">년생
		<!-- <select id="ADAge_type" name="ageOption">
			<option value="i" selected="selected">대상으로 한다.</option>
			<option value="o">제외한다.</option>
		</select> -->
	<br>
	</div>
	
	<br>	
	<div id="div_calcPoint"> <span id="span_calcPoint"><a class="bigStr">0</a></span> 포인트</div> </font>
	<button type="button" onclick="addAD();"> 광고 추가 </button>
</form>
</body>
</html>