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
      | token                                                                   | name | gender | email             | status   |
      | 123123123                                                               | test | gender | email@gmail.com   | inactive |
      | Bearer 106b8f21995c73c87f315a314df2a751097151c10820b7bf28bed937c94a191f | Nana | male   | gmail.gmail.com   | active   |
      | slakdjfkljasdfksdf865f64s5df634a3s5d43543543ds5a4f3545sd4f354asd5f      | Nana | male   | gmajkfd@gmail.com | Inactive |


