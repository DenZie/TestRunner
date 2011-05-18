package com.den.qa.qtf.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.Enumeration;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.Test;



import com.den.qa.qtf.Qtf;
import com.den.qa.qtf.Settings;

import static com.den.qa.qtf.utils.ClasspathHacker.*;

public class MyClassFinder
{
    private ArrayList files = new ArrayList();
    private ArrayList classList = new ArrayList();
    Map<String, ArrayList> testclass =  new HashMap<String, ArrayList>();
    private ArrayList methodList = new ArrayList();

    private String dirName = null;
    private String className = null;
    private boolean verbose = false;
    private boolean ignoreCase = true;

    /**
     * The driver for the ClassFinder.
     */
    public static void main(String argv[])
    {
    	
    	String dirName = "C:\\Documents and Settings\\user\\.qtf\\tests";
    	MyClassFinder mCF = new MyClassFinder();
    	mCF.buildClassList();
//    	mCF.showFileList();
//    	mCF.showClassList();
    	mCF.findSubClass();
//    	mCF.showMetodList();
    	System.out.println(mCF.generateJson());
//        System.exit(0);
    }

    /**
     * Public constructor that expects to be given the argument array from the
     * command line.
     *
     * @param argv - an array of Strings from the command line.
     *
     * @throws an IllegalArgumentException if the command line is invalid or if
     *            the directory name specified is not really a directory.
     *
     */
    public MyClassFinder() throws IllegalArgumentException
    {
//    	try {
//    		try {
////				Class<?> cls = Class.forName("org.openqa.selenium.WebDriver");
//			} catch (ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}

//    		addFile("C:\\Documents and Settings\\user\\.qtf\\tests\\dep\\selenium-server-standalone-2.0b1.jar");
//			addFile("C:\\Documents and Settings\\user\\.qtf\\tests\\qtfTest.jar");
////
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		Qtf qtf = new Qtf();
		dirName = qtf.getHome() + qtf.fileSep + "tests";

        File directory = new File(dirName);
        if (!directory.exists())
        {
            throw (new IllegalArgumentException("The directory \"" + dirName + "\" does not exist"));
        }

        if (!directory.isDirectory())
        {
            throw (new IllegalArgumentException("The file \"" + dirName + "\" is not a directory"));
        }

        buildFileList(directory);
        buildClassList();
        buildSuiteMap();

    }

	/**
	 * Recursively decends into a directory looking for files to search for.  This
	 * populates the "files" List with files to look at later.
	 *
	 * @param nextFile a file or directory that is examined for files to add.  If
	 * there are any directories within the File then they are recursively added.
	 *
	 */
    private void buildFileList(File nextFile) {
        File[] fileList = nextFile.listFiles();

        if (fileList != null) {
            for (int i = 0; i < fileList.length; i++) {
//                if (fileList[i].isDirectory()) {
//                    buildFileList(fileList[i]);
//                }

                if (fileList[i].getName().toLowerCase().endsWith(".jar")
                        || fileList[i].getName().toLowerCase().endsWith(".war")
                        || fileList[i].getName().toLowerCase().endsWith(".rar")
                        || fileList[i].getName().toLowerCase().endsWith(".ear")
                        || fileList[i].getName().toLowerCase().endsWith(".class") ) {
					if( !fileList[i].isDirectory() )
                    	files.add(fileList[i]);
//					try {
//						addFile(fileList[i]);
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
                }
            }
        }
    }

    private void buildClassList() {
        for (int i = 0; i < files.size(); i++) {
//        	try {
////				addFile(files.get(i).toString());
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
            File nextFile = (File)(files.get(i));
            JarFile nextJarFile = null;
            try {
                nextJarFile = new JarFile(nextFile);
            }
            catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            for (Enumeration jarEntries = nextJarFile.entries(); jarEntries.hasMoreElements();) {
                ZipEntry nextEntry = (ZipEntry) jarEntries.nextElement();
                String entryName = nextEntry.getName();
                if(entryName.endsWith(".class")) {
                	entryName = entryName.replaceAll("/", ".");
                	entryName = entryName.replaceAll(".class", "");
                	classList.add(entryName);
                }
            }
        }
    }
    
    public void buildSuiteMap() {
    	for (int i = 0; i < classList.size(); i++) {
    		String name = classList.get(i).toString();
    		try {
				Class<?> cls = Class.forName (name);
				Method[] methods = cls.getDeclaredMethods();
				ArrayList<Method> mts = new ArrayList<Method>();
				for(Method mt: methods) {
					if(mt.isAnnotationPresent(Test.class)) {
						mts.add(mt);
					}
				}
				testclass.put(name, mts);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}

    }
    
    public void findSubClass() {
    	for (int i = 0; i < classList.size(); i++) {
    		String name = classList.get(i).toString();
    		try {
				Class<?> cls = Class.forName (name);
				Method[] methods = cls.getDeclaredMethods();
				ArrayList<Method> mts = new ArrayList<Method>();
				for(Method mt: methods) {
					if(mt.isAnnotationPresent(Test.class)) {
						mts.add(mt);
					}
				}
				testclass.put(name, mts);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			if (superClass.isAssignableFrom (c) &&
//				!fqcn.equals (packageName + "." + classname))
//			{
//				thisResult.put (c, url);
//			}

    	}
    }

    public void showClassList() {
    	for (int i = 0; i < classList.size(); i++) {
    		System.out.println(classList.get(i));
    	}
    }

    public void showFileList() {
    	for (int i = 0; i < files.size(); i++) {
    		System.out.println(files.get(i));
    	}
    }

    public void showMetodList() {
    	for (int i = 0; i < methodList.size(); i++) {
    		System.out.println(methodList.get(i));
    	}
    }

	public String generateJson() {
		String jspString = null;
		JSONObject suiteMap = new JSONObject();
		try {
			suiteMap.put("identifier", "name");
			suiteMap.put("label", "name");
			JSONArray items = new JSONArray();
			JSONObject item = new JSONObject();
			java.util.Iterator<String> itr = testclass.keySet().iterator();
			while(itr.hasNext()){
				String k =  itr.next();
				JSONObject classes = new JSONObject();
				classes.put("name", k.toString());
				classes.put("type", "parent");
				classes.put("checkbox", false);
				JSONArray children = new JSONArray();

				ArrayList mts = testclass.get(k);
				for (int i = 0; i < mts.size(); i++) {
		    		System.out.println("   :- " + mts.get(i));
		    		String mName = mts.get(i).toString();

					JSONObject reference = new JSONObject();
					reference.put("_reference", mName);
					children.put(reference);
					JSONObject method = new JSONObject();
					method.put("name", mName);
					method.put("type", "child");
					method.put("checkbox", false);
					items.put(method);
		    	}
				classes.put("children", children);
				items.put(classes);
			}
			suiteMap.put("items", items);
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