package ge.tbc.testautomation.tbcbankapp.ui.tests;

import ge.tbc.testautomation.tbcbankapp.api.steps.CommercialListSteps;
import ge.tbc.testautomation.tbcbankapp.ui.base.BaseDeviceTest;
import io.qameta.allure.*;
import org.testng.annotations.Test;

@Epic("TBC Bank Web Application")
@Feature("Currency Exchange")
public class CommercialCurrencyTitleTest extends BaseDeviceTest {

    public CommercialCurrencyTitleTest(String device, String browser) {
        super(device, browser);
    }

    public CommercialCurrencyTitleTest() {
        super();
    }

    private String apiTitle;

    @Story("API vs UI Data Consistency")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Fetch popularCurrencyTitle from sectionComponents[0].inputs via the Pages API.")
    @Test(description = "Step 1: Fetch popularCurrencyTitle from Pages API", priority = 1)
    public void fetchPopularCurrencyTitleFromApi() {
        apiTitle = new CommercialListSteps()
                .fetchPopularCurrencyTitle()
                .getPopularCurrencyTitle();
    }

    @Story("API vs UI Data Consistency")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Open homepage and navigate to the Currency Exchange page.")
    @Test(description = "Step 2: Access homepage and open navigation menu", priority = 2)
    public void homepageAccess() {
        homeSteps
                .openHomepage()
                .verifyHomepageLoaded()
                .verifyMenuVisibility()
                .openNavigationMenu();
    }

    @Story("API vs UI Data Consistency")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify currency exchange option is visible in the navigation menu.")
    @Test(description = "Step 3: Validate currency exchange option visibility in nav menu", priority = 3)
    public void navMenuAccess() {
        homeSteps
                .verifyCurrencyExchangeOptionVisibility()
                .openCurrencyExchangePage();
    }

    @Story("API vs UI Data Consistency")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify the Currency Exchange page URL and header are correctly displayed.")
    @Test(description = "Step 4: Validate Currency Exchange page URL and header", priority = 4)
    public void currencyExchangePageAccess() {
        currencyExchangeSteps
                .verifyCurrencyExchangePageURL()
                .verifyCurrencyExchangePageOpened();
    }

    @Story("API vs UI Data Consistency")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Grab the commercial currency section title from the UI and assert it matches the Pages API response.")
    @Test(description = "Step 5: Assert UI title matches API popularCurrencyTitle", priority = 5)
    public void assertCommercialTitleMatchesApi() {
        currencyExchangeSteps
                .assertCommercialSectionTitleMatchesApi(apiTitle);
    }
}