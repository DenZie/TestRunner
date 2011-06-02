package com.clearspring.qa.qtf;

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
import com.clearspring.utils.ClassFinder;

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
		String className = settings.getBaseClass();
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
		try {
			JSONArray items = new JSONArray();
			Settings settings = new Settings();
			String className = settings.getBaseClass();
			ClassFinder cf = new ClassFinder();
			Vector<Class<?>> v = cf.findSubclasses(className);
			if (v != null && v.size() > 0) {
				for (Class<?> cls : v) {
					JSONObject classes = new JSONObject();
					classes.put("text", cls.getName());
					classes.put("cls", "folder");
					classes.put("expanded", false);
					JSONArray children = new JSONArray();
					Method[] methods = cls.getDeclaredMethods();
					Set testMethods = new HashSet();
					for (Method mt : methods) {
						if (mt.isAnnotationPresent(Test.class)) {
							JSONObject method = new JSONObject();
							method.put("text", mt.getName());
							method.put("leaf", true);
							method.put("checked", false);
							children.put(method);
						}
					}
					classes.put("children", children);
					items.put(classes);
				}
			}
		jspString = filterEscapes(items.toString());
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
