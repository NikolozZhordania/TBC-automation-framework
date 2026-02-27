package ge.tbc.testautomation.tbcbankapp.ui.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import ge.tbc.testautomation.tbcbankapp.ui.pages.LocationsPage;
import ge.tbc.testautomation.tbcbankapp.ui.utils.LocationHelper;
import io.qameta.allure.Step;
import org.json.JSONArray;

import static ge.tbc.testautomation.tbcbankapp.ui.data.constants.Constants.URLs.*;
import static ge.tbc.testautomation.tbcbankapp.ui.utils.GeoCodeUtils.addressComponentExists;
import static ge.tbc.testautomation.tbcbankapp.ui.utils.LocatorHelpers.atmOption;
import static ge.tbc.testautomation.tbcbankapp.ui.utils.LocatorHelpers.cityOption;
import static org.testng.Assert.*;

public class LocationSteps {

    private final Page page;
    private final LocationsPage locationsPage;
    private final LocationHelper locationHelper;

    public LocationSteps(Page page) {
        this.page = page;
        this.locationsPage = new LocationsPage(page);
        this.locationHelper = new LocationHelper(page);
    }

    @Step("Wait for Locations page to load")
    public LocationSteps waitForLocationsPageToLoad() {
        locationsPage.pageHeader.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
        return this;
    }

    @Step("Verify Locations page URL")
    public LocationSteps verifyLocationsPageURL() {
        String actualUrl = page.url();
        assertTrue(actualUrl.equals(LOCATIONS_PAGE_URL),
                "Expected URL: " + LOCATIONS_PAGE_URL + ", but got: " + actualUrl);
        return this;
    }

    @Step("Verify page header is visible")
    public LocationSteps verifyPageHeaderIsVisible() {
        assertTrue(locationsPage.pageHeader.isVisible(),
                "Page header is not visible");
        return this;
    }

    @Step("Wait for ATM service button")
    public LocationSteps waitForATMServiceButton() {
        locationsPage.atmServiceButton.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
        return this;
    }

    @Step("Click ATM service button")
    public LocationSteps clickATMServiceButton() {
        locationsPage.atmServiceButton.click();
        return this;
    }

    @Step("Wait for ATM title to appear")
    public LocationSteps waitForATMTitleToAppear() {
        locationsPage.atmTitle.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
        return this;
    }

    @Step("Verify ATM title is displayed")
    public LocationSteps verifyATMTitleDisplayed() {
        waitForATMTitleToAppear();
        String titleText = locationsPage.atmTitle.innerText();
        assertTrue(titleText.contains("ATM"),
                "ATM title is not displayed correctly. Found: " + titleText);
        return this;
    }

    @Step("Wait for city dropdown")
    public LocationSteps waitForCityDropdown() {
        locationsPage.cityDropdown.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
        return this;
    }

    @Step("Click city dropdown")
    public LocationSteps clickCityDropdown() {
        locationsPage.cityDropdown.click();
        return this;
    }

    @Step("Open city dropdown")
    public LocationSteps openCityDropdown() {
        waitForCityDropdown();
        clickCityDropdown();
        return this;
    }

    @Step("Wait for city option: {city}")
    public LocationSteps waitForCityOption(String city) {
        Locator cityOption = cityOption(page, city);
        cityOption.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
        return this;
    }

    @Step("Click city option: {city}")
    public LocationSteps clickCityOption(String city) {
        Locator cityOption = cityOption(page, city);
        cityOption.click();
        return this;
    }

    @Step("Select city: {city}")
    public LocationSteps selectCity(String city) {
        openCityDropdown();
        waitForCityOption(city);
        clickCityOption(city);
        waitForATMListToUpdate();
        return this;
    }

    @Step("Wait for location input field")
    public LocationSteps waitForLocationInput() {
        locationsPage.locationInput.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
        return this;
    }

    @Step("Type in location input: {location}")
    public LocationSteps typeInLocationInput(String location) {
        locationsPage.locationInput.fill(location);
        return this;
    }

    @Step("Wait for ATM list to update")
    public LocationSteps waitForATMListToUpdate() {
        page.waitForTimeout(1500);
        return this;
    }

