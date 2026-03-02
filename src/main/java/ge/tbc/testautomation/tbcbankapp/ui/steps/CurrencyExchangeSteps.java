package ge.tbc.testautomation.tbcbankapp.ui.steps;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import ge.tbc.testautomation.tbcbankapp.ui.pages.CurrencyExchangePage;
import ge.tbc.testautomation.tbcbankapp.ui.utils.CurrencyExchangeHelper;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static ge.tbc.testautomation.tbcbankapp.ui.data.constants.Constants.URLs.*;
import static org.hamcrest.Matchers.equalTo;

public class CurrencyExchangeSteps {

    private final Page page;
    private final CurrencyExchangePage currencyExchangePage;
    private final CurrencyExchangeHelper currencyExchangeHelper;
    private double currentRate;
    private boolean isFromGel;

    public CurrencyExchangeSteps(Page page) {
        this.page = page;
        this.currencyExchangePage = new CurrencyExchangePage(page);
        this.currencyExchangeHelper = new CurrencyExchangeHelper(currencyExchangePage);
    }

    @Step("Verify Currency Exchange page URL")
    public CurrencyExchangeSteps verifyCurrencyExchangePageURL() {
        assertThat(page).hasURL(CURRENCY_EXCHANGE_URL);
        return this;
    }

    @Step("Verify Currency Exchange page is opened")
    public CurrencyExchangeSteps verifyCurrencyExchangePageOpened() {
        assertThat(currencyExchangePage.pageHeader).isVisible();
        return this;
    }

    @Step("Open currency dropdown")
    public CurrencyExchangeSteps openCurrencyDropdown() {
        currencyExchangePage.fromCurrencyListButton.first().click();
        return this;
    }

    @Step("Choose currency option: {currency}")
    public CurrencyExchangeSteps chooseCurrencyOption(String currency) {
        Locator currencyOption = page.locator("tbcx-dropdown-popover-item")
                .filter(new Locator.FilterOptions().setHasText(currency));
        assertThat(currencyOption.first()).isVisible();
        currencyOption.first().click();
        return this;
    }

    @Step("Select currency: {currency}")
    public CurrencyExchangeSteps selectCurrency(String currency) {
        return openCurrencyDropdown()
                .chooseCurrencyOption(currency);
    }

    @Step("Verify from currency: {fromCurrency}")
    public CurrencyExchangeSteps verifyFromCurrency(String fromCurrency) {
        assertThat(currencyExchangePage.currencyRates).containsText(fromCurrency);
        isFromGel = fromCurrency.equals("GEL");
        return this;
    }

    @Step("Verify to currency: {toCurrency}")
    public CurrencyExchangeSteps verifyToCurrency(String toCurrency) {
        assertThat(currencyExchangePage.currencyRates).containsText(toCurrency);
        return this;
    }

    @Step("Verify currency conversion direction from {fromCurrency} to {toCurrency}")
    public CurrencyExchangeSteps verifyCurrencyConversionDirection(String fromCurrency, String toCurrency) {
        return verifyFromCurrency(fromCurrency)
                .verifyToCurrency(toCurrency);
    }

    @Step("Enter amount in sell field: {amount}")
    public CurrencyExchangeSteps enterAmountInSellField(String amount) {
        currencyExchangePage.fromCurrencyInput.fill(amount);
        return this;
    }

    @Step("Enter amount in sell field: {amount}")
    public CurrencyExchangeSteps enterAmountInSellField(double amount) {
        return enterAmountInSellField(String.valueOf(amount));
    }

    @Step("Click swap button")
    public CurrencyExchangeSteps clickSwapButton() {
        currencyExchangePage.swapButton.click();
        return this;
    }

    @Step("Fetch current exchange rate")
    public CurrencyExchangeSteps fetchCurrentRate() {
        String text = currencyExchangeHelper.getRateText();
        currentRate = currencyExchangeHelper.parseRateFromText(text);
        return this;
    }

    @Step("Verify buy field amount for input: {firstAmount}")
    public CurrencyExchangeSteps verifyBuyFieldAmount(double firstAmount) {
        double uiCalculatedRate = currencyExchangeHelper.getUiCalculatedRate();
        double normalizedRate = currencyExchangeHelper.getNormalizedRate(uiCalculatedRate, currentRate);
        currencyExchangeHelper.assertRateWithinTolerance(uiCalculatedRate, normalizedRate);
        return this;
    }

    @Step("Get commercial currency section title from UI")
    public String getCommercialSectionTitle() {
        assertThat(currencyExchangePage.pageHeader).isVisible();
        return currencyExchangePage.pageHeader.innerText().trim();
    }

    @Step("Assert UI commercial section title matches API popularCurrencyTitle")
    public CurrencyExchangeSteps assertCommercialSectionTitleMatchesApi(String apiTitle) {
        String uiTitle = getCommercialSectionTitle();
        Allure.addAttachment("API Title", apiTitle);
        Allure.addAttachment("UI Title", uiTitle);
        org.hamcrest.MatcherAssert.assertThat("popularCurrencyTitle mismatch between Pages API and UI",
                uiTitle, equalTo(apiTitle));
        return this;
    }
}