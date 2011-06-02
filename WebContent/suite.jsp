<jsp:useBean id="task" scope="application" class="com.clearspring.utils.TaskBean"/>
	<%@page import="java.util.ArrayList,com.clearspring.utils.*,com.clearspring.utils.*,java.util.Set,java.util.Map,com.clearspring.qa.qtf.Qtf" %>
    <link rel="stylesheet" type="text/css" href="resources/css/ext-all.css" />
    <link rel="stylesheet" type="text/css" href="css/example.css" />
    <script type="text/javascript" src="js/bootstrap.js"></script>
    <script type="text/javascript" src="js/Spotlight.js"></script>
	<script type="text/javascript" src="js/form.js"></script>
	<link rel="stylesheet" type="text/css" href="list-view.css" />
    <style>
        .x-tree-checked {
            text-decoration: line-through;
            color: #777;
        }
        .x-grid-row-selected .x-grid-cell {
            background-color: #efefef !important;
        }
    </style>
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
	<body>
		<h1 align="center">Test Suite Selector</h1> <div id="msgdiv" style="position:absolute;top:0;right:0;background:#ff0;border:1px solid #ddd;"></div>
		<table>
			<tr>
				<td>
					<div id="tree-div"></div>
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

