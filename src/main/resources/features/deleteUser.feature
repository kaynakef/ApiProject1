Feature: Testing the delete User functionality
  Background:
    Given Users api endpoint set

  Scenario: Validating the successful user deleting
    And User sets request header and authorization for delete
#    first we are creating a user to delete it
    When User sets request body, sends a Post HTTP request and validates the status for delete
      | Name   | test   |
      | Gender | female |
      | Status | active |

    Then User gets the user id from the response body
    When User sets user id and sends a Delete HTTP request and validates the status
    Then User validates the response body 204 and "null"
    Then User sets request header
    Then User sends a Get HTTP request with ID and validates the status
    Then User validates the response body 404 and "Resource not found"


    Scenario: Validating negative test for user deleting

      And User sets request header and authorization for delete
      When User sets non-exist userID 4108 and sends a Delete Http request and validates the status
#    if there was a real user, response should be 204 and null
#        since this is negative testing it can not find the user
      Then User validates the response body 404 and "Resource not found"

      Scenario: Validating negative test for user deleting without authorization
        And User sets request header and authorization for delete
#    first we are creating a user to delete it
        When User sets request body, sends a Post HTTP request and validates the status for delete
          | Name   | test   |
          | Gender | female |
          | Status | active |

        Then User gets the user id from the response body
        Then User sets request header without authorization for delete
        When User sets user id and sends a Delete HTTP request and validates the status
#        if there was authorization response should be 204 and null
#        since this is negative testing it can not delete
        Then User validates the response body 404 and "Resource not found"





