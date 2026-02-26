package ge.tbc.testautomation.tbcbankapp.utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import ge.tbc.testautomation.tbcbankapp.data.Constants;
import ge.tbc.testautomation.tbcbankapp.pages.LocationsPage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Locale;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class Utils {

    public static Locator getButton(Locator desktopButton, Locator mobileButton) {
        return TestContext.getDevice() == DeviceType.DESKTOP
                ? desktopButton
                : mobileButton;
    }

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

        String url = String.format(
                Locale.US,
                "https://maps.googleapis.com/maps/api/geocode/json?latlng=%f,%f&key=%s&language=ka",
                lat, lng, Constants.GOOGLE_MAPS_API_KEY
        );

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject json = new JSONObject(response.body());

            if (!json.getString("status").equals("OK")) {
                throw new RuntimeException(
                        "Google Maps API call failed: " +
                                json.optString("error_message")
                );
            }

            return json.getJSONArray("results");

        } catch (Exception e) {
            throw new RuntimeException("Google Maps API call failed", e);
        }
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