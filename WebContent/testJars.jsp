  <%@page import="org.json.JSONObject, com.clearspring.qa.qtf.SettingsManager,java.util.ArrayList"%>
  
  <% SettingsManager sMan = new SettingsManager(); 
  JSONObject obj  = sMan.getTestJarsforExtJs();
  out.print(obj);
  out.flush();
%>
  
 