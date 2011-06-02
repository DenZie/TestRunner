package com.clearspring.qa.qtf;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.clearspring.qa.qtf.utils.Io;

public class ResultLogger {
	Document docResult;
	String resultFileName ; 
	
	public ResultLogger() {
		Qtf qtf = new Qtf();
		resultFileName = qtf.getTestResultFolder() + qtf.fileSep + "testng-results.xml";

	}
	
	public Node getResultNode() {
		docResult = Io.readXml(resultFileName);
		Node nodeTest = docResult.getElementsByTagName("test").item(0);
		return nodeTest;
	}
}
