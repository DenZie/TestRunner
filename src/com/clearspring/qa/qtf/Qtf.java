package com.clearspring.qa.qtf;

import java.io.File;
import java.io.IOException;

public class Qtf {
	public String homeDir;
	public String logFile;
	public String historyFile;
	public String fileSep;
	public String testOutDir;
	public String settingsFile;
	public String suitefile;
	public String savedSuites;
	
	public Qtf() {
		fileSep = System.getProperty("file.separator");
		homeDir = getHome();
		logFile = "status.log";
		historyFile = "history.xml";
		testOutDir = "test-output";
		settingsFile = "settings.xml";
		suitefile = "suiteMap.json";
		savedSuites = "savedSuites.xml";
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
	
	public  String getSettingsFile() {
		return (homeDir + fileSep + settingsFile) ;
	}
	
	public  String getSavedSuiteFile() {
 		String sf =  homeDir + fileSep + savedSuites;
		return sf;
	}
	
	public  String getTestResultFolder() {
		return (homeDir + fileSep +testOutDir);
	}
	
	public  String getLogFileName() {
		return (getHome() + fileSep + logFile);
	}
	
	public  String getHistoryFileName() {
		return (getHome() + fileSep + historyFile);
	}
	
	public  String getSuiteFileName() {
		return (getHome() + fileSep + suitefile);
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
