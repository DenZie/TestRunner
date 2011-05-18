<% session.removeAttribute("task"); %>
<jsp:useBean id="task" scope="session" class="com.den.utils.TaskBean"/>
<% 
String[] methods = request.getParameterValues("Mlist");


if(methods != null) {
	task.createXmlSuite(methods);
	task.setRunning(true);
	task.build(); 
}
%> 
<jsp:forward page="suite.jsp"/>