    @Step("Wait for ATM list to load")
    public LocationSteps waitForATMListToLoad() {
        locationsPage.atmListItems.first().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(15000));
        return this;
    }

    @Step("Verify ATM list is not empty")
    public LocationSteps verifyATMListIsNotEmpty() {
        int count = locationHelper.getATMListCount();
        assertTrue(count > 0,
                "ATM list is empty. Expected at least 1 ATM");
        return this;
    }

    @Step("Verify filtered ATMs contain text: {expectedText}")
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

    @Step("Wait for specific ATM: {atmName}")
    public LocationSteps waitForSpecificATM(String atmName) {
        Locator atmListItem = page.getByText(atmName);
        atmListItem.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(30000));
        return this;
    }

    @Step("Verify ATM list contains: {atmName}")
    public LocationSteps verifyATMListContains(String atmName) {
        waitForSpecificATM(atmName);

        Locator atmListItem = page.getByText(atmName);
        int count = atmListItem.count();

        assertTrue(count > 0,
                "ATM '" + atmName + "' not found in the list");

        return this;
    }

    @Step("Scroll to ATM: {atmName}")
    public LocationSteps scrollToATM(String atmName) {
        Locator atm = atmOption(locationsPage.atmListItems, atmName).first();
        atm.scrollIntoViewIfNeeded();
        return this;
    }

    @Step("Click ATM: {atmName}")
    public LocationSteps clickATM(String atmName) {
        Locator atm = atmOption(locationsPage.atmListItems, atmName).first();
        atm.click();
        return this;
    }

    @Step("Wait for ATM highlight: {atmName}")
    public LocationSteps waitForATMHighlight(String atmName) {
        Locator atmListItem = page.getByText(atmName);
        atmListItem.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
        return this;
    }

    @Step("Verify ATM has active class: {atmName}")
    public LocationSteps verifyATMHasActiveClass(String atmName) {
        String classAttr = locationHelper.getATMHighlightClass(atmName);
        assertNotNull(classAttr, "Class attribute is null for ATM: " + atmName);
        assertTrue(classAttr.contains("active"),
                "ATM '" + atmName + "' does not have 'active' class. Found: " + classAttr);
        return this;
    }

    @Step("Verify ATM point is highlighted: {atmName}")
    public LocationSteps verifyATMPointHighlighted(String atmName) {
        waitForATMHighlight(atmName);
        verifyATMHasActiveClass(atmName);
        return this;
    }

    @Step("Wait for map to update")
    public LocationSteps waitForMapToUpdate() {
        page.waitForTimeout(1000);
        return this;
    }

    @Step("Verify map is visible")
    public LocationSteps verifyMapIsVisible() {
        assertTrue(locationsPage.map.isVisible(),
                "Map is not visible");
        return this;
    }

    @Step("Verify map has markers")
    public LocationSteps verifyMapHasMarkers() {
        int count = locationHelper.getMapMarkerCount();
        assertTrue(count > 0,
                "No markers found on the map");
        return this;
    }

    @Step("Verify street in geocode results: {expectedStreet}")
    public LocationSteps verifyStreetInGeocodeResults(String expectedStreet) {
        JSONArray results = locationHelper.fetchGeocodeResults();
        boolean streetFound = addressComponentExists(results, "route", expectedStreet);

        assertTrue(streetFound,
                "Street '" + expectedStreet + "' was NOT found in geocode results for selected ATM");

        return this;
    }

    @Step("Verify nearest street using API: {expectedStreet}")
    public LocationSteps verifyNearestStreetUsingAPI(String expectedStreet) {
        return verifyStreetInGeocodeResults(expectedStreet);
    }

    @Step("Verify city in geocode results: {expectedCity}")
    public LocationSteps verifyCityInGeocodeResults(String expectedCity) {
        JSONArray results = locationHelper.fetchGeocodeResults();

        boolean cityFound = addressComponentExists(results, "locality", expectedCity)
                || addressComponentExists(results, "administrative_area_level_1", expectedCity);

        assertTrue(cityFound,
                "City '" + expectedCity + "' was NOT found in geocode results for selected ATM");

        return this;
    }

    @Step("Log ATM list count")
    public LocationSteps logATMListCount() {
        int count = locationHelper.getATMListCount();
        System.out.println("ATM list contains " + count + " items");
        return this;
    }

    @Step("Log map marker count")
    public LocationSteps logMapMarkerCount() {
        int count = locationHelper.getMapMarkerCount();
        System.out.println("Map has " + count + " markers");
        return this;
    }

    @Step("Filter by search term if present: {searchTerm}")
    public LocationSteps filterBySearchTermIfPresent(String searchTerm) {
        if (searchTerm == null) return this;
        return waitForLocationInput()
                .typeInLocationInput(searchTerm)
                .waitForATMListToUpdate();
    }

    @Step("Verify ATM list count is at least {expectedMin} for city {city} and search '{searchTerm}'")
    public LocationSteps verifyATMListCountAtLeast(int expectedMin, String city, String searchTerm) {
        int actual = locationHelper.getATMListCount();
        org.testng.Assert.assertTrue(
                actual >= expectedMin,
                String.format("[%s | search='%s'] Expected >= %d ATMs but found %d",
                        city, searchTerm, expectedMin, actual)
        );
        return this;
    }

    @Step("Verify street in geocode results if present: {expectedStreet}")
    public LocationSteps verifyStreetInGeocodeResultsIfPresent(String expectedStreet) {
        if (expectedStreet == null) return this;
        return verifyStreetInGeocodeResults(expectedStreet);
    }
}