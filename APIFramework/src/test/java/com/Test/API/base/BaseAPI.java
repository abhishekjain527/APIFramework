package com.Test.API.base;

import java.io.File;
import java.io.FileInputStream;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;
import java.util.Properties;

import com.Test.API.util.CommonUtils;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.apache.log4j.Logger;
import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class BaseAPI extends CommonUtils {


	public Properties prop;
	protected static String BASE_URL;
	private String resourceURL;
	private int statusCode;
	//public static PostOrder_AckgID_Object ackg = new PostOrder_AckgID_Object();
	public static Logger logger = Logger.getLogger(BaseAPI.class);

	public String getResourceURL() {
		return resourceURL;
	}

	public void setResourceURL(String resourceURL) {
		this.resourceURL = resourceURL;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	protected String PASSWORD;

	public BaseAPI() {
		prop = new Properties();
		try {
			FileInputStream fis = new FileInputStream(
					System.getProperty("user.dir") + "/resources/config.properties");
			try {
				prop.load(fis);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		BASE_URL = prop.getProperty("BaseUrl");
		PASSWORD = prop.getProperty("email_password");
		RestAssured.baseURI = BASE_URL;
	}


	TestBase tb = new TestBase();

	public void createAndTestsToCycle() throws Exception {


	}


	public Response POST(String resporceUrl, String payLoad) {
		logger.info("Payload is " + payLoad);

		Response response = RestAssured.given().header("Content-Type", "application/json").body(payLoad).when()
				.post(resporceUrl);
		logger.info("Response of the request is : " + response.getBody().asString());
		setStatusCode(response.getStatusCode());
		return response;
	}


	public Response POST_withSubscriptionKey(String resporceUrl, String payLoad) {
		RestAssured.baseURI = BASE_URL;
		logger.info("Payload is " + payLoad);

		String username = prop.getProperty("Username");
		String password = prop.getProperty("Password");
		Response response = given()
				.relaxedHTTPSValidation().auth().preemptive().basic(username, password)
				.header("Ocp-Apim-Subscription-Key", prop.getProperty("subsKey"))
				.header("Ocp-Apim-Trace", prop.getProperty("traceValue"))
				.header("Content-Type", "application/json")
				.body(payLoad)
				.when().post(resporceUrl);
		setStatusCode(response.getStatusCode());
		logger.info("Response of the request is : " + response.getBody().asString());


		return response;
	}


	public Response POST_withoutAuthorization(String resporceUrl, String payLoad) {
		RestAssured.baseURI = BASE_URL;
		logger.info("Payload is " + payLoad);

		String username = prop.getProperty("Username");
		String password = prop.getProperty("Password");
		Response response = given()
				.relaxedHTTPSValidation()
				.header("Ocp-Apim-Subscription-Key", prop.getProperty("subsKey"))
				.header("Ocp-Apim-Trace", prop.getProperty("traceValue"))
				.header("Content-Type", "application/json")
				.body(payLoad)
				.when().post(resporceUrl);
		setStatusCode(response.getStatusCode());
		logger.info("Response of the request is : " + response.getBody().asString());
		return response;
	}


	public Response GET(String resporceUrl) {
		logger.info("Resource Url is " + resporceUrl);
		Response response = RestAssured.given().header("Content-Type", "application/json").when()
				.get(resporceUrl);
		logger.info("Response of the request is : " + response.getBody().asString());
		return response;
	}


	public Response GET_API(String resporceUrl) throws ParseException {
		RestAssured.baseURI = BASE_URL;
		logger.info("Resource Url is " + resporceUrl);

		Response response = (Response) RestAssured.given()
				.header("Content-Type", "application/json")
				.when()
				.get(resporceUrl);
		//	.then().assertThat()
		//	.body(JsonSchemaValidator.
		//			matchesJsonSchema(new File("./JSONFiles/ResponseSchema.json")));
		logger.info("Response of the request is : " + response.getBody().asString());
		return response;
	}


	public Response DELETE(String resporceUrl) {
		logger.info("Resource Url is " + resporceUrl);
		Response response = RestAssured.given().header("Content-Type", "application/json").when()
				.delete(resporceUrl);
		logger.info("Response of the request is : " + response.getBody().asString());
		return response;
	}

	public Response PUT(String resporceUrl, String payLoad) {
		logger.info("Payload for put request is " + payLoad);
		Response response = RestAssured.given().header("Content-Type", "application/json").body(payLoad).when()
				.put(resporceUrl);
		logger.info("Response of the request is : " + response.getBody().asString());
		setStatusCode(response.getStatusCode());
		return response;
	}

	public int getStatusCode(Response response) {
		return response.getStatusCode();
	}

	public boolean verifyStatusCode(Response response, int expectedStatusCode) {
		boolean status = response.getStatusCode() == expectedStatusCode;
		//Assert.assertEquals(response.getStatusCode(), expectedStatusCode, "Verify status code");
		//
		return status;
	}

	public int verifyCurrencyCount(Response response, int expectedCount) {
		Map<String, Float> rates = response.path("rates");
		int count = rates.size();
		Assert.assertEquals(count, expectedCount, "162 currency pairs are returned by the API");
		System.out.println("Currency pair is correct" + " " + count);
		return count;
	}

	public void verifyAEDValue(Response response) {

		Map<String, Float> rates = response.path("rates");
		Float value = rates.get("AED");
		assertTrue(3.6 <= value && value <= 3.7);
		System.out.println("AED price is correct" + " " + value);
	}

	public void verifyJsonSchemaAndResponsetime(Response response) {
		response.then().assertThat()
				.body(JsonSchemaValidator.
						matchesJsonSchema(new File("./JSONFiles/ResponseSchema.json")));

		System.out.println("Json Schema verified successfully");
		response.then().assertThat().time(greaterThan(3000L));
//		DateTime lastUpdate = response.path("time_last_update_utc");
//		DateTime nextUpdate = response.path("time_next_update_utc");

	}










}
