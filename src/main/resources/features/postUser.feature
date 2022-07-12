Feature: Testing to post user functionality

  Background:
    Given Post api endpoint set

  Scenario: Validating successful user post creation
    And User sets request header and authorization for post
    When User sets request body, sends a Post HTTP request for new User
      | name   | test   |
      | gender | male   |
      | status | active |
    And User sends a post request to update the new user
      | title | test for title |
      | body  | test for body  |
    Then User validates the response body for post request
      | expectedTitle | test for title |
      | expectedBody  | test for body  |

  Scenario Outline: Validating negative test for creating a new post
    And User sets request header and authorization '<token>'
    When User sets request body '<title>' and '<body>', and sends a post HTTP request and validates the status
    Then User validates the response body '<token>', '<errorMsg>' message from HTTP post
    Examples:
      | token                 | title          | body          | errorMsg              |
      | Correct Authorization |                | test for body | can\'t be blank       |
      | Correct Authorization | test for title |               | can\'t be blank       |
      | Correct Authorization |                |               | can\'t be blank       |
      | Incorrect             | test for title | test for body | Authentication failed |
