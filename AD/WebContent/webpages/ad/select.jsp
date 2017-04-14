<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, DTO.*, util.*" %>
<script type="text/javascript" >
var origin_ADCtt;
var origin_checkbox_sex;
var origin_checkbox_age;
var origin_checkbox_local;
var origin_local_values;
var origin_radio_sex;
var origin_radio_ageType;
var origin_ADBonus;
var origin_ADRemainCount;

function readyEditAD() {
	origin_checkbox_sex = document.getElementById("input_checkbox_sex").checked;
	origin_checkbox_age = document.getElementById("input_checkbox_age").checked;
	origin_checkbox_local = document.getElementById("input_checkbox_local").checked;
	origin_ADCtt = document.getElementById("textarea_ADCtt").value;
	
	var localValues = document.getElementsByClassName("local");
	origin_local_values = new Array(localValues.length);
	for (var i=0 ; i<localValues.length ; i++) { var localValue = localValues[i]; origin_local_values[i] = localValue.checked; }
	
	var sexValues = document.getElementsByClassName("sex");
	for (var i=0 ; i<sexValues.length ; i++) { var sex = sexValues[i]; if(sex.checked) { origin_radio_sex = i; }  }
	
	var ageTypes = document.getElementsByClassName("ageType");
	for (var i=0 ; i<ageTypes.length ; i++) { var ageType = ageTypes[i]; if(ageType.checked) { origin_radio_ageType = i;} }
	
	var divOther = document.getElementById("div_btn_other_set");
	var divEditFunction = document.getElementById("div_btn_edit_function");
	var reads = document.getElementsByClassName("read");
	var diss = document.getElementsByClassName("dis");
	
	for (var i=0 ; i<reads.length ; i++) { var input = reads[i]; input.removeAttribute('readonly'); }
	for (var i=0 ; i<diss.length ; i++) { var input = diss[i]; input.removeAttribute('disabled'); }
	
	divOther.style.display = "none";
	divEditFunction.style.display = "block";
}

function cancelEditAD() {
	var checkbox_sex = document.getElementById("input_checkbox_sex");
	var checkbox_age = document.getElementById("input_checkbox_age");
	var checkbox_local = document.getElementById("input_checkbox_local");
	var textarea_ADCtt = document.getElementById("textarea_ADCtt")
	
	var divOther = document.getElementById("div_btn_other_set");
	var divEditFunction = document.getElementById("div_btn_edit_function");
	var reads = document.getElementsByClassName("read");
	var diss = document.getElementsByClassName("dis");
	
	textarea_ADCtt.value = origin_ADCtt;
	checkbox_sex.checked = origin_checkbox_sex; changeSex();
	checkbox_age.checked = origin_checkbox_age; changeAge();
	checkbox_local.checked = origin_checkbox_local; changeLocal();

	for (var i=0 ; i<reads.length ; i++) { var input = reads[i]; input.setAttribute('readonly', true); }
	for (var i=0 ; i<diss.length ; i++) { var input = diss[i]; input.setAttribute('disabled', true); }
	
	divOther.style.display = "block";
	divEditFunction.style.display = "none";
		
}

function changeSex() {
	var sexValues = document.getElementsByClassName("sex");
	var checkbox_sex = document.getElementById("input_checkbox_sex");
	var inputs = document.getElementsByClassName("sex");
	var div = document.getElementById("div_target_option_sex");

	if (checkbox_sex.checked) { 
		var sexValues = document.getElementsByClassName("sex");
		for (var i=0 ; i<sexValues.length ; i++) { var sex = sexValues[i]; if(i == origin_radio_sex) { sex.checked = true; }  }
		div.style.visibility='visible'; 
	}
	else { 
		for (var i=0 ; i<sexValues.length ; i++) { var sex = sexValues[i];  sex.checked = false;  }
		div.style.visibility="hidden";
	}
	
	for (var i=0 ; i<inputs.length ; i++) {
		var input = inputs[i];
		if (checkbox_sex.checked) { input.removeAttribute('disabled'); } 
		else { input.setAttribute('disabled', true); }
	}
}

function changeAge() {
	var ageTypes = document.getElementsByClassName("ageType");
	var checkbox_age = document.getElementById("input_checkbox_age");
	var inputs = document.getElementsByClassName("ageType");
	var div = document.getElementById("div_target_option_age");
	
	if (checkbox_age.checked) { 
		var ageTypes = document.getElementsByClassName("ageType");
		for (var i=0 ; i<ageTypes.length ; i++) { var ageType = ageTypes[i]; if(i == origin_radio_ageType) { ageType.checked = true; } }	
		div.style.visibility='visible'; 
	}
	else { 
		for (var i=0 ; i<ageTypes.length ; i++) { var ageType = ageTypes[i]; ageType.checked = false; }
		div.style.visibility="hidden"; 
	}
	
	for (var i=0 ; i<inputs.length ; i++) {
		var input = inputs[i];
		if (checkbox_age.checked) { input.removeAttribute('disabled'); } 
		else { input.setAttribute('disabled', true); }
	}
}

function changeLocal() {
	var checkbox_age = document.getElementById("input_checkbox_local");
	var inputs = document.getElementsByClassName("local");
	var div = document.getElementById("div_target_option_local");

	var localValues = document.getElementsByClassName("local");
	for (var i=0 ; i<localValues.length ; i++) {
		var localValue = localValues[i];
		localValue.checked = origin_local_values[i];
	}
	
	if (checkbox_age.checked) { div.style.visibility='visible'; }
	else { div.style.visibility="hidden"; }
	
	for (var i=0 ; i<inputs.length ; i++) {
		var input = inputs[i];
		if (checkbox_age.checked) { input.removeAttribute('disabled'); } 
		else { input.setAttribute('disabled', true); }
	}
}

</script>
<script type="text/javascript" >
function deleteAD() {
	var form = document.getElementById("form_functionAD");
	var ADFunction = document.getElementById("input_function");
	if (confirm("정말 삭제하시겠습니까?\n잔여 포인트는 자동으로 회수됩니다.") == true) {
		ADFunction.value = "D";
		form.submit();
	} else {
		return;
	}
}

function stopAD() {
	var form = document.getElementById("form_functionAD");
	var ADFunction = document.getElementById("input_function");
	if (confirm("정말 중지하시겠습니까?") == true) {
		ADFunction.value = "S";
		form.submit();
	} else {
		return;
	}
}

function awakenAD() {
	var form = document.getElementById("form_functionAD");
	var ADFunction = document.getElementById("input_function");
	if (confirm("광고를 재개하시겠습니까?") == true) {
		ADFunction.value = "A";
		form.submit();
	} else {
		return;
	}
}

function collectAD(isADing) {
	var form = document.getElementById("form_functionAD");
	var ADFunction = document.getElementById("input_function");
	var msg = "포인트를 회수하시겠습니까?";
	if (isADing) { msg+="\n광고는 자동으로 중지됩니다.";	} 
	
	if (confirm(msg) == true) {
		ADFunction.value = "collect";
		form.submit();
	} else {
		return;
	}
}

</script>

<%
	ServletContext sc = request.getServletContext();

	String checkbox_ADLocal = "";
	String HTMLTag = "";

	long ADCode = 0;
	String ADSbj = "";
	String ADCtt = "";
	String ADImgURL = "";
	String ADURL = "";
	long ADRemainPoint = 0;
	long ADRemainCount = 0;
	long ADBonus = 0;
	boolean isADing = false;
	
	HashMap<String, Object> tmp = new HashMap<String ,Object>();
	ArrayList<ADTarget> ADTargetList = new ArrayList<ADTarget>();
	ADInfo a = new ADInfo();
	
	tmp = (HashMap<String, Object>)request.getAttribute("ADInfoMap_some");
	if(tmp.get("result").equals("T")) { 
		a = (ADInfo)tmp.get("ADInfo");
		
		ADCode = a.getADCode();
		ADCtt = a.getADCtt();
		ADImgURL = a.getADImgAddr();
		if (ADTools.isNull(ADImgURL)) { ADImgURL = "image/default.png"; }
		else if (!ADImgURL.startsWith("http")) { ADImgURL = "http://"+ADImgURL; }
		ADURL = a.getADURL();
		if (!ADURL.startsWith("http") && !ADTools.isNull(ADURL)) { ADURL = "http://"+ADURL; }
		ADRemainPoint = a.getADRemainPoint();
		ADRemainCount = a.getADRemainCount();
		ADBonus = a.getADBonus();
		isADing = a.isADing();
	}
%>
	
<%
	boolean useSex = false;
	boolean useAge = false;
	String ageType = "";
	boolean useLocal = false;
	
	String sex = "";
	ArrayList<String> localList = new ArrayList<String>();
	long age1 = 0;
	long age2 = 0;
	
	tmp = (HashMap<String, Object>)request.getAttribute("ADTargetMap_some");
	if(tmp.get("result").equals("T"))	{
		ADTargetList = (ArrayList<ADTarget>) tmp.get("ADTargetList");  
		for (ADTarget t : ADTargetList) {
			if (t.getTargetType().equals("G")) { useSex = true; sex = t.getTargetValue(); }
			else if (t.getTargetType().equals("B") || t.getTargetType().equals("S")) { 
				useAge = true; 
				ageType = t.getTargetType();
				if (age1 == 0) { age1 = Long.parseLong(t.getTargetValue()); }
				else { age2 = Long.parseLong(t.getTargetValue()); }
			}
			else if (t.getTargetType().equals("L")) { useLocal = true; localList.add(t.getTargetValue()); }
		}
	}
	
%>

