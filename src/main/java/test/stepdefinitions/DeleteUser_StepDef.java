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


import java.util.List;
import java.util.Map;

public class DeleteUser_StepDef {
    RequestSpecification requestSpecification;
    Response response;
    String userId;

    @Given("User sets request header and authorization for delete")
    public void user_sets_request_header_and_authorization_for_delete() {
        requestSpecification= RestAssured.given().accept("application/json")
                .header("Authorization", ConfigReader.readProperty("auth"));
    }
    @When("User sets request body, sends a Post HTTP request and validates the status for delete")
    public void user_sets_request_body_sends_a_post_http_request_and_validates_the_status_for_delete(DataTable dataTable) {
        Map<String,String> requestBodyCredentials=dataTable.asMap();
        response = requestSpecification
                .body(PayloadUtils.getCreateUserPayload(requestBodyCredentials.get("name"), requestBodyCredentials.get("gender"), PayloadUtils.createEmail(), requestBodyCredentials.get("status")))
                .when().post().then().statusCode(200).extract().response();

    }

    @Then("User gets the user id from the response body")
    public void user_gets_the_user_id_from_the_response_body() {
        UsersPojo parsedResponse = response.as(UsersPojo.class);
        userId=(String)((((List<Map<String, Object>>)(parsedResponse.getData())).get(0)).get("id"));

    }

    @When("User sets user id and sends a Delete HTTP request and validates the status")
    public void user_sets_user_id_and_sends_a_delete_http_request_and_validates_the_status() {
        response = requestSpecification
                .when().delete("https://gorest.co.in/public-api/users/"+userId).then().log().body().statusCode(200).extract().response();

    }
    @Then("User validates the response body {int} and {string}")
    public void user_validates_the_response_body_and(int code, String message) {
        UsersPojo parsedResponse = response.as(UsersPojo.class);
        Assert.assertEquals(code,parsedResponse.getCode());
       Assert.assertEquals(message, (String)(((Map<String, Object>)(parsedResponse.getData())).get("message")));

    }
}
