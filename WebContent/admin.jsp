<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="com.den.qa.qtf.Settings,java.util.Set,java.util.Map"%>
    
    <%
    Settings settings = new Settings();
    Map<String,String> settingsMap = settings.read();
    String tj = "value='" + settingsMap.get("tj") + "'";
    String bc = "value='" + settingsMap.get("bc") + "'";
    %>
  
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>QTF : Admin Iterface</title>



</head>
<body >
<form id="qtfSuiteMaker" action="admin.jsp.jsp" method="post" >
<label for="testJar">Test Jar</label> <input type="text" name="testJar" <%=tj%>/></br>
<label for="baseClass">Base Class</label> <input type="text" name="baseClass" <%=bc%>/></br>
<input type="submit" value="Save"></input>
</form>

</body>
</html>