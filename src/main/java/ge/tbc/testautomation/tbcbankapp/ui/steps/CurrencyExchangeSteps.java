package ge.tbc.testautomation.tbcbankapp.ui.steps;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import ge.tbc.testautomation.tbcbankapp.ui.pages.CurrencyExchangePage;
import ge.tbc.testautomation.tbcbankapp.ui.utils.CurrencyExchangeHelper;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static ge.tbc.testautomation.tbcbankapp.ui.data.constants.Constants.CURRENCY_EXCHANGE_PAGE_URL;

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


    public CurrencyExchangeSteps verifyCurrencyExchangePageURL() {
        assertThat(page).hasURL(CURRENCY_EXCHANGE_PAGE_URL);
        return this;
    }

    public CurrencyExchangeSteps verifyCurrencyExchangePageOpened() {
        assertThat(currencyExchangePage.pageHeader).isVisible();
        return this;
    }


    public CurrencyExchangeSteps openCurrencyDropdown() {
        currencyExchangePage.fromCurrencyListButton.first().click();
        return this;
    }

    public CurrencyExchangeSteps chooseCurrencyOption(String currency) {
        Locator currencyOption = page.locator("tbcx-dropdown-popover-item")
                .filter(new Locator.FilterOptions().setHasText(currency));
        assertThat(currencyOption.first()).isVisible();
        currencyOption.first().click();
        return this;
    }

    public CurrencyExchangeSteps selectCurrency(String currency) {
        return openCurrencyDropdown()
                .chooseCurrencyOption(currency);
    }


    public CurrencyExchangeSteps verifyFromCurrency(String fromCurrency) {
        assertThat(currencyExchangePage.currencyRates).containsText(fromCurrency);
        isFromGel = fromCurrency.equals("GEL");
        return this;
    }

    public CurrencyExchangeSteps verifyToCurrency(String toCurrency) {
        assertThat(currencyExchangePage.currencyRates).containsText(toCurrency);
        return this;
    }

    public CurrencyExchangeSteps verifyCurrencyConversionDirection(String fromCurrency, String toCurrency) {
        return verifyFromCurrency(fromCurrency)
                .verifyToCurrency(toCurrency);
    }

    public CurrencyExchangeSteps enterAmountInSellField(String amount) {
        currencyExchangePage.fromCurrencyInput.fill(amount);
        return this;
    }

    public CurrencyExchangeSteps enterAmountInSellField(double amount) {
        return enterAmountInSellField(String.valueOf(amount));
    }

    public CurrencyExchangeSteps clickSwapButton() {
        currencyExchangePage.swapButton.click();
        return this;
    }

    public CurrencyExchangeSteps fetchCurrentRate() {
        String text = currencyExchangeHelper.getRateText();
        currentRate = currencyExchangeHelper.parseRateFromText(text);
        return this;
    }

    public CurrencyExchangeSteps verifyBuyFieldAmount(double firstAmount) {
        double uiCalculatedRate = currencyExchangeHelper.getUiCalculatedRate();
        double normalizedRate = currencyExchangeHelper.getNormalizedRate(uiCalculatedRate, currentRate);
        currencyExchangeHelper.assertRateWithinTolerance(uiCalculatedRate, normalizedRate);
        return this;
    }
}