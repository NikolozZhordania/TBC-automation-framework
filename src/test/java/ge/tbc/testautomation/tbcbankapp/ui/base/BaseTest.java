package ge.tbc.testautomation.tbcbankapp.ui.base;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;
import ge.tbc.testautomation.tbcbankapp.ui.pages.CommonPage;
import ge.tbc.testautomation.tbcbankapp.ui.steps.*;
import ge.tbc.testautomation.tbcbankapp.ui.utils.DeviceType;
import ge.tbc.testautomation.tbcbankapp.ui.utils.TestContext;
import org.testng.annotations.*;

public class BaseTest {

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    protected HomeSteps homeSteps;
    protected LocationSteps locationSteps;
    protected MoneyTransferSteps moneyTransferSteps;
    protected CurrencyExchangeSteps currencyExchangeSteps;
    protected ConsumerLoanOverviewSteps consumerLoanOverviewSteps;
    protected ConsumerLoanSteps consumerLoanSteps;

    @Parameters({"device", "browser"})
    @BeforeClass(alwaysRun = true)
    public void setUp(
            @Optional("mobile") String device,
            @Optional("chromium") String browserType) {

        DeviceType deviceType = device.equalsIgnoreCase("mobile")
                ? DeviceType.MOBILE
                : DeviceType.DESKTOP;
        TestContext.setDevice(deviceType);

        playwright = Playwright.create();

        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                .setHeadless(false);


        switch (browserType.toLowerCase()) {
            case "firefox":
                browser = playwright.firefox().launch(options);
                break;
            case "webkit":
                browser = playwright.webkit().launch(options);
                break;
            case "chromium":
            default:
                browser = playwright.chromium().launch(options);
                break;
        }

        createContextAndPage(deviceType);
        initSteps();
        handleCookies();
    }

    private void createContextAndPage(DeviceType deviceType) {
        Browser.NewContextOptions contextOptions = new Browser.NewContextOptions();

        if (deviceType == DeviceType.MOBILE) {
            contextOptions.setViewportSize(390, 844);
            contextOptions.setUserAgent("Mozilla/5.0 (iPhone; CPU iPhone OS 15_0 like Mac OS X)");
        } else {
            contextOptions.setViewportSize(1920, 1080);
        }

        contextOptions
                .setGeolocation(41.707225, 44.849191)
                .setPermissions(java.util.List.of("geolocation"));

        context = browser.newContext(contextOptions);
        page = context.newPage();
    }

    private void initSteps() {
        homeSteps = new HomeSteps(page);
        consumerLoanOverviewSteps = new ConsumerLoanOverviewSteps(page);
        consumerLoanSteps = new ConsumerLoanSteps(page);
        locationSteps = new LocationSteps(page);
        moneyTransferSteps = new MoneyTransferSteps(page);
        currencyExchangeSteps = new CurrencyExchangeSteps(page);
    }

    private void handleCookies() {
        try {
            CommonPage commonPage = new CommonPage(page);
            Locator cookieButton = commonPage.cookieAcceptButton;

            cookieButton.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(3000));

            cookieButton.click();
            page.waitForTimeout(500);
            System.out.println("Cookie banner accepted");

        } catch (Exception e) {
            System.out.println("No cookie banner to dismiss");
        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (page != null) {
            page.close();
            page = null;
        }
        if (context != null) {
            context.close();
            context = null;
        }
        if (browser != null) {
            browser.close();
            browser = null;
        }
        if (playwright != null) {
            playwright.close();
            playwright = null;
        }
    }
}