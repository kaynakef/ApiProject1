Feature: Testing the create user functionality

  Scenario: Validating the successful user creation

    Given User get request with correct authorization
    When User pass request body
    Then User validates the response body


  Scenario Outline: Validating the negative test for creating user.

    Given User get request with authorization '<token>'
    When User pass wrong request body '<name>', '<gender>', '<email>', '<status>'
    Then user validates the response body error

    Examples:
    |token| name| gender| email| status|
    |123123123| test | gender | email@gmail.com| inactive|


