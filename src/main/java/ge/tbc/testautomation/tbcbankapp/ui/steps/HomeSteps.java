package ge.tbc.testautomation.tbcbankapp.ui.steps;

import com.microsoft.playwright.Page;
import ge.tbc.testautomation.tbcbankapp.ui.pages.HomePage;
import ge.tbc.testautomation.tbcbankapp.ui.pages.ForMeMenu;
import ge.tbc.testautomation.tbcbankapp.ui.utils.DeviceType;
import ge.tbc.testautomation.tbcbankapp.ui.utils.TestContext;
import io.qameta.allure.Step;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static ge.tbc.testautomation.tbcbankapp.ui.data.constants.Constants.URLs.*;

public class HomeSteps {

    private final Page page;
    private final HomePage homePage;
    private final ForMeMenu forMeMenu;

    public HomeSteps(Page page) {
        this.page = page;
        this.homePage = new HomePage(page);
        this.forMeMenu = new ForMeMenu(page);
    }

    @Step("Open homepage")
    public HomeSteps openHomepage() {
        page.navigate(APP);
        return this;
    }

    @Step("Open navigation menu")
    public HomeSteps openNavigationMenu() {
        if (TestContext.getDevice() == DeviceType.MOBILE) {
            homePage.burgerMenu.click();
        } else {
            homePage.forMeButton.click();
        }
        return this;
    }

    @Step("Open Locations page")
    public HomeSteps openLocationsPage() {
        if (TestContext.getDevice() == DeviceType.MOBILE) {
            forMeMenu.locationsButtonMobile.click();
        } else {
            forMeMenu.locationsButton.click();
        }
        return this;
    }

    @Step("Open Currency Exchange page")
    public HomeSteps openCurrencyExchangePage() {
        if (TestContext.getDevice() == DeviceType.MOBILE) {
            forMeMenu.currencyExchangeButtonMobile.click();
        } else {
            forMeMenu.currencyExchangeButton.click();
        }
        return this;
    }

    @Step("Open Money Transfer page")
    public HomeSteps openMoneyTransferPage() {
        if (TestContext.getDevice() == DeviceType.MOBILE) {
            forMeMenu.otherProductsButton.click();
            forMeMenu.moneyTransferButtonMobile.click();
        } else {
            forMeMenu.moneyTransferButton.click();
        }
        return this;
    }

    @Step("Open Consumer Loan page")
    public HomeSteps openConsumerLoanPage() {
        if (TestContext.getDevice() == DeviceType.MOBILE) {
            forMeMenu.loanCategoryButton.click();
            forMeMenu.consumerLoanButtonMobile.click();
        } else {
            forMeMenu.consumerLoanButton.click();
        }
        return this;
    }

    @Step("Verify homepage loaded")
    public HomeSteps verifyHomepageLoaded() {
        if (TestContext.getDevice() == DeviceType.DESKTOP) {
            assertThat(homePage.forMeButton).isVisible();
        } else {
            assertThat(homePage.burgerMenu).isVisible();
        }
        return this;
    }

    @Step("Verify main menu visibility")
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

    @Step("Verify dropdown menu visibility")
    public HomeSteps verifyDropDownMenuVisibility() {
        assertThat(homePage.dropDownMenuContainer).isVisible();
        return this;
    }

    @Step("Verify Locations option visibility")
    public HomeSteps verifyLocationsOptionVisibility() {
        if (TestContext.getDevice() == DeviceType.MOBILE) {
            assertThat(forMeMenu.locationsButtonMobile).isVisible();
        } else {
            assertThat(forMeMenu.locationsButton).isVisible();
        }
        return this;
    }

    @Step("Verify Currency Exchange option visibility")
    public HomeSteps verifyCurrencyExchangeOptionVisibility() {
        if (TestContext.getDevice() == DeviceType.MOBILE) {
            assertThat(forMeMenu.currencyExchangeButtonMobile).isVisible();
        } else {
            assertThat(forMeMenu.currencyExchangeButton).isVisible();
        }
        return this;
    }
}