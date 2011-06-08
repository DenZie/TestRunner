<%@page import="java.util.HashMap, com.clearspring.qa.qtf.SuiteLogger,org.json.JSONObject" %>
<%
SuiteLogger sLogger = new SuiteLogger();
String[] methods = request.getParameterValues("Mlist"); 
if(methods != null) {
	String suiteName = request.getParameterValues("suiteName")[0]; 
	String save = request.getParameterValues("save")[0]; 
	if(save == null) {
	    JSONObject obj= sLogger.getSavedSuiteMap();
	    out.print(obj);
	    out.flush();
	} else {
		if(methods != null) {
			sLogger.saveSuite(methods, suiteName);
			out.print("{success: true, message: \"suite Saved\"}");
		} 
	}
} else {
    JSONObject obj= sLogger.getSavedSuiteMap();
    out.print(obj);
    out.flush();
}

%> 

