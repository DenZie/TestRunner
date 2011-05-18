<jsp:useBean id="task" scope="session" class="com.den.utils.TaskBean"/>
<%@page import="java.util.ArrayList,com.den.utils.*,com.den.utils.*,java.util.Set,java.util.Map,com.den.qa.qtf.Qtf" %>
<% 
Qtf qtf = new Qtf();
qtf.initialise();
ListTests lt = new ListTests();
Map<String, Object[]> testclasses = lt.FetchTestClass();
ArrayList<String> tc = lt.getTestClass(); %>
<script language="javascript">
var data =new Array();
function renderClassList() {
	selectBox = document.getElementById("sltTC");
	var tag ="";
	for(var key in data) {
		tag = tag +"<option value='"+ key +"'>" + key +"</option>";
    }
	selectBox.innerHTML= tag;
	selectBox.selectedIndex=0;
	renderMethodList(selectBox);
};
function validate(from) {
	var selMethod = document.getElementById("sltM");
	if(selMethod.selectedIndex== -1 ) {
		var divMsg = document.getElementById("msgdiv");
		divMsg.innerHTML = "Are you crazy? No methods selected for run. :)";
		var t=setTimeout('document.getElementById("msgdiv").innerHTML=""',3000);
	} else {
		document.getElementById("suiteForm").submit();
	}
};

function renderMethodList(el) {
	var methodDiv = document.getElementById("divTM");
	var tag="<select id='sltM' name='sltM' multiple='multiple'>";
	var optns = el.options;
	for (i = 0; i < optns.length; i++) {
		if (optns[i].selected) {
			cls =  optns[i].value;
			mts = data[cls];
			for(var mtdkey in mts) {
				method= mts[mtdkey];
				tag = tag + "<option value='" + cls + ":" + method+ "'>"+method+"</option>";
		    }
		} 
	}
	tag = tag + "</select>"
	methodDiv.innerHTML= tag;
};

function loadData() {
	<%for (String s: tc) {
		Object[] mts = testclasses.get(s);
		s=s.replace(".", "_");
	%>
	var mtsobj =new Array();
	<%for(int i=0;i<mts.length;i++) {
		String method= mts[i].toString();
	%>
	mtsobj[<%=i%>]= "<%=method%>";
	<%}%>
	data["<%=s%>"] = mtsobj; 
	<%}%>
	renderClassList();
	
};
</script>
<html>
<head> <title>QTF : Test Runner Interface</title>

 
<% if (task.isRunning()) { 
		/*long percent = task.getPercent(); */
				int progress = task.getProgress();
		int total = task.getTotal();

%>
	<SCRIPT LANGUAGE="JavaScript"> setTimeout("location='suite.jsp'", 1000);</SCRIPT> 
	</head>
	<body>
		<table width="60%" align="center" border="1" cellpadding="0" cellspacing="2"><tr>
			<% for(int i=0;i<total;i++) {
				if(progress>i) {%>
				 <td width="10%" bgcolor="#000080">&nbsp;</td></td>
			<%} else {%>
			<td width="10%" >&nbsp;</td>
			<%}}%>
			<tr>
			</table>
			<form method="post" action="stop.jsp">
				<input type="submit" value="Stop">
			</form>
			
<%} else {%> 
	</head>
	<body onload="loadData();">
		<h1 align="center">Test Suite Selector</h1><div id="msgdiv" style="position:absolute;top:0;right:0;background:#ff0;border:1px solid #ddd;"></div>
		<form id="suiteForm" method="post" action="start.jsp">
			<table width="60%" align="center" style="noborder" cellspacing="2">
			<thead>
			<tr><td>
				Test Classes:
			</td><td>
				Test Methods:
			</td></tr>
			</thead>
				<tr>
					<td>
						<div id="divTC">
						<select id="sltTC" name="sltTC" multiple="multiple"  onchange="renderMethodList(this)" ></select>
						</div>
					</td><td>
						
						<div id="divTM"></div>
					</td>
				</tr>
					<td align="center">
						<input type="submit" value="Run Selected Tests" onClick="validate('suiteform');return false;"> 
					</td>
				</tr>
			</table>
		</form>
<%}%> 
	</body> 
</html>

