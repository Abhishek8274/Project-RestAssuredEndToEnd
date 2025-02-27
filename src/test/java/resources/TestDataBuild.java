package resources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import pojo.AddPlace;
import pojo.Location;

public class TestDataBuild {

    private static final int DEFAULT_ACCURACY = 50;
    private static final String DEFAULT_PHONE_NUMBER = "(+91) 983 893 3937";
    private static final String DEFAULT_WEBSITE = "https://rahulshettyacademy.com";
    private static final List<String> DEFAULT_TYPES;

    static {
        List<String> types = new ArrayList<>();
        types.add("shoe park");
        types.add("shop");
        DEFAULT_TYPES = Collections.unmodifiableList(types);
    }

    /**
     * Builds an AddPlace payload with given parameters.
     *
     * @param name     Name of the place
     * @param language Language of the place
     * @param address  Address of the place
     * @return AddPlace object populated with given details
     */
    public AddPlace addPlacePayload(String name, String language, String address) {
        AddPlace place = new AddPlace();
        place.setAccuracy(DEFAULT_ACCURACY);
        place.setAddress(address);
        place.setLanguage(language);
        place.setPhone_number(DEFAULT_PHONE_NUMBER);
        place.setWebsite(DEFAULT_WEBSITE);
        place.setName(name);
        place.setTypes(DEFAULT_TYPES);

        Location location = new Location();
        location.setLat(-38.383494);
        location.setLng(33.427362);
        place.setLocation(location);

        return place;
    }

    /**
     * Constructs a JSON payload for deleting a place.
     *
     * @param placeId Unique identifier of the place
     * @return JSON String formatted with the given placeId
     */
    public String deletePlacePayload(String placeId) {
        return String.format("{\n    \"place_id\": \"%s\"\n}", placeId);
    }
}
