package ge.tbc.testautomation.tbcbankapp.api;

import ge.tbc.testautomation.tbcbankapp.api.steps.ForwardRateSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.tbcbankapp.api.data.constants.ForwardRateConstants.Currencies.EUR;
import static ge.tbc.testautomation.tbcbankapp.api.data.constants.ForwardRateConstants.Currencies.USD;
import static ge.tbc.testautomation.tbcbankapp.api.data.constants.ForwardRateConstants.Locales.EN_US;
import static ge.tbc.testautomation.tbcbankapp.api.data.constants.ForwardRateConstants.Locales.KA_GE;

@Feature("Forward Rate API")
public class ForwardRateTests {

    @Story("Response Validation")
    @Description("Verify that the ka-GE locale returns HTTP 200")
    @Test(description = "ka-GE locale returns HTTP 200")
    public void forwardRatesKaGeReturns200Test() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateStatusCode(200);
    }

    @Story("Response Validation")
    @Description("Verify that the Content-Type header is application/json")
    @Test(description = "Content-Type is application/json")
    public void forwardRatesContentTypeIsJsonTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateContentTypeIsJson();
    }

    @Story("Response Validation")
    @Description("Verify that the API responds within 3000ms")
    @Test(description = "Response time is under 3000ms")
    public void forwardRatesResponseTimeTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateResponseTimeIsUnder(3000);
    }

    @Story("Response Validation")
    @Description("Verify that the rates list in the response is not empty")
    @Test(description = "Rates list is not empty")
    public void forwardRatesListNotEmptyTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateRatesListIsNotEmpty();
    }

    @Story("Response Validation")
    @Description("Verify that the response contains exactly 2 currency groups: EUR and USD")
    @Test(description = "Response contains exactly 2 currency groups (EUR and USD)")
    public void forwardRatesContainsTwoCurrencyGroupsTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateRatesCountEquals(2);
    }

    @Story("Response Validation")
    @Description("Verify that the updateDate field is present and not empty")
    @Test(description = "updateDate is present and not empty")
    public void forwardRatesUpdateDatePresentTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateUpdateDateIsPresent();
    }

    @Story("Response Validation")
    @Description("Verify that the updateDate field follows ISO 8601 format")
    @Test(description = "updateDate follows ISO 8601 format")
    public void forwardRatesUpdateDateFormatTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateUpdateDateFormat();
    }

    @Story("Currency Groups")
    @Description("Verify that the EUR currency group exists in the response")
    @Test(description = "EUR currency group exists in response")
    public void forwardRatesEurGroupExistsTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateCurrencyGroupExists(EUR);
    }

    @Story("Currency Groups")
    @Description("Verify that the USD currency group exists in the response")
    @Test(description = "USD currency group exists in response")
    public void forwardRatesUsdGroupExistsTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateCurrencyGroupExists(USD);
    }

    @Story("Currency Groups")
    @Description("Verify that all currency groups contain a non-empty ISO code")
    @Test(description = "All currency groups have non-empty iso codes")
    public void forwardRatesAllGroupsHaveIsoCodeTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateAllCurrencyGroupsHaveIsoCode();
    }

    @Story("EUR Forward Rates")
    @Description("Verify that EUR has exactly 9 forward rate periods")
    @Test(description = "EUR has exactly 9 forward rate periods")
    public void eurHasExpectedPeriodCountTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validatePeriodCount(EUR);
    }

    @Story("EUR Forward Rates")
    @Description("Verify that EUR period days are in ascending order")
    @Test(description = "EUR period days are in ascending order")
    public void eurPeriodsAreAscendingTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validatePeriodsAreInAscendingDayOrder(EUR);
    }

    @Story("EUR Forward Rates")
    @Description("Verify that EUR period days match expected values: 7, 14, 31, 60, 91, 181, 273, 365, 730")
    @Test(description = "EUR period days match expected values: 7,14,31,60,91,181,273,365,730")
    public void eurExpectedDaysMatchTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateExpectedDays(EUR);
    }

    @Story("EUR Forward Rates")
    @Description("Verify that all forward rate fields for EUR are non-null")
    @Test(description = "EUR all forward rate fields are non-null")
    public void eurAllFieldsNonNullTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateAllForwardRateFieldsAreNotNull(EUR);
    }

    @Story("EUR Forward Rates")
    @Description("Verify that iso1 equals EUR inside EUR forward rate entries")
    @Test(description = "EUR iso1 is EUR inside forward rates")
    public void eurIso1InsideRatesTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateIso1InsideRatesEquals(EUR);
    }

    @Story("EUR Forward Rates")
    @Description("Verify that iso2 equals GEL inside EUR forward rate entries")
    @Test(description = "EUR iso2 is GEL inside forward rates")
    public void eurIso2InsideRatesTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateIso2InsideRatesIsGel(EUR);
    }

    @Story("EUR Forward Rates")
    @Description("Verify that askForwardRate is always greater than bidForwardRate for EUR across all periods")
    @Test(description = "EUR askForwardRate > bidForwardRate for all periods")
    public void eurAskRateGreaterThanBidRateTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateAskRateIsAlwaysGreaterThanBidRate(EUR);
    }

    @Story("EUR Forward Rates")
    @Description("Verify that askForwardPoint is always greater than bidForwardPoint for EUR across all periods")
    @Test(description = "EUR askForwardPoint > bidForwardPoint for all periods")
    public void eurAskPointGreaterThanBidPointTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateAskPointIsAlwaysGreaterThanBidPoint(EUR);
    }

    @Story("EUR Forward Rates")
    @Description("Verify that askForwardInterest is always greater than bidForwardInterest for EUR across all periods")
    @Test(description = "EUR askForwardInterest > bidForwardInterest for all periods")
    public void eurAskInterestGreaterThanBidInterestTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateAskInterestIsAlwaysGreaterThanBidInterest(EUR);
    }

    @Story("EUR Forward Rates")
    @Description("Verify that EUR bid and ask forward rates are positive values")
    @Test(description = "EUR bid/ask forward rates are positive")
    public void eurForwardRatesArePositiveTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateForwardRatesArePositive(EUR);
    }

    @Story("EUR Forward Rates")
    @Description("Verify that EUR forward points are non-negative")
    @Test(description = "EUR forward points are non-negative")
    public void eurForwardPointsAreNonNegativeTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateForwardPointsAreNonNegative(EUR);
    }

    @Story("EUR Forward Rates")
    @Description("Verify that EUR forward interest rates are within the expected 0–20% range")
    @Test(description = "EUR forward interest rates are within 0–20% range")
    public void eurForwardInterestWithinRangeTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateForwardInterestRatesAreWithinRange(EUR);
    }

    @Story("EUR Forward Rates")
    @Description("Verify that EUR bidForwardRate increases as duration increases")
    @Test(description = "EUR bidForwardRate increases with longer durations")
    public void eurBidRatesIncreaseOverTimeTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateBidRatesIncreaseOverTime(EUR);
    }

    @Story("EUR Forward Rates")
    @Description("Verify that EUR askForwardRate increases as duration increases")
    @Test(description = "EUR askForwardRate increases with longer durations")
    public void eurAskRatesIncreaseOverTimeTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateAskRatesIncreaseOverTime(EUR);
    }

    @Story("USD Forward Rates")
    @Description("Verify that USD has exactly 9 forward rate periods")
    @Test(description = "USD has exactly 9 forward rate periods")
    public void usdHasExpectedPeriodCountTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validatePeriodCount(USD);
    }

    @Story("USD Forward Rates")
    @Description("Verify that USD period days are in ascending order")
    @Test(description = "USD period days are in ascending order")
    public void usdPeriodsAreAscendingTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validatePeriodsAreInAscendingDayOrder(USD);
    }

    @Story("USD Forward Rates")
    @Description("Verify that USD period days match expected values: 7, 14, 31, 60, 91, 181, 273, 365, 730")
    @Test(description = "USD period days match expected values: 7,14,31,60,91,181,273,365,730")
    public void usdExpectedDaysMatchTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateExpectedDays(USD);
    }

    @Story("USD Forward Rates")
    @Description("Verify that all forward rate fields for USD are non-null")
    @Test(description = "USD all forward rate fields are non-null")
    public void usdAllFieldsNonNullTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateAllForwardRateFieldsAreNotNull(USD);
    }

    @Story("USD Forward Rates")
    @Description("Verify that iso1 equals USD inside USD forward rate entries")
    @Test(description = "USD iso1 is USD inside forward rates")
    public void usdIso1InsideRatesTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateIso1InsideRatesEquals(USD);
    }

    @Story("USD Forward Rates")
    @Description("Verify that iso2 equals GEL inside USD forward rate entries")
    @Test(description = "USD iso2 is GEL inside forward rates")
    public void usdIso2InsideRatesTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateIso2InsideRatesIsGel(USD);
    }

    @Story("USD Forward Rates")
    @Description("Verify that askForwardRate is always greater than bidForwardRate for USD across all periods")
    @Test(description = "USD askForwardRate > bidForwardRate for all periods")
    public void usdAskRateGreaterThanBidRateTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateAskRateIsAlwaysGreaterThanBidRate(USD);
    }

    @Story("USD Forward Rates")
    @Description("Verify that askForwardPoint is always greater than bidForwardPoint for USD across all periods")
    @Test(description = "USD askForwardPoint > bidForwardPoint for all periods")
    public void usdAskPointGreaterThanBidPointTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateAskPointIsAlwaysGreaterThanBidPoint(USD);
    }

    @Story("USD Forward Rates")
    @Description("Verify that askForwardInterest is always greater than bidForwardInterest for USD across all periods")
    @Test(description = "USD askForwardInterest > bidForwardInterest for all periods")
    public void usdAskInterestGreaterThanBidInterestTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateAskInterestIsAlwaysGreaterThanBidInterest(USD);
    }

    @Story("USD Forward Rates")
    @Description("Verify that USD bid and ask forward rates are positive values")
    @Test(description = "USD bid/ask forward rates are positive")
    public void usdForwardRatesArePositiveTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateForwardRatesArePositive(USD);
    }

    @Story("USD Forward Rates")
    @Description("Verify that USD forward points are non-negative")
    @Test(description = "USD forward points are non-negative")
    public void usdForwardPointsAreNonNegativeTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateForwardPointsAreNonNegative(USD);
    }

    @Story("USD Forward Rates")
    @Description("Verify that USD forward interest rates are within the expected 0–20% range")
    @Test(description = "USD forward interest rates are within 0–20% range")
    public void usdForwardInterestWithinRangeTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateForwardInterestRatesAreWithinRange(USD);
    }

    @Story("USD Forward Rates")
    @Description("Verify that USD bidForwardRate increases as duration increases")
    @Test(description = "USD bidForwardRate increases with longer durations")
    public void usdBidRatesIncreaseOverTimeTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateBidRatesIncreaseOverTime(USD);
    }

    @Story("USD Forward Rates")
    @Description("Verify that USD askForwardRate increases as duration increases")
    @Test(description = "USD askForwardRate increases with longer durations")
    public void usdAskRatesIncreaseOverTimeTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateAskRatesIncreaseOverTime(USD);
    }

    @Story("EUR Forward Rates")
    @Description("Full happy path for EUR — runs all structural and business rule validations in sequence")
    @Test(description = "EUR full happy path — all validations combined")
    public void eurFullValidationTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateStatusCode(200)
                .validateContentTypeIsJson()
                .validateRatesListIsNotEmpty()
                .validateCurrencyGroupExists(EUR)
                .validatePeriodCount(EUR)
                .validateExpectedDays(EUR)
                .validatePeriodsAreInAscendingDayOrder(EUR)
                .validateAllForwardRateFieldsAreNotNull(EUR)
                .validateIso1InsideRatesEquals(EUR)
                .validateIso2InsideRatesIsGel(EUR)
                .validateAskRateIsAlwaysGreaterThanBidRate(EUR)
                .validateAskPointIsAlwaysGreaterThanBidPoint(EUR)
                .validateAskInterestIsAlwaysGreaterThanBidInterest(EUR)
                .validateForwardRatesArePositive(EUR)
                .validateForwardPointsAreNonNegative(EUR)
                .validateForwardInterestRatesAreWithinRange(EUR)
                .validateBidRatesIncreaseOverTime(EUR)
                .validateAskRatesIncreaseOverTime(EUR);
    }

    @Story("USD Forward Rates")
    @Description("Full happy path for USD — runs all structural and business rule validations in sequence")
    @Test(description = "USD full happy path — all validations combined")
    public void usdFullValidationTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateStatusCode(200)
                .validateContentTypeIsJson()
                .validateRatesListIsNotEmpty()
                .validateCurrencyGroupExists(USD)
                .validatePeriodCount(USD)
                .validateExpectedDays(USD)
                .validatePeriodsAreInAscendingDayOrder(USD)
                .validateAllForwardRateFieldsAreNotNull(USD)
                .validateIso1InsideRatesEquals(USD)
                .validateIso2InsideRatesIsGel(USD)
                .validateAskRateIsAlwaysGreaterThanBidRate(USD)
                .validateAskPointIsAlwaysGreaterThanBidPoint(USD)
                .validateAskInterestIsAlwaysGreaterThanBidInterest(USD)
                .validateForwardRatesArePositive(USD)
                .validateForwardPointsAreNonNegative(USD)
                .validateForwardInterestRatesAreWithinRange(USD)
                .validateBidRatesIncreaseOverTime(USD)
                .validateAskRatesIncreaseOverTime(USD);
    }

    @Story("Locale Handling")
    @Description("Verify that an invalid locale value results in a 4xx error response")
    @Test(description = "Invalid locale returns 4xx")
    public void invalidLocaleReturnsErrorTest() {
        new ForwardRateSteps()
                .fetchForwardRatesExpectingError("xx-XX", 400)
                .validateStatusCode(400);
    }

    @Story("Locale Handling")
    @Description("Verify that an empty locale string results in a 4xx error response")
    @Test(description = "Empty locale string returns 4xx")
    public void emptyLocaleReturnsErrorTest() {
        new ForwardRateSteps()
                .fetchForwardRatesExpectingError("", 400)
                .validateStatusCode(400);
    }

    @Story("Locale Handling")
    @Description("Verify that omitting the locale parameter still returns a valid 200 response")
    @Test(description = "No locale parameter still returns a valid response")
    public void noLocaleParamReturnsValidResponseTest() {
        new ForwardRateSteps()
                .fetchForwardRatesNoParams()
                .validateStatusCode(200);
    }

    @Story("Locale Handling")
    @Description("Verify that the en-US locale returns a valid response with a non-empty rates list")
    @Test(description = "en-US locale returns valid response")
    public void enUsLocaleReturnsValidResponseTest() {
        new ForwardRateSteps()
                .fetchForwardRates(EN_US)
                .validateStatusCode(200)
                .validateRatesListIsNotEmpty();
    }

    @Story("Locale Handling")
    @Description("Verify that a numeric locale value results in a 4xx error response")
    @Test(description = "Numeric locale value returns 4xx")
    public void numericLocaleReturnsErrorTest() {
        new ForwardRateSteps()
                .fetchForwardRatesExpectingError("12345", 400)
                .validateStatusCode(400);
    }

    @Story("Security")
    @Description("Verify that an SQL injection payload in the locale parameter is handled safely with a 4xx response")
    @Test(description = "SQL injection in locale param is handled safely")
    public void sqlInjectionLocaleIsHandledSafelyTest() {
        new ForwardRateSteps()
                .fetchForwardRatesExpectingError("'; DROP TABLE rates; --", 400)
                .validateStatusCode(400);
    }
}