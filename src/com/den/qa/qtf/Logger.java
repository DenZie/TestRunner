package com.den.qa.qtf;

import java.io.File;
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

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Logger {
	Document doc;
	DocumentBuilder db;
	
	public Logger() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void start() {
		Qtf qtf = new Qtf();
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

}
