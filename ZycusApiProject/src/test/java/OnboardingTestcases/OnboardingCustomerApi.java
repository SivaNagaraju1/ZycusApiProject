package OnboardingTestcases;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import com.google.gson.JsonParser;

import TestBase.Constents;
import TestBase.EndPonts;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class OnboardingCustomerApi {

	RequestSpecification InvalidCustomerRequest;
	Response InvalidCustomerResponce;
	
	RequestSpecification ValidCustomerRequest;
	Response ValidCustomerResponce;

	@When("^Create the PostRequest with Valid Customer$")
	public void create_the_PostRequest_with_Valid_Customer() throws Throwable {

		ValidCustomerRequest = RestAssured.given();
		ValidCustomerRequest.header("Content-Type", "application/json");

		JsonParser parser = new JsonParser();
		Object obj = parser.parse(new FileReader(Constents.ValidCustomerJsonfile));
		String json = obj.toString();

		ValidCustomerResponce =ValidCustomerRequest.when().body(json).post(Constents.BaseURL + EndPonts.End_Point1);

	}

	@When("^Create the PostRequest with InvalidCustomer$")
	public void create_the_PostRequest_with_InvalidCustomer() throws Throwable {
		InvalidCustomerRequest = RestAssured.given();
		InvalidCustomerRequest.header("Content-Type", "application/json");

		JsonParser parser = new JsonParser();
		Object obj = parser.parse(new FileReader(Constents.InvalidCustomerJsonfile));
		String json = obj.toString();

		InvalidCustomerResponce = InvalidCustomerRequest.when().body(json).post(Constents.BaseURL + EndPonts.End_Point1);
	}

	@Then("^Check the Customer Responce$")
	public void check_the_Customer_Responce() throws Throwable {
		
		String validCustomerResponce = ValidCustomerResponce.then().extract().asString();
		System.out.println(validCustomerResponce);
		
		String invalidCustomerResponce = InvalidCustomerResponce.then().extract().asString();
		System.out.println(invalidCustomerResponce);

	}

	@And("^Check Status code$")
	public void check_Status_code() throws Throwable {
		int validCustomerstauscode = ValidCustomerResponce.statusCode();
		Assert.assertEquals(validCustomerstauscode, 200);
		
		int inValidCustomerstauscode = InvalidCustomerResponce.statusCode();
		Assert.assertEquals(inValidCustomerstauscode, 405);

	}

	@Given("^Enter the CustomerId$")
	public void enter_the_CustomerId(DataTable table) throws Throwable {

		List<CustomerId> list = new ArrayList<CustomerId>();
		list = table.asList(CustomerId.class);
		for (CustomerId Customerid : list) {
			RequestSpecification res = RestAssured.given().param("customerId", Customerid.id);
			Response customerResponce = res.when().get(Constents.BaseURL + EndPonts.End_Point1);

			String custResponce = customerResponce.then().extract().asString();
			System.out.println(custResponce);

			int customeridStatuscode = customerResponce.statusCode();
			Assert.assertEquals(customeridStatuscode, 405);
		}
	}

	
	class CustomerId {
		int id;

		public CustomerId(int id) {
			this.id = id;
		}
	}

	class StatusCode {
		int code;

		public StatusCode(int statusCode) {
			this.code = statusCode;
		}
	}

}
