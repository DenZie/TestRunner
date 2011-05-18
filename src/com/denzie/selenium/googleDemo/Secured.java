package com.denzie.selenium.googleDemo;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.den.qa.DemoBase;

public class Secured extends DemoBase{
	String url;
	@Test
	public void qburst() {
		System.out.println("qburst");
		url="http://localhost:8080/Qtf/suite.jsp";
		driver.get(url);
//		driver.findElement(By.name("q")).sendKeys("qburst");
//		driver.findElement(By.name("btnG")).click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void hot2(){
		System.out.println("hot2");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	@Test
	public void hot3() {
		System.out.println("hot3");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void hot4() {
		System.out.println("hot4");
		
	}
}
