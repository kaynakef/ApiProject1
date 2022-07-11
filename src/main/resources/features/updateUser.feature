Feature: Testing the update user functionality

  Background:
    Given Users api endpoint set

  Scenario: Validating the successful user update
    And User sets request header and authorization for update
#    first we are creating a user to update it
    When User sets request body, sends a Post HTTP request and validates the status for update
      | Name   | test   |
      | Gender | female |
      | Status | active |

    Then User gets the user id from the response body for update
    And User sets user id, sets request body, sends a Put Http request and validates the status
      | Name   | ella     |
      | Gender | male     |
      | Status | inactive |
    And User validates the response body for the updated user
      | expectedName   | ella     |
      | expectedGender | male     |
      | expectedStatus | inactive |

Scenario: Validating the negative testing for user update (No Authorization)
  And User sets request header and authorization for update
#    first we are creating a user to update it
  When User sets request body, sends a Post HTTP request and validates the status for update
    | Name   | test   |
    | Gender | female |
    | Status | active |
  Then User gets the user id from the response body for update
  Then User sets request header without authorization for update
  And User sets user id, sets request body, sends a Put Http request and validates the status
    | Name   | ella     |
    | Gender | male     |
    | Status | inactive |

  And User validates the response body with error 404

  Scenario: Validating the negative testing for user update(Non existing user)
    And User sets request header and authorization for update
    And User sets user id 4014, sets request body, sends a Put Http request and validates the status
      | Name   | ella     |
      | Gender | male     |
      | Status | inactive |
    And User validates the response body with error 404



