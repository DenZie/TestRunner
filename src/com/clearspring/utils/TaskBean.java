package com.clearspring.utils;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import org.json.JSONException;
import org.json.JSONObject;

import com.clearspring.qa.qtf.Archiver;
import com.clearspring.qa.qtf.HistoryLogger;
import com.clearspring.qa.qtf.Qtf;
import com.clearspring.qa.qtf.StatusLogger;
import com.clearspring.qa.qtf.utils.Io;

public class TaskBean implements Runnable, Serializable {
    private boolean started;
    private boolean running;
    private boolean suiteGenerated;
    private Thread suitethread ;

    private Map<String, ArrayList<String>> testMethodMap;
	private XmlSuite testSuite;
    Archiver arc;
    String suiteName;
    
    public TaskBean() {

//		System.out.println("LogileName created");
		testMethodMap = new HashMap<String, ArrayList<String>>();
        started = false;
        running = false;
        suiteGenerated = false;

    }
     public synchronized void setSuiteGenerated() {
    	 suiteGenerated = true;
     }
     
     public synchronized boolean isSuiteGenerated() {
    	 return suiteGenerated;
     }

    public synchronized long getPercent() throws InterruptedException {
    	long total = getTotal();
    	long percent=0;
    	if(total>0) {
    		long progress = getProgress();
	    	percent =   (progress*100)/total;
    }
    	return percent;
    }

    public synchronized boolean isStarted() {
        return started;
    }

    public synchronized boolean isRunning() {
        return running;
    }

    public synchronized void setRunning(boolean running){

        this.running = running;
        if (running) {
            started  = true;
        } 
        else {
        	if(suitethread.isAlive()) {
        		suitethread.stop();
        	}
        	StatusLogger sl = new StatusLogger();
            sl.logBuildEnd();
        }
        
    }

    public void run() {
    	try {
        	Thread.sleep(3000);
            runSuite();
            Thread.sleep(2000);
         } catch (Exception e) {
			// TODO: handle exception
        	e.printStackTrace();
		}
        finally {
				setRunning(false);
        }
    }
    
    public void build() {
    	suitethread = new Thread(this);
    	suitethread.start();
    }

	public void runSuite() {
		StatusLogger sl = new StatusLogger();
		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		suites.add(testSuite);
		TestNG tng = new TestNG();
		showSuiteXml();
		tng.setXmlSuites(suites);
		sl.logSuiteName(testSuite.getName());
		Interceptor interceptor = new Interceptor();
		tng.addListener(interceptor);
		TestCounter tCounter = new TestCounter();
		tng.addListener(tCounter);
		HistoryLogger hLogger = new HistoryLogger();
		hLogger.logBuildStart();
		Qtf qtf = new Qtf();
		tng.setOutputDirectory(qtf.getTestResultFolder());
		tng.run();
		sl.logBuildEnd();
		hLogger.logTestResult();
		arc.ArchieveStatusFile();
		arc.ArchieveTestOutput();
		hLogger.logBuildEnd();
	}

    public void createXmlSuite(String [] mts, HashMap<String,String> parameters) {
    	createTestMethodMap(mts);
		cleanLog();
		createVirtualSuite(parameters);
    }
    
    public void showSuiteXml() {
    	System.out.println(testSuite.toXml().toString());
    }

	public void createTestMethodMap(String[] methods) {
		for(String m: methods) {
			m = m.replace("_", ".");
			String[] arMC = m.split("::");
			if(testMethodMap== null) {
				ArrayList<String> methodList = new ArrayList<String>();
				methodList.add(arMC[1]);
				testMethodMap.put(arMC[0], methodList);
			} else {
				if(testMethodMap.containsKey(arMC[0])) {
					testMethodMap.get(arMC[0]).add(arMC[1]);
				} else{
					ArrayList<String> methodList = new ArrayList<String>();
					methodList.add(arMC[1]);
					testMethodMap.put(arMC[0], methodList);
				}
			}
		}
	}

	public void cleanLog() {
		Qtf qtf = new Qtf();
		String logFileName = qtf.getLogFileName();
		File f = new File(logFileName);
		if (f.exists()) {
			if(!(f.delete())) {
				System.out.println("Can't delete log file");
			}
		}
	}

	public void createVirtualSuite(HashMap<String,String> parameters) {
		arc = new Archiver();
		suiteName = arc.getTimestampFolder();
		testSuite = new XmlSuite();
		testSuite.setParameters(parameters);
		testSuite.setName("QTF_" + suiteName);
		XmlTest test = new XmlTest(testSuite);
		test.setName("QTF_Test_" + suiteName);
		Set<String> stClass = testMethodMap.keySet();
		Iterator<String> it = stClass.iterator();
		List<XmlClass> classes = new ArrayList<XmlClass>();
		while(it.hasNext()) {
			String testClassName = it.next();
			XmlClass xmlcls = new XmlClass(testClassName);
			ArrayList<String> methodslist = testMethodMap.get(testClassName);
			ArrayList<XmlInclude> methodsToRun = new ArrayList<XmlInclude>();
			for(String m: methodslist) {
				 methodsToRun.add(new XmlInclude(m));
			}
			xmlcls.setIncludedMethods(methodsToRun);
			classes.add(xmlcls);
			test.setXmlClasses(classes) ;
		}
	}

	public String getLog() throws InterruptedException {
		Qtf qtf = new Qtf();
		String logFileName = qtf.getLogFileName();
		String log = "{}";
		for(int i=0;i< 5 ;i++) {
			Thread.sleep(500);
			File f = new File(logFileName);
			if (f.exists()) {
				i=5;
				log = Io.readFromFile(logFileName);
			}
		}
		return log;
	}

	public int getTotal() throws InterruptedException {
		int total = 0;
		String jsonString = getLog();
		try {
			JSONObject status = new JSONObject(jsonString);
			total = Integer.valueOf(status.get("total").toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return total;
	}
	
	public String getTestName() throws InterruptedException {
		if(suiteName== null) 
			return "" ;
		else 
			return suiteName;
	}

	public int getProgress() throws InterruptedException {

		int progress = 0;
		String jsonString = getLog();
		try {
			JSONObject status = new JSONObject(jsonString);
			progress = Integer.valueOf(status.get("progress").toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
//			System.out.println("ERROR :- [" + jsonString +"]");
		}
		return progress;
	}

}