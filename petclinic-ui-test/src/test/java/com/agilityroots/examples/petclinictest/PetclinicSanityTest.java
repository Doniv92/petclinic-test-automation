package com.agilityroots.examples.petclinictest;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Example Class for Headless Selenium automation tests of PetClinic.
 * <p>
 * <b>Notes</b>
 * <p>
 * <ul>
 * <li>Petclinic Application must be running for this test to work.</li>
 * <li>As of date, PhantomJS methods in Selenium are deprecated.</li>
 * </ul>
 * <p>
 * <b>References</b><p>
 * https://www.guru99.com/selenium-with-htmlunit-driver-phantomjs.html<br>
 */
public class PetclinicSanityTest 
{
	private PhantomJSDriver driver;
	@Before
	public void setUp() throws Exception {
	    
	    if (System.getProperty("os.name").toLowerCase().contains("linux")) {
		    File file = new File(Constants.PJS_PATH_LNX);
	        System.setProperty("phantomjs.binary.path", file.getAbsolutePath());	    		    	
	    } else if (System.getProperty("os.name").toLowerCase().contains("win")) {
		    File file = new File(Constants.PJS_PATH_WIN);
	        System.setProperty("phantomjs.binary.path", file.getAbsolutePath());	    	
	    }
		DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
	    driver = new PhantomJSDriver(capabilities);
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	}
	
	@Test
	public void testHomePageShouldContainElements() throws Exception {
	    driver.get(Constants.URL);
	    driver.findElement(By.xpath(Constants.FIND_OWNERS));
	    driver.findElement(By.xpath(Constants.VETS));
	}

	@Test
	public void testVetsPageShouldContainElements() throws Exception {
	    driver.get(Constants.URL + "/vets.html");
	    Thread.sleep(1000);
	    WebDriverWait wait = new WebDriverWait(driver, 10);
	    WebElement vetsTable = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(By.id("vets")));
	    String someClass = vetsTable.getAttribute("class");
	    Assert.assertTrue(someClass.contains("table"));
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}
}
