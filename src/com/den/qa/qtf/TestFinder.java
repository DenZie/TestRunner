package com.den.qa.qtf;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.Test;

import com.clearspring.json.JSONArray;
import com.den.utils.ClassFinder;

public class TestFinder {
	Map<String, Object[]> testclass =  new HashMap<String, Object[]>();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestFinder lt=  new TestFinder();
		lt.getTestClass();	
	}
	
	public Map<String, Object[]>  FetchTestClass() {
		Settings settings = new Settings();
		Map<String,String> settingsMap = settings.read();
		String className = settingsMap.get("bc");
		ClassFinder cf = new ClassFinder ();
		Vector<Class<?>> v = cf.findSubclasses (className);
		if (v != null && v.size () > 0)
		{
			for (Class<?> cls : v)
			{
				Method[] methods = cls.getDeclaredMethods();
				Set testMethods = new HashSet();
				for(Method mt: methods) {
					if(mt.isAnnotationPresent(Test.class)) {
						testMethods.add(mt.getName());
					}
				}
				testclass.put(cls.getName(), testMethods.toArray());
			}
		}
		return testclass;
	}
	
	public ArrayList<String>  getTestClass() {
		Iterator<String> itr = testclass.keySet().iterator();
		ArrayList<String> testClass = new ArrayList<String>();
		while(itr.hasNext()){
			String k =  itr.next();
			testClass.add(k);
		}
		return testClass;
	}

	public String  getTestMthods(String className) {
		Object[] mts = testclass.get(className);
		ArrayList<String> testMethods = null;
		String datastring=null;
		for(int i=0;i<mts.length;i++) {
			testMethods.add(mts[i].toString());
		}
		return datastring;
	}
	
	public String generateJson() {
		String jspString = null;
		JSONObject suiteMap = new JSONObject();
		try {
			suiteMap.put("identifier", "name");
			suiteMap.put("label", "name");
			JSONArray items = new JSONArray();
			JSONObject item = new JSONObject();
			Settings settings = new Settings();
			Map<String,String> settingsMap = settings.read();
			String className = settingsMap.get("bc");
			ClassFinder cf = new ClassFinder();
			Vector<Class<?>> v = cf.findSubclasses(className);
			if (v != null && v.size() > 0) {
				for (Class<?> cls : v) {
					JSONObject classes = new JSONObject();
					classes.put("name", cls.getName());
					classes.put("type", "parent");
					classes.put("checkbox", false);
					JSONArray children = new JSONArray();
					Method[] methods = cls.getDeclaredMethods();
					Set testMethods = new HashSet();
					for (Method mt : methods) {
						if (mt.isAnnotationPresent(Test.class)) {
							JSONObject reference = new JSONObject();
							reference.put("_reference", mt.getName());
							children.put(reference);
							JSONObject method = new JSONObject();
							method.put("name", mt.getName());
							method.put("type", "child");
							method.put("checkbox", false);
							items.put(method);
						}
					}
					classes.put("children", children);
					items.put(classes);
				}
				suiteMap.put("items", items);
			}
		jspString = filterEscapes(suiteMap.toString(0));
		} catch (JSONException e) {
			// TODO: handle exception
		}
		return jspString;
	}
	
	public String filterEscapes(String data) {
		data = data.replaceAll("\\\\+", "");
		data = data.replaceAll("\"\\[", "[");
		data = data.replaceAll("\"\\{", "{");
		data = data.replaceAll("\\]\"", "]");
		data = data.replaceAll("\\}\"", "}");

		return data;
	}
	
	
	
}
