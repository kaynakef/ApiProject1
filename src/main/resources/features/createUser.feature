Feature: Testing the create user functionality

  Background:
    Given Users api endpoint set

  Scenario: Validating the successful user creation

    And User sets request header and authorization
    When User sets request body, sends a Post HTTP request and validates the status
      | name   | test   |
      | gender | female |
      | status | active |
    Then User validates the response body
      | expectedName   | test   |
      | expectedGender | female |
      | expectedStatus | active |


  Scenario Outline: Validating the negative test for creating user.

    And User sets request header with authorization '<token>'
    When User sets request body '<name>', '<gender>', '<email>', '<status>' , sends a Post HTTP request and validates the status
    Then user validates the response body error '<token>', '<error>'

    Examples:
      | token                 | name   | gender | email             | status   | error                                  |
      | 123123                | test   | female | email@gmail.com   | inactive | Authentication failed                  |
      | Correct Authorization | test   | male   | gmail.gmail.com   | active   | is invalid                             |
      | Correct Authorization | test   | gender | gmajkfd@gmail.com | Inactive | can\'t be blank, can be male or female |
      | Correct Authorization |        | male   | email@gmail.com   | active   | can\'t be blank                        |
      | Correct Authorization | test   | female |                   | active   | can\'t be blank                        |
      | Correct Authorization | test   | male   | email@gmail.com   |          | can\'t be blank                        |
      | Correct Authorization | 234-0o | female | email@gmail.com   | inactive | has already been taken                 |
      | Correct Authorization | test   | male   | em  ail@gmail.com | active   | is invalid                             |


