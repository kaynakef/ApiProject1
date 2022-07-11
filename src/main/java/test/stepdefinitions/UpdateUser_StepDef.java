package test.stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import test.pojo.UsersPojo;
import test.utils.ConfigReader;
import test.utils.PayloadUtils;

import java.util.Map;

public class UpdateUser_StepDef {
    RequestSpecification requestSpecification;
    Response response;
    int userID;

    @Given("User sets request header and authorization for update")
    public void user_sets_request_header_and_authorization_for_update() {
        requestSpecification= RestAssured.given().accept("application/json").contentType(ContentType.JSON)
                .header("Authorization", ConfigReader.readProperty("auth"));
    }
    @When("User sets request body, sends a Post HTTP request and validates the status for update")
    public void user_sets_request_body_sends_a_post_http_request_and_validates_the_status_for_update(DataTable dataTable) {
        Map<String,String> requestBodyCredentials=dataTable.asMap();
        response = requestSpecification
                .body(PayloadUtils.getCreateUserPayload(requestBodyCredentials.get("Name"), requestBodyCredentials.get("Gender"), PayloadUtils.createEmail(), requestBodyCredentials.get("Status")))
                .when().post().then().statusCode(200).extract().response();

    }
    @Then("User gets the user id from the response body for update")
    public void user_gets_the_user_id_from_the_response_body_for_update() {
        UsersPojo parsedResponse = response.as(UsersPojo.class);
        userID=(int)((((Map<String, Object>)(parsedResponse.getData())).get("id")));
    }
    @Then("User sets user id, sets request body, sends a Put Http request and validates the status")
    public void user_sets_user_id_sets_request_body_sends_a_put_http_request_and_validates_the_status(DataTable dataTable) {
        Map<String,String> requestBodyCredentials=dataTable.asMap();
        response = requestSpecification
                .body(PayloadUtils.getCreateUserPayload(requestBodyCredentials.get("Name"), requestBodyCredentials.get("Gender"), PayloadUtils.createEmail(), requestBodyCredentials.get("Status")))

                .when().put(String.valueOf(userID)).then().statusCode(200).extract().response();


    }
    @Then("User validates the response body for the updated user")
    public void user_validates_the_response_body_for_the_updated_user(DataTable dataTable) {
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
    @Then("User sets request header without authorization for update")
    public void user_sets_request_header_without_authorization_for_update() {
        requestSpecification= RestAssured.given().accept("application/json").contentType(ContentType.JSON);

    }
    @Then("User validates the response body with error {int}")
    public void user_validates_the_response_body_with_error(int code) {
        UsersPojo parsedResponse = response.as(UsersPojo.class);
        Assert.assertEquals(code,parsedResponse.getCode());
            Assert.assertEquals ("Resource not found", ((Map<String, Object>)(parsedResponse.getData())).get("message"));
    }

    @Given("User sets user id {int}, sets request body, sends a Put Http request and validates the status")
    public void user_sets_user_id_sets_request_body_sends_a_put_http_request_and_validates_the_status(Integer userID, DataTable dataTable) {
        Map<String,String> requestBodyCredentials=dataTable.asMap();
        response = requestSpecification
                .body(PayloadUtils.getCreateUserPayload(requestBodyCredentials.get("Name"), requestBodyCredentials.get("Gender"), PayloadUtils.createEmail(), requestBodyCredentials.get("Status")))

                .when().put(String.valueOf(userID)).then().statusCode(200).extract().response();



    }


}
