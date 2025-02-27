package resources;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {

    private static RequestSpecification reqSpec;
    private static final String CONFIG_FILE_PATH = Paths.get("src/test/java/resources/global.properties").toAbsolutePath().toString();
    private static final Object LOCK = new Object(); // Lock for thread safety

    /**
     * Returns a singleton instance of RequestSpecification.
     * Uses Double-Checked Locking for thread safety.
     */
    public static RequestSpecification getRequestSpecification() throws IOException {
        if (reqSpec == null) {
            synchronized (LOCK) {
                if (reqSpec == null) {
                    try (PrintStream log = new PrintStream(new FileOutputStream("logging.txt"))) {
                        reqSpec = new RequestSpecBuilder()
                                .setBaseUri(getGlobalValue("baseUrl"))
                                .addQueryParam("key", "qaclick123")
                                .addFilter(RequestLoggingFilter.logRequestTo(log))
                                .addFilter(ResponseLoggingFilter.logResponseTo(log))
                                .setContentType(ContentType.JSON)
                                .build();
                    }
                }
            }
        }
        return reqSpec;
    }

    /**
     * Retrieves a global property value from the configuration file.
     * Uses try-with-resources to prevent resource leaks.
     */
    public static String getGlobalValue(String key) throws IOException {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(fis);
            String value = properties.getProperty(key);
            if (value == null) {
                throw new IllegalArgumentException("Property '" + key + "' not found in " + CONFIG_FILE_PATH);
            }
            return value;
        } catch (IOException e) {
            throw new IOException("Error loading configuration file: " + CONFIG_FILE_PATH, e);
        }
    }

    /**
     * Extracts a value from a JSON response using JsonPath.
     */
    public static String getJsonPath(Response response, String key) {
        return new JsonPath(response.asString()).getString(key);
    }
}
