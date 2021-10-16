package com.appium.tboader;

import static org.junit.Assert.*;

import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.MobileElement;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class TestGoogleMap {

	public static AndroidDriver<MobileElement> driver;
	public static WebDriverWait                wait;	
		
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		
		
		// URL url = new URL("http://127.0.0.1:4723/wd/hub");
		URL url = new URL("http://192.168.100.185:4723/wd/hub");
		// URL url = new URL("http://192.168.100.41:4723/wd/hub");

		// Desired capability
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability("platfromName", "Android");
		cap.setCapability("platformVersion", "11");
		cap.setCapability("appPackage", "com.google.android.apps.maps");
		cap.setCapability("appActivity","com.google.android.maps.MapsActivity");

		//  cap.setCapability("skipDeviceInitialization", true);
		//cap.setCapability("skipServerInstallation", true);
		// use this with care!
		//cap.setCapability("ignoreUnimportantViews", true);		

		//		{
		//			  "platformName": "Android",
		//			  "platformVersion": "11",
		//			  "Package": "com.google.android.apps.maps",
		//			  "Activity": "com.google.android.maps.MapsActivity"
		//		}

		// Android Driver
		driver = new AndroidDriver<MobileElement>(url, cap);
		
		// Set Implicit wait
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		
		wait = new WebDriverWait(driver, 8);			
		
		SessionId sessionId = driver.getSessionId();
		System.out.println("INFO: sessionId: " + sessionId + " created on " + driver.getRemoteAddress());
		Thread.sleep(1000);		
		
		// click SKIP first
		driver.findElement(By.xpath("//android.widget.Button[@text='SKIP']")).click();
		
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
	public void testGoogleZoomOut() {
	
		System.out.println("test multi tap on Google Map");
		
		int height = driver.manage().window().getSize().getHeight();
		int width = driver.manage().window().getSize().getWidth();
		
		// drop a pin first
		// Point pin = new Point((int)(0.5 * width), (int)(0.5 * height));
		// TouchAction quickTap = new TouchAction(driver).tap(PointOption.point(pin));
		
		
		
		Point p1s = new Point((int)(0.5 * width) , (int)(0.5 * height) -30);
		Point p1e = p1s.moveBy(0, -150);		
		System.out.println("P1 start:" + p1s + ", P1 end:" + p1e);
		
		TouchAction action1 = new TouchAction(driver);
		
		action1
		.longPress(PointOption.point(p1s.getX(), p1s.getY()))
//		.waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
		.moveTo(PointOption.point(p1e))
		.release();		

		Point p2s = new Point((int)(0.5 * width), (int)(0.5 * height) + 30);
		Point p2e = p1s.moveBy(0, 150);		
		System.out.println("P2 start:" + p2s + ", P2 end:" + p2e);

		
		TouchAction action2 = new TouchAction(driver);
		
		action2
		.longPress(PointOption.point(p2s.getX(), p2s.getY()))
//		.waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
		.moveTo(PointOption.point(p2e))
		.release();				
		
        try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		System.out.println("Going to zoom...");
		
		MultiTouchAction action = new MultiTouchAction(driver);
		action.add(action1).add(action2).perform();
		
        try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Done");
		
		
		
	}

}
