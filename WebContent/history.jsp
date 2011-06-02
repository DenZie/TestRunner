 <jsp:useBean id="task" scope="session" class="com.clearspring.utils.TaskBean"/>
 <%@page contentType="text/html; charset=UTF-8"%>
  <%@page import="org.json.JSONObject, com.clearspring.qa.qtf.HistoryLogger"%>
  <%
  	HistoryLogger hLogger = new HistoryLogger();
  	hLogger.getHistory();
    JSONObject obj= hLogger.getHistoryForExtJs();
    out.print(obj);
    out.flush();
  %>