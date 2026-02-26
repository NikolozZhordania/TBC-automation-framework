package ge.tbc.testautomation.tbcbankapp.steps;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import ge.tbc.testautomation.tbcbankapp.pages.CurrencyExchangePage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static ge.tbc.testautomation.tbcbankapp.data.Constants.CURRENCY_EXCHANGE_PAGE_URL;
import static org.testng.Assert.assertTrue;

public class CurrencyExchangeSteps {

    private final Page page;
    private final CurrencyExchangePage currencyExchangePage;
    private double currentRate;
    private boolean isFromGel;

    public CurrencyExchangeSteps(Page page) {
        this.page = page;
        this.currencyExchangePage = new CurrencyExchangePage(page);
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
        String text = getRateText();
        currentRate = parseRateFromText(text);
        return this;
    }

    private String getRateText() {
        String text = currencyExchangePage.currencyRates.textContent();
        return text;
    }

    private double parseRateFromText(String text) {
        Matcher matcher = Pattern.compile("=\\s*([0-9]+[.,][0-9]+)").matcher(text);
        if (!matcher.find()) {
            throw new AssertionError("Could not parse exchange rate from text: " + text);
        }
        return Double.parseDouble(matcher.group(1).replace(",", "."));
    }


    public CurrencyExchangeSteps verifyBuyFieldAmount(double firstAmount) {
        double uiCalculatedRate = getUiCalculatedRate();
        double normalizedRate = getNormalizedRate(uiCalculatedRate);
        assertRateWithinTolerance(uiCalculatedRate, normalizedRate);
        return this;
    }

    private double getUiCalculatedRate() {
        double fromValue = Double.parseDouble(
                currencyExchangePage.fromCurrencyInput.inputValue().replace(",", ".")
        );
        double toValue = Double.parseDouble(
                currencyExchangePage.toCurrencyInput.inputValue().replace(",", ".")
        );
        return toValue / fromValue;
    }

    private double getNormalizedRate(double uiCalculatedRate) {
        return Math.abs(uiCalculatedRate - currentRate) < Math.abs(uiCalculatedRate - (1.0 / currentRate))
                ? currentRate
                : 1.0 / currentRate;
    }

    private void assertRateWithinTolerance(double uiCalculatedRate, double normalizedRate) {
        assertTrue(
                Math.abs(uiCalculatedRate - normalizedRate) <= 0.05,
                String.format(
                        "Conversion mismatch! Expected rate: %.4f, UI rate: %.4f",
                        normalizedRate, uiCalculatedRate
                )
        );
    }
}