Feature: Performance Testing for Place APIs

@Performance @AddPlace
Scenario: Verify Add Place API Responds Within 2 Seconds
    Given Add Place Payload with "SpeedHouse" "English" "Fast Lane"
    When user calls "ADD_PLACE" with "POST" http request
    Then the API call got success with status code 200
    And API response time should be less than 2000 ms

@Performance @GetPlace
Scenario: Verify Get Place API Responds Quickly
    Given Place ID is available
    When user calls "GET_PLACE" with "GET" http request
    Then the API call got success with status code 200
    And API response time should be less than 1500 ms

@Performance @DeletePlace
Scenario: Verify Delete Place API Response Time
    Given DeletePlace Payload
    When user calls "DELETE_PLACE" with "POST" http request
    Then the API call got success with status code 200
    And API response time should be less than 10000 ms
