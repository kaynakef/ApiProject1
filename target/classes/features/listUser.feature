Feature: Testing the list Users functionality
  Background:
    Given Users api endpoint set

  Scenario: Validating the successful user listing

    And User sets request header
    When User sends a Get HTTP request and validates the status
    Then User validates the list User response body

#    Scenario: Validating the unsuccessful user listing

#   possible negative scenarios can be added:
#  for listing user authorization is not required so can't be tested as negative
#  automatic accept is application/json so can't be tested with other types such as application/xml
#  can't think of any other negative scenarios? maybe changing the path?




