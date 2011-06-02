package com.clearspring.utils;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;

import org.json.JSONException;
import org.json.JSONObject;

import com.clearspring.qa.qtf.Qtf;
import com.clearspring.qa.qtf.utils.Io;

public class Interceptor implements IMethodInterceptor  {

	@Override
	public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
		Qtf qtf = new Qtf();
		String logFileName = qtf.getLogFileName();
		int count = methods.size();
		String jsonString = Io.readFromFile(logFileName);
		try {
			JSONObject summary = new JSONObject(jsonString);
			summary.put("total", count);
			summary.put("progress" , 0);
			Io.writeToFile(logFileName, summary.toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return methods;
		}
}
