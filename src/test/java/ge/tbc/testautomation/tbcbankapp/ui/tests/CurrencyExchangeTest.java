package ge.tbc.testautomation.tbcbankapp.ui.tests;

import ge.tbc.testautomation.tbcbankapp.ui.base.BaseDeviceTest;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.tbcbankapp.ui.data.constants.Constants.CurrencyExchange.*;

@Epic("TBC Bank Web Application")
@Feature("Currency Exchange")
@Test(description = "DEV-T2 Verify currency conversion rates end-to-end flow")
public class CurrencyExchangeTest extends BaseDeviceTest {

    public CurrencyExchangeTest(String device, String browser) {
        super(device, browser);
    }

    public CurrencyExchangeTest(){
        super();
    }

    @Story("Homepage Access")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that the homepage loads correctly, all main UI elements are visible, " +
            "and navigation menu can be opened for currency exchange flow.")
    @Test(description = "Step 1: Access homepage and open navigation menu", priority = 1)
    public void homepageAccess() {
        homeSteps
                .openHomepage()
                .verifyHomepageLoaded()
                .verifyMenuVisibility()
                .openNavigationMenu();
    }

    @Story("Navigation Menu Validation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that the currency exchange option is visible in the navigation menu and dropdown menu is functional.")
    @Test(description = "Step 2: Validate dropdown menu and currency exchange option visibility", priority = 2)
    public void navMenuAccess() {
        homeSteps
                .verifyDropDownMenuVisibility()
                .verifyCurrencyExchangeOptionVisibility();
    }

    @Story("Currency Exchange Page Access")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that the user can navigate to the Currency Exchange page and that the page URL and header are correct.")
    @Test(description = "Step 3: Navigate to Currency Exchange page and validate page URL and header", priority = 3)
    public void currencyExchangePageSelection() {
        homeSteps
                .openCurrencyExchangePage();

        currencyExchangeSteps
                .verifyCurrencyExchangePageURL()
                .verifyCurrencyExchangePageOpened();
    }

    @Story("Currency Selection")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Select the source currency as EUR and target currency as GEL and validate that selection is correct.")
    @Test(description = "Step 4: Select EUR as source currency and GEL as target currency", priority = 4)
    public void currencySelection() {
        currencyExchangeSteps
                .openCurrencyDropdown()
                .chooseCurrencyOption(EUR_CURRENCY)
                .verifyFromCurrency(EUR_CURRENCY)
                .verifyToCurrency(GEL_CURRENCY);
    }

    @Story("Currency Conversion Validation")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Enter an amount in the sell field, fetch the current conversion rate, and validate that the buy field value is correct.")
    @Test(description = "Step 5: Validate currency conversion for EUR → GEL", priority = 5)
    public void currencyConversion() {
        currencyExchangeSteps
                .enterAmountInSellField(SELL_AMOUNT)
                .fetchCurrentRate()
                .verifyBuyFieldAmount(SELL_AMOUNT);
    }

    @Story("Currency Swap Functionality")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Click the swap button to switch source and target currencies and validate that the swap updated the fields correctly.")
    @Test(description = "Step 6: Swap currencies and verify fields updated correctly", priority = 6)
    public void currencyConversionSwap() {
        currencyExchangeSteps
                .clickSwapButton()
                .verifyFromCurrency(GEL_CURRENCY)
                .verifyToCurrency(EUR_CURRENCY);
    }

    @Story("Swapped Currency Conversion Validation")
    @Severity(SeverityLevel.BLOCKER)
    @Description("After swapping currencies, enter the same amount again and validate that the conversion value is correct for GEL → EUR.")
    @Test(description = "Step 7: Validate conversion after currency swap (GEL → EUR)", priority = 7)
    public void swappedCurrencyConversionCheck() {
        currencyExchangeSteps
                .enterAmountInSellField(SELL_AMOUNT)
                .fetchCurrentRate()
                .verifyBuyFieldAmount(SELL_AMOUNT);
    }
}