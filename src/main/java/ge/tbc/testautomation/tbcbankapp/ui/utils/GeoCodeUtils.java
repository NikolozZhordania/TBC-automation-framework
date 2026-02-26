package ge.tbc.testautomation.tbcbankapp.ui.utils;

import com.microsoft.playwright.Page;
import ge.tbc.testautomation.tbcbankapp.ui.pages.LocationsPage;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Locale;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class GeoCodeUtils {

    public static JSONArray getGeocodeResults(Page page) {

        LocationsPage locationsPage = new LocationsPage(page);

        assertThat(locationsPage.activeMapMarker.first()).isVisible();

        String position = locationsPage.activeMapMarker.first().getAttribute("position");

        if (position == null)
            throw new RuntimeException("Marker position attribute is null");

        String[] coords = position.split(",");
        if (coords.length != 2)
            throw new RuntimeException("Invalid marker position: " + position);

        double lat = Double.parseDouble(coords[0].trim());
        double lng = Double.parseDouble(coords[1].trim());

        String apiKey = System.getenv("GOOGLE_MAPS_API_KEY");

        if (apiKey == null)
            throw new RuntimeException("GOOGLE_MAPS_API_KEY is not set");

        Response response = RestAssured
                .given()
                .baseUri("https://maps.googleapis.com")
                .queryParam("latlng", String.format(Locale.US, "%f,%f", lat, lng))
                .queryParam("key", apiKey)
                .queryParam("language", "ka")
                .when()
                .get("/maps/api/geocode/json")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JSONObject json = new JSONObject(response.getBody().asString());

        if (!json.getString("status").equals("OK"))
            throw new RuntimeException(
                    "Google Maps API call failed: " +
                            json.optString("error_message")
            );

        return json.getJSONArray("results");
    }

    public static boolean addressComponentExists(
            JSONArray results,
            String componentType,
            String expectedValue) {

        for (int i = 0; i < results.length(); i++) {
            JSONArray components =
                    results.getJSONObject(i).getJSONArray("address_components");

            for (int j = 0; j < components.length(); j++) {
                JSONObject component = components.getJSONObject(j);
                JSONArray types = component.getJSONArray("types");

                if (types.toList().contains(componentType)) {
                    String value = component.getString("long_name");
                    if (value.contains(expectedValue)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}