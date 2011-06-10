<jsp:useBean id="task" scope="application" class="com.clearspring.utils.TaskBean"/>
<%@ include file="header.jsp" %>
	<%@page import="java.util.ArrayList,com.clearspring.utils.*,com.clearspring.utils.*,java.util.Set,java.util.Map,com.clearspring.qa.qtf.Qtf" %>
    <link rel="stylesheet" type="text/css" href="resources/css/ext-all.css" />
    <link rel="stylesheet" type="text/css" href="css/example.css" />
    <script type="text/javascript" src="js/bootstrap.js"></script>
    <script type="text/javascript" src="js/Spotlight.js"></script>
	<script type="text/javascript" src="js/form.js"></script>
	<link rel="stylesheet" type="text/css" href="css/list-view.css" />
<script language="javascript">
<% if(task.isRunning()) {%>
	showProgress();
<%}%>
function showProgress() {
	 Ext.Ajax.request({
		 url: 'result.jsp',
		 success: function(r) {
		 	var result = Ext.decode(r.responseText);
		 	var total = result.total;
		 	var progress = result.progress;
		 	if(!progress) progress= 0;
		 	var suite = result.suiteName;
		 	var isCompleted = result.finished;
			if(total> 0 ) {
				if (isCompleted == "1") {
					document.getElementById("test").style.width = "100%";
					document.getElementById("progressText").innerHTML = suite + " - Finished All tests [" + (progress) + " / " + total + "]";
					Ext.Ajax.request({
						url: 'stop.jsp',
						success: function(r) {
							qtf.reloadHistory();
					 	}
				 	}); 
					document.getElementById("progress").style.display="none";
				} else {
					if (total == progress) total = total+1;
					percent = (progress * 100 ) / total;
					document.getElementById("progress").style.display="block";
					document.getElementById("test").style.width = percent + "%";
					document.getElementById("progressText").innerHTML = suite + " - Running tests " + (progress+1) + " / " + total ;
					setTimeout(showProgress, 1000);
				}
			} else {
				setTimeout(showProgress, 1000);
			}
	 	}
	 });
}
</script>
<html>
	<head>
		<title>QTF : Test Runner Interface</title>
	</head>
	<body>
		<table>
			<tr>
				<td>
					<div id="tree-div"></div>
				</td>
				<td>
					<div id="suiteDiv"></div>
				</td>
				<td>
	    			<div id="historyDiv" ></div>
	    		</td>
	    		<td>
					<div id="progress"  style="display: none">
						<h5>Test Execution Progress.</h5>
			    		<div class="progressbar">
		    	    		<div id="test" class="progressbar-completed" style="width:0;"></div>
		        			<div id="progressText">&nbsp;</div>
		    			</div>
		    			<div id="stopBtn"></div>
		    		</div>
	    		</td>
	    	</tr>  
    	</table>		
	</div>
	<div id="startBtn"></div>
	<div id="stopBtn"></div>
	<div id="resultDiv" style="display: none"></div>
	</body> 
</html>

