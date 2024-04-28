package com.Test.API.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

import com.Test.API.base.TestBase;

public class TestUtil extends TestBase{
	
	public static long PAGE_LOAD_TIMEOUT = 20;
	public static long IMPLICIT_WAIT = 20;
	private static Sheet sheet;
	private static Workbook book;
	
	
	public static WebElement findElement(String locatorName) {
		WebElement element = null;
		element = driver.findElement(getbjectLocator(locatorName));
		return element;
	}
	
	public static List<WebElement> findElements(String locatorName){
		List<WebElement> element = null;
		element = driver.findElements(getbjectLocator(locatorName));
		return element;
	}
	
	
	 public static String takeScreenshotAtEndOfTest() throws IOException { 
		 File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE); 
		 String destinationDir = System.getProperty("user.dir")+ "/screenshots/" + System.currentTimeMillis() + ".png"; 
	     FileUtils.copyFile(scrFile, new File(destinationDir)); 
	     return destinationDir;
	     }
	 
	
	private static By getbjectLocator(String locatorName)
	 {
	 String locatorProperty = or.getProperty(locatorName);
	 System.out.println(locatorProperty.toString());
	 String locatorType = locatorProperty.split(":")[0];
	 String locatorValue = locatorProperty.split(":")[1];
	 
	 By locator = null;
	 switch(locatorType)
	 {
	 case "Id":
	 locator = By.id(locatorValue);
	 break;
	 case "Name":
	 locator = By.name(locatorValue);
	 break;
	 case "CssSelector":
	 locator = By.cssSelector(locatorValue);
	 break;
	 case "LinkText":
	 locator = By.linkText(locatorValue);
	 break;
	 case "PartialLinkText":
	 locator = By.partialLinkText(locatorValue);
	 break;
	 case "TagName":
	 locator = By.tagName(locatorValue);
	 break;
	 case "Xpath":
	 locator = By.xpath(locatorValue);
	 break;
	 }
	 return locator;
	 }



}
