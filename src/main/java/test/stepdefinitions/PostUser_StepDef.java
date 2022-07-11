package test.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import test.utils.ConfigReader;
import test.utils.PayloadUtils;

import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;

public class PostUser_StepDef {
    RequestSpecification requestSpecification;
    Response response;
    String userId;

    @Given("Post api endpoint set")
    public void post_api_endpoint_set() {
        baseURI = ConfigReader.readProperty("baseURI");
        basePath = ConfigReader.readProperty("usersPath");

    }

    @Given("User sets request header and authorization for post")
    public void user_sets_request_header_and_authorization_for_post() {
        requestSpecification = given().accept(JSON).contentType(JSON)
                .header("Authorization", ConfigReader.readProperty("auth"));
    }

    @When("User sets request body, sends a Post HTTP request for new User")
    public void user_sets_request_body_sends_a_post_http_request_for_new_user(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> requestBody = dataTable.asMap();
        response = requestSpecification
                .body(PayloadUtils.getCreateUserPayload(requestBody.get("name")
                        ,requestBody.get("gender"),PayloadUtils.createEmail()
                        ,requestBody.get("status")))
                .when().post().then().statusCode(200).extract().response();
        JsonPath deserializedResponse = response.jsonPath();
        userId = deserializedResponse.getString("data.id");

    }

    @When("User sends a post request to update the new user")
    public void user_sends_a_post_request_for_the_update_of_the_new_user(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> requestPostBody = dataTable.asMap();
        response = requestSpecification.body(PayloadUtils.getCreatePostPayload(requestPostBody.get("title")
                        , requestPostBody.get("body")))
                .when().post("/"+userId+ConfigReader.readProperty("postPath"))
                .then().statusCode(200).extract().response();
    }

    @Then("User validates the response body for post request")
    public void user_validates_the_response_body_for_post_request(io.cucumber.datatable.DataTable dataTable) {
        JsonPath deserializedResponse = response.jsonPath();
        Map<String, String> validatePostBody = dataTable.asMap();
        String oldUserId = deserializedResponse.getString("data.user_id");
        String actualTitle = deserializedResponse.getString("data.title");
        String actualBody = deserializedResponse.getString("data.body");
        Assert.assertEquals(validatePostBody.get("expectedTitle"),actualTitle);
        Assert.assertEquals(validatePostBody.get("expectedBody"),actualBody);
        Assert.assertEquals(userId,oldUserId);
    }

    @Given("User sets request header and authorization {string}")
    public void user_sets_request_header_and_authorization(String token) {
        if (token.equals("Correct Authorization")){
            token = ConfigReader.readProperty("auth");
        }
        requestSpecification = given().accept(JSON).contentType(JSON).header("Authorization",token);
    }

    @When("User sets request body {string} and {string}, and sends a post HTTP request and validates the status")
    public void user_sets_request_body_and_and_sends_a_post_http_request_and_validates_the_status(String title, String body) {
        response = requestSpecification.body(PayloadUtils.getCreatePostPayload(title, body))
                .when().post()
                .then().statusCode(200).extract().response();
    }

    @Then("User validates the response body {string}, {string} message from HTTP post")
    public void user_validates_the_response_body_message_from_http_post(String token, String errorMsg) {
        JsonPath parsedResponse = response.jsonPath();
        if (token.equals("Correct Authorization")){
            Assert.assertEquals(errorMsg,parsedResponse.getString("data[0].message"));
        }else {
            Assert.assertEquals(errorMsg,parsedResponse.getString("data.message"));
        }
    }


}
