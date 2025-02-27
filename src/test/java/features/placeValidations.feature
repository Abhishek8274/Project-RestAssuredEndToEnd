Feature: Validating Place API's

@AddPlace @Regression
Scenario Outline: Verify if Place is Successfully Added using AddPlaceAPI
    Given Add Place Payload with "<name>" "<language>" "<address>"
    When user calls "ADD_PLACE" with "POST" http request
    Then the API call got success with status code 200
    And "status" in response body is "OK"
    And "scope" in response body is "APP"
    And verify place_Id created maps to "<name>" using "GET_PLACE"

Examples:
    | name     | language | address              |
    | AAhouse  | English  | World cross center  |
    | BBhouse  | Spanish  | Sea cross center    |

@DeletePlace @Regression
Scenario: Verify if Delete Place Functionality is Working
    Given DeletePlace Payload
    When user calls "DELETE_PLACE" with "POST" http request
    Then the API call got success with status code 200
    And "status" in response body is "OK"

@GetPlace @Regression
Scenario: Verify if Place Details are Correctly Retrieved
    Given Add Place Payload with "TestHouse" "English" "Test Street"
    When user calls "ADD_PLACE" with "POST" http request
    Then the API call got success with status code 200
    And "status" in response body is "OK"
    And verify place_Id created maps to "TestHouse" using "GET_PLACE"

@DuplicatePlace @Regression
Scenario: Verify Duplicate Place Addition
    Given Add Place Payload with "DuplicateHouse" "English" "Duplicate Street"
    When user calls "ADD_PLACE" with "POST" http request
    Then the API call got success with status code 200
    And "status" in response body is "OK"
    And verify place_Id created maps to "DuplicateHouse" using "GET_PLACE"

@InvalidGetPlace @NegativeTest
Scenario: Verify Getting Place with Invalid Place ID
    Given Add Place Payload with "FakeHouse" "English" "Fake Street"
    When user calls "ADD_PLACE" with "POST" http request
    Then the API call got success with status code 200
    And "status" in response body is "OK"
    And verify place_Id created maps to "FakeHouse" using "GET_PLACE"
    When user calls "GET_PLACE" with "GET" http request
    Then the API call got success with status code 404
    And "msg" in response body is "Place not found"
