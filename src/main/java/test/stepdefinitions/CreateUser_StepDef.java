package test.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapper;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.Assert;
import test.pojo.UsersPojo;
import test.utils.ConfigReader;
import test.utils.PayloadUtils;

import java.io.IOException;
import java.util.Map;

public class CreateUser_StepDef {
RequestSpecification requestSpecification;
Response response;

    @Given("User get request with correct authorization")
    public void user_get_request_with_correct_authorization() {

    }
    @When("User pass request body")
    public void user_pass_request_body() {
        requestSpecification= RestAssured.given().accept("application/json").contentType("application/json")
                .header("Authorization", ConfigReader.readProperty("auth"));
//        RestAssured.baseURI="https://gorest.co.in/public-api";
//        RestAssured.basePath="users";
          response = requestSpecification.body(PayloadUtils.getCreateUserPayload()).when().post("https://gorest.co.in/public-api/users/").then().log().all().statusCode(200).extract().response();

    }
    @Then("User validates the response body")
    public void user_validates_the_response_body() throws IOException {
        UsersPojo parsedResponse = response.as(UsersPojo.class);

        ResponseBody body = response.getBody();
        String bodyAsString = body.asString();

        String body1 = bodyAsString.replace(":","=").replace("\"","").replace(",",", ");

        Assert.assertEquals(body1.contains(parsedResponse.getData().toString()),true);

//

    }

    @Given("User get request with authorization {string}")
    public void user_get_request_with_authorization_negative_authorization(String token) {
        requestSpecification= RestAssured.given().accept("application/json").contentType("application/json")
                .header("Authorization", token);
    }
    @When("User pass wrong request body {string}, {string}, {string}, {string}")
    public void user_pass_wrong_request_body(String string, String string2, String string3, String string4) {

    }
    @Then("user validates the response body error")
    public void user_validates_the_response_body_error() {

    }

}
