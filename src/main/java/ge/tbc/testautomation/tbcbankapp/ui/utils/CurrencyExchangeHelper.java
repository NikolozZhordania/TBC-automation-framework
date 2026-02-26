package ge.tbc.testautomation.tbcbankapp.ui.utils;

import ge.tbc.testautomation.tbcbankapp.ui.pages.CurrencyExchangePage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.testng.Assert.assertTrue;

/**
 * Helper class for currency exchange calculations and parsing
 */
public class CurrencyExchangeHelper {

    private final CurrencyExchangePage currencyExchangePage;

    public CurrencyExchangeHelper(CurrencyExchangePage currencyExchangePage) {
        this.currencyExchangePage = currencyExchangePage;
    }

    public String getRateText() {
        return currencyExchangePage.currencyRates.textContent();
    }

    public double parseRateFromText(String text) {
        Matcher matcher = Pattern.compile("=\\s*([0-9]+[.,][0-9]+)").matcher(text);
        if (!matcher.find()) {
            throw new AssertionError("Could not parse exchange rate from text: " + text);
        }
        return Double.parseDouble(matcher.group(1).replace(",", "."));
    }

    public double getUiCalculatedRate() {
        double fromValue = Double.parseDouble(
                currencyExchangePage.fromCurrencyInput.inputValue().replace(",", ".")
        );
        double toValue = Double.parseDouble(
                currencyExchangePage.toCurrencyInput.inputValue().replace(",", ".")
        );
        return toValue / fromValue;
    }

    public double getNormalizedRate(double uiCalculatedRate, double currentRate) {
        return Math.abs(uiCalculatedRate - currentRate) < Math.abs(uiCalculatedRate - (1.0 / currentRate))
                ? currentRate
                : 1.0 / currentRate;
    }

    public void assertRateWithinTolerance(double uiCalculatedRate, double normalizedRate) {
        assertTrue(
                Math.abs(uiCalculatedRate - normalizedRate) <= 0.05,
                String.format(
                        "Conversion mismatch! Expected rate: %.4f, UI rate: %.4f",
                        normalizedRate, uiCalculatedRate
                )
        );
    }
}