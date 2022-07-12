package test.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import test.utils.ConfigReader;
import test.utils.PayloadUtils;

import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.junit.Assert.assertEquals;

public class CommentUser_StepDef {
    RequestSpecification requestSpecification;
    Response response;
    String userId;
    String postIdString;
    int postIdInt;

    @Given("Comment api endpoint set")
    public void comment_api_endpoint_set() {
        baseURI = ConfigReader.readProperty("baseURI");
        basePath = ConfigReader.readProperty("usersPath");
    }

    @Given("User sets request header and authorization for comment")
    public void user_sets_request_header_and_authorization_for_comment() {
        requestSpecification = given().accept(JSON).contentType(JSON)
                .header("Authorization", ConfigReader.readProperty("auth"));
    }

    @When("User sets request body, sends a Post HTTP request for new User for comments")
    public void user_sets_request_body_sends_a_post_http_request_for_new_user_for_comments(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> requestBody = dataTable.asMap();
        response = requestSpecification
                .body(PayloadUtils.getCreateUserPayload(requestBody.get("name")
                        , requestBody.get("gender"), PayloadUtils.createEmail()
                        , requestBody.get("status")))
                .when().post().then().statusCode(200).extract().response();
        JsonPath deserializedResponse = response.jsonPath();
        userId = deserializedResponse.getString("data.id");

    }


    @When("User sends a post request to update the new user for comments")
    public void user_sends_a_post_request_to_update_the_new_user_for_comments(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> requestBodyToCreatePost = dataTable.asMap();
        response = requestSpecification.body(PayloadUtils.getCreatePostPayload(requestBodyToCreatePost.get("title")
                        , requestBodyToCreatePost.get("body")))
                .when().post("/" + userId + ConfigReader.readProperty("postPath"))
                .then().statusCode(200).extract().response();
        JsonPath parsedResponse = response.jsonPath();
        postIdString = parsedResponse.getString("data.id");
        postIdInt = parsedResponse.getInt("data.id");


    }

    @Then("User validates there are no comments for the user")
    public void user_validates_there_are_no_comments_for_the_user() {
        response = requestSpecification.given()
                .when().get(ConfigReader.readProperty("comment1stPath") +
                        postIdString + ConfigReader.readProperty("commentPath"))
                .then().statusCode(200).extract().response();
        JsonPath parsedResponse = response.jsonPath();
        Assert.assertTrue(parsedResponse.getList("data").isEmpty());

    }

    @When("User validates the response body for comment request")
    public void user_send_a_comment_request_to_update_the_new_user(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> requestCommentBody = dataTable.asMap();
        response = requestSpecification.body(PayloadUtils.getCreateCommentPayload(postIdString
                        , requestCommentBody.get("name"), PayloadUtils.createEmail()
                        , requestCommentBody.get("body")))
                .when().post(ConfigReader.readProperty("comment1stPath") + postIdString
                        + ConfigReader.readProperty("commentPath"))
                .then().statusCode(200).extract().response();
        JsonPath parsedResponse = response.jsonPath();
        assertEquals(requestCommentBody.get("name"), parsedResponse.getString("data.name"));
        assertEquals(requestCommentBody.get("body"), parsedResponse.getString("data.body"));
        assertEquals(postIdInt, parsedResponse.getInt("data.post_id"));


    }

    @Given("User sets request header and authorization for comment {string}")
    public void user_sets_request_header_and_authorization_for_comment(String token) {
        if (token.equals("Correct Authorization")){
            token = ConfigReader.readProperty("auth");
        }
        requestSpecification = given().accept(JSON).contentType(JSON).header("Authorization",token);
    }

    @When("User sets request body {string},{string},{string},{string}, and sends the post to HTTPS request")
    public void user_sets_request_body_and_sends_the_post_to_https_request(String id, String name, String email, String body) {
        response = requestSpecification.body(PayloadUtils.getCreateCommentPayload(id, name, email, body))
                .when().post()
                .then().statusCode(200).extract().response();

    }

    @Then("User validates the response body {string}, {string} messages from HTTP post")
    public void user_validates_the_response_body_messages_from_http_post(String token, String error) {
        JsonPath parsedResponse = response.jsonPath();
        if (token.equals("Correct Authorization")){
            assertEquals(error,parsedResponse.getString("data[0].message"));
        }else {
            assertEquals(error,parsedResponse.getString("data.message"));
        }

    }
}
