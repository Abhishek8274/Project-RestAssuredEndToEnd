package resources;

/**
 * Enum representing API resource endpoints.
 */
public enum APIResources {

    ADD_PLACE("/maps/api/place/add/json"),
    GET_PLACE("/maps/api/place/get/json"),
    DELETE_PLACE("/maps/api/place/delete/json");

    private final String resource;

    APIResources(String resource) {
        this.resource = resource;
    }

    public String getResource() {
        return resource;
    }

    /**
     * Fetches the APIResources enum constant using a case-insensitive lookup.
     * 
     * @param name The enum name (case-insensitive).
     * @return The matching APIResources constant.
     * @throws IllegalArgumentException if no match is found.
     */
    public static APIResources fromString(String name) {
        for (APIResources api : APIResources.values()) {
            if (api.name().equalsIgnoreCase(name)) {
                return api;
            }
        }
        throw new IllegalArgumentException("No enum constant found for: " + name);
    }
}
