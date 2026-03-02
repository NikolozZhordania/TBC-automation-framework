package ge.tbc.testautomation.tbcbankapp.ui.tests;

import ge.tbc.testautomation.tbcbankapp.ui.base.BaseDeviceTest;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.tbcbankapp.ui.data.constants.Constants.LocationData.*;

@Epic("TBC Bank Web Application")
@Feature("Locations & ATMs")
@Test(description = "DEV-T1: View Nearest ATM on Map")
public class LocationsTest extends BaseDeviceTest {

    public LocationsTest(String device, String browser) {
        super(device, browser);
    }

    public LocationsTest() {
        super();
    }

    @Story("Homepage Access")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Open the homepage and verify that the main page elements and navigation menu are visible.")
    @Test(description = "DEV-T1 Step 1: Homepage access", priority = 1)
    public void homepageAccess() {
        homeSteps
                .openHomepage()
                .verifyHomepageLoaded()
                .verifyMenuVisibility()
                .openNavigationMenu();
    }

    @Story("Navigation Menu Validation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that the dropdown navigation menu is visible and the Locations option is accessible.")
    @Test(description = "DEV-T1 Step 2: Navigation menu access",
            priority = 2,
            dependsOnMethods = "homepageAccess")
    public void navMenuAccess() {
        homeSteps
                .verifyDropDownMenuVisibility()
                .verifyLocationsOptionVisibility();
    }

    @Story("Locations Page Access")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Open the Locations page and validate the page header and URL are displayed correctly.")
    @Test(description = "DEV-T1 Step 3: Locations page selection",
            priority = 3,
            dependsOnMethods = "navMenuAccess")
    public void locationsPageSelection() {
        homeSteps
                .openLocationsPage();

        locationSteps
                .waitForLocationsPageToLoad()
                .verifyLocationsPageURL()
                .verifyPageHeaderIsVisible();
    }

    @Story("Bank Service Point Selection")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Select the ATM service point option, ensure ATM list loads correctly, and verify it is not empty.")
    @Test(description = "DEV-T1 Step 4: Bank service point selection (ATM)",
            priority = 4,
            dependsOnMethods = "locationsPageSelection")
    public void servicePointSelection() {
        locationSteps
                .waitForATMServiceButton()
                .clickATMServiceButton()
                .waitForATMTitleToAppear()
                .verifyATMTitleDisplayed()
                .waitForATMListToLoad()
                .logATMListCount()
                .verifyATMListIsNotEmpty();
    }

    @Story("Location Input Filtering")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Type in the location input to filter ATMs, verify filtered results and that a specific ATM is displayed in the list.")
    @Test(description = "DEV-T1 Step 5: Location input and filtering",
            priority = 5,
            dependsOnMethods = "servicePointSelection")
    public void locationInput() {
        locationSteps
                .waitForLocationInput()
                .typeInLocationInput(LOCATION_INPUT)
                .waitForATMListToUpdate()
                .logATMListCount()
                .verifyFilteredATMs(EXPECTED_LOCATION_INPUT)
                .waitForSpecificATM(SELECTED_ATM_LOCATION)
                .verifyATMListContains(SELECTED_ATM_LOCATION);
    }

    @Story("ATM Selection from List")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Scroll to the selected ATM, click it, and verify it is highlighted on the map along with visible map markers.")
    @Test(description = "DEV-T1 Step 6: ATM location selection from list",
            priority = 6,
            dependsOnMethods = "locationInput")
    public void atmLocationSelection() {
        locationSteps
                .scrollToATM(SELECTED_ATM_LOCATION)
                .clickATM(SELECTED_ATM_LOCATION)
                .waitForMapToUpdate()
                .logMapMarkerCount()
                .verifyATMPointHighlighted(SELECTED_ATM_LOCATION)
                .verifyMapIsVisible()
                .verifyMapHasMarkers();
    }

    @Story("ATM Location Validation via Geocoding")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Validate that the selected ATM's city and street match expected values using the geocoding API results.")
    @Test(description = "DEV-T1 Step 7: ATM location validation using Geocoding API",
            priority = 7,
            dependsOnMethods = "atmLocationSelection")
    public void atmLocationValidation() {
        locationSteps
                .verifyCityInGeocodeResults(CITY_NAME)
                .verifyStreetInGeocodeResults(STREET_NAME);
    }
}