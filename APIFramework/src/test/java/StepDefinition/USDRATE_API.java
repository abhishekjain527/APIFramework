package StepDefinition;

import com.Test.API.base.BaseAPI;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.simple.JSONObject;
import org.testng.Assert;

public class USDRATE_API extends BaseAPI {

	Response postResponse;
	JSONObject jsonObject;

	// CommonUtils common;
	public static Logger logger = Logger.getRootLogger();

	@Given("^User has valid end points \"([^\"]*)\"$")
	public void User_has_valid_end_points(String arg1) throws Throwable {
		logger = Logger.getLogger(USDRATE_API.class.getName());
		PropertyConfigurator.configure("Log4j.properties");
		setResourceURL(arg1);
	}

	@When("^User sends the GET request$")
	public void user_sends_the_GET_request() throws Throwable {
		// Write code here that turns the phrase above into concrete actions

		String endpoint = getResourceURL();

		postResponse = GET_API(getResourceURL());

	}


	boolean status;
	int status_code;

	@Then("^User gets the response status code as (\\d+)$")
	public void user_gets_the_response_status_code_as_and_update(int arg1) throws Throwable {

		status = verifyStatusCode(postResponse, arg1); // Assert
		status_code = postResponse.getStatusCode();
		if (status) {

			logger.info("Status code is correct, status code is  " + status_code);

		} else {

			logger.error("Status code is incorrect, status code is " + status_code + " and expected should be " + arg1);
			Assert.assertEquals(status_code, arg1, "Status code incorrect");
		}


	}
	@And("^Verify AED value and Json Schema$")
	public void Verify_AED_value_and_Json_Schema() throws Throwable {
		int count = verifyCurrencyCount(postResponse, 162);
		verifyAEDValue(postResponse);
		verifyJsonSchemaAndResponsetime(postResponse);
	}

}
