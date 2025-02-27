package stepDefinitions;

import java.io.IOException;
import io.cucumber.java.Before;

public class Hooks {

    private final StepDefinition stepDefinition = new StepDefinition();

    @Before("@DeletePlace")
    public void beforeScenario() throws IOException {
        // Execute this only if placeId is null
        if (StepDefinition.getPlaceId() == null) {
            System.out.println("Place ID is null. Creating a new place...");

            stepDefinition.addPlacePayload("Shetty", "French", "Asia");
            stepDefinition.userCallsWithHttpRequest("ADD_PLACE", "POST");
            stepDefinition.verifyPlaceIdMapping("Shetty", "GET_PLACE");

            System.out.println("Place ID created successfully!");
        }
    }
}
