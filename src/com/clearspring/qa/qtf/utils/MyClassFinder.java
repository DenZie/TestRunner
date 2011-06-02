package com.clearspring.qa.qtf.utils;

import java.io.File;
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
import org.testng.annotations.*;

import com.clearspring.qa.qtf.Qtf;

public class MyClassFinder
{
    private ArrayList files = new ArrayList();
    private ArrayList classList = new ArrayList();
    Map<String, ArrayList<String>> testclass =  new HashMap<String, ArrayList<String>>();
    private ArrayList methodList = new ArrayList();
    Qtf qtf;

    private String dirName = null;
    File directory;



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
		qtf = new Qtf();
		dirName = qtf.homeDir + qtf.fileSep + "tests";

        directory = new File(dirName);
        if (!directory.exists())
        {
            throw (new IllegalArgumentException("The directory \"" + dirName + "\" does not exist"));
        }

        if (!directory.isDirectory())
        {
            throw (new IllegalArgumentException("The file \"" + dirName + "\" is not a directory"));
        }


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
                if (fileList[i].getName().toLowerCase().endsWith(".jar")
                        || fileList[i].getName().toLowerCase().endsWith(".war")
                        || fileList[i].getName().toLowerCase().endsWith(".rar")
                        || fileList[i].getName().toLowerCase().endsWith(".ear")
                        || fileList[i].getName().toLowerCase().endsWith(".class") ) {
                    	files.add(fileList[i]);
                }
            }
        }
    }

    private void buildClassList() {
        for (int i = 0; i < files.size(); i++) {
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
				ArrayList<String> mts = new ArrayList<String>();
				for(Method mt: methods) {
					if(mt.isAnnotationPresent(org.testng.annotations.Test.class) ) {
						mts.add(mt.getName());
					}
				}
				if(mts.size() > 0) {
					testclass.put(name, mts);
				}
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

	public void generateJson() {
        buildFileList(directory);
        buildClassList();
        buildSuiteMap();

		String jspString = null;
		try {
			JSONArray items = new JSONArray();
			java.util.Iterator<String> itr = testclass.keySet().iterator();
			while(itr.hasNext()){
				String k =  itr.next();
				ArrayList mts = testclass.get(k);
				JSONObject classes = new JSONObject();
				classes.put("text", k);
				classes.put("cls", "folder");
				classes.put("expanded", false);
				JSONArray children = new JSONArray();
				for (int i = 0; i < mts.size(); i++) {
		    		String mName = mts.get(i).toString();
					JSONObject method = new JSONObject();
					method.put("text", mName);
					method.put("leaf", true);
					method.put("checked", false);
					children.put(method);
		    	}
				classes.put("children", children);
				items.put(classes);
			}
			jspString = filterEscapes(items.toString());
		} catch (JSONException e) {
			// TODO: handle exception
		}
		Io.writeToFile( qtf.getSuiteFileName(), jspString);
	}
	
	public String getSuiteMap() {
		return Io.readFromFile(qtf.getSuiteFileName());
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