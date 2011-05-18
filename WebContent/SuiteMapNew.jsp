 <jsp:useBean id="task" scope="session" class="com.den.utils.TaskBean"/>
 <%@page contentType="text/html; charset=UTF-8"%>
  <%@page import="org.json.JSONObject, com.den.qa.qtf.utils.MyClassFinder"%>
  <%
  MyClassFinder mcf = new MyClassFinder();
  String obj= mcf.generateJson();
	out.print(obj);
	out.flush();
  %>