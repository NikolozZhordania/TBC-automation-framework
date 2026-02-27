package ge.tbc.testautomation.tbcbankapp.api.steps;

import ge.tbc.testautomation.tbcbankapp.api.client.ExchangeRateAPI;
import ge.tbc.testautomation.tbcbankapp.api.data.constants.ExchangeRateConstants.*;
import ge.tbc.testautomation.tbcbankapp.api.data.models.response.currencyexchange.ExchangeRateResponse;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ExchangeRateSteps {

    private final ExchangeRateAPI api = new ExchangeRateAPI();
    private ExchangeRateResponse exchangeRate;
    private Response rawResponse;

    @Step("Fetch exchange rate from {iso1} to {iso2}")
    public ExchangeRateSteps fetchExchangeRate(String iso1, String iso2) {
        Allure.addAttachment("Request Params", "iso1=" + iso1 + ", iso2=" + iso2);
        this.rawResponse = api.getExchangeRate(iso1, iso2);
        Allure.addAttachment("Raw Response", rawResponse.asString());

        this.exchangeRate = rawResponse
                .then()
                .statusCode(200)
                .extract()
                .as(ExchangeRateResponse.class);

        Allure.addAttachment("Parsed Response",
                String.format("iso1: %s\niso2: %s\nbuyRate: %.4f\nsellRate: %.4f",
                        exchangeRate.getIso1(),
                        exchangeRate.getIso2(),
                        exchangeRate.getBuyRate(),
                        exchangeRate.getSellRate()));

        return this;
    }

    @Step("Fetch exchange rate expecting error for {iso1}/{iso2}")
    public ExchangeRateSteps fetchExchangeRateExpectingError(String iso1, String iso2, int expectedStatusCode) {
        Allure.addAttachment("Request Params", "iso1=" + iso1 + ", iso2=" + iso2);
        this.rawResponse = api.getExchangeRate(iso1, iso2);
        Allure.addAttachment("Raw Response", rawResponse.asString());
        System.out.printf("Received %d for %s/%s%n", rawResponse.statusCode(), iso1, iso2);
        return this;
    }

    @Step("Fetch exchange rate without iso1 parameter (iso2={iso2})")
    public ExchangeRateSteps fetchWithoutIso1(String iso2) {
        this.rawResponse = api.getExchangeRateWithoutIso1(iso2);
        Allure.addAttachment("Raw Response", rawResponse.asString());
        return this;
    }

    @Step("Fetch exchange rate without iso2 parameter (iso1={iso1})")
    public ExchangeRateSteps fetchWithoutIso2(String iso1) {
        this.rawResponse = api.getExchangeRateWithoutIso2(iso1);
        Allure.addAttachment("Raw Response", rawResponse.asString());
        return this;
    }

    @Step("Fetch exchange rate with no parameters")
    public ExchangeRateSteps fetchWithNoParams() {
        this.rawResponse = api.getExchangeRateNoParams();
        Allure.addAttachment("Raw Response", rawResponse.asString());
        return this;
    }

    @Step("Validate status code is {expectedCode}")
    public ExchangeRateSteps validateStatusCode(int expectedCode) {
        assertThat("Status code", rawResponse.statusCode(), equalTo(expectedCode));
        return this;
    }

    @Step("Validate Content-Type contains 'application/json'")
    public ExchangeRateSteps validateContentTypeIsJson() {
        assertThat("Content-Type", rawResponse.contentType(), containsString("application/json"));
        return this;
    }

    @Step("Validate response time is under {maxMillis} ms")
    public ExchangeRateSteps validateResponseTimeIsUnder(long maxMillis) {
        assertThat(
                String.format("Response time %dms exceeds limit of %dms", rawResponse.getTime(), maxMillis),
                rawResponse.getTime(), lessThan(maxMillis)
        );
        return this;
    }

    @Step("Validate all mandatory fields are present")
    public ExchangeRateSteps validateAllFieldsArePresent() {
        return validateIso1IsPresent()
                .validateIso2IsPresent()
                .validateBuyRateIsPresent()
                .validateSellRateIsPresent()
                .validateConversionTypeIsPresent()
                .validateCurrencyWeightIsPresent()
                .validateUpdateDateIsPresent();
    }

    @Step("Validate iso1 equals '{expected}'")
    public ExchangeRateSteps validateIso1Equals(String expected) {
        assertThat("iso1", exchangeRate.getIso1(), equalToIgnoringCase(expected));
        return this;
    }

    @Step("Validate iso2 equals '{expected}'")
    public ExchangeRateSteps validateIso2Equals(String expected) {
        assertThat("iso2", exchangeRate.getIso2(), equalToIgnoringCase(expected));
        return this;
    }

    @Step("Validate buyRate is positive")
    public ExchangeRateSteps validateBuyRateIsPositive() {
        assertThat("buyRate must be positive", exchangeRate.getBuyRate(), greaterThan(0.0));
        return this;
    }

    @Step("Validate sellRate is positive")
    public ExchangeRateSteps validateSellRateIsPositive() {
        assertThat("sellRate must be positive", exchangeRate.getSellRate(), greaterThan(0.0));
        return this;
    }

    @Step("Validate sellRate > buyRate (bank spread)")
    public ExchangeRateSteps validateSellRateIsGreaterThanBuyRate() {
        assertThat("sellRate must exceed buyRate", exchangeRate.getSellRate(), greaterThan(exchangeRate.getBuyRate()));
        return this;
    }

    @Step("Validate buyRate is within realistic range")
    public ExchangeRateSteps validateBuyRateIsWithinReasonableRange() {
        assertThat("buyRate below floor", exchangeRate.getBuyRate(),
                greaterThanOrEqualTo(ExpectedValues.USD_GEL_BUY_RATE_MIN));
        assertThat("buyRate above ceiling", exchangeRate.getBuyRate(),
                lessThanOrEqualTo(ExpectedValues.USD_GEL_BUY_RATE_MAX));
        return this;
    }

    @Step("Validate sellRate is within realistic range")
    public ExchangeRateSteps validateSellRateIsWithinReasonableRange() {
        assertThat("sellRate below floor", exchangeRate.getSellRate(),
                greaterThanOrEqualTo(ExpectedValues.USD_GEL_SELL_RATE_MIN));
        assertThat("sellRate above ceiling", exchangeRate.getSellRate(),
                lessThanOrEqualTo(ExpectedValues.USD_GEL_SELL_RATE_MAX));
        return this;
    }

    @Step("Validate conversionType matches expected")
    public ExchangeRateSteps validateConversionType() {
        assertThat("conversionType", exchangeRate.getConversionType(),
                equalTo(ExpectedValues.EXPECTED_CONVERSION_TYPE));
        return this;
    }

    @Step("Validate currencyWeight equals expected")
    public ExchangeRateSteps validateCurrencyWeight() {
        assertThat("currencyWeight", exchangeRate.getCurrencyWeight(),
                equalTo(ExpectedValues.EXPECTED_CURRENCY_WEIGHT));
        return this;
    }

    @Step("Validate updateDate is not empty")
    public ExchangeRateSteps validateUpdateDateIsNotEmpty() {
        assertThat("updateDate must not be empty", exchangeRate.getUpdateDate(), not(emptyString()));
        return this;
    }

    @Step("Validate updateDate format ISO 8601")
    public ExchangeRateSteps validateUpdateDateFormat() {
        assertThat("updateDate ISO 8601 format",
                exchangeRate.getUpdateDate(),
                matchesPattern("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.*"));
        return this;
    }


    @Step("Validate iso1 is present")
    public ExchangeRateSteps validateIso1IsPresent() {
        assertThat("iso1 must not be null", exchangeRate.getIso1(), notNullValue());
        return this;
    }

    @Step("Validate iso2 is present")
    public ExchangeRateSteps validateIso2IsPresent() {
        assertThat("iso2 must not be null", exchangeRate.getIso2(), notNullValue());
        return this;
    }

    @Step("Validate buyRate is present")
    public ExchangeRateSteps validateBuyRateIsPresent() {
        assertThat("buyRate must not be null", exchangeRate.getBuyRate(), notNullValue());
        return this;
    }

    @Step("Validate sellRate is present")
    public ExchangeRateSteps validateSellRateIsPresent() {
        assertThat("sellRate must not be null", exchangeRate.getSellRate(), notNullValue());
        return this;
    }

    @Step("Validate conversionType is present")
    public ExchangeRateSteps validateConversionTypeIsPresent() {
        assertThat("conversionType must not be null", exchangeRate.getConversionType(), notNullValue());
        return this;
    }

    @Step("Validate currencyWeight is present")
    public ExchangeRateSteps validateCurrencyWeightIsPresent() {
        assertThat("currencyWeight must not be null", exchangeRate.getCurrencyWeight(), notNullValue());
        return this;
    }

    @Step("Validate updateDate is present")
    public ExchangeRateSteps validateUpdateDateIsPresent() {
        assertThat("updateDate must not be null", exchangeRate.getUpdateDate(), notNullValue());
        return this;
    }
}