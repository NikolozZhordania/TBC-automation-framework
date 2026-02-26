package ge.tbc.testautomation.tbcbankapp.ui.tests;

import ge.tbc.testautomation.tbcbankapp.ui.base.BaseDeviceTest;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.tbcbankapp.ui.data.Constants.*;

@Test(description = "DEV-T1: View Nearest ATM on Map")
public class LocationsTest extends BaseDeviceTest {

    public LocationsTest(String device, String browser) {
        super(device, browser);
    }

    public LocationsTest(){
        super();
    }

    @Test(description = "DEV-T1 Step 1: Homepage access", priority = 1)
    public void homepageAccess() {
        homeSteps
                .openHomepage()
                .verifyHomepageLoaded()
                .verifyMenuVisibility()
                .openNavigationMenu();
    }

    @Test(description = "DEV-T1 Step 2: Navigation menu access",
            priority = 2,
            dependsOnMethods = "homepageAccess")
    public void navMenuAccess() {
        homeSteps
                .verifyDropDownMenuVisibility()
                .verifyLocationsOptionVisibility();
    }

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

    @Test(description = "DEV-T1 Step 7: ATM location validation using Geocoding API",
            priority = 7,
            dependsOnMethods = "atmLocationSelection")
    public void atmLocationValidation() {
        locationSteps
                .verifyCityInGeocodeResults(CITY_NAME)
                .verifyStreetInGeocodeResults(STREET_NAME);
    }
}