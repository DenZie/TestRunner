<%@page import="com.den.qa.qtf.ResultRender" %>
<html>
<head>
</head>
<body>
<a href="suite.jsp" style="color: green; background: #000000">Go Back To Test Suite</a>
<%ResultRender rRender = new ResultRender(""); 

String backFolder = "1304845835567";
backFolder = request.getParameter("test");
String source="";
if(backFolder != null) {
	source = "src='" + rRender.getHtml(backFolder) + "'";
}
%>
<iframe width="100%" height="90%" <%=source%> ><P>Nothing to Display. I cant find ur test report.</P></iframe>
</body>
</html>


