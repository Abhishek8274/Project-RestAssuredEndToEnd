package stepDefinitions;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import java.io.IOException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;
import static org.junit.Assert.assertTrue;

public class StepDefinition extends Utils {
    private RequestSpecification request;
    private ResponseSpecification responseSpec;
    private Response response;
    private final TestDataBuild testData = new TestDataBuild();
    private static String placeId; // Private static variable

    // Getter method for placeId
    public static String getPlaceId() {
        return placeId;
    }

    @Given("Add Place Payload with {string} {string} {string}")
    public void addPlacePayload(String name, String language, String address) throws IOException {
        request = given().spec(getRequestSpecification())
                .body(testData.addPlacePayload(name, language, address));
    }

    @When("user calls {string} with {string} http request")
    public void userCallsWithHttpRequest(String resource, String method) {
    	System.out.println("Received resource: " + resource);
        APIResources resourceAPI = APIResources.fromString(resource);
        System.out.println("Calling API: " + resourceAPI.getResource());

        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();

        if (method.equalsIgnoreCase("POST"))
            response = request.when().post(resourceAPI.getResource());
        else if (method.equalsIgnoreCase("GET"))
            response = request.when().get(resourceAPI.getResource());
        else if (method.equalsIgnoreCase("DELETE"))
            response = request.when().delete(resourceAPI.getResource());
        else
            throw new IllegalArgumentException("Invalid HTTP method: " + method);
    }

    @Then("the API call got success with status code {int}")
    public void verifySuccessStatusCode(Integer expectedStatusCode) {
        assertEquals(expectedStatusCode.intValue(), response.getStatusCode());
    }

    @Then("{string} in response body is {string}")
    public void verifyResponseBody(String key, String expectedValue) {
        assertEquals(expectedValue, getJsonPath(response, key));
    }

    @Then("verify place_Id created maps to {string} using {string}")
    public void verifyPlaceIdMapping(String expectedName, String resource) throws IOException {
        placeId = getJsonPath(response, "place_id");

        request = given().spec(getRequestSpecification())
                .queryParam("place_id", placeId);

        userCallsWithHttpRequest(resource, "GET");

        String actualName = getJsonPath(response, "name");
        assertEquals(expectedName, actualName);
    }

    @Given("DeletePlace Payload")
    public void deletePlacePayload() throws IOException {
        request = given().spec(getRequestSpecification())
                .body(testData.deletePlacePayload(placeId));
    }
    
    @Then("API response time should be less than {int} ms")
    public void api_response_time_should_be_less_than_ms(Integer maxResponseTime) {
        long actualResponseTime = response.getTime(); // Get response time in milliseconds
        System.out.println("Actual Response Time: " + actualResponseTime + " ms");
        
        assertTrue("API response time exceeded " + maxResponseTime + " ms!", actualResponseTime < maxResponseTime);
    }
}
