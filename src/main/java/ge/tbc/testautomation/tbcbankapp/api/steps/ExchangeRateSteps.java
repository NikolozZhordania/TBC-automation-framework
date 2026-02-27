package ge.tbc.testautomation.tbcbankapp.api.steps;

import ge.tbc.testautomation.tbcbankapp.api.client.ExchangeRateAPI;
import ge.tbc.testautomation.tbcbankapp.api.data.constants.ExchangeRateConstants.*;
import ge.tbc.testautomation.tbcbankapp.api.data.models.response.currencyexchange.ExchangeRateResponse;
import io.restassured.response.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ExchangeRateSteps {

    private final ExchangeRateAPI api = new ExchangeRateAPI();
    private ExchangeRateResponse exchangeRate;
    private Response rawResponse;

    public ExchangeRateSteps fetchExchangeRate(String iso1, String iso2) {
        this.rawResponse = api.getExchangeRate(iso1, iso2);
        this.exchangeRate = rawResponse
                .then()
                .statusCode(200)
                .extract()
                .as(ExchangeRateResponse.class);
        System.out.printf("Fetched %s/%s — buy: %.4f, sell: %.4f%n",
                iso1, iso2, exchangeRate.getBuyRate(), exchangeRate.getSellRate());
        return this;
    }

    public ExchangeRateSteps fetchExchangeRateExpectingError(String iso1, String iso2, int expectedStatusCode) {
        this.rawResponse = api.getExchangeRate(iso1, iso2);
        System.out.printf("Received %d for %s/%s%n", rawResponse.statusCode(), iso1, iso2);
        return this;
    }

    public ExchangeRateSteps fetchWithoutIso1(String iso2) {
        this.rawResponse = api.getExchangeRateWithoutIso1(iso2);
        return this;
    }

    public ExchangeRateSteps fetchWithoutIso2(String iso1) {
        this.rawResponse = api.getExchangeRateWithoutIso2(iso1);
        return this;
    }

    public ExchangeRateSteps fetchWithNoParams() {
        this.rawResponse = api.getExchangeRateNoParams();
        return this;
    }

    public ExchangeRateSteps validateStatusCode(int expectedCode) {
        assertThat("Status code", rawResponse.statusCode(), equalTo(expectedCode));
        return this;
    }

    public ExchangeRateSteps validateContentTypeIsJson() {
        assertThat("Content-Type", rawResponse.contentType(), containsString("application/json"));
        return this;
    }

    public ExchangeRateSteps validateResponseTimeIsUnder(long maxMillis) {
        assertThat(
                String.format("Response time %dms exceeds limit of %dms", rawResponse.getTime(), maxMillis),
                rawResponse.getTime(), lessThan(maxMillis)
        );
        return this;
    }

    public ExchangeRateSteps validateIso1IsPresent() {
        assertThat("iso1 must not be null", exchangeRate.getIso1(), notNullValue());
        return this;
    }

    public ExchangeRateSteps validateIso2IsPresent() {
        assertThat("iso2 must not be null", exchangeRate.getIso2(), notNullValue());
        return this;
    }

    public ExchangeRateSteps validateBuyRateIsPresent() {
        assertThat("buyRate must not be null", exchangeRate.getBuyRate(), notNullValue());
        return this;
    }

    public ExchangeRateSteps validateSellRateIsPresent() {
        assertThat("sellRate must not be null", exchangeRate.getSellRate(), notNullValue());
        return this;
    }

    public ExchangeRateSteps validateConversionTypeIsPresent() {
        assertThat("conversionType must not be null", exchangeRate.getConversionType(), notNullValue());
        return this;
    }

    public ExchangeRateSteps validateCurrencyWeightIsPresent() {
        assertThat("currencyWeight must not be null", exchangeRate.getCurrencyWeight(), notNullValue());
        return this;
    }

    public ExchangeRateSteps validateUpdateDateIsPresent() {
        assertThat("updateDate must not be null", exchangeRate.getUpdateDate(), notNullValue());
        return this;
    }

    // Convenience composite — all fields present in one call
    public ExchangeRateSteps validateAllFieldsArePresent() {
        return validateIso1IsPresent()
                .validateIso2IsPresent()
                .validateBuyRateIsPresent()
                .validateSellRateIsPresent()
                .validateConversionTypeIsPresent()
                .validateCurrencyWeightIsPresent()
                .validateUpdateDateIsPresent();
    }

    public ExchangeRateSteps validateIso1Equals(String expected) {
        assertThat("iso1", exchangeRate.getIso1(), equalToIgnoringCase(expected));
        return this;
    }

    public ExchangeRateSteps validateIso2Equals(String expected) {
        assertThat("iso2", exchangeRate.getIso2(), equalToIgnoringCase(expected));
        return this;
    }

    public ExchangeRateSteps validateBuyRateIsPositive() {
        assertThat("buyRate must be positive", exchangeRate.getBuyRate(), greaterThan(0.0));
        return this;
    }

    public ExchangeRateSteps validateSellRateIsPositive() {
        assertThat("sellRate must be positive", exchangeRate.getSellRate(), greaterThan(0.0));
        return this;
    }

    public ExchangeRateSteps validateSellRateIsGreaterThanBuyRate() {
        assertThat("sellRate must exceed buyRate (bank spread)",
                exchangeRate.getSellRate(), greaterThan(exchangeRate.getBuyRate()));
        return this;
    }

    public ExchangeRateSteps validateBuyRateIsWithinReasonableRange() {
        assertThat("buyRate below floor", exchangeRate.getBuyRate(),
                greaterThanOrEqualTo(ExpectedValues.USD_GEL_BUY_RATE_MIN));
        assertThat("buyRate above ceiling", exchangeRate.getBuyRate(),
                lessThanOrEqualTo(ExpectedValues.USD_GEL_BUY_RATE_MAX));
        return this;
    }

    public ExchangeRateSteps validateSellRateIsWithinReasonableRange() {
        assertThat("sellRate below floor", exchangeRate.getSellRate(),
                greaterThanOrEqualTo(ExpectedValues.USD_GEL_SELL_RATE_MIN));
        assertThat("sellRate above ceiling", exchangeRate.getSellRate(),
                lessThanOrEqualTo(ExpectedValues.USD_GEL_SELL_RATE_MAX));
        return this;
    }

    public ExchangeRateSteps validateConversionType() {
        assertThat("conversionType", exchangeRate.getConversionType(),
                equalTo(ExpectedValues.EXPECTED_CONVERSION_TYPE));
        return this;
    }

    public ExchangeRateSteps validateCurrencyWeight() {
        assertThat("currencyWeight", exchangeRate.getCurrencyWeight(),
                equalTo(ExpectedValues.EXPECTED_CURRENCY_WEIGHT));
        return this;
    }

    public ExchangeRateSteps validateUpdateDateIsNotEmpty() {
        assertThat("updateDate must not be empty", exchangeRate.getUpdateDate(), not(emptyString()));
        return this;
    }

    public ExchangeRateSteps validateUpdateDateFormat() {
        assertThat("updateDate ISO 8601 format",
                exchangeRate.getUpdateDate(),
                matchesPattern("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.*"));
        return this;
    }
}