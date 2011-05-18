<% session.removeAttribute("task"); %>
<jsp:useBean id="task" scope="session" class="com.den.utils.TaskBean"/>
<% 
String[] methods = request.getParameterValues("sltM");
task.createXmlSuite(methods);
task.setRunning(true);
	new Thread(task).start(); 
	out.println(request.getParameterMap());
%> 


<jsp:forward page="suite.jsp"/>