package ge.tbc.testautomation.tbcbankapp.ui.tests;

import ge.tbc.testautomation.tbcbankapp.ui.base.BaseDeviceTest;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.tbcbankapp.ui.data.constants.Constants.LocationData.*;

@Epic("TBC Bank Web Application")
@Feature("Locations & ATMs")
@Test(description = "DEV-T5: Filter Available ATM Locations by Selected City")
public class LocationsCityFilterTest extends BaseDeviceTest {

    public LocationsCityFilterTest(String device, String browser) {
        super(device, browser);
    }

    public LocationsCityFilterTest(){
        super();
    }

    @Story("Homepage Access")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Access the homepage and verify that the main elements and navigation menu are visible.")
    @Test(description = "DEV-T5 Step 1: Homepage access", priority = 1)
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
    @Test(description = "DEV-T5 Step 2: Navigation menu access",
            priority = 2,
            dependsOnMethods = "homepageAccess")
    public void navMenuAccess() {
        homeSteps
                .verifyDropDownMenuVisibility()
                .verifyLocationsOptionVisibility();
    }

    @Story("Locations Page Access")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Open the Locations page and validate that the page header and URL are correctly displayed.")
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

    @Story("Bank Service Point Selection")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Select the ATM service point option and verify that the ATM list is loaded and not empty.")
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

    @Story("City Filter Selection & Verification")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Select a city from the dropdown filter and validate that the ATM list, geocode results, and map markers match the selected city.")
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