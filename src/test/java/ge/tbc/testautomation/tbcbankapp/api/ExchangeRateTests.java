package ge.tbc.testautomation.tbcbankapp.api;

import ge.tbc.testautomation.tbcbankapp.api.steps.ExchangeRateSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.tbcbankapp.api.data.constants.ExchangeRateConstants.CurrencyPairs.*;
import static ge.tbc.testautomation.tbcbankapp.api.data.constants.ExchangeRateConstants.ExpectedValues.*;
import static ge.tbc.testautomation.tbcbankapp.api.data.constants.ExchangeRateConstants.InvalidCurrencies.*;
import static ge.tbc.testautomation.tbcbankapp.api.data.constants.ExchangeRateConstants.LowercaseCurrencies.*;

@Feature("Exchange Rate API")
public class ExchangeRateTests {

    @Test(description = "USD/GEL returns HTTP 200")
    @Story("Happy Path - Status Code")
    @Description("Verify that fetching USD/GEL exchange rate returns HTTP 200 OK")
    public void usdGelReturns200Test() {
        new ExchangeRateSteps()
                .fetchExchangeRate(USD, GEL)
                .validateStatusCode(EXPECTED_STATUS_200);
    }

    @Test(description = "USD/GEL Content-Type is application/json")
    @Story("Happy Path - Content Type")
    @Description("Verify that fetching USD/GEL exchange rate returns Content-Type 'application/json'")
    public void usdGelContentTypeIsJsonTest() {
        new ExchangeRateSteps()
                .fetchExchangeRate(USD, GEL)
                .validateContentTypeIsJson();
    }

    @Test(description = "USD/GEL response time is under 2000ms")
    @Story("Happy Path - Response Time")
    @Description("Verify that USD/GEL API response time is less than 2 seconds")
    public void usdGelResponseTimeTest() {
        new ExchangeRateSteps()
                .fetchExchangeRate(USD, GEL)
                .validateResponseTimeIsUnder(MAX_RESPONSE_TIME_MS);
    }

    @Test(description = "USD/GEL all mandatory fields are present")
    @Story("Happy Path - Field Presence")
    @Description("Verify that all mandatory fields are present in USD/GEL exchange rate response")
    public void usdGelAllFieldsPresentTest() {
        new ExchangeRateSteps()
                .fetchExchangeRate(USD, GEL)
                .validateAllFieldsArePresent();
    }

    @Test(description = "USD/GEL buyRate and sellRate validations")
    @Story("Happy Path - Rate Validations")
    @Description("Verify USD/GEL buyRate and sellRate are positive, sellRate > buyRate, and within reasonable GEL range")
    public void usdGelRatesValidationTest() {
        new ExchangeRateSteps()
                .fetchExchangeRate(USD, GEL)
                .validateBuyRateIsPositive()
                .validateSellRateIsPositive()
                .validateSellRateIsGreaterThanBuyRate()
                .validateBuyRateIsWithinReasonableRange()
                .validateSellRateIsWithinReasonableRange();
    }

    @Test(description = "USD/GEL conversionType and currencyWeight validations")
    @Story("Happy Path - Metadata Validations")
    @Description("Verify conversionType and currencyWeight for USD/GEL exchange rate")
    public void usdGelMetadataValidationTest() {
        new ExchangeRateSteps()
                .fetchExchangeRate(USD, GEL)
                .validateConversionType()
                .validateCurrencyWeight();
    }

    @Test(description = "USD/GEL updateDate validations")
    @Story("Happy Path - Update Date Validations")
    @Description("Verify updateDate is present and follows ISO 8601 format for USD/GEL")
    public void usdGelUpdateDateValidationTest() {
        new ExchangeRateSteps()
                .fetchExchangeRate(USD, GEL)
                .validateUpdateDateIsNotEmpty()
                .validateUpdateDateFormat();
    }

    @Test(description = "USD/GEL full happy path — all validations combined")
    @Story("Happy Path - Full Validation")
    @Description("Fetch USD/GEL and validate status, content type, fields, rates, conversionType, currencyWeight, and updateDate in one flow")
    public void usdGelFullValidationTest() {
        new ExchangeRateSteps()
                .fetchExchangeRate(USD, GEL)
                .validateStatusCode(EXPECTED_STATUS_200)
                .validateContentTypeIsJson()
                .validateAllFieldsArePresent()
                .validateIso1Equals(USD)
                .validateIso2Equals(GEL)
                .validateBuyRateIsPositive()
                .validateSellRateIsPositive()
                .validateSellRateIsGreaterThanBuyRate()
                .validateBuyRateIsWithinReasonableRange()
                .validateSellRateIsWithinReasonableRange()
                .validateConversionType()
                .validateCurrencyWeight()
                .validateUpdateDateIsNotEmpty()
                .validateUpdateDateFormat();
    }

    @Test(description = "EUR/GEL happy path")
    @Story("Happy Path - EUR/GEL")
    @Description("Fetch EUR/GEL exchange rate and validate mandatory fields and rates")
    public void eurGelHappyPathTest() {
        new ExchangeRateSteps()
                .fetchExchangeRate(EUR, GEL)
                .validateStatusCode(EXPECTED_STATUS_200)
                .validateAllFieldsArePresent()
                .validateIso1Equals(EUR)
                .validateIso2Equals(GEL)
                .validateBuyRateIsPositive()
                .validateSellRateIsPositive()
                .validateSellRateIsGreaterThanBuyRate();
    }

    @Test(description = "Invalid iso1 returns 4xx")
    @Story("Error Path - Invalid iso1")
    @Description("Verify API returns 4xx when iso1 parameter is invalid")
    public void invalidIso1ReturnsErrorTest() {
        new ExchangeRateSteps()
                .fetchExchangeRateExpectingError(INVALID_ISO1, GEL, EXPECTED_STATUS_400)
                .validateStatusCode(EXPECTED_STATUS_400);
    }

    @Test(description = "Invalid iso2 returns 4xx")
    @Story("Error Path - Invalid iso2")
    @Description("Verify API returns 4xx when iso2 parameter is invalid")
    public void invalidIso2ReturnsErrorTest() {
        new ExchangeRateSteps()
                .fetchExchangeRateExpectingError(USD, INVALID_ISO2, EXPECTED_STATUS_400)
                .validateStatusCode(EXPECTED_STATUS_400);
    }

    @Test(description = "Both currencies invalid returns 4xx")
    @Story("Error Path - Both Currencies Invalid")
    @Description("Verify API returns 4xx when both iso1 and iso2 parameters are invalid")
    public void bothInvalidCurrenciesReturnErrorTest() {
        new ExchangeRateSteps()
                .fetchExchangeRateExpectingError(INVALID_BOTH_ISO1, INVALID_BOTH_ISO2, EXPECTED_STATUS_400)
                .validateStatusCode(EXPECTED_STATUS_400);
    }

    @Test(description = "Missing iso1 param returns 4xx")
    @Story("Error Path - Missing Parameters")
    @Description("Verify API returns 4xx when iso1 parameter is missing")
    public void missingIso1ReturnsErrorTest() {
        new ExchangeRateSteps()
                .fetchWithoutIso1(GEL)
                .validateStatusCode(EXPECTED_STATUS_400);
    }

    @Test(description = "Missing iso2 param returns 4xx")
    @Story("Error Path - Missing Parameters")
    @Description("Verify API returns 4xx when iso2 parameter is missing")
    public void missingIso2ReturnsErrorTest() {
        new ExchangeRateSteps()
                .fetchWithoutIso2(USD)
                .validateStatusCode(EXPECTED_STATUS_400);
    }

    @Test(description = "No params returns 4xx")
    @Story("Error Path - Missing Parameters")
    @Description("Verify API returns 4xx when both iso1 and iso2 parameters are missing")
    public void noParamsReturnErrorTest() {
        new ExchangeRateSteps()
                .fetchWithNoParams()
                .validateStatusCode(EXPECTED_STATUS_400);
    }

    @Test(description = "Numeric currency code returns 4xx")
    @Story("Error Path - Invalid Format")
    @Description("Verify API returns 4xx when numeric currency code is provided")
    public void numericCurrencyCodeReturnsErrorTest() {
        new ExchangeRateSteps()
                .fetchExchangeRateExpectingError(NUMERIC_CODE, GEL, EXPECTED_STATUS_400)
                .validateStatusCode(EXPECTED_STATUS_400);
    }

    @Test(description = "Empty string currency code returns 4xx")
    @Story("Error Path - Invalid Format")
    @Description("Verify API returns 4xx when empty string currency code is provided")
    public void emptyCurrencyCodeReturnsErrorTest() {
        new ExchangeRateSteps()
                .fetchExchangeRateExpectingError(EMPTY_CODE, GEL, EXPECTED_STATUS_400)
                .validateStatusCode(EXPECTED_STATUS_400);
    }

    @Test(description = "Lowercase currency code is accepted or handled gracefully")
    @Story("Edge Case - Lowercase Handling")
    @Description("Verify API accepts lowercase currency codes and returns 200")
    public void lowercaseCurrencyCodeTest() {
        new ExchangeRateSteps()
                .fetchExchangeRate(USD_LOWER, GEL_LOWER)
                .validateStatusCode(EXPECTED_STATUS_200);
    }

    @Test(description = "Same currency pair (GEL/GEL) returns 4xx")
    @Story("Error Path - Same Currency Pair")
    @Description("Verify API returns 4xx when both currencies are the same")
    public void sameCurrencyPairReturnsErrorTest() {
        new ExchangeRateSteps()
                .fetchExchangeRateExpectingError(GEL, GEL, EXPECTED_STATUS_400)
                .validateStatusCode(EXPECTED_STATUS_400);
    }
}