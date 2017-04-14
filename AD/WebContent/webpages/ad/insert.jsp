<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="util.*, java.util.*"%>

<script type="text/javascript" src="js/astk/ADPointAutoCalc.js" > </script>

<!-- Desktop -->
<link rel="stylesheet" type="text/css" href="css/ADInsert.css" media="all" />

</head>
<body id="ADInsertBody">
<%
ServletContext sc = request.getServletContext();

final String oo = "<option value='";
final String om = "'>";
final String oc = "</option>";
String option_ADPoint = "";
String option_ADBonus = "";
String checkbox_ADLocal = "";

HashMap<String, String> localMap = new HashMap<String, String>();

localMap = (HashMap<String, String>) sc.getAttribute("localMap");

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
	<br>
	</div>
	
	<br>	
	<div id="div_calcPoint"> <span id="span_calcPoint"><a class="bigStr">0</a></span> 포인트</div> </font>
	<button type="button" onclick="addAD();"> 광고 추가 </button>
</form>
</body>
</html>