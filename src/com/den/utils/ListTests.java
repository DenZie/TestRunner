package com.den.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.testng.annotations.Test;

import com.den.qa.qtf.Settings;
import com.den.utils.ClassFinder;

public class ListTests {
	Map<String, Object[]> testclass =  new HashMap<String, Object[]>();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ListTests lt=  new ListTests();
		lt.getTestClass();	
	}
	
	public Map<String, Object[]>  FetchTestClass() {
		Settings settings = new Settings();
		Map<String,String> settingsMap = settings.read();
		String className = "value='" + settingsMap.get("tc") + "'";
		System.out.println(className);
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
}
