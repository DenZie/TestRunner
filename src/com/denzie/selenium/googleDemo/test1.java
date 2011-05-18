package com.denzie.selenium.googleDemo;

import org.testng.annotations.Test;

import com.den.qa.DemoBase;

public class test1 extends DemoBase{

	@Test
	public void gMail1() {
		System.out.println("gMail1");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void gMail2() {
		System.out.println("gMail2");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void gMail3() {
		System.out.println("gMail3");
		try {
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void gMail4() {
		try {
			Thread.sleep(9000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("gMail4");
	}
}
