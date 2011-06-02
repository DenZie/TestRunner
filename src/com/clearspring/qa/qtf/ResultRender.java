package com.clearspring.qa.qtf;

import java.io.File;
import java.util.Date;

import static com.clearspring.qa.qtf.utils.Io.*;

public class ResultRender {
	
	private String folder;
	private String timestamp;
	private Qtf qtf;
	
	public ResultRender(String ArchiveFolder) {
		qtf = new Qtf();
		Date date =new Date();
        timestamp = String.valueOf(date.getTime());
        folder = ArchiveFolder;
		(new File(folder)).mkdirs();
		
	}
	
	public String getHtml(String fileName) {
		String resultFile = "report.html";
		String source = qtf.homeDir + qtf.fileSep + fileName + qtf.fileSep + qtf.testOutDir + qtf.fileSep + "emailable-report.html";
		java.net.URL url= this.getClass().getResource("ResultRender.class");
		String classFilePath = url.getPath();
		int index = classFilePath.indexOf("WEB-INF");
		String tmp = classFilePath.substring(0, index);
		index = tmp.lastIndexOf("/");
		String dest = tmp.substring(0, index) + qtf.fileSep +  resultFile;
		copyFile(source, dest);
		return resultFile;
	}
	
}
