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


import java.util.Map;

public class DeleteUser_StepDef {
    RequestSpecification requestSpecification;
    Response response;
    String userId;



    @Then("User gets the user id from the response body")
    public void user_gets_the_user_id_from_the_response_body() {
        UsersPojo parsedResponse = response.as(UsersPojo.class);
        userId=(String)(((Map<String, Object>)(parsedResponse.getData())).get("id"));

    }

    @When("User sets user id and sends a Delete HTTP request and validates the status")
    public void user_sets_user_id_and_sends_a_delete_http_request_and_validates_the_status() {
        response = requestSpecification
                .when().delete(userId).then().statusCode(200).extract().response();

    }
    @Then("User validates the response body {int} and {string}")
    public void user_validates_the_response_body_and(int code, String message) {
        UsersPojo parsedResponse = response.as(UsersPojo.class);
        Assert.assertEquals(code,parsedResponse.getCode());
       Assert.assertEquals(message, (String)(((Map<String, Object>)(parsedResponse.getData())).get("message")));

    }
}
