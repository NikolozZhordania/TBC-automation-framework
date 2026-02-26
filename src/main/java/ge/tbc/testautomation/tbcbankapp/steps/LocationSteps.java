package ge.tbc.testautomation.tbcbankapp.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import ge.tbc.testautomation.tbcbankapp.pages.LocationsPage;
import org.json.JSONArray;

import static ge.tbc.testautomation.tbcbankapp.data.Constants.LOCATIONS_PAGE_URL;
import static ge.tbc.testautomation.tbcbankapp.utils.Utils.addressComponentExists;
import static ge.tbc.testautomation.tbcbankapp.utils.Utils.getGeocodeResults;
import static org.testng.Assert.*;

public class LocationSteps {

    private final Page page;
    private final LocationsPage locationsPage;

    public LocationSteps(Page page) {
        this.page = page;
        this.locationsPage = new LocationsPage(page);
    }

    public LocationSteps waitForLocationsPageToLoad() {
        locationsPage.pageHeader.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
        return this;
    }

    public LocationSteps verifyLocationsPageURL() {
        String actualUrl = page.url();
        assertTrue(actualUrl.equals(LOCATIONS_PAGE_URL),
                "Expected URL: " + LOCATIONS_PAGE_URL + ", but got: " + actualUrl);
        return this;
    }

    public LocationSteps verifyPageHeaderIsVisible() {
        assertTrue(locationsPage.pageHeader.isVisible(),
                "Page header is not visible");
        return this;
    }

    public LocationSteps waitForATMServiceButton() {
        locationsPage.atmServiceButton.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
        return this;
    }

    public LocationSteps clickATMServiceButton() {
        locationsPage.atmServiceButton.click();
        return this;
    }


    public LocationSteps waitForATMTitleToAppear() {
        locationsPage.atmTitle.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
        return this;
    }

    public LocationSteps verifyATMTitleDisplayed() {
        waitForATMTitleToAppear();
        String titleText = locationsPage.atmTitle.innerText();
        assertTrue(titleText.contains("ATM"),
                "ATM title is not displayed correctly. Found: " + titleText);
        return this;
    }

    public LocationSteps waitForCityDropdown() {
        locationsPage.cityDropdown.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
        return this;
    }

    public LocationSteps clickCityDropdown() {
        locationsPage.cityDropdown.click();
        return this;
    }

    public LocationSteps openCityDropdown() {
        waitForCityDropdown();
        clickCityDropdown();
        return this;
    }

    public LocationSteps waitForCityOption(String city) {
        Locator cityOption = locationsPage.cityOption(city);
        cityOption.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
        return this;
    }

    public LocationSteps clickCityOption(String city) {
        Locator cityOption = locationsPage.cityOption(city);
        cityOption.click();
        return this;
    }

    public LocationSteps selectCity(String city) {
        openCityDropdown();
        waitForCityOption(city);
        clickCityOption(city);
        waitForATMListToUpdate();
        return this;
    }

    public LocationSteps waitForLocationInput() {
        locationsPage.locationInput.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
        return this;
    }


    public LocationSteps typeInLocationInput(String location) {
        locationsPage.locationInput.fill(location);
        return this;
    }


    public LocationSteps waitForATMListToUpdate() {
        page.waitForTimeout(1500);
        return this;
    }

    public LocationSteps waitForATMListToLoad() {
        locationsPage.atmListItems.first().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(15000));
        return this;
    }

    public int getATMListCount() {
        return locationsPage.atmListItems.count();
    }

    public LocationSteps verifyATMListIsNotEmpty() {
        int count = getATMListCount();
        assertTrue(count > 0,
                "ATM list is empty. Expected at least 1 ATM");
        return this;
    }


    public LocationSteps verifyFilteredATMs(String expectedText) {
        Locator atmList = locationsPage.atmListItems;
        Locator matchingAtms = atmList.filter(
                new Locator.FilterOptions().setHasText(expectedText)
        );

        int count = matchingAtms.count();
        assertTrue(count > 0,
                "No ATM found containing text: '" + expectedText + "'. Total ATMs: " + atmList.count());

        return this;
    }


    public LocationSteps waitForSpecificATM(String atmName) {
        Locator atmListItem = page.getByText(atmName);
        atmListItem.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(30000));
        return this;
    }

    public LocationSteps verifyATMListContains(String atmName) {
        waitForSpecificATM(atmName);

        Locator atmListItem = page.getByText(atmName);
        int count = atmListItem.count();

        assertTrue(count > 0,
                "ATM '" + atmName + "' not found in the list");

        return this;
    }


    public LocationSteps scrollToATM(String atmName) {
        Locator atm = locationsPage.atmOption(atmName).first();
        atm.scrollIntoViewIfNeeded();
        return this;
    }


    public LocationSteps clickATM(String atmName) {
        Locator atm = locationsPage.atmOption(atmName).first();
        atm.click();
        return this;
    }


    public LocationSteps waitForATMHighlight(String atmName) {
        Locator atmListItem = page.getByText(atmName);
        atmListItem.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
        return this;
    }

    public String getATMHighlightClass(String atmName) {
        Locator atmListItem = page.getByText(atmName);
        Locator innerDiv = atmListItem.locator("xpath=ancestor::div[2]");
        return innerDiv.getAttribute("class");
    }

    public LocationSteps verifyATMHasActiveClass(String atmName) {
        String classAttr = getATMHighlightClass(atmName);
        assertNotNull(classAttr, "Class attribute is null for ATM: " + atmName);
        assertTrue(classAttr.contains("active"),
                "ATM '" + atmName + "' does not have 'active' class. Found: " + classAttr);
        return this;
    }

    public LocationSteps verifyATMPointHighlighted(String atmName) {
        waitForATMHighlight(atmName);
        verifyATMHasActiveClass(atmName);
        return this;
    }


    public LocationSteps waitForMapToUpdate() {
        page.waitForTimeout(1000);
        return this;
    }

    public LocationSteps verifyMapIsVisible() {
        assertTrue(locationsPage.map.isVisible(),
                "Map is not visible");
        return this;
    }

    public int getMapMarkerCount() {
        return locationsPage.activeMapMarker.count();
    }

    public LocationSteps verifyMapHasMarkers() {
        int count = getMapMarkerCount();
        assertTrue(count > 0,
                "No markers found on the map");
        return this;
    }


    public JSONArray fetchGeocodeResults() {
        waitForMapToUpdate();
        return getGeocodeResults(page);
    }

    public LocationSteps verifyStreetInGeocodeResults(String expectedStreet) {
        JSONArray results = fetchGeocodeResults();
        boolean streetFound = addressComponentExists(results, "route", expectedStreet);

        assertTrue(streetFound,
                "Street '" + expectedStreet + "' was NOT found in geocode results for selected ATM");

        return this;
    }

    public LocationSteps verifyNearestStreetUsingAPI(String expectedStreet) {
        return verifyStreetInGeocodeResults(expectedStreet);
    }

    public LocationSteps verifyCityInGeocodeResults(String expectedCity) {
        JSONArray results = fetchGeocodeResults();

        boolean cityFound = addressComponentExists(results, "locality", expectedCity)
                || addressComponentExists(results, "administrative_area_level_1", expectedCity);

        assertTrue(cityFound,
                "City '" + expectedCity + "' was NOT found in geocode results for selected ATM");

        return this;
    }

    public LocationSteps logATMListCount() {
        int count = getATMListCount();
        System.out.println("ATM list contains " + count + " items");
        return this;
    }

    public LocationSteps logMapMarkerCount() {
        int count = getMapMarkerCount();
        System.out.println("Map has " + count + " markers");
        return this;
    }


}