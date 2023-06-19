@Regression
@CurrencyAPI
Feature: Testing Exchange Rate API

  @Critical
  Scenario: Retrieve information using valid API key
    Given I have a valid API key
    When I send a GET request to the API endpoint
    Then the response status code should be 200
    And the response body should contains exchange rates for 150 currencies
    And the response contains today's date


  Scenario: Invalid API key provided
    Given I have a invalid API key
    When I send a GET request to the API endpoint
    Then the response status code should be 401
    And the response body should contain the error message "API key not valid"


  Scenario Outline: Get Exchange Rate for Multiple Base Currencies
    Given I have a valid API key
    When I send a GET request to the API endpoint with base currency "<base>"
    Then the response status code should be 200
    And the response body contains exchange rates for "<base>" currency
    Examples:
      | base |
      | USD  |
      | EUR  |
      | GBP  |

  @Critical
  Scenario Outline: Get Exchange Rate for Major Currencies
    Given I have a valid API key
    When I send a GET request to the API endpoint with base currency "USD"
    Then the response status code should be 200
    And the response should contain exchange rates for "<currency>"
    Examples:
      | currency |
      | INR  |
      | EUR  |
      | GBP  |

  Scenario: Invalid base currency provided
    Given I have a valid API key
    When I send a GET request to the API endpoint
    When I send a GET request to the API endpoint with base currency "ABC"
    Then the response status code should be 400
    And the response body should contain the error message "unsupported target currency"

