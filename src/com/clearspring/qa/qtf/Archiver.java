package com.clearspring.qa.qtf;

import java.io.File;
import java.util.Date;

import static com.clearspring.qa.qtf.utils.Io.*;

import java.io.Serializable;

public class Archiver implements Serializable {
	
	private String folder;
	private String timestamp;
	private Qtf qtf;
	
	public Archiver() {
		qtf = new Qtf();
		Date date =new Date();
        timestamp = String.valueOf(date.getTime());
        folder = qtf.getHome() + qtf.fileSep + timestamp;
		(new File(folder)).mkdirs();
		
	}
	
	public boolean ArchieveStatusFile() {
		boolean archieved = true;
		String statusFileName = qtf.getLogFileName();
		copyFile(statusFileName, folder + qtf.fileSep + qtf.logFile);
		return archieved;
	}
	
	public boolean ArchieveTestOutput() {
		boolean archieved = true;
		String source = qtf.getTestResultFolder();
//		String dest =  folder + qtf.fileSep + qtf.testOutDir;
//		System.out.println("Created :- " + dest);
//		(new File(dest)).mkdirs();
		moveFile(source, folder);
		return archieved;
	}
	
	public String getFolder() {
		return folder;
	}
	
	public String getTimestampFolder() {
		return timestamp;
	}

}
