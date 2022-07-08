package test.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CreateUser_StepDef {

    @Given("User get request with correct authorization")
    public void user_get_request_with_correct_authorization() {


    }
    @When("User pass request body")
    public void user_pass_request_body() {


    }
    @Then("User validates the response body")
    public void user_validates_the_response_body() {


    }

    @Given("User get request with authorization {string}")
    public void user_get_request_with_authorization_negative_authorization(String string) {

    }
    @When("User pass wrong request body {string}, {string}, {string}, {string}")
    public void user_pass_wrong_request_body(String string, String string2, String string3, String string4) {

    }
    @Then("user validates the response body error")
    public void user_validates_the_response_body_error() {

    }

}
