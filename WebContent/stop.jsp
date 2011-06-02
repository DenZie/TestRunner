<jsp:useBean id="task" scope="application" class="com.clearspring.utils.TaskBean"/>
<% task.setRunning(false); 
out.print("{success: true}");%> 