package com.den.qa.qtf;

import java.io.File;
import java.io.IOException;

public class Qtf {
	public String homeDir;
	public String logFile;
	public String historyFile;
	public String fileSep;
	public String testOutDir;
	public String settingsFile;
	
	public Qtf() {
		fileSep = System.getProperty("file.separator");
		homeDir = getHome();
		logFile = "status.log";
		historyFile = "history.xml";
		testOutDir = "test-output";
		settingsFile = "settings.xml";
	}
	
	public  boolean initialise() {
		boolean qtfReady = false;
		String getQtfHome = getHome();
		qtfReady = (new File(getQtfHome)).mkdirs();
		return qtfReady;
	}
	
	public  String getHome() {
		String usrDir = System. getProperty("user.home");
		String qtfHome = usrDir + fileSep + ".qtf" ;
		return qtfHome;
	}
	
	public  String getTestResultFolder() {
		return (homeDir + fileSep +testOutDir);
	}
	
	public  String getLogFileName() {
		String fileSep=System.getProperty("file.separator");
		return (getHome() + fileSep + logFile);
	}
	
	public  String getHistoryFileName() {
		String fileSep=System.getProperty("file.separator");
		return (getHome() + fileSep + historyFile);
	}
	
	private boolean createHistoryFile() {
		boolean status=true;
		File history = new File(historyFile);
		if(!history.exists()) {
			try {
				history.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				status = false;
			}
		}
		return status;
	}
}
