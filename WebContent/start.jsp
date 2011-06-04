<jsp:useBean id="task" scope="application" class="com.clearspring.utils.TaskBean"/>
<%@page import="java.util.HashMap" %>
<%
String[] methods = request.getParameterValues("Mlist"); 
if(methods != null) {
	HashMap<String,String> parameters = new HashMap<String, String>();
	parameters.put("env", request.getParameterValues("env")[0]);
	parameters.put("driver", request.getParameterValues("driver")[0]);
	parameters.put("proxyp", request.getParameterValues("proxy")[0]);
	parameters.put("cacheEnabled", "false");
	parameters.put("closep", "true");
	task.createXmlSuite(methods, parameters);
	task.setRunning(true);
	task.build(); 
	out.print("{success: true, methods: \"" + methods.length	 + "\"}");
} else {
	out.print("{success: true, messege : No Methods to run}");
}

%> 

