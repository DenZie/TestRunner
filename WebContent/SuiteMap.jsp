 <jsp:useBean id="task" scope="session" class="com.den.utils.TaskBean"/>
 <%@page contentType="text/html; charset=UTF-8"%>
  <%@page import="org.json.JSONObject, com.den.qa.qtf.TestFinder"%>
  <%
  	TestFinder tFinder = new TestFinder();
  	String obj= tFinder.generateJson();
	out.print(obj);
	out.flush();
  %>