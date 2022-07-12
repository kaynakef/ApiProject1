Feature: Testing adding comment for user functionality

  Background:
    Given Comment api endpoint set

  Scenario: Validating successful user comment creation
    And User sets request header and authorization for comment
    When User sets request body, sends a Post HTTP request for new User for comments
      | name   | test   |
      | gender | male   |
      | status | active |

    And User sends a post request to update the new user for comments
      | title | test for title |
      | body  | test for body  |
    Then User validates there are no comments for the user
    And User validates the response body for comment request
      | name | test      |
      | body | min5lines |

  Scenario Outline: Validating negative test for comment creation
    And User sets request header and authorization for comment '<token>'
    When User sets request body '<id>','<name>','<email>','<body>', and sends the post to HTTPS request
    Then User validates the response body '<token>', "<error>" messages from HTTP post
    Examples:
      | token                 | id   | name  | email             | body           | error                       |
      | Correct Authorization |      | fdsaf | fasdfa@gmail.com  | fklsajklfjkals | can't be blank, can be male or female       |
      | Correct Authorization | 4564 |       | fasdfa@gmail.com  | fklsajklfjkals | can\'t be blank             |
      | Correct Authorization | 4544 | fdsaf |                   | fklsajklfjkals | can't be blank |
      | Correct Authorization | 4544 | fdsaf | @jsfdfkjs.com     | fklsajklfjkals | can't be blank, can be male or female                 |
      | Correct Authorization | 4544 | fdsaf | fsadfjsfdfkjs.com | fklsajklfjkals | can't be blank, can be male or female                  |