<%
	
	HashMap<String, String> localMap = (HashMap<String, String>) sc.getAttribute("localMap");
	
	for(Map.Entry<String, String> entry : localMap.entrySet()) {
		String val = entry.getValue();
		String key = entry.getKey();
		boolean checked = false;
		for (String local : localList) {
			if (val.equals(local)) { checked = true; localList.remove(local); break; }
		}
		checkbox_ADLocal
		+= "<label for='local_"+ val +"'>";
		checkbox_ADLocal 
			+= "<input class='local dis' type='checkbox' id='local_"+ val +"'name='localValue'";
		checkbox_ADLocal
			+= " value='"+ val + "' " + (checked ? "checked='checked'" : "") +" disabled />" + key +"</label>";
	}			

%>

<textarea id="textarea_ADCtt" name="ADCtt" class="read" form="form_updateAD" rows="3" cols="80" readonly="readonly"><%=ADCtt %></textarea><br>
<span id="span_image" style="display: inline-block; " >
	<img src="<%=ADImgURL %>" width="200px" height="200px" />
</span>
<span id="span_form" style="display: inline-block; " >
	<form id="form_updateAD" action="/updateAD.ad" method="post" >
		<input type="text" class="read" name="ADURL" 
			value="<%=ADTools.isNull(ADURL) ? "등록된 URL이 없습니다." : ADURL %>" readonly /><br>
		
		<label for="input_checkbox_sex"  >
			<input id="input_checkbox_sex" class="dis" type="checkbox" name="targetType" value="G"
			 <% if(useSex) out.print("checked='checked'"); %> disabled onchange="changeSex(); " /> 성별
		</label>
		<label for="input_checkbox_age"  >
			<input id="input_checkbox_age" class="dis" type="checkbox" name="targetType" value="A"
			 <% if(useAge) out.print("checked='checked'"); %> disabled onchange="changeAge(); " /> 나이
		</label>
		<label for="input_checkbox_local" >
			<input id="input_checkbox_local" class="dis" type="checkbox" name="targetType" value="L" 
			<% if(useLocal) out.print("checked='checked'"); %> disabled onchange="changeLocal(); " /> 지역
		</label>
		
		
		<div id="div_target_option_sex" style='visibility: <% out.print( (useSex ? "visible" : "hidden") ); %>' >
			<label for="input_radio_sex_nope">
				<input id="input_radio_sex_nope" class="sex dis" type="radio" name="sexValue" value="N" 
				<% if(sex.equals("N")) out.print("checked='checked'"); %> disabled /> 비공개
			</label>
			<label for="input_radio_sex_male">
				<input id="input_radio_sex_male" class="sex dis " type="radio" name="sexValue" value="M" 
				<% if(sex.equals("M")) out.print("checked='checked'"); %> disabled /> 남성		
			</label>
			<label for="input_radio_sex_female">
				<input id="input_radio_sex_female" class="sex dis" type="radio" name="sexValue" value="F"
				<% if(sex.equals("F")) out.print("checked='checked'"); %> disabled /> 여성
			</label>
		</div>
		
		<div id="div_target_option_age" style='visibility: <% out.print( (useAge ? "visible" : "hidden") ); %>' >
			<label for="input_radio_age_some">
				<input id="input_radio_age_some" class="ageType dis" type="radio" name="ageType" value="S"
				<% if(ageType.equals("S")) out.print("checked='checked'"); %> disabled /> 연령
			</label>
			<label for="input_radio_age_between" >
				<input id="input_radio_age_between" class="ageType dis" type="radio" name="ageType" value="B"
				<% if(ageType.equals("B")) out.print("checked='checked'"); %> disabled /> 연령대
			</label>
		</div>
		
		<div id="div_target_option_local" style='visibility: <% out.print( (useLocal ? "visible" : "hidden") ); %>' >
			<%=checkbox_ADLocal	%>
		</div>
		
	</form>
</span>

<br>
<div id="div_btn_other_set" >
	<button id="btn_readyEditAD" onclick="readyEditAD();" >  광고 수정 </button> 
	<button id="btn_collectAD" onclick="collectAD(<%=isADing %>);" > 포인트 회수 </button>
	<% 
	if(isADing) { %><button id="btn_stopAD" onclick="stopAD();" > 광고 중단 </button><% } 
	else { %><button id="btn_awakenAD" onclick="awakenAD();" > 광고 재개 </button><% }
	%>
<button id="btn_deleteAD" onclick="deleteAD();" > 광고 삭제 </button><br>
</div>
<div id="div_btn_edit_function" style="display:none; ">
	<button id="btn_cancelEditAD" onclick="cancelEditAD(); "> 취소 </button>
	<button id="btn_doEditAD" onclick="doEditAD(); "> 수정 </button>
</div>

<form id="form_functionAD" action="functionAD.ad" method="post" >
	<input id="input_ADCode" name="ADCode" type="hidden" value="<%=ADCode %>" />
	<input id="input_function" name="ADFunction" type="hidden" value="" />
</form>

