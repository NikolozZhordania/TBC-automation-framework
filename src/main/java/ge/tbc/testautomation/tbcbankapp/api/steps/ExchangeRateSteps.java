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
        System.out.printf("Fetched exchange rate: %s/%s - Buy: %.4f, Sell: %.4f%n",
                iso1, iso2, exchangeRate.getBuyRate(), exchangeRate.getSellRate());
        return this;
    }

    public ExchangeRateSteps fetchExchangeRateExpectingError(String iso1, String iso2, int expectedStatusCode) {
        this.rawResponse = api.getExchangeRate(iso1, iso2);
        rawResponse.then().statusCode(expectedStatusCode);
        System.out.printf("Received expected status %d for %s/%s%n", expectedStatusCode, iso1, iso2);
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
        assertThat("Status code mismatch", rawResponse.statusCode(), equalTo(expectedCode));
        return this;
    }

    public ExchangeRateSteps validateContentTypeIsJson() {
        assertThat("Content-Type should be JSON",
                rawResponse.contentType(), containsString("application/json"));
        return this;
    }

    public ExchangeRateSteps validateResponseFieldsAreNotNull() {
        assertThat("iso1 must not be null", exchangeRate.getIso1(), notNullValue());
        assertThat("iso2 must not be null", exchangeRate.getIso2(), notNullValue());
        assertThat("buyRate must not be null", exchangeRate.getBuyRate(), notNullValue());
        assertThat("sellRate must not be null", exchangeRate.getSellRate(), notNullValue());
        assertThat("conversionType must not be null", exchangeRate.getConversionType(), notNullValue());
        assertThat("currencyWeight must not be null", exchangeRate.getCurrencyWeight(), notNullValue());
        assertThat("updateDate must not be null", exchangeRate.getUpdateDate(), notNullValue());
        return this;
    }

    public ExchangeRateSteps validateIsoCurrencyCodes(String expectedIso1, String expectedIso2) {
        assertThat("iso1 should match requested currency", exchangeRate.getIso1(), equalToIgnoringCase(expectedIso1));
        assertThat("iso2 should match requested currency", exchangeRate.getIso2(), equalToIgnoringCase(expectedIso2));
        return this;
    }

    public ExchangeRateSteps validateIso1IsUSD() {
        assertThat("iso1 should be USD", exchangeRate.getIso1(), equalTo(CurrencyPairs.USD));
        return this;
    }

    public ExchangeRateSteps validateIso2IsGEL() {
        assertThat("iso2 should be GEL", exchangeRate.getIso2(), equalTo(CurrencyPairs.GEL));
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
        assertThat("sellRate must be greater than buyRate (bank margin)",
                exchangeRate.getSellRate(), greaterThan(exchangeRate.getBuyRate()));
        return this;
    }

    public ExchangeRateSteps validateBuyRateIsWithinReasonableRange() {
        assertThat("buyRate should be within reasonable bounds",
                exchangeRate.getBuyRate(), greaterThanOrEqualTo(ExpectedValues.USD_GEL_BUY_RATE_MIN));
        assertThat("buyRate should not exceed reasonable ceiling",
                exchangeRate.getBuyRate(), lessThanOrEqualTo(ExpectedValues.USD_GEL_BUY_RATE_MAX));
        return this;
    }

    public ExchangeRateSteps validateSellRateIsWithinReasonableRange() {
        assertThat("sellRate should be within reasonable bounds",
                exchangeRate.getSellRate(), greaterThanOrEqualTo(ExpectedValues.USD_GEL_SELL_RATE_MIN));
        assertThat("sellRate should not exceed reasonable ceiling",
                exchangeRate.getSellRate(), lessThanOrEqualTo(ExpectedValues.USD_GEL_SELL_RATE_MAX));
        return this;
    }

    public ExchangeRateSteps validateConversionType() {
        assertThat("conversionType should be 2",
                exchangeRate.getConversionType(), equalTo(ExpectedValues.EXPECTED_CONVERSION_TYPE));
        return this;
    }

    public ExchangeRateSteps validateCurrencyWeight() {
        assertThat("currencyWeight should be 1.0",
                exchangeRate.getCurrencyWeight(), equalTo(ExpectedValues.EXPECTED_CURRENCY_WEIGHT));
        return this;
    }

    public ExchangeRateSteps validateUpdateDateIsNotEmpty() {
        assertThat("updateDate must not be empty", exchangeRate.getUpdateDate(), not(emptyString()));
        return this;
    }

    public ExchangeRateSteps validateUpdateDateFormat() {
        // Validates ISO 8601 datetime format
        assertThat("updateDate should match ISO 8601 format",
                exchangeRate.getUpdateDate(), matchesPattern("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.*"));
        return this;
    }

    public ExchangeRateSteps validateResponseTimeIsUnder(long maxMillis) {
        long responseTime = rawResponse.getTime();
        assertThat(String.format("Response time %d ms should be under %d ms", responseTime, maxMillis),
                responseTime, lessThan(maxMillis));
        return this;
    }
}