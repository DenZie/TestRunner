package com.den.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import com.clearspring.json.*;
import com.den.qa.qtf.Qtf;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class TestCounter extends TestListenerAdapter {
	private int m_count = 0;

	@Override
	public void onTestFailure(ITestResult tr) {
		log(tr.getMethod().getMethodName());
	}

	@Override
	public void onTestSkipped(ITestResult tr) {
		log(tr.getMethod().getMethodName());
	}

	@Override
	public void onTestSuccess(ITestResult tr) {
		log(tr.getMethod().getMethodName());
	}

	private void log(String string) {
		Qtf qtf = new Qtf();
		String logFileName = qtf.getLogFileName();
		String jsonData = getJson(logFileName);
			JSONObject summary;
			try {
				summary = new JSONObject(jsonData);
				summary.put("finished", ++m_count);
				Date startTime = new Date();
				summary.put(String.valueOf( m_count) , startTime);
				FileWriter fstream = null;
				fstream = new FileWriter(logFileName);
				BufferedWriter out = new BufferedWriter(fstream);
				out.write(summary.toString());
				System.out.println("finished...." + string);
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	public String getJson(String fileName) {
		BufferedReader in = null;
		StringBuilder sb = new StringBuilder();
		try {
			in = new BufferedReader(new FileReader(fileName));
			String line;
			while((line =in.readLine()) != null){
			    sb.append(line);
			}
			in.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return sb.toString();
	}

}
