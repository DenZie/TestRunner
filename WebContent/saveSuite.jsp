<jsp:useBean id="task" scope="application" class="com.clearspring.utils.TaskBean"/>
<%@page import="java.util.HashMap" %>
<%
String[] methods = request.getParameterValues("Mlist"); 
String suiteName = request.getParameterValues("suiteName")[0]; 
if(methods != null) {
	task.createXmlSuite(methods, suiteName);
	out.print("{success: true, message: \"suite Saved\"}");
} else {
	out.print("{success: false, message: \"unable to save suite\"}");
}

%> 

