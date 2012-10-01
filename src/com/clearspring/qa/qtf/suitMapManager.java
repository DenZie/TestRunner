package com.clearspring.qa.qtf;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.clearspring.qa.qtf.utils.Io;

import static com.clearspring.qa.qtf.utils.Io.*;

public class suitMapManager {

	Qtf qtf;
	String savedSuiteFile;
	DocumentBuilder dbSavedSuites;
	Document doc;
	Element suitedNode;

	public suitMapManager() {
		qtf = new Qtf();
		savedSuiteFile = qtf.getSavedSuiteFile();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			 dbSavedSuites = dbf.newDocumentBuilder();
			 if(!readXml()) {
				 createXml();
			 }

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean readXml() {
		boolean fileReady = true;
		try {
			File file = new File(savedSuiteFile);
			doc = dbSavedSuites.parse(file);
			suitedNode = doc.getDocumentElement();
		} catch (Exception e) {
			// TODO: handle exception
			fileReady = false;
		}
		return fileReady;
	}

	public void createXml() {
		doc = dbSavedSuites.newDocument();
		String createdDate = new Date().toString();
		suitedNode = doc.createElement("suites");
		suitedNode.setAttribute("created", createdDate);
		doc.appendChild(suitedNode);
	}


    public void saveSuite(String [] mts, String suiteName) {
		Element suite = doc.createElement("suite");
		String createdDate = new Date().toString();
		suite.setAttribute("name", suiteName);
		suite.setAttribute("created", createdDate);
    	for(String m:mts) {
    		Element testCase = doc.createElement("testcase");
    		testCase.setAttribute("name", m);
    		suite.appendChild(testCase);
    	}
    	suitedNode.appendChild(suite);
    	Io.writeXmlToFile(savedSuiteFile, doc);
    }


	public JSONObject getSavedSuiteMap() {
		Document doc = Io.readXml(savedSuiteFile);
		JSONObject savedSuite = new JSONObject();
		JSONArray suites = new JSONArray();
		if(doc != null) {
			NodeList sNodes = doc.getElementsByTagName("suite");
			if(sNodes != null && sNodes.getLength() > 0) {
				for(int i = 0 ; i < sNodes.getLength();i++) {
					Element sNode = (Element)sNodes.item(i);
					String date = sNode.getAttributes().getNamedItem("created").getTextContent();
					String name = sNode.getAttributes().getNamedItem("name").getTextContent();
					JSONObject suite = new JSONObject();
					JSONArray methods = new JSONArray();
					NodeList mNodes = sNode.getElementsByTagName("testcase");
					if(mNodes != null && mNodes.getLength() > 0) {
						for(int j = 0 ; j < mNodes.getLength();j++) {
							String mName = mNodes.item(j).getAttributes().getNamedItem("name").getTextContent();
							methods.put(mName);
						}
						try {
							suite.put("methods", methods);
							suite.put("name", name);
							suite.put("created", date);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						suites.put(suite);
					}
				}
			}
		} else {
			JSONObject suite = new JSONObject();
			JSONArray methods = new JSONArray();
			try {
				methods.put("placeholder-1");
				suite.put("methods", methods);
				suite.put("name", "No Suites");
				suite.put("created", "");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			suites.put(suite);
		}
		try {
			savedSuite.put("suites", suites);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return savedSuite;
	}
}
