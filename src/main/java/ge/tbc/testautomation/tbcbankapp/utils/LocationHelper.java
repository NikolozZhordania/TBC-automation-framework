package ge.tbc.testautomation.tbcbankapp.utils;

import com.microsoft.playwright.Page;
import ge.tbc.testautomation.tbcbankapp.pages.LocationsPage;
import org.json.JSONArray;

import static ge.tbc.testautomation.tbcbankapp.utils.GeoCodeUtils.getGeocodeResults;

public class LocationHelper {

    private final Page page;
    private final LocationsPage locationsPage;

    public LocationHelper(Page page) {
        this.page = page;
        this.locationsPage = new LocationsPage(page);
    }

    public int getATMListCount() {
        return locationsPage.atmListItems.count();
    }

    public int getMapMarkerCount() {
        return locationsPage.activeMapMarker.count();
    }

    public String getATMHighlightClass(String atmName) {
        var atmListItem = page.getByText(atmName);
        var innerDiv = atmListItem.locator("xpath=ancestor::div[2]");
        return innerDiv.getAttribute("class");
    }

    public JSONArray fetchGeocodeResults() {
        page.waitForTimeout(1000);
        return getGeocodeResults(page);
    }
}