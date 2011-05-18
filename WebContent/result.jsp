 <jsp:useBean id="task" scope="session" class="com.den.utils.TaskBean"/>
 <%@page contentType="text/html; charset=UTF-8"%>
  <%@page import="org.json.JSONObject"%>
  <%
    JSONObject obj=new JSONObject();
    obj.put("total", task.getTotal());
    obj.put("progress",task.getProgress());
    out.print(obj);
    out.flush();
  %>