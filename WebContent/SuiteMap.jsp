 <jsp:useBean id="task" scope="application" class="com.clearspring.utils.TaskBean"/>
<%@page contentType="text/html; charset=UTF-8"%>
  <%@page import="org.json.JSONObject, com.clearspring.qa.qtf.utils.MyClassFinder"%>
  <%
  MyClassFinder mcf = new MyClassFinder();
	if(false == task.isSuiteGenerated()) { 
		mcf.generateJson();
		task.setSuiteGenerated();
	}
  	String obj= mcf.getSuiteMap();
	out.print(obj);
	out.flush();
  %>