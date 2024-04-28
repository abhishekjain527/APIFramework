package com.Test.API.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.Test.API.util.TestUtil;
import com.Test.API.util.WebEventListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;


public class TestBase {

	public static WebDriver driver;
	public static Properties prop;
	public static Properties or;
	public static EventFiringWebDriver e_driver;
	public static WebEventListener eventListener;
	public static String cycleId;
	public static String projectId;
	public static String versionId;
	
	
	public TestBase() {

		try {
			prop = new Properties();
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+ "/src/test/java/com/siegwerk/th/config/config.properties");
			prop.load(fis);

		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static void initialization() {
		String browser = prop.getProperty("browser");

		if(browser.equals("Chrome")){
			System.setProperty("webdriver.chrome.driver", "chromedriver.exe");	
			driver = new ChromeDriver(); 
		}
		else if(browser.equals("FF")){
			System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
			//System.setProperty("webdriver.firefox.marionette","geckodriver.exe");
			/*
			 * DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			 * capabilities.setCapability("marionette",true);
			 * 
			 */ 
			FirefoxOptions options = new FirefoxOptions();  
			options.setLegacy(true); 
			driver = new FirefoxDriver(options); 
		}


		e_driver = new EventFiringWebDriver(driver);
		// Now create object of EventListerHandler to register it with EventFiringWebDriver
		eventListener = new WebEventListener();
		e_driver.register(eventListener);
		driver = e_driver;
		//ngDriver = new NgWebDriver((JavascriptExecutor) driver);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);

		driver.get(prop.getProperty("url"));
		//ngDriver.waitForAngularRequestsToFinish();

	}
	

     


}
