/**
 * 
 */

window.onload = function() {
	calcPoint();
}

<!-- To Num String -->

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

<!-- option select -->

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


<!-- calc Point -->
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



<!-- 양수만 입력 -->

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


<!-- add AD -->

function addAD() {
	var form = document.getElementById("insertADForm");
	form.submit();
}


