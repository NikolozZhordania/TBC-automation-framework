package ge.tbc.testautomation.tbcbankapp.steps;

import com.microsoft.playwright.Page;
import ge.tbc.testautomation.tbcbankapp.pages.HomePage;
import ge.tbc.testautomation.tbcbankapp.pages.ForMeMenu;
import ge.tbc.testautomation.tbcbankapp.utils.DeviceType;
import ge.tbc.testautomation.tbcbankapp.utils.TestContext;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static ge.tbc.testautomation.tbcbankapp.data.Constants.APP_URL;

public class HomeSteps {

    private final Page page;
    private final HomePage homePage;
    private final ForMeMenu forMeMenu;

    public HomeSteps(Page page) {
        this.page = page;
        this.homePage = new HomePage(page);
        this.forMeMenu = new ForMeMenu(page);
    }

    public HomeSteps openHomepage() {
        page.navigate(APP_URL);
        return this;
    }

    public HomeSteps openNavigationMenu() {
        if (TestContext.getDevice() == DeviceType.MOBILE) {
            homePage.burgerMenu.click();
        } else {
            homePage.forMeButton.click();
        }
        return this;
    }

    public HomeSteps openLocationsPage() {
        if (TestContext.getDevice() == DeviceType.MOBILE) {
            forMeMenu.locationsButtonMobile.click();
        } else {
            forMeMenu.locationsButton.click();
        }
        return this;
    }

    public HomeSteps openCurrencyExchangePage() {
        if (TestContext.getDevice() == DeviceType.MOBILE) {
            forMeMenu.currencyExchangeButtonMobile.click();
        } else {
            forMeMenu.currencyExchangeButton.click();
        }
        return this;
    }

    public HomeSteps openMoneyTransferPage() {
        if (TestContext.getDevice() == DeviceType.MOBILE) {
            forMeMenu.otherProductsButton.click();
            forMeMenu.moneyTransferButtonMobile.click();
        } else {
            forMeMenu.moneyTransferButton.click();
        }
        return this;
    }

    public HomeSteps openConsumerLoanPage() {
        if (TestContext.getDevice() == DeviceType.MOBILE) {
            forMeMenu.loanCategoryButton.click();
            forMeMenu.consumerLoanButtonMobile.click();
        } else {
            forMeMenu.consumerLoanButton.click();
        }
        return this;
    }

    /* ---------- Validations ---------- */

    public HomeSteps verifyHomepageLoaded() {
        if (TestContext.getDevice() == DeviceType.DESKTOP) {
            assertThat(homePage.forMeButton).isVisible();
        } else {
            assertThat(homePage.burgerMenu).isVisible();
        }
        return this;
    }

    public HomeSteps verifyMenuVisibility() {
        if (TestContext.getDevice() == DeviceType.DESKTOP) {
            assertThat(homePage.forMeButton).isVisible();
            assertThat(homePage.forMyBusinessButton).isVisible();
            assertThat(homePage.tbcButton).isVisible();
        } else {
            assertThat(homePage.burgerMenu).isVisible();
        }
        return this;
    }

    public HomeSteps verifyDropDownMenuVisibility() {
        assertThat(homePage.dropDownMenuContainer).isVisible();
        return this;
    }

    public HomeSteps verifyLocationsOptionVisibility() {
        if (TestContext.getDevice() == DeviceType.MOBILE) {
            assertThat(forMeMenu.locationsButtonMobile).isVisible();
        } else {
            assertThat(forMeMenu.locationsButton).isVisible();
        }
        return this;
    }

    public HomeSteps verifyCurrencyExchangeOptionVisibility() {
        if (TestContext.getDevice() == DeviceType.MOBILE) {
            assertThat(forMeMenu.currencyExchangeButtonMobile).isVisible();
        } else {
            assertThat(forMeMenu.currencyExchangeButton).isVisible();
        }
        return this;
    }

}