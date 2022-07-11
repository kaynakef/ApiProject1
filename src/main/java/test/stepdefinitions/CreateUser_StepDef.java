package test.stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import test.pojo.UsersPojo;
import test.utils.ConfigReader;
import test.utils.PayloadUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CreateUser_StepDef {
RequestSpecification requestSpecification;
Response response;

    @Given("Users api endpoint set")
    public void users_api_endpoint_set() {
        RestAssured.baseURI=ConfigReader.readProperty("baseURI");
        RestAssured.basePath=ConfigReader.readProperty("usersPath");
    }
    @Given("User sets request header and authorization")
    public void user_sets_request_header_and_authorization() {
        requestSpecification= RestAssured.given().accept("application/json").contentType("application/json")
                .header("Authorization", ConfigReader.readProperty("auth"));
    }
    @When("User sets request body, sends a Post HTTP request and validates the status")
    public void user_sets_request_body_sends_a_post_http_request_and_validates_the_status(DataTable dataTable) {
        Map<String,String> requestBodyCredentials=dataTable.asMap();
        response = requestSpecification
                .body(PayloadUtils.getCreateUserPayload(requestBodyCredentials.get("name"), requestBodyCredentials.get("gender"), PayloadUtils.createEmail(), requestBodyCredentials.get("status")))
                .when().post().then().statusCode(200).extract().response();

    }

    @Then("User validates the response body")
    public void user_validates_the_response_body(DataTable dataTable) throws IOException {
        UsersPojo parsedResponse = response.as(UsersPojo.class);
        Map<String,String> expectedResponseBody=dataTable.asMap();

        Assert.assertTrue(((Map<String, Object>)parsedResponse.getData()).containsKey("id"));
        Assert.assertTrue(((Map<String, Object>)parsedResponse.getData()).containsKey("name"));
        Assert.assertEquals(expectedResponseBody.get("expectedName"), ((String) ((Map<String, Object>) parsedResponse.getData()).get("name")));
        Assert.assertTrue(((Map<String, Object>)parsedResponse.getData()).containsKey("email"));
        Assert.assertTrue(((Map<String, Object>)parsedResponse.getData()).containsKey("gender"));
        Assert.assertEquals(expectedResponseBody.get("expectedGender"), ((String) ((Map<String, Object>) parsedResponse.getData()).get("gender")));
        Assert.assertTrue(((Map<String, Object>)parsedResponse.getData()).containsKey("status"));
        Assert.assertEquals(expectedResponseBody.get("expectedStatus"), ((String) ((Map<String, Object>) parsedResponse.getData()).get("status")));


    }

    @Given("User sets request header with authorization {string}")
    public void user_sets_request_header_with_authorization(String authorization) {

        if(authorization.equals("Correct Authorization")){
            authorization= ConfigReader.readProperty("auth");}
        requestSpecification= RestAssured.given().accept("application/json").contentType("application/json")
                .header("Authorization", authorization);
    }
    @When("User sets request body {string}, {string}, {string}, {string} , sends a Post HTTP request and validates the status")
    public void user_sets_request_body_sends_a_post_http_request_and_validates_the_status(String name, String gender, String email, String status) {
        response = requestSpecification.body(PayloadUtils.getCreateUserPayload(name,gender,email,status)).when().post().then().statusCode(200).extract().response();

    }

    @Then("user validates the response body error {string}, {string}")
    public void user_validates_the_response_body_error(String authorization, String error) {
        UsersPojo parsedResponse = response.as(UsersPojo.class);

        if (authorization.equals("Correct Authorization")){
            Assert.assertEquals(error,((String)(((List<Map<String, Object>>)(parsedResponse.getData())).get(0)).get("message")));
        }else{
            Assert.assertEquals(error, ((String) ((Map<String, Object>) parsedResponse.getData()).get("message")));
        }



    }



}
