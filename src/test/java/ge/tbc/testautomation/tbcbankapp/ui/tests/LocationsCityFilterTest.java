package ge.tbc.testautomation.tbcbankapp.ui.tests;

import ge.tbc.testautomation.tbcbankapp.ui.base.BaseDeviceTest;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.tbcbankapp.ui.data.Constants.CITY_NAME;

@Test(description = "DEV-T5: Filter Available ATM Locations by Selected City")
public class LocationsCityFilterTest extends BaseDeviceTest {

    public LocationsCityFilterTest(String device, String browser) {
        super(device, browser);
    }

    public LocationsCityFilterTest(){
        super();
    }

    @Test(description = "DEV-T5 Step 1: Homepage access", priority = 1)
    public void homepageAccess() {
        homeSteps
                .openHomepage()
                .verifyHomepageLoaded()
                .verifyMenuVisibility()
                .openNavigationMenu();
    }

    @Test(description = "DEV-T5 Step 2: Navigation menu access",
            priority = 2,
            dependsOnMethods = "homepageAccess")
    public void navMenuAccess() {
        homeSteps
                .verifyDropDownMenuVisibility()
                .verifyLocationsOptionVisibility();
    }

    @Test(description = "DEV-T5 Step 3: Locations page selection",
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

    @Test(description = "DEV-T5 Step 4: Bank service point selection (ATM)",
            priority = 4,
            dependsOnMethods = "locationsPageSelection")
    public void servicePointSelection() {
        locationSteps
                .waitForATMServiceButton()
                .clickATMServiceButton()
                .waitForATMTitleToAppear()
                .verifyATMTitleDisplayed()
                .waitForATMListToLoad()
                .verifyATMListIsNotEmpty();
    }

    @Test(description = "DEV-T5 Step 5: City filter selection and verification",
            priority = 5,
            dependsOnMethods = "servicePointSelection")
    public void citySelection() {
        locationSteps
                .waitForCityDropdown()
                .clickCityDropdown()
                .waitForCityOption(CITY_NAME)
                .clickCityOption(CITY_NAME)
                .waitForATMListToUpdate()
                .logATMListCount()
                .verifyATMListIsNotEmpty()
                .verifyCityInGeocodeResults(CITY_NAME)
                .verifyMapIsVisible()
                .verifyMapHasMarkers();
    }
}