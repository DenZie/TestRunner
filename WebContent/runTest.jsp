<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="com.den.utils.*"%>
	<%
		Status ju = new Status();
		out.println(ju.getStatus("status.log"));
	%>
