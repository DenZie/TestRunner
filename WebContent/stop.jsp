<jsp:useBean id="task" scope="session" class="com.den.utils.TaskBean"/>
<% task.setRunning(false); %> <jsp:forward page="suite.jsp"/>