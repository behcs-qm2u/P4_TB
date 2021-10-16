package com.appium.tboader;

import static org.junit.Assert.*;

import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;




public class TestAmazonApps {

	public static AndroidDriver<MobileElement> driver;
	public static WebDriverWait                wait;		
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	        System.out.println("Create Driver Instance");
	       
	        // URL url = new URL("http://192.168.100.41:4723/wd/hub");
	        URL url = new URL("http://192.168.100.185:4723/wd/hub");
	        DesiredCapabilities cap = new DesiredCapabilities();
	        cap.setCapability("platformName", "Android");
	        cap.setCapability("platformVersion", "11");
	        // cap.setCapability("productName", "sdk_gphone_x86");
	        cap.setCapability("appPackage", "com.amazon.mShop.android.shopping");
	        cap.setCapability("appActivity", "sg.amazon.mShop.home.HomeActivity");
	        driver = new AndroidDriver<MobileElement>(url, cap);
	        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
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
	public void test() throws InterruptedException {
		
		String loc;
		// type earphones 
		//String loc = "com.amazon.mShop.android.shopping:id/chrome_search_hint_view"; 
		// Step 1: click the search bar
		//System.out.println("DEBUG: start test.. Step1");
		//driver.findElement(By.id(loc)).click();
		//safeGetME(loc, "id").click();
		
		
		
		// Step 1: key in "earphones"
		loc = "com.amazon.mShop.android.shopping:id/rs_search_src_text";
		System.out.println("DEBUG: start test.. Step2");
		safeGetME(loc, "id").click();
		Thread.sleep(2000);
		safeGetME(loc, "id").sendKeys("earphones");
        
		// Step 3: select "earphones with microphone" in the hint list
		loc = "com.amazon.mShop.android.shopping:id/iss_search_suggestions_list_view";
		MobileElement suggestParent = safeGetME(loc, "id");
		clickViewFromParent(suggestParent, "earphones with microphone");
		Thread.sleep(3000);
		

		// Step 4: click one of the item
		loc = "//android.widget.RelativeLayout[@resource-id='com.amazon.mShop.android.shopping:id/mash_web_fragment']";
		MobileElement productParent = safeGetME(loc, "xpath" );
		Thread.sleep(5000);

		System.out.println("Hallo 1...2...3");
		
		
		
		System.out.println("DEBUG: productParent is:" + productParent);
		loc = "//android.view.View[@resource-id='search']/android.view.View/android.view.View[contains(@content-desc, ' ') and @index='3']/android.view.View[contains(@text, ' ') and @index < '4' ]";
		// List <MobileElement> productList = productParent.findElements(By.xpath(loc));
		List <MobileElement> productList = productParent.findElements(By.xpath(loc));
		
		System.out.println("Number of products: " + productList.size());
		
		int counter = 1;
		if ( productList.size() > 0 ) {
			for ( MobileElement product : productList ) {
				
				System.out.println("Product [" + counter + "] : " + product.getText() ); 
				counter++;
			}
		}
		
		// iss_search_dropdown_item_text
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
	}

	
	
	
	/**
	 * Helper method to check element in prechkLocator exists, then select and click the element of clickLocator
	 * 
	 * @param prechkLocator Xpath string for pre-check element
	 * @param clickLocator Xpath string for the element to click
	 * @param searchType "id" or "xpath"
	 */
	private static void valAndClick(String prechkLocator, String clickLocator, String searchType) {
		
		try {
			if ( searchType.equalsIgnoreCase("id")) {
				wait.until(ExpectedConditions.presenceOfElementLocated(By.id(prechkLocator)));
			}
			else { 
				wait.until(ExpectedConditions.presenceOfElementLocated(By.id(prechkLocator)));
			}
			checkAndClick(clickLocator, searchType);
		}
		catch (TimeoutException e) {
			System.out.println("FATAL: Pre-check element not exist / Timeout!");
		}
		catch (Exception e) {
			// something else wrong?
			e.printStackTrace();
		}
		
	}
	
	private static void checkAndClick(String locValue, String searchType) {

			List<MobileElement> lstElems;
		
			if ( searchType.equalsIgnoreCase("id")) { 
				lstElems = driver.findElements(By.id(locValue));
			}
			else {
				lstElems = driver.findElements(By.xpath(locValue));
			}
				
		
			if (lstElems.size() > 0) {
				// System.out.println("DEBUG: checkAndClick()::Elems size: " + lstElems.size());
				lstElems.get(0).click();
			}
			else {
				System.out.println("ERROR: checkAndClick()::element [" + locValue + "] not found!");
			}
		
	}	
	
	/**
	 * Wrapper to get MobileElement by trying maximum of 3 times. This is general work-around for frequent StaleElementException due to timing issue.
	 * @param locValue String for locator. 
	 * @param type     "id" or "xpath"
	 * @return MobileElement 
	 */
	private static MobileElement safeGetME(String locValue, String type) {

		MobileElement tempME = null;
		int retry_max = 3;	// also indicate scroll max
		
		WebDriverWait wait2 = (WebDriverWait) new WebDriverWait(driver, 8)
				.ignoring(StaleElementReferenceException.class);

		while (retry_max-- > 0) {

			try {
				if ( type.equalsIgnoreCase("id") ) {
					tempME = (MobileElement) wait2.until(ExpectedConditions.presenceOfElementLocated(By.id(locValue)));
				}
				else {
					tempME = (MobileElement) wait2.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locValue)));
				}
				
				if ( tempME != null ) {
					return tempME;
				}
				else {
					System.out.println("DEBUG:safeGetME():: [" + locValue + "] not found! To retry..");
					continue;
				}
			}
			catch (StaleElementReferenceException e) {
				// not supposed, we already ignore it
				System.out.println("DEBUG:safeGetME():: Caught [StaleElementReferenceException] retrying.." );
			}
			catch (TimeoutException e) {
				System.out.println("DEBUG:safeGetME():: Caught [TimeoutException] for [" + locValue + "] retrying.." );
			}
			catch (Exception e) {
				System.out.println("DEBUG:safeGetME():: Caught [something else?!] for [" + locValue + "] retrying.." );
				e.printStackTrace();
			}
		}
		
		// exhausted retries
		System.out.println("ERROR:safeGetME()::Exhausted all retries. [" + locValue + "] not found!" );
		return null;
		
	}
		

	/**
	 * Helper method to select and click the list view item identified by elemClick
	 * The list must come from class adroid.widget.TextView
	 * 
	 * @param elemClick String for the element's text attribute
	 * @throws InterruptedException 
	 */
	private static void clickViewFromParent(MobileElement parentME, String elemClick) throws InterruptedException {

		List<MobileElement> lstViews = null;
		int retry_max = 3;
		
		System.out.println("DEBUG:clickViewFromParent():: Going to click [" + elemClick +"]");

		// we will do <retries times>
		// StaleElement exception seems very common here, we will do retry
		
		WebDriverWait wait2 = (WebDriverWait) new WebDriverWait(driver, 8)
				.ignoring(StaleElementReferenceException.class);

		while (retry_max-- > 0) {

			if ( parentME != null ) {
				try {
					lstViews = parentME.findElements(By.xpath("//android.widget.TextView"));
					
					System.out.println("DEBUG:clickViewFromParent():: Size of List View: " + lstViews.size());
					for (MobileElement view : lstViews) {
						System.out.println(" -> " + view.getAttribute("text"));
						// iterate until the elemClick to click
						if (view.getAttribute("text").equals(elemClick)) {
							System.out.println("     ^-- CLICK!");
							view.click();
							// break;
							return ;
						}
					}	
					
					// if not found?!
					System.out.println("FATAL:clickViewFromParent():: [" + elemClick + "] not found! To retry..");
					continue;
					
				}
				catch (StaleElementReferenceException e) {
					System.out.println("DEBUG:clickViewFromParent():: Caught [StaleElementReferenceException] retrying.." );
				}
				catch (Exception e) {
					System.out.println("DEBUG:clickViewFromParent():: Caught [something else?!] retrying.." );
					e.printStackTrace();
				}

			}
			else {
				System.out.println("DEBUG:clickViewFromParent():: parent is null! Retrying.." );
			}

		}

		// fall through
		fail("FATAL:clickView():: [" + elemClick + "] not found! Exhausted retries!");


	}
		
	
	
}
