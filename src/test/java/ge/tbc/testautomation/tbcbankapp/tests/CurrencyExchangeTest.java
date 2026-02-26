package ge.tbc.testautomation.tbcbankapp.tests;

import ge.tbc.testautomation.tbcbankapp.base.BaseDeviceTest;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.tbcbankapp.data.Constants.*;

@Test(description = "DEV-T2 Verify Currency Conversion Rates")
public class CurrencyExchangeTest extends BaseDeviceTest {

    public CurrencyExchangeTest(String device, String browser) {
        super(device, browser);
    }

    public CurrencyExchangeTest(){
        super();
    }

    @Test(description = "DEV-T2 Step 1: Homepage access", priority = 1)
    public void homepageAccess() {
        homeSteps
                .openHomepage()
                .verifyHomepageLoaded()
                .openNavigationMenu();
    }

    @Test(description = "DEV-T2 Step 2: Navigation menu access", priority = 2)
    public void navMenuAccess() {
        homeSteps
                .verifyDropDownMenuVisibility()
                .verifyCurrencyExchangeOptionVisibility();
    }

    @Test(description = "DEV-T2 Step 3: Currency Exchange page selection", priority = 3)
    public void currencyExchangePageSelection() {
        homeSteps
                .openCurrencyExchangePage();

        currencyExchangeSteps
                .verifyCurrencyExchangePageURL()
                .verifyCurrencyExchangePageOpened();
    }

    @Test(description = "DEV-T2 Step 4: Currency selection (EUR → GEL)", priority = 4)
    public void currencySelection() {
        currencyExchangeSteps
                .openCurrencyDropdown()
                .chooseCurrencyOption(EUR_CURRENCY)
                .verifyFromCurrency(EUR_CURRENCY)
                .verifyToCurrency(GEL_CURRENCY);
    }

    @Test(description = "DEV-T2 Step 5: Currency conversion validation", priority = 5)
    public void currencyConversion() {
        currencyExchangeSteps
                .enterAmountInSellField(SELL_AMOUNT)
                .fetchCurrentRate()
                .verifyBuyFieldAmount(SELL_AMOUNT);
    }

    @Test(description = "DEV-T2 Step 6: Currency conversion swap", priority = 6)
    public void currencyConversionSwap() {
        currencyExchangeSteps
                .clickSwapButton()
                .verifyFromCurrency(GEL_CURRENCY)
                .verifyToCurrency(EUR_CURRENCY);
    }

    @Test(description = "DEV-T2 Step 7: Swapped currency conversion validation", priority = 7)
    public void swappedCurrencyConversionCheck() {
        currencyExchangeSteps
                .enterAmountInSellField(SELL_AMOUNT)
                .fetchCurrentRate()
                .verifyBuyFieldAmount(SELL_AMOUNT);
    }
}