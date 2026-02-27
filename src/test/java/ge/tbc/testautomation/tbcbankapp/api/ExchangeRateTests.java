package ge.tbc.testautomation.tbcbankapp.api;

import ge.tbc.testautomation.tbcbankapp.api.steps.ExchangeRateSteps;
import org.testng.annotations.Test;

public class ExchangeRateTests {

    @Test(description = "USD/GEL returns HTTP 200")
    public void usdGelReturns200Test() {
        new ExchangeRateSteps()
                .fetchExchangeRate("USD", "GEL")
                .validateStatusCode(200);
    }

    @Test(description = "USD/GEL Content-Type is application/json")
    public void usdGelContentTypeIsJsonTest() {
        new ExchangeRateSteps()
                .fetchExchangeRate("USD", "GEL")
                .validateContentTypeIsJson();
    }

    @Test(description = "USD/GEL response time is under 2000ms")
    public void usdGelResponseTimeTest() {
        new ExchangeRateSteps()
                .fetchExchangeRate("USD", "GEL")
                .validateResponseTimeIsUnder(2000);
    }

    @Test(description = "USD/GEL all mandatory fields are present")
    public void usdGelAllFieldsPresentTest() {
        new ExchangeRateSteps()
                .fetchExchangeRate("USD", "GEL")
                .validateAllFieldsArePresent();
    }

    @Test(description = "USD/GEL iso1 matches requested currency")
    public void usdGelIso1MatchesTest() {
        new ExchangeRateSteps()
                .fetchExchangeRate("USD", "GEL")
                .validateIso1Equals("USD");
    }

    @Test(description = "USD/GEL iso2 matches requested currency")
    public void usdGelIso2MatchesTest() {
        new ExchangeRateSteps()
                .fetchExchangeRate("USD", "GEL")
                .validateIso2Equals("GEL");
    }

    @Test(description = "USD/GEL buyRate is positive")
    public void usdGelBuyRateIsPositiveTest() {
        new ExchangeRateSteps()
                .fetchExchangeRate("USD", "GEL")
                .validateBuyRateIsPositive();
    }

    @Test(description = "USD/GEL sellRate is positive")
    public void usdGelSellRateIsPositiveTest() {
        new ExchangeRateSteps()
                .fetchExchangeRate("USD", "GEL")
                .validateSellRateIsPositive();
    }

    @Test(description = "USD/GEL sellRate is greater than buyRate (bank spread)")
    public void usdGelSellRateGreaterThanBuyRateTest() {
        new ExchangeRateSteps()
                .fetchExchangeRate("USD", "GEL")
                .validateSellRateIsGreaterThanBuyRate();
    }

    @Test(description = "USD/GEL buyRate is within realistic GEL range")
    public void usdGelBuyRateInRangeTest() {
        new ExchangeRateSteps()
                .fetchExchangeRate("USD", "GEL")
                .validateBuyRateIsWithinReasonableRange();
    }

    @Test(description = "USD/GEL sellRate is within realistic GEL range")
    public void usdGelSellRateInRangeTest() {
        new ExchangeRateSteps()
                .fetchExchangeRate("USD", "GEL")
                .validateSellRateIsWithinReasonableRange();
    }

    @Test(description = "USD/GEL conversionType equals expected value")
    public void usdGelConversionTypeTest() {
        new ExchangeRateSteps()
                .fetchExchangeRate("USD", "GEL")
                .validateConversionType();
    }

    @Test(description = "USD/GEL currencyWeight is 1.0")
    public void usdGelCurrencyWeightTest() {
        new ExchangeRateSteps()
                .fetchExchangeRate("USD", "GEL")
                .validateCurrencyWeight();
    }

    @Test(description = "USD/GEL updateDate is present and not empty")
    public void usdGelUpdateDateIsPresentTest() {
        new ExchangeRateSteps()
                .fetchExchangeRate("USD", "GEL")
                .validateUpdateDateIsNotEmpty();
    }

    @Test(description = "USD/GEL updateDate follows ISO 8601 format")
    public void usdGelUpdateDateFormatTest() {
        new ExchangeRateSteps()
                .fetchExchangeRate("USD", "GEL")
                .validateUpdateDateFormat();
    }

    @Test(description = "USD/GEL full happy path — all validations combined")
    public void usdGelFullValidationTest() {
        new ExchangeRateSteps()
                .fetchExchangeRate("USD", "GEL")
                .validateStatusCode(200)
                .validateContentTypeIsJson()
                .validateAllFieldsArePresent()
                .validateIso1Equals("USD")
                .validateIso2Equals("GEL")
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
    public void eurGelHappyPathTest() {
        new ExchangeRateSteps()
                .fetchExchangeRate("EUR", "GEL")
                .validateStatusCode(200)
                .validateAllFieldsArePresent()
                .validateIso1Equals("EUR")
                .validateIso2Equals("GEL")
                .validateBuyRateIsPositive()
                .validateSellRateIsPositive()
                .validateSellRateIsGreaterThanBuyRate();
    }

    @Test(description = "Invalid iso1 returns 4xx")
    public void invalidIso1ReturnsErrorTest() {
        new ExchangeRateSteps()
                .fetchExchangeRateExpectingError("XXX", "GEL", 400)
                .validateStatusCode(400);
    }

    @Test(description = "Invalid iso2 returns 4xx")
    public void invalidIso2ReturnsErrorTest() {
        new ExchangeRateSteps()
                .fetchExchangeRateExpectingError("USD", "ZZZ", 400)
                .validateStatusCode(400);
    }

    @Test(description = "Both currencies invalid returns 4xx")
    public void bothInvalidCurrenciesReturnErrorTest() {
        new ExchangeRateSteps()
                .fetchExchangeRateExpectingError("XXX", "YYY", 400)
                .validateStatusCode(400);
    }

    @Test(description = "Missing iso1 param returns 4xx")
    public void missingIso1ReturnsErrorTest() {
        new ExchangeRateSteps()
                .fetchWithoutIso1("GEL")
                .validateStatusCode(400);
    }

    @Test(description = "Missing iso2 param returns 4xx")
    public void missingIso2ReturnsErrorTest() {
        new ExchangeRateSteps()
                .fetchWithoutIso2("USD")
                .validateStatusCode(400);
    }

    @Test(description = "No params returns 4xx")
    public void noParamsReturnErrorTest() {
        new ExchangeRateSteps()
                .fetchWithNoParams()
                .validateStatusCode(400);
    }

    @Test(description = "Numeric currency code returns 4xx")
    public void numericCurrencyCodeReturnsErrorTest() {
        new ExchangeRateSteps()
                .fetchExchangeRateExpectingError("123", "GEL", 400)
                .validateStatusCode(400);
    }

    @Test(description = "Empty string currency code returns 4xx")
    public void emptyCurrencyCodeReturnsErrorTest() {
        new ExchangeRateSteps()
                .fetchExchangeRateExpectingError("", "GEL", 400)
                .validateStatusCode(400);
    }

    @Test(description = "Lowercase currency code is accepted or handled gracefully")
    public void lowercaseCurrencyCodeTest() {
        new ExchangeRateSteps()
                .fetchExchangeRate("usd", "gel")
                .validateStatusCode(200);
    }

    @Test(description = "Same currency pair (GEL/GEL) returns 4xx")
    public void sameCurrencyPairReturnsErrorTest() {
        new ExchangeRateSteps()
                .fetchExchangeRateExpectingError("GEL", "GEL", 400)
                .validateStatusCode(400);
    }
}