package com.den.utils;


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

import com.clearspring.json.JSONException;
import com.clearspring.json.JSONObject;
import com.den.qa.qtf.Archiver;
import com.den.qa.qtf.HistoryLogger;
import com.den.qa.qtf.Qtf;

public class TaskBean implements Runnable, Serializable {
    private boolean started;
    private boolean running;
    private Thread suitethread ;

    private Map<String, ArrayList<String>> testMethodMap;
	private XmlSuite testSuite;
    Archiver arc;
    
    public TaskBean() {

//		System.out.println("LogileName created");
		testMethodMap = new HashMap<String, ArrayList<String>>();
        started = false;
        running = false;

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
        } else {
        	if(suitethread.isAlive()) {
        		System.out.println("I dont want you running. And I am gonna Kill you");
        		suitethread.stop();
        		System.out.println("Should be died by now....");
        	}
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
		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		suites.add(testSuite);
		TestNG tng = new TestNG();
		tng.setXmlSuites(suites);
		Interceptor interceptor = new Interceptor();
		tng.addListener(interceptor);
		TestCounter tCounter = new TestCounter();
		tng.addListener(tCounter);
		HistoryLogger hLogger = new HistoryLogger();
		hLogger.logBuildStart();
		Qtf qtf = new Qtf();
		tng.setOutputDirectory(qtf.getTestResultFolder());
		tng.run();
		hLogger.logTestResult();

		arc.ArchieveStatusFile();
		arc.ArchieveTestOutput();
		hLogger.logBuildEnd();
	}

    public void createXmlSuite(String [] mts) {
    	createTestMethodMap(mts);
		cleanLog();
		createVirtualSuite();
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

	public void createVirtualSuite() {
		arc = new Archiver();
		String timestamp = arc.getTimestampFolder();
		testSuite = new XmlSuite();
		testSuite.setName("QTF_" + timestamp);
		XmlTest test = new XmlTest(testSuite);
		test.setName("QTF_Test_" + timestamp);
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
		StringBuilder sb = new StringBuilder();
		for(int i=0;i< 5 ;i++) {
		Thread.sleep(500);
		File f = new File(logFileName);
		if (f.exists()) {
			i=5;
			BufferedReader in = null;
			try {
				in = new BufferedReader(new FileReader(logFileName));
				String line;
				while((line =in.readLine()) != null){
				    sb.append(line);
				}
				in.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return "junk";
			}
		}
		}
		return sb.toString();
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

	public int getProgress() throws InterruptedException {

		int progress = 0;
		String jsonString = getLog();
		try {
			JSONObject status = new JSONObject(jsonString);
			progress = Integer.valueOf(status.get("finished").toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
//			System.out.println("ERROR :- [" + jsonString +"]");
		}
		return progress;
	}

}