package ge.tbc.testautomation.tbcbankapp.ui.utils;

import com.microsoft.playwright.Page;
import ge.tbc.testautomation.tbcbankapp.ui.pages.LocationsPage;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Locale;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static ge.tbc.testautomation.tbcbankapp.ui.data.constants.Constants.GeocodeJson.*;
import static ge.tbc.testautomation.tbcbankapp.ui.data.constants.Constants.GoogleMaps.*;
import static ge.tbc.testautomation.tbcbankapp.ui.data.constants.Constants.MarkerAttributes.*;

public class GeoCodeUtils {

    public static JSONArray getGeocodeResults(Page page) {

        LocationsPage locationsPage = new LocationsPage(page);

        assertThat(locationsPage.activeMapMarker.first()).isVisible();

        String position = locationsPage.activeMapMarker.first().getAttribute(POSITION_ATTR);

        if (position == null)
            throw new RuntimeException("Marker position attribute is null");

        String[] coords = position.split(COORD_SEPARATOR);
        if (coords.length != COORD_LENGTH)
            throw new RuntimeException("Invalid marker position: " + position);

        double lat = Double.parseDouble(coords[0].trim());
        double lng = Double.parseDouble(coords[1].trim());

        String apiKey = System.getenv(API_KEY_ENV);

        if (apiKey == null)
            throw new RuntimeException("GOOGLE_MAPS_API_KEY is not set");

        Response response = RestAssured
                .given()
                .baseUri(BASE_URI)
                .queryParam(LATLNG_PARAM, String.format(Locale.US, "%f,%f", lat, lng))
                .queryParam(KEY_PARAM, apiKey)
                .queryParam(LANGUAGE_PARAM, LANGUAGE)
                .when()
                .get(GEOCODE_PATH)
                .then()
                .statusCode(STATUS_CODE)
                .extract()
                .response();

        JSONObject json = new JSONObject(response.getBody().asString());

        if (!json.getString(STATUS).equals(STATUS_OK))
            throw new RuntimeException(
                    "Google Maps API call failed: " +
                            json.optString(ERROR_MESSAGE)
            );

        return json.getJSONArray(RESULTS);
    }

    public static boolean addressComponentExists(
            JSONArray results,
            String componentType,
            String expectedValue) {

        for (int i = 0; i < results.length(); i++) {
            JSONArray components =
                    results.getJSONObject(i).getJSONArray(ADDRESS_COMPONENTS);

            for (int j = 0; j < components.length(); j++) {
                JSONObject component = components.getJSONObject(j);
                JSONArray types = component.getJSONArray(TYPES);

                if (types.toList().contains(componentType)) {
                    String value = component.getString(LONG_NAME);
                    if (value.contains(expectedValue)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}