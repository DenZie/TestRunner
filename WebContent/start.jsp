<jsp:useBean id="task" scope="application" class="com.clearspring.utils.TaskBean"/>
<%@page import="java.util.HashMap" %>
<% 
String[] methods = request.getParameterValues("Mlist");

HashMap<String,String> parameters = new HashMap<String, String>();
parameters.put("env", request.getParameterValues("env")[0]);
parameters.put("driver", request.getParameterValues("driver")[0]);
parameters.put("proxyp", request.getParameterValues("proxy")[0]);
parameters.put("cacheEnabled", "false");
parameters.put("closep", "true");

if(methods != null) {
	task.createXmlSuite(methods, parameters);
	task.setRunning(true);
	task.build(); 
}
out.print("{success: true}");
%> 

