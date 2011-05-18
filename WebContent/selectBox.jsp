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
var jHistory;
var xmlhttp = new getXMLObject();

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
	var mtsobj = new Array();
	<%for(int i=0;i<mts.length;i++) {
		String method= mts[i].toString();
	%>
	mtsobj[<%=i%>]= "<%=method%>";
	<%}%>
	data["<%=s%>"] = mtsobj; 
	<%}%>
	renderClassList();
	xmlhttp = new getXMLObject();
	showHistory();
	
};
</script>
<html>
<head> <title>QTF : Test Runner Interface</title>
<style type="text/css">
body{font:small "Myriad Pro", sans-serif;}
h1{font-size:1.5em;}
h2{color:#666;font-size:1em;font-weight:normal;}

/*= Core CSS progress bar code */
.progressbar {
    width: 300px;
    background: url(progressbar.png) no-repeat 0 -40px;
}
.progressbar-completed {
    height: 20px;
    margin-left: -1px;
    background: url(progressbar.png) no-repeat 1px 0;
}
.progressbar-completed div {
    float: right;
    width: 50%;
    height: 20px;
    margin-right: -1px;
    background: url(progressbar.png) no-repeat 100% 0;
    display: inline; /* IE 6 double float bug */
}
</style>
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
					<td align="center">
							
					</td>
					
				</tr>
			</table>
		</form>
	</body> 
</html>

