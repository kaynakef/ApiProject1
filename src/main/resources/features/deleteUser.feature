Feature: Testing the delete User functionality
  Background:
    Given Users api endpoint set

  Scenario: Validating the successful user deleting
    And User sets request header and authorization
#    first we are creating a user to delete it
    When User sets request body, sends a Post HTTP request and validates the status
      | name   | test   |
      | gender | female |
      | status | active |
#    here i am getting null pointer exception since this before steps are coming from CreateUser_StepDef class
    Then User gets the user id from the response body
    When User sets user id and sends a Delete HTTP request and validates the status
    Then User validates the response body 401 and "Authentication failed"
    Then User sets request header
    Then User sends a Get HTTP request and validates the status
    Then User validates the response body 401 and "Authentication failed"
