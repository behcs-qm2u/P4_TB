package com.appium.tboader;

import static org.junit.Assert.*;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;




public class TestAmazonApps {

	public static AndroidDriver<MobileElement> driver;
	public static WebDriverWait                wait;		
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	        System.out.println("Create Driver Instance");
	       
	        URL url = new URL("http://192.168.100.41:4723/wd/hub");
	        DesiredCapabilities cap = new DesiredCapabilities();
	        cap.setCapability("platformName", "Android");
	        cap.setCapability("platformVersion", "11");
	        cap.setCapability("productName", "sdk_gphone_x86");
	        cap.setCapability("appPackage", "com.amazon.mShop.android.shopping");
	        cap.setCapability("appActivity", "com.amazon.mShop.home.HomeActivity");
	        driver = new AndroidDriver<MobileElement>(url, cap);
	        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	        SessionId sessionId = driver.getSessionId();
	        System.out.println(sessionId);
	        Thread.sleep(2000);
	        
			System.out.println("INFO: sessionId: " + sessionId + " created on " + driver.getRemoteAddress());
			Thread.sleep(1000);	        
		
	        System.out.println("DEBUG: to click Skip sign in button");
	        driver.findElement(By.id("com.amazon.mShop.android.shopping:id/skip_sign_in_button")).click();
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		
		try {
			Thread.sleep(500000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		fail("Not yet implemented");
	}

}
