<jsp:useBean id="task" scope="session" class="com.den.utils.TaskBean"/>
<%@page import="java.util.ArrayList,com.den.utils.*,com.den.utils.*,java.util.Set,java.util.Map,com.den.qa.qtf.Qtf" %>
	<link rel="stylesheet" href="/Qtf/js/dojotoolkit/dijit/themes/nihilo/nihilo.css" />
    <script type="text/javascript" src="/Qtf/js/dojotoolkit/dojo/dojo.js"
            djConfig="parseOnLoad:false, isDebug:false"></script> 

<script language="javascript">
var data =new Array();

var jHistory;
var xmlhttpHistory = new getXMLObject();
var xmlhttpProgress = new getXMLObject();
<% if (task.isRunning()) { %>
	var xmlhttp = new getXMLObject();
	var jResult;
	var percent;
	var total;
	showProgress();
<%}%>
var selectedSuite = new Object();
function renderSelectedSuite() {
	var methodList = document.getElementById("divTmUl");
	var methodDiv = document.getElementById("divTM");
	var tagVisible ="<ul id='sltM' >";
	var tagHidden ="";
    	for(i in selectedSuite) {
    		tagVisible = tagVisible + "<li value='" + i + "'>"+i+"</li>";
    		tagHidden = tagHidden + "<input name='Mlist' type='hidden' value='" + i + "'/>";
		    }
    	tagVisible = tagVisible + "</ul>";
    	methodList.innerHTML= tagVisible;
    	methodDiv.innerHTML= tagHidden;
    };
    
    
	dojo.registerModulePath("tmpdir","/Qtf/js/tmpdir");
	dojo.require("dojo.data.ItemFileWriteStore");
	dojo.require("tmpdir.CheckBoxTree");

function myTree( domLocation ) {
	var store = new dojo.data.ItemFileWriteStore( {
      url: "SuiteMapNew.jsp"
      });
	var model = new tmpdir.CheckBoxStoreModel( {
					store: store,
					query: {type: 'parent'},
					rootLabel: 'QTF Test Suite',
					checkboxAll:  true,
					checkboxRoot: true,
					checkboxState: true,
					checkboxStrict: true
					}); 
	var tree = new tmpdir.CheckBoxTree( {
					model: model,
					id: "MenuTree",
					allowMultiState: false,
					branchIcons: true,
					nodeIcons: true
					});
	tree.placeAt( domLocation );

	dojo.connect( tree,"onNodeChecked", function(storeItem, nodeWidget){
		if( !storeItem.root ) {
			var parent = nodeWidget.getParent();
			if(storeItem.type=="child") {
				var methodName = parent.label + "::" + storeItem.name;
			 	selectedSuite[methodName] = "1";
			 	renderSelectedSuite();
			} else {
			alert("Parent");
			}
			 }
		
		}
	);

	dojo.connect(tree, 'onNodeUnchecked', function(storeItem, nodeWidget) {
		if( !storeItem.root ) {
			var parent = nodeWidget.getParent();
			if(storeItem.type=="child") {
				var methodName = parent.label + "::" + storeItem.name;
				delete selectedSuite[methodName];
			 	renderSelectedSuite();
			} else {
			alert("Parent");
			}
			renderSelectedSuite();
		}
	});
}

function getXMLObject()  //XML OBJECT
{
 var xmlHttp = false;
 try {
   xmlHttp = new ActiveXObject("Msxml2.XMLHTTP")  // For Old Microsoft Browsers
 }
 catch (e) {
   try {
     xmlHttp = new ActiveXObject("Microsoft.XMLHTTP")  // For Microsoft IE 6.0+
   }
   catch (e2) {
     xmlHttp = false   // No Browser accepts the XMLHTTP Object then false
   }
 }
 if (!xmlHttp && typeof XMLHttpRequest != 'undefined') {
   xmlHttp = new XMLHttpRequest();        //For Mozilla, Opera Browsers
 }
 return xmlHttp;  // Mandatory Statement returning the ajax object created
}

	//xmlhttp holds the ajax object

function showProgress() {
	if(xmlhttpProgress) { 
		xmlhttpProgress.open("GET","result.jsp" ,true); //gettime will be the servlet name
		xmlhttpProgress.onreadystatechange  = handleServerResponse;
		xmlhttpProgress.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
		xmlhttpProgress.send(null);
	}
}

function showHistory() {
	if(xmlhttpHistory) { 
		xmlhttpHistory.open("GET","history.jsp" ,true); //gettime will be the servlet name
		xmlhttpHistory.onreadystatechange  = handleHistoryRespose;
		xmlhttpHistory.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
		xmlhttpHistory.send(null);
	}
}

function handleHistoryRespose() {
	hDiv = document.getElementById("historyDiv");
	 if (xmlhttpHistory.readyState == 4) {
	   if(xmlhttpHistory.status == 200) {
	     jHistory = eval('(' + xmlhttpHistory.responseText +')'); //Update the HTML Form element 
	     var historyHtml = "";
	     for(i in jHistory) {
	    	jTest = jHistory[i];
	    	var style = "style='color: red;'";
	    	if(jTest.result) {
	    		style = "style='color: green;'";
		    } 
	    	var patt = /(\d+$)/i;
	    	var name = jTest.name;
	    	var folder = name.match(patt);
	    	var param ="";
	    	if(folder!=undefined) {
	    		param = "?test=" + folder[0];
		    } 
		    historyHtml = historyHtml + "<a " + style + " href='showResult.jsp" + param +"'> [" +  + i + "] " + jTest.name + " ( Took " + jTest.duration+ " ms. ) " + "</a></br>";
		}
	    hDiv.innerHTML = historyHtml;
	   }
	   }
	};
	

function handleServerResponse() {
 if (xmlhttpProgress.readyState == 4) {
   if(xmlhttpProgress.status == 200) {
     jResult = eval('(' + xmlhttpProgress.responseText +')'); //Update the HTML Form element 
	total = jResult.total;
	if(total> 0 ) {
	     percent = (jResult.progress * 100 ) / total;
		if (percent < 100) {
			document.getElementById("resultDiv").style.display="block";
			document.getElementById("test").style.width = percent + "%";
  			setTimeout(showProgress, 1000);
       	} else {
       		document.getElementById("test").style.width = "100%";
       		setTimeout(document.getElementById("resultDiv").style.display="none", 1000);
           	setTimeout("location='stop.jsp'", 100);
        }
  		}else {
  	  	setTimeout(showProgress, 5000);
  	}
   }
   }
};

function validate(form) {
	var mList= document.getElementsByName("Mlist")
	if( mList.length < 1 ) {
		var divMsg = document.getElementById("msgdiv");
		divMsg.innerHTML = "Are you crazy? No methods selected for run. :)";
		var t=setTimeout('document.getElementById("msgdiv").innerHTML=""',3000);
	} else {
		document.getElementById("suiteForm").submit();
	}
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
	<body  class="nihilo" onload="showHistory();">
		<h1 align="center">Test Suite Selector</h1> <div id="msgdiv" style="position:absolute;top:0;right:0;background:#ff0;border:1px solid #ddd;"></div>
		
		<table>
		<tr>
			<td>
				<h3>Test Suite Map</h3>
				<div id="CheckboxTree">	
			<script type="text/javascript">
				myTree("CheckboxTree");
			</script>
		</div>
				<div id="contents"></div>
			<td>
			<td>
				<h3>Build History</h3>
    			<div id="historyDiv" ></div>
    		<td>
    	</tr>
		<tr>
			<td>
				<h3>Selected Test Cases</h3>
				<div id="divTmUl" style="display: block"></div>		
    		<td>
    		<td>
				<h5>Test Execution Progress.</h5>
	    		<div class="progressbar">
	        		<div id="test" class="progressbar-completed" style="width:0;">
	        			<div>&nbsp;</div>
	        		</div>
	    		</div>
    		<td>    		
		</tr>
		</table>		
	</div>
		<form id="suiteForm" method="post" action="start.jsp">
			<div id="resultDiv" style="display: none">
	
    		</div>
    		<div id="divTM" name="divTM"></div>
    		<input type="submit" value="Run Selected Tests" onClick="validate('suiteform');return false;"> 
    	</form>
<%//}%> 

	</body> 
</html>

