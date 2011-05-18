package com.den.utils;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;

import com.clearspring.json.JSONException;
import com.clearspring.json.JSONObject;
import com.den.qa.qtf.Qtf;

public class Interceptor implements IMethodInterceptor  {

	@Override
	public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
		int count = methods.size();
		JSONObject summary = new JSONObject();
		try {
			summary.put("total", count);
			FileWriter fstream = null;
			Qtf qtf = new Qtf();
			String logFileName = qtf.getLogFileName();
			fstream = new FileWriter(logFileName);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(summary.toString());
			//Close the output stream
			out.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return methods;
		}
}
