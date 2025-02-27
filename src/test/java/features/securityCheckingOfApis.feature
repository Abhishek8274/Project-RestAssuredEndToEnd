Feature: Security Testing for Place APIs

@UnauthorizedAccess @Security
Scenario: Verify Unauthorized Access is Blocked
    When user calls "ADD_PLACE" with "POST" http request without authentication
    Then the API call got success with status code 401
    And "error_message" in response body is "Unauthorized request"

@InvalidToken @Security
Scenario: Verify API Request with Invalid Token Fails
    Given Add Place Payload with "SecureHouse" "English" "Safe Street"
    When user calls "ADD_PLACE" with "POST" http request using invalid token
    Then the API call got success with status code 403
    And "error_message" in response body is "Forbidden request"

@SQLInjectionTest @Security
Scenario: Verify API Handles SQL Injection Attacks
    Given Add Place Payload with "'; DROP TABLE places; --" "English" "Hacked Street"
    When user calls "ADD_PLACE" with "POST" http request
    Then the API call got success with status code 400
    And "error_message" in response body is "Invalid input detected"
