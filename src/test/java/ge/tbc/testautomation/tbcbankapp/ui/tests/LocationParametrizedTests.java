package ge.tbc.testautomation.tbcbankapp.ui.tests;

import ge.tbc.testautomation.tbcbankapp.ui.base.BaseDeviceTest;
import ge.tbc.testautomation.tbcbankapp.ui.data.db.model.Branch;
import ge.tbc.testautomation.tbcbankapp.ui.data.providers.BranchDataProvider;
import io.qameta.allure.*;
import org.testng.SkipException;
import org.testng.annotations.Test;

@Epic("TBC Bank Web Application")
@Feature("Locations & ATMs")
@Test(description = "Parametrized tests to verify ATM lists, map markers, and geocode accuracy per city")
public class LocationParametrizedTests extends BaseDeviceTest {

    public LocationParametrizedTests(String device, String browser) {
        super(device, browser);
    }

    public LocationParametrizedTests() {
        super();
    }

    private void navigateToAtmSection(String city) {
        homeSteps
                .openHomepage()
                .verifyHomepageLoaded()
                .verifyMenuVisibility()
                .openNavigationMenu()
                .verifyDropDownMenuVisibility()
                .verifyLocationsOptionVisibility()
                .openLocationsPage();

        locationSteps
                .waitForLocationsPageToLoad()
                .waitForATMServiceButton()
                .clickATMServiceButton()
                .waitForATMTitleToAppear()
                .selectCity(city);
    }

    @Story("ATM List Validation")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify that the ATM list count for a given city and search term from the DB meets or exceeds the expected minimum count.")
    @Test(
            dataProvider      = "branchTestCases",
            dataProviderClass = BranchDataProvider.class,
            description       = "ATM list count >= expected_min_count per city/search_term from DB"
    )
    public void atmListCountMeetsExpectationTest(Branch tc) {
        System.out.println("Running: " + tc.getDescription());
        navigateToAtmSection(tc.getCity());

        locationSteps
                .filterBySearchTermIfPresent(tc.getSearchTerm())
                .waitForATMListToLoad()
                .logATMListCount()
                .verifyATMListCountAtLeast(tc.getExpectedMinCount(), tc.getCity(), tc.getSearchTerm());
    }

    @Story("ATM Highlight & Geocode Verification")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify that a named ATM is highlighted on the map and the street in geocode results matches the DB expectation, if provided.")
    @Test(
            dataProvider      = "branchTestCases",
            dataProviderClass = BranchDataProvider.class,
            description       = "Named ATM is highlighted and geocode street matches DB expectation"
    )
    public void namedAtmHighlightAndGeocodeTest(Branch tc) {
        if (tc.getExpectedAtmName() == null) {
            throw new SkipException("Row " + tc.getId() + " — no expected_atm_name");
        }

        navigateToAtmSection(tc.getCity());

        locationSteps
                .waitForATMListToLoad()
                .verifyATMListContains(tc.getExpectedAtmName())
                .scrollToATM(tc.getExpectedAtmName())
                .clickATM(tc.getExpectedAtmName())
                .waitForMapToUpdate()
                .verifyATMPointHighlighted(tc.getExpectedAtmName())
                .verifyStreetInGeocodeResultsIfPresent(tc.getExpectedStreet());
    }

    @Story("Map Marker Validation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that after filtering by city, the map shows at least the expected number of markers corresponding to ATMs.")
    @Test(
            dataProvider      = "branchTestCases",
            dataProviderClass = BranchDataProvider.class,
            description       = "Map marker count >= expected_min_count after city filter"
    )
    public void mapMarkersMatchAtmListTest(Branch tc) {
        navigateToAtmSection(tc.getCity());

        locationSteps
                .waitForATMListToLoad()
                .waitForMapToUpdate()
                .verifyMapIsVisible()
                .logMapMarkerCount()
                .verifyMapHasMarkers();
    }
}