Feature: Validating Error Handling in Place API's

@InvalidAddPlace @NegativeTest
Scenario: Verify Adding Place with Missing Data Fails
    Given Add Place Payload with "" "" ""
    When user calls "ADD_PLACE" with "POST" http request
    Then the API call got success with status code 400
    And "status" in response body is "FAILED"
    And "error_message" in response body is "Invalid request"

@InvalidGetPlace @NegativeTest
Scenario: Verify Retrieving Place with Invalid ID Fails
    Given Place ID is set to "invalid123"
    When user calls "GET_PLACE" with "GET" http request
    Then the API call got success with status code 404
    And "msg" in response body is "Place not found"

@InvalidDeletePlace @NegativeTest
Scenario: Verify Deleting Non-Existent Place Fails
    Given DeletePlace Payload with "fakeID123"
    When user calls "DELETE_PLACE" with "POST" http request
    Then the API call got success with status code 404
    And "msg" in response body is "Place not found"
