package com.appium.tboader;

import static org.junit.Assert.*;

import java.net.URL;
import java.util.Iterator;
import java.util.Set;
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

public class TestWebView {

	public static AndroidDriver<MobileElement> driver;
	public static WebDriverWait                wait;		
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

        System.out.println("Create Driver Instance");
        URL url = new URL("http://192.168.100.41:4723/wd/hub");
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("platformName", "Android");
        cap.setCapability("platformVersion", "11");
        cap.setCapability("appPackage", "org.chromium.webview_shell");
        cap.setCapability( "chromeBundleId", "com.android.chrome");
        cap.setCapability("appActivity", "org.chromium.webview_shell.WebViewBrowserActivity");
        driver = new AndroidDriver<MobileElement>(url, cap);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        SessionId sessionId = driver.getSessionId();
        System.out.println(sessionId);
        Thread.sleep(1000);

		
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
        
        MobileElement srch = driver.findElement(By.id("org.chromium.webview_shell:id/url_field"));
        srch.clear();
        driver.findElement(By.id("org.chromium.webview_shell:id/url_field")).sendKeys("https://www.google.com");
        driver.findElement(By.xpath("//android.widget.ImageButton")).click();
        Set<String> contextHandles = driver.getContextHandles();
        System.out.println(contextHandles);
        System.out.println(contextHandles.size());
        for (String cH : contextHandles) {
            System.out.println(cH);
        }
        
        Iterator<String> cHi = contextHandles.iterator(); 

        String nativeView = cHi.next();
        String webView = cHi.next();

        
        //String nativeView = contextHandles.iterator().next().toString();
        //String webView = contextHandles.iterator().next().toString();
        
        System.out.println("contextHandles nativeView:" + nativeView);
        System.out.println("contextHandles webView:" + webView);
        
        driver.context("WEBVIEW_org.chromium.webview_shell");
        driver.context(webView);
        
        
    }


}
