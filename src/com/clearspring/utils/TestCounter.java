package com.clearspring.utils;

import org.json.*;

import com.clearspring.qa.qtf.Qtf;
import com.clearspring.qa.qtf.utils.Io;

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
				int total = Integer.valueOf(summary.get("total").toString());
				summary.put("progress", ++m_count);
				if( m_count > total) {
					summary.put("total", m_count);
				}
				Io.writeToFile(logFileName, summary.toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	public String getJson(String fileName) {
		return Io.readFromFile(fileName);
	}

}
