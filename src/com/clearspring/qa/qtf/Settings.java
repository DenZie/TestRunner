package com.clearspring.qa.qtf;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;

import com.clearspring.qa.qtf.utils.Io;

public class Settings {
	Qtf qtf;
	Map settingsMap;
	public Settings() {
		qtf = new Qtf();
		settingsMap = new HashMap<String, String>();
		String fileName = qtf.getHome() + qtf.fileSep + qtf.settingsFile;
		File file = new File(fileName);

		if(!file.exists()) {
			String xmlString = "<settings><baseClass>com.den.qa.DemoBase</baseClass><testJar>qtfTest.jar</testJar></settings>";
			try{
			    // Create file
				FileWriter fstream = new FileWriter(qtf.settingsFile);
				    BufferedWriter out = new BufferedWriter(fstream);
				out.write(xmlString);
				//Close the output stream
				out.close();
			} catch (Exception e){//Catch exception if any
				System.err.println("Error: " + e.getMessage());
			}
		}
		read();
	}

	public  void read() {
		String fileName = qtf.getHome() + qtf.fileSep + qtf.settingsFile;
		Document settingsDoc = Io.readXml(fileName);
		String baseClass = settingsDoc.getElementsByTagName("baseClass").item(0).getTextContent();
		String testJar = settingsDoc.getElementsByTagName("testJar").item(0).getTextContent();
		settingsMap.put("bc", baseClass);
		settingsMap.put("tj", testJar);
	}

	public  Map<String,String> getAll() {
		return settingsMap;
	}

	public  String getTestJarLocation() {
		return settingsMap.get("tj").toString();
	}

	public  String getBaseClass() {
		return settingsMap.get("bc").toString();
	}


	public  void write() {

	}


}
