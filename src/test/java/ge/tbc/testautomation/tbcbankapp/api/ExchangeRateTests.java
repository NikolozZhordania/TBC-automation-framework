package ge.tbc.testautomation.tbcbankapp.api;

import ge.tbc.testautomation.tbcbankapp.api.steps.ExchangeRateSteps;
import org.testng.annotations.Test;

public class ExchangeRateTests {

    @Test(description = "TC-ER-01: Verify USD/GEL exchange rate returns 200 with valid response body")
    public void usdGelExchangeRateReturns200Test() {
        new ExchangeRateSteps()
                .fetchExchangeRate("USD", "GEL")
                .validateStatusCode(200);
    }

    @Test(description = "TC-ER-02: Verify USD/GEL exchange rate response has all mandatory fields present")
    public void usdGelExchangeRateAllFieldsPresentTest() {
        new ExchangeRateSteps()
                .fetchExchangeRate("USD", "GEL")
                .validateResponseFieldsAreNotNull();
    }

    @Test(description = "TC-ER-03: Verify USD/GEL exchange rate ISO codes match request parameters")
    public void usdGelExchangeRateIsoCodesMatchTest() {
        new ExchangeRateSteps()
                .fetchExchangeRate("USD", "GEL")
                .validateIsoCurrencyCodes("USD", "GEL")
                .validateIso1IsUSD()
                .validateIso2IsGEL();
    }

    @Test(description = "TC-ER-04: Verify buyRate and sellRate are positive numbers")
    public void usdGelExchangeRateRatesArePositiveTest() {
        new ExchangeRateSteps()
                .fetchExchangeRate("USD", "GEL")
                .validateBuyRateIsPositive()
                .validateSellRateIsPositive();
    }

    @Test(description = "TC-ER-05: Verify sellRate is strictly greater than buyRate (bank spread)")
    public void usdGelSellRateGreaterThanBuyRateTest() {
        new ExchangeRateSteps()
                .fetchExchangeRate("USD", "GEL")
                .validateSellRateIsGreaterThanBuyRate();
    }

    @Test(description = "TC-ER-06: Verify buyRate is within realistic GEL range")
    public void usdGelBuyRateInReasonableRangeTest() {
        new ExchangeRateSteps()
                .fetchExchangeRate("USD", "GEL")
                .validateBuyRateIsWithinReasonableRange();
    }

    @Test(description = "TC-ER-07: Verify sellRate is within realistic GEL range")
    public void usdGelSellRateInReasonableRangeTest() {
        new ExchangeRateSteps()
                .fetchExchangeRate("USD", "GEL")
                .validateSellRateIsWithinReasonableRange();
    }

    @Test(description = "TC-ER-08: Verify conversionType equals 2")
    public void usdGelConversionTypeIsCorrectTest() {
        new ExchangeRateSteps()
                .fetchExchangeRate("USD", "GEL")
                .validateConversionType();
    }

    @Test(description = "TC-ER-09: Verify currencyWeight is 1.0")
    public void usdGelCurrencyWeightIsOneTest() {
        new ExchangeRateSteps()
                .fetchExchangeRate("USD", "GEL")
                .validateCurrencyWeight();
    }

    @Test(description = "TC-ER-10: Verify updateDate is present and not empty")
    public void usdGelUpdateDateIsPresentTest() {
        new ExchangeRateSteps()
                .fetchExchangeRate("USD", "GEL")
                .validateUpdateDateIsNotEmpty();
    }

    @Test(description = "TC-ER-11: Verify updateDate follows ISO 8601 datetime format")
    public void usdGelUpdateDateFormatTest() {
        new ExchangeRateSteps()
                .fetchExchangeRate("USD", "GEL")
                .validateUpdateDateFormat();
    }

    @Test(description = "TC-ER-12: Verify response Content-Type is application/json")
    public void usdGelContentTypeIsJsonTest() {
        new ExchangeRateSteps()
                .fetchExchangeRate("USD", "GEL")
                .validateContentTypeIsJson();
    }

    @Test(description = "TC-ER-13: Full happy path — USD/GEL all validations combined")
    public void usdGelFullValidationTest() {
        new ExchangeRateSteps()
                .fetchExchangeRate("USD", "GEL")
                .validateStatusCode(200)
                .validateContentTypeIsJson()
                .validateResponseFieldsAreNotNull()
                .validateIso1IsUSD()
                .validateIso2IsGEL()
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

    @Test(description = "TC-ER-14: Verify EUR/GEL exchange rate returns 200 with valid response body")
    public void eurGelExchangeRateHappyPathTest() {
        new ExchangeRateSteps()
                .fetchExchangeRate("EUR", "GEL")
                .validateStatusCode(200)
                .validateResponseFieldsAreNotNull()
                .validateIsoCurrencyCodes("EUR", "GEL")
                .validateBuyRateIsPositive()
                .validateSellRateIsPositive()
                .validateSellRateIsGreaterThanBuyRate();
    }

    @Test(description = "TC-ER-15: Verify USD/GEL exchange rate response time is under 2000ms")
    public void usdGelResponseTimeTest() {
        new ExchangeRateSteps()
                .fetchExchangeRate("USD", "GEL")
                .validateResponseTimeIsUnder(2000);
    }

    @Test(description = "TC-ER-16: Verify request with invalid ISO1 currency returns 4xx error")
    public void invalidIso1CurrencyReturnsErrorTest() {
        new ExchangeRateSteps()
                .fetchExchangeRateExpectingError("XXX", "GEL", 400);
    }

    @Test(description = "TC-ER-17: Verify request with invalid ISO2 currency returns 4xx error")
    public void invalidIso2CurrencyReturnsErrorTest() {
        new ExchangeRateSteps()
                .fetchExchangeRateExpectingError("USD", "ZZZ", 400);
    }

    @Test(description = "TC-ER-18: Verify request with both invalid currencies returns 4xx error")
    public void bothInvalidCurrenciesReturnErrorTest() {
        new ExchangeRateSteps()
                .fetchExchangeRateExpectingError("XXX", "YYY", 400);
    }

    @Test(description = "TC-ER-19: Verify request missing Iso1 param returns 4xx error")
    public void missingIso1ParamReturnsErrorTest() {
        new ExchangeRateSteps()
                .fetchWithoutIso1("GEL")
                .validateStatusCode(400);
    }

    @Test(description = "TC-ER-20: Verify request missing Iso2 param returns 4xx error")
    public void missingIso2ParamReturnsErrorTest() {
        new ExchangeRateSteps()
                .fetchWithoutIso2("USD")
                .validateStatusCode(400);
    }

    @Test(description = "TC-ER-21: Verify request with no parameters returns 4xx error")
    public void noParamsReturnErrorTest() {
        new ExchangeRateSteps()
                .fetchWithNoParams()
                .validateStatusCode(400);
    }

    @Test(description = "TC-ER-22: Verify request with numeric currency code returns 4xx error")
    public void numericCurrencyCodeReturnsErrorTest() {
        new ExchangeRateSteps()
                .fetchExchangeRateExpectingError("123", "GEL", 400);
    }

    @Test(description = "TC-ER-23: Verify request with empty string currency code returns 4xx error")
    public void emptyStringCurrencyCodeReturnsErrorTest() {
        new ExchangeRateSteps()
                .fetchExchangeRateExpectingError("", "GEL", 400);
    }

    @Test(description = "TC-ER-24: Verify request with lowercase currency code is accepted or handled gracefully")
    public void lowercaseCurrencyCodeHandledTest() {
        new ExchangeRateSteps()
                .fetchExchangeRate("usd", "gel")
                .validateStatusCode(200);
    }

    @Test(description = "TC-ER-25: Verify same currency pair (GEL/GEL) returns 4xx or appropriate response")
    public void sameCurrencyPairTest() {
        new ExchangeRateSteps()
                .fetchExchangeRateExpectingError("GEL", "GEL", 400);
    }
}
