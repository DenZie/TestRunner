package com.den.qa.qtf.servlet;

import java.io.*;

import java.text.*;
 
import java.util.*;
 
import javax.servlet.*;
 
import javax.servlet.http.*;


public class Result extends HttpServlet {
 
	public void init(ServletConfig config) throws ServletException {	
		super.init(config);
	}
 
	public void destroy() {
 
	}
 
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
		PrintWriter out = response.getWriter();
		Date df = new Date();
		out.println("{\"key\" : \"value\"}");
	}
 
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
		doPost(request,response);
	}
}