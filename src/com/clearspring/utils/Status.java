package com.clearspring.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONException;
import  org.json.JSONObject;


public class Status {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String fileName = "tata.log";
		Status jutil = new Status();
		jutil.setTotal(fileName);
		jutil.setProgress(fileName);


	}
	
	public void setTotal(String fileName) {
		JSONObject summary = new JSONObject();
		try {
			summary.put("total", 5);
			FileWriter fstream = null;
			fstream = new FileWriter(fileName);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(summary.toString());
			//Close the output stream
			out.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
	}
	
	public void setProgress(String fileName) {
		String jsonData = getStatus(fileName);
		try {
			JSONObject summary = new JSONObject(jsonData);
			summary.put("finished", 3);
			FileWriter fstream = null;
			fstream = new FileWriter(fileName);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(summary.toString());
			// Close the output stream
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public String getStatus(String fileName) {
		setTotal(fileName);
		String path = null;
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
