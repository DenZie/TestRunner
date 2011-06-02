 <jsp:useBean id="task" scope="session" class="com.clearspring.utils.TaskBean"/>
 <%@page contentType="text/html; charset=UTF-8"%>
  <%@page import="org.json.JSONObject"%>
  <%
    Object obj= task.getLog();
    out.print(obj);
    out.flush();
  %>