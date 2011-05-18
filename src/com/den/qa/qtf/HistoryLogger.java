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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.clearspring.json.JSONArray;
import com.den.qa.qtf.utils.Io;

import org.json.JSONException;
import org.json.JSONObject;

public class HistoryLogger {
	Document docHistory;
	DocumentBuilder dbHistory;
	String historyFileName ; 
	Element buildNode;
	Element histNode;
	
	public HistoryLogger() {
		Qtf qtf = new Qtf();
		historyFileName = qtf.getHistoryFileName(); ;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			dbHistory = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void create() {
		createXml();
		writeToFile();
	}
	
	public void createXml() {
		docHistory = dbHistory.newDocument();
		String createdDate = new Date().toString();
		histNode = docHistory.createElement("history");
		histNode.setAttribute("created", createdDate);
		docHistory.appendChild(histNode);
	}
	
	public void logBuildStart() {
		if(!readXml()) {
			createXml();
		}
		createBuildNode();
	}
	
	public void logTestResult() {
		ResultLogger rLogger = new ResultLogger();
		Node resultNode = rLogger.getResultNode();
		Node dupResultNode = docHistory.importNode(resultNode, true);
		buildNode.appendChild(dupResultNode);
	}
	
	public void logBuildEnd() {
		histNode.appendChild(buildNode);
		writeToFile();
	}
	    
	public void createBuildNode() {
		String buildId;
		try {
			Node lastNode = histNode.getLastChild().getPreviousSibling();
			buildId = ((Element) lastNode).getAttribute("id");
			int tmpid = Integer.valueOf(buildId) +1 ;
			buildId =String.valueOf(tmpid);
		} catch (Exception e) {
			buildId = "1";
		}
		buildNode = docHistory.createElement("build");
		buildNode.setAttribute("id", buildId);
	}
	
	public boolean readXml() {
		boolean fileReady = true;
		try {
			File fileHistory = new File(historyFileName);
			docHistory = dbHistory.parse(fileHistory);
			histNode = docHistory.getDocumentElement();
		} catch (Exception e) {
			// TODO: handle exception
			fileReady = false;
		}
		return fileReady;
	}
	
	public void writeToFile() {
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			//initialize StreamResult with File object to save to file
			StreamResult result = new StreamResult(new FileOutputStream(historyFileName));
			DOMSource source = new DOMSource(docHistory);
			transformer.transform(source, result);
			String xmlString = result.getWriter().toString();
			System.out.println(xmlString);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public int getBuildCount() {
		int buildCount =0;
		if(readXml()) {
			buildCount = docHistory.getElementsByTagName("build").getLength(); 
		}
		return buildCount;
	}
	
	public boolean historyLogFileExists() {
		File file = new File(historyFileName);
		return file.exists();
	}
	
	public JSONObject getHistory() {
		Document doc = Io.readXml(historyFileName);
		NodeList bNodes = doc.getElementsByTagName("build");
		JSONObject obj= new JSONObject();
		if(bNodes != null && bNodes.getLength() > 0) {
			for(int i = 0 ; i < bNodes.getLength();i++) {
				Element bNode = (Element)bNodes.item(i);
				String id = bNode.getAttributes().item(0).getTextContent();
				Node tNode = bNode.getElementsByTagName("test").item(0);
				String duration = tNode.getAttributes().getNamedItem("duration-ms").getTextContent();	
				String name = tNode.getAttributes().getNamedItem("name").getTextContent();
				JSONObject objresult = new JSONObject();
				try {
					objresult.put("duration", duration);
					objresult.put("name", name);
					objresult.put("result", isBuildFailure(bNode));
					obj.put(id, objresult);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return obj;
	}
	
	public boolean isBuildFailure(Element bNode) {

		Node tNode = bNode.getElementsByTagName("test").item(0);
		 NodeList cNodes = ((Element)tNode).getElementsByTagName("class");
		 if(cNodes != null && cNodes.getLength() > 0) {
				for(int j = 0 ; j < cNodes.getLength();j++) {
					NodeList tmNodes = ((Element)cNodes.item(j)).getElementsByTagName("test-method");
					 if(tmNodes != null && tmNodes.getLength() > 0) {
							for(int k= 0 ; k < tmNodes.getLength(); k++) {
								Element tmNode = ((Element)tmNodes.item(k));
								String tmResult = tmNode.getAttributes().getNamedItem("status").getTextContent();
								if(tmResult.contains("FAIL")) {
									return false;
								}
							}
					 }
				}
		 }
		return true;
	}
}
