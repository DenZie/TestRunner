package com.clearspring.qa.qtf;

import java.io.FileOutputStream;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.clearspring.qa.qtf.utils.Io;


public class StatusLogger {
	Document doc;
	DocumentBuilder db;
	Qtf qtf;
	
	public StatusLogger() {
		qtf = new Qtf();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void start() {
		String historyFileName = qtf.getHistoryFileName(); 
		createHistoryXml();
		writeToXml(historyFileName);
	}
	
	public boolean createHistoryXml() {
		doc = db.newDocument();
		String createdDate = new Date().toString();
		boolean fileCreated = true;
		Element build = doc.createElement("history");
		build.setAttribute("created", createdDate);
		doc.appendChild(build);
		return fileCreated;
	}
	
	public void writeToXml(String fileName) {
		try{
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		//initialize StreamResult with File object to save to file
		StreamResult result = new StreamResult(new FileOutputStream(fileName));
		DOMSource source = new DOMSource(doc);
		transformer.transform(source, result);
		String xmlString = result.getWriter().toString();
		System.out.println(xmlString);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void logBuildEnd() {
		String logFileName = qtf.getLogFileName();
		String jsonData = Io.readFromFile(logFileName);
		JSONObject summary;
		try {
			summary = new JSONObject(jsonData);
			summary.put("finished", "1");
			Io.writeToFile(logFileName, summary.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void logSuiteName(String suiteName) {
		String logFileName = qtf.getLogFileName();
		JSONObject summary;
		try {
			summary = new JSONObject();
			summary.put("suiteName", suiteName);
			Io.writeToFile(logFileName, summary.toString());
		} catch (JSONException e) {
				e.printStackTrace();
		}
	}

}
