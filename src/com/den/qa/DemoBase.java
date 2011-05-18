package com.den.qa;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class DemoBase {
	public WebDriver driver;
	
	@BeforeClass
	public void setup() {
		System.out.println("Startup");
		driver = new FirefoxDriver();
	}
	
	@AfterClass
	public void stop() {
		System.out.println("Teardown");
		driver.quit();
	}
}
