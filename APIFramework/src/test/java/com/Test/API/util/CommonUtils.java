package com.Test.API.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.TimeZone;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class CommonUtils {

	public static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getRootLogger();

	/**
	 * Read data from JSON file
	 * 
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	public JSONObject getDataFromJson(String filePath) throws FileNotFoundException, IOException, ParseException {
		Object object = new JSONParser().parse(new FileReader(System.getProperty("user.dir") + filePath));
		return (JSONObject) object;
	}

	/**
	 * Get parameter value from JSON file
	 * 
	 * @param response
	 * @param parameterKey
	 * @return
	 */
	public String getParameterValueFromJSON(Response response, String parameterKey) {
		JsonPath jsonPathEvaluator = response.jsonPath();
		String parameterValue = jsonPathEvaluator.getString(parameterKey);
		logger.info("Given parameterkay: '" + parameterKey + "' has value as '" + parameterValue + "'");
		return parameterValue;
	}

	/**
	 * Get list of Array from JSON response
	 * 
	 * @param response
	 * @param arrayName
	 * @return
	 */
	public List<Object> getListOfJsonArrayFromResponse(Response response, String arrayName) {
		List<Object> jsonResponse = null;
		try {
			jsonResponse = response.jsonPath().getList(arrayName);
		} catch (Exception e) {
			Assert.fail(e.getClass().getSimpleName() + " getListofAttributeTypeFromResponse method failed");
		}
		return jsonResponse;
	}

	/**
	 * Verify List data
	 * 
	 * @param listObjects
	 * @param expectedValue
	 * @return
	 */
	public boolean verifyList(List<Object> listObjects, String expectedValue) {
		boolean isTrue = true;
		for (Object object : listObjects) {
			if (!object.toString().equalsIgnoreCase(expectedValue)) {
				isTrue = false;
				return isTrue;
			}
		}
		return isTrue;
	}

	public String getParameterValueFromSubset(Response response, String nestedjsonattribute, String nestedattribute)
			throws ParseException {
		Object obj = JSONValue.parse(response.asString());
		JSONObject jsonObject = (JSONObject) obj;
		JSONArray jsonChildArray = (JSONArray) jsonObject.get(nestedjsonattribute);
		JSONObject secObject = (JSONObject) jsonChildArray.get(0);
		String string = (String) secObject.get(nestedattribute);
		return string;
	}
//	public String getJsonAttributeData(Response prevResponse, String attributeName) {
//		String request1 = prevResponse.asString();
//
//		JSONObject jsonObj = new JSONObject(request1);
//
//		String attributeValue = jsonObj.get(attributeName).toString();
//		return attributeValue;
//		
//
//		
//	}

	public void setValueInPropertFile(String newKey, String newValue) throws IOException {
		/*
		 * FileWriter fileWriter = new FileWriter("resources/test.properties") ;
		 * Properties prop = new Properties();
		 * 
		 * prop.setProperty(newKey, newValue); prop.store(fileWriter, "User Info");
		 * 
		 * System.out.println("Property file is created successfully..");
		 */
		FileInputStream in = new FileInputStream("resources/test.properties");
		Properties props = new Properties();
		props.load(in);
		in.close();

		FileOutputStream out = new FileOutputStream("resources/test.properties");
		props.setProperty(newKey, newValue);
		props.store(out, null);
		out.close();

	}

	public String getTestData(String key) {
		String val = null;
		try {
			FileReader reader = new FileReader("resources/test.properties");
			Properties properties = new Properties();
			properties.load(reader);
			val = properties.getProperty(key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return val;
	}

	public JSONObject replacekeyInJSONObject(JSONObject jsonObject, String jsonKey, String jsonValue) {

		for (Object key : jsonObject.keySet()) {
			if (key.equals(jsonKey) && ((jsonObject.get(key) instanceof String)
					|| (jsonObject.get(key) instanceof Number) || jsonObject.get(key) == null)) {
				jsonObject.put(key, jsonValue);
				return jsonObject;
			} else if (jsonObject.get(key) instanceof JSONObject) {
				JSONObject modifiedJsonobject = (JSONObject) jsonObject.get(key);
				if (modifiedJsonobject != null) {

					replacekeyInJSONObject(modifiedJsonobject, jsonKey, jsonValue);
				}
			}

		}
		return jsonObject;
	}

	public static String getCurrentUtcTime() throws ParseException, java.text.ParseException {
		// SimpleDateFormat simpleDateFormat = new
		// SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		// simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		// System.out.println(simpleDateFormat.toString().replace("+0000", ""));
		// Date date = simpleDateFormat;
		// System.out.println();

		// SimpleDateFormat localDateFormat = new
		// SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		// System.out.println(simpleDateFormat.format(new Date()));

		Instant instant = Instant.now();
		String datestring = instant.toString();
		// Date date = localDateFormat.parse( simpleDateFormat.format(new Date()) );
		System.out.println(datestring);		
		datestring = datestring.substring(0, 14);
		System.out.println(datestring);		
		return datestring;
	}

	
	String jsonkeyvalue = "";

	public String getkeyInJSONObjectinNested(JSONObject jsonObject, String jsonKey) {

		for (Object key : jsonObject.keySet()) {

			if (key.equals(jsonKey) && ((jsonObject.get(key) instanceof String)
					|| (jsonObject.get(key) instanceof Number) || jsonObject.get(key) == null)) {
				jsonkeyvalue = jsonObject.get(key).toString();
				break;
			} else if (jsonObject.get(key) instanceof JSONObject) {

				JSONObject modifiedJsonobject = (JSONObject) jsonObject.get(key);
				if (modifiedJsonobject != null) {

					getkeyInJSONObjectinNested(modifiedJsonobject, jsonKey);
				}
			}
			if (!jsonkeyvalue.equalsIgnoreCase("")) {
				break;
			}
		}
		return jsonkeyvalue;
	}

}
