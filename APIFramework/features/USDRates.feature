@ScenarioUSDRate
Feature: Returns the USD rates against multiple currency.
  Scenario Outline: To Test GET API response returns the USD rates against multiple currency.

    Given User has valid end points <endpoint>
    When User sends the GET request
    Then User gets the response status code as <status code>
    And Verify AED value and Json Schema

    Examples:
      | endpoint          | status code |
      | "/v6/latest/USD"  |   200       |