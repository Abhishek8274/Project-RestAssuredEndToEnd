package cucumber.Options;


import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
				features = "src/test/java/features",  
				glue = {"stepDefinitions"}, 
				plugin = {
						"pretty",  // Makes console logs readable
						"json:target/jsonReports/cucumber-report.json", // JSON Report for integrations
						"html:target/cucumber-reports.html", // HTML Report for easy viewing
						"junit:target/junitReports/cucumber.xml" // JUnit XML for CI/CD integrations
						},
				monochrome = true, // Ensures proper console output format,
				dryRun = false,
				tags="@DeletePlace"
				)
public class TestRunner {
    // To execute specific test scenarios, uncomment and specify: 
    // tags = "@DeletePlace"
}

