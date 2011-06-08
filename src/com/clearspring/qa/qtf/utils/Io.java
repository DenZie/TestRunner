package com.clearspring.qa.qtf.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.apache.commons.io.IOUtils;


public class Io {
	
	public static boolean moveFile(String source, String dest) {
		File file = new File(source);
	    File destFolder = new File(dest);
	    return file.renameTo(new File(destFolder, file.getName()));
	}
	
	public static boolean copyFile(String source, String dest) {
		boolean fileCopied = true;
		FileInputStream from = null;
	    FileOutputStream to = null;
	    File destFile = new File(dest);
	    if (!destFile.exists()) {
	    	try {
				destFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	    try {
			from = new FileInputStream(source);
			to = new FileOutputStream(dest);
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = from.read(buffer)) != -1)
				to.write(buffer, 0, bytesRead); // write
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
	    	try {
				from.close();
				to.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    } 
	    return fileCopied;
	}
	
	public static Document  readXml(String fileName) {
		Document doc = null ;
		try {
			File xmlFileHandle = new File(fileName);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder dbHistory = dbf.newDocumentBuilder();
				doc = dbHistory.parse(xmlFileHandle);
			} catch (Exception e) {
			}
		return doc;
	}
	
	public static String  getFileContent(String fileName) {
		System.out.println(fileName);
		 BufferedReader reader = null;
		try {
			reader = new BufferedReader( new FileReader (fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		    String line  = null;
		    StringBuilder stringBuilder = new StringBuilder();
		    String ls = System.getProperty("line.separator");
		    try {
				while( ( line = reader.readLine() ) != null ) {
				    stringBuilder.append( line );
				    stringBuilder.append( ls );
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return stringBuilder.toString();
	}
	public static void writeToFile(String fileName, String data) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
			out.write(data);
			out.close();
			} 
			catch (IOException e) 
			{ 
			System.out.println("Exception ");

			}
	}
	public static String readFromFile(String fileName) {
		String data= null;
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(fileName);
			data = IOUtils.toString(inputStream);
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
	        try {
				inputStream.close();
			} catch (IOException e) {

			}
	    }
		return data;

	}
	
	
	public static ArrayList<String> getfileList(String folderName) {
		ArrayList<String> fileList = new ArrayList<String>();
		File folder = new File(folderName);
		String type = "xml"; // replace what ever type of file you need to search
		
		if (folder.isDirectory() && folder != null) {
			for (File f : folder.listFiles()) {
				if (f.isFile() && f.getName().endsWith(type)) {
					fileList.add(f.getName());
				}
			}
		}
		return fileList;
	}
	
	public static void writeXmlToFile(String fileName, Document doc) {
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			//initialize StreamResult with File object to save to file
			StreamResult result = new StreamResult(new FileOutputStream(fileName));
			DOMSource source = new DOMSource(doc);
			transformer.transform(source, result);
			String xmlString = result.getWriter().toString();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
