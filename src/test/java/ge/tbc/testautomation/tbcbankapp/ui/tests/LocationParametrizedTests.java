package ge.tbc.testautomation.tbcbankapp.ui.tests;

import ge.tbc.testautomation.tbcbankapp.ui.base.BaseDeviceTest;
import ge.tbc.testautomation.tbcbankapp.ui.data.db.model.Branch;
import ge.tbc.testautomation.tbcbankapp.ui.data.providers.BranchDataProvider;
import ge.tbc.testautomation.tbcbankapp.ui.utils.LocationHelper;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class LocationParametrizedTests extends BaseDeviceTest {

    public LocationParametrizedTests(String device, String browser) {
        super(device, browser);
    }

    public LocationParametrizedTests() {
        super();
    }

    private LocationHelper locationHelper = new LocationHelper(page);

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

    @Test(
            dataProvider      = "atmTestCases",
            dataProviderClass = BranchDataProvider.class,
            description       = "ATM list count >= expected_min_count per city/search_term from DB"
    )
    public void atmListCountMeetsExpectationTest(Branch tc) {
        System.out.println("Running: " + tc.getDescription());
        navigateToAtmSection(tc.getCity());

        if (tc.getSearchTerm() != null) {
            locationSteps
                    .waitForLocationInput()
                    .typeInLocationInput(tc.getSearchTerm())
                    .waitForATMListToUpdate();
        }

        locationSteps
                .waitForATMListToLoad()
                .logATMListCount();

        int actual = locationHelper.getATMListCount();

        org.testng.Assert.assertTrue(
                actual >= tc.getExpectedMinCount(),
                String.format("[%s | search='%s'] Expected >= %d ATMs but found %d",
                        tc.getCity(), tc.getSearchTerm(), tc.getExpectedMinCount(), actual)
        );
    }


    @Test(
            dataProvider      = "atmTestCases",
            dataProviderClass = BranchDataProvider.class,
            description       = "Named ATM is highlighted and geocode street matches DB expectation"
    )
    public void namedAtmHighlightAndGeocodeTest(Branch tc) {
        if (tc.getExpectedAtmName() == null) {
            throw new SkipException(
                    tc.getId() + " — no expected_atm_name"
            );
        }

        navigateToAtmSection(tc.getCity());

        locationSteps
                .waitForATMListToLoad()
                .verifyATMListContains(tc.getExpectedAtmName())
                .scrollToATM(tc.getExpectedAtmName())
                .clickATM(tc.getExpectedAtmName())
                .waitForMapToUpdate()
                .verifyATMPointHighlighted(tc.getExpectedAtmName());

        if (tc.getExpectedStreet() != null) {
            locationSteps.verifyStreetInGeocodeResults(tc.getExpectedStreet());
        }
    }

    @Test(
            dataProvider      = "atmTestCases",
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