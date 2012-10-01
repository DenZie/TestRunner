package com.clearspring.qa.qtf;

import java.util.ArrayList;

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


public class SettingsManager {

	Qtf qtf;
	String settingsFile;
	DocumentBuilder dbSettings;
	Document doc; 
	Element settingsNode;

	public SettingsManager() {
		qtf = new Qtf();
		settingsFile = qtf.getSettingsFile();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			dbSettings = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		settingsNode = getSetingsNode();
	}
	
	public Element getSetingsNode() {
		doc = Io.readXml(settingsFile);
		Element settingsNode =null;
		if(doc == null ) {
			doc = dbSettings.newDocument();
		} else {
			settingsNode = (Element) doc.getElementsByTagName("settings").item(0);
		} 
		if(settingsNode == null) {
			settingsNode = (Element) doc.createElement("settings");
		}
		return settingsNode;
	}
	
	public Element getTestJarNode() {
		Element testJarsNode = (Element) settingsNode.getElementsByTagName("testJars").item(0);
		return testJarsNode;
	}
	
	public ArrayList<String> getTestJars() {
		ArrayList<String> testjars = new ArrayList<String>();
		Element testJarsNode = getTestJarNode();
		if(testJarsNode != null) {
		NodeList testJarsNodes = testJarsNode.getElementsByTagName("testJar");
			for(int i = 0 ; i < testJarsNodes.getLength();i++) {
				testjars.add(testJarsNodes.item(i).getTextContent().toString());
			}
		} else {
			testJarsNode = doc.createElement("testJars");
			settingsNode.appendChild(testJarsNode);
		}
		return testjars;
	}
	
	
	public JSONObject getTestJarsforExtJs() {
			NodeList bNodes = doc.getElementsByTagName("testJar");
			JSONObject jarsJson = new JSONObject();
			JSONArray jarList = new JSONArray();
			if(bNodes != null && bNodes.getLength() > 0) {
				for(int i = 0 ; i < bNodes.getLength();i++) {
					Node tNode = bNodes.item(i);
					String name = tNode.getAttributes().getNamedItem("name").getTextContent();	
					String clscount = tNode.getAttributes().getNamedItem("cls").getTextContent();
					String mtsCount = tNode.getAttributes().getNamedItem("mts").getTextContent();
					JSONObject jar = new JSONObject();
					try {
						jar.put("name", name);
						jar.put("clscount", clscount);
						jar.put("mtscount", mtsCount);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					jarList.put(jar);
				}
			}
			try {
				jarsJson.put("testjars", jarList);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return jarsJson;
		}
	
	
	
	public Node getTestJarNode(String jarName) {
		Element testJarsNode = getTestJarNode();
		NodeList testJarNodes = testJarsNode.getElementsByTagName("testJar");
		for(int i = 0 ; i < testJarNodes.getLength();i++) {
			if (testJarNodes.item(i).getAttributes().getNamedItem(jarName) != null) {
				 return testJarNodes.item(i);
			}
		}
		return null;
	}
	
	public void addTestJar(String jarName) {
		Element testJarsNode = getTestJarNode();
		Element testjar = doc.createElement("testJar");
		testjar.setAttribute("name", jarName);
		testJarsNode.appendChild(testjar);
	}
	
	public void removeTestJar(String jarName) {
		Element testJarsNode = getTestJarNode();
		Node testjar = getTestJarNode(jarName);
		testJarsNode.removeChild(testjar);
	}

    public void saveTestJar(String jarName) {
    	addTestJar(jarName);
    	Io.writeXmlToFile(settingsFile, doc);
    }

}
