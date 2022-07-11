package test.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Test;
import test.pojo.UsersPojo;
import java.util.List;
import java.util.Map;

public class ListUser_StepDef {
    RequestSpecification requestSpecification;
    Response response;

    @Given("User sets request header")
    public void user_sets_request_header() {
        requestSpecification= RestAssured.given().accept("application/json");
    }
    @When("User sends a Get HTTP request and validates the status")
    public void user_sends_a_get_http_request_and_validates_the_status() {
        response = requestSpecification
                .when().get().then().log().all().statusCode(200).extract().response();
    }
    @Then("User validates the list User response body")
    public void user_validates_the_list_user_response_body() {
        UsersPojo parsedResponse = response.as(UsersPojo.class);
        Assert.assertEquals(200,parsedResponse.getCode());
        Assert.assertFalse(((Map<String, Object>)(((Map<String, Object>)(parsedResponse.getMeta())).get("pagination"))).isEmpty());
        Assert.assertFalse(((List<Map<String, Object>>)(parsedResponse.getData())).isEmpty());

    }


    @Test
    public void Test(){
        System.out.println("hello");
        System.out.println("hello");
        System.out.println("hello");
        System.out.println("hello");
        System.out.println("hello");
        System.out.println("hello");
        System.out.println("hello");
        System.out.println("hello");
    }

}
