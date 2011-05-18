 <jsp:useBean id="task" scope="session" class="com.den.utils.TaskBean"/>
 <%@page contentType="text/html; charset=UTF-8"%>
  <%@page import="org.json.JSONObject, com.den.qa.qtf.HistoryLogger"%>
  <%
  HistoryLogger hLogger = new HistoryLogger();
  	hLogger.getHistory();
    JSONObject obj= hLogger.getHistory();
    out.print(obj);
    out.flush();
  %>