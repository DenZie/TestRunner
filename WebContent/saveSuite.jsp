<%@page import="java.util.HashMap, com.clearspring.qa.qtf.SuiteLogger,org.json.JSONObject" %>
<%
	SuiteLogger sLogger = new SuiteLogger();
	String[] methods = request.getParameterValues("Mlist"); 
	if(methods != null) {
		String suiteName = request.getParameterValues("suiteName")[0]; 
		if(suiteName != null) {
				sLogger.saveSuite(methods, suiteName);
				out.print("{success: true, message: \"suite Saved\"}");
		} 
	} else {
	    JSONObject obj= sLogger.getSavedSuiteMap();
	    out.print(obj);
	    out.flush();
	}

%> 

