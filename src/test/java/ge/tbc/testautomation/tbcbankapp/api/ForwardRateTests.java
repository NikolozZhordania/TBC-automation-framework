package ge.tbc.testautomation.tbcbankapp.api;

import ge.tbc.testautomation.tbcbankapp.api.steps.ForwardRateSteps;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.tbcbankapp.api.data.constants.ForwardRateConstants.Currencies.EUR;
import static ge.tbc.testautomation.tbcbankapp.api.data.constants.ForwardRateConstants.Currencies.USD;
import static ge.tbc.testautomation.tbcbankapp.api.data.constants.ForwardRateConstants.Locales.EN_US;
import static ge.tbc.testautomation.tbcbankapp.api.data.constants.ForwardRateConstants.Locales.KA_GE;

public class ForwardRateTests {

    @Test(description = "ka-GE locale returns HTTP 200")
    public void forwardRatesKaGeReturns200Test() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateStatusCode(200);
    }

    @Test(description = "Content-Type is application/json")
    public void forwardRatesContentTypeIsJsonTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateContentTypeIsJson();
    }

    @Test(description = "Response time is under 3000ms")
    public void forwardRatesResponseTimeTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateResponseTimeIsUnder(3000);
    }

    @Test(description = "Rates list is not empty")
    public void forwardRatesListNotEmptyTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateRatesListIsNotEmpty();
    }

    @Test(description = "Response contains exactly 2 currency groups (EUR and USD)")
    public void forwardRatesContainsTwoCurrencyGroupsTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateRatesCountEquals(2);
    }

    @Test(description = "updateDate is present and not empty")
    public void forwardRatesUpdateDatePresentTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateUpdateDateIsPresent();
    }

    @Test(description = "updateDate follows ISO 8601 format")
    public void forwardRatesUpdateDateFormatTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateUpdateDateFormat();
    }

    @Test(description = "EUR currency group exists in response")
    public void forwardRatesEurGroupExistsTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateCurrencyGroupExists(EUR);
    }

    @Test(description = "USD currency group exists in response")
    public void forwardRatesUsdGroupExistsTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateCurrencyGroupExists(USD);
    }

    @Test(description = "All currency groups have non-empty iso codes")
    public void forwardRatesAllGroupsHaveIsoCodeTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateAllCurrencyGroupsHaveIsoCode();
    }

    @Test(description = "EUR has exactly 9 forward rate periods")
    public void eurHasExpectedPeriodCountTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validatePeriodCount(EUR);
    }

    @Test(description = "EUR period days are in ascending order")
    public void eurPeriodsAreAscendingTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validatePeriodsAreInAscendingDayOrder(EUR);
    }

    @Test(description = "EUR period days match expected values: 7,14,31,60,91,181,273,365,730")
    public void eurExpectedDaysMatchTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateExpectedDays(EUR);
    }

    @Test(description = "EUR all forward rate fields are non-null")
    public void eurAllFieldsNonNullTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateAllForwardRateFieldsAreNotNull(EUR);
    }

    @Test(description = "EUR iso1 is EUR inside forward rates")
    public void eurIso1InsideRatesTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateIso1InsideRatesEquals(EUR);
    }

    @Test(description = "EUR iso2 is GEL inside forward rates")
    public void eurIso2InsideRatesTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateIso2InsideRatesIsGel(EUR);
    }

    @Test(description = "EUR askForwardRate > bidForwardRate for all periods")
    public void eurAskRateGreaterThanBidRateTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateAskRateIsAlwaysGreaterThanBidRate(EUR);
    }

    @Test(description = "EUR askForwardPoint > bidForwardPoint for all periods")
    public void eurAskPointGreaterThanBidPointTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateAskPointIsAlwaysGreaterThanBidPoint(EUR);
    }

    @Test(description = "EUR askForwardInterest > bidForwardInterest for all periods")
    public void eurAskInterestGreaterThanBidInterestTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateAskInterestIsAlwaysGreaterThanBidInterest(EUR);
    }

    @Test(description = "EUR bid/ask forward rates are positive")
    public void eurForwardRatesArePositiveTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateForwardRatesArePositive(EUR);
    }

    @Test(description = "EUR forward points are non-negative")
    public void eurForwardPointsAreNonNegativeTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateForwardPointsAreNonNegative(EUR);
    }

    @Test(description = "EUR forward interest rates are within 0–20% range")
    public void eurForwardInterestWithinRangeTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateForwardInterestRatesAreWithinRange(EUR);
    }

    @Test(description = "EUR bidForwardRate increases with longer durations")
    public void eurBidRatesIncreaseOverTimeTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateBidRatesIncreaseOverTime(EUR);
    }

    @Test(description = "EUR askForwardRate increases with longer durations")
    public void eurAskRatesIncreaseOverTimeTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateAskRatesIncreaseOverTime(EUR);
    }

    @Test(description = "USD has exactly 9 forward rate periods")
    public void usdHasExpectedPeriodCountTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validatePeriodCount(USD);
    }

    @Test(description = "USD period days are in ascending order")
    public void usdPeriodsAreAscendingTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validatePeriodsAreInAscendingDayOrder(USD);
    }

    @Test(description = "USD period days match expected values: 7,14,31,60,91,181,273,365,730")
    public void usdExpectedDaysMatchTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateExpectedDays(USD);
    }

    @Test(description = "USD all forward rate fields are non-null")
    public void usdAllFieldsNonNullTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateAllForwardRateFieldsAreNotNull(USD);
    }

    @Test(description = "USD iso1 is USD inside forward rates")
    public void usdIso1InsideRatesTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateIso1InsideRatesEquals(USD);
    }

    @Test(description = "USD iso2 is GEL inside forward rates")
    public void usdIso2InsideRatesTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateIso2InsideRatesIsGel(USD);
    }

    @Test(description = "USD askForwardRate > bidForwardRate for all periods")
    public void usdAskRateGreaterThanBidRateTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateAskRateIsAlwaysGreaterThanBidRate(USD);
    }

    @Test(description = "USD askForwardPoint > bidForwardPoint for all periods")
    public void usdAskPointGreaterThanBidPointTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateAskPointIsAlwaysGreaterThanBidPoint(USD);
    }

    @Test(description = "USD askForwardInterest > bidForwardInterest for all periods")
    public void usdAskInterestGreaterThanBidInterestTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateAskInterestIsAlwaysGreaterThanBidInterest(USD);
    }

    @Test(description = "USD bid/ask forward rates are positive")
    public void usdForwardRatesArePositiveTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateForwardRatesArePositive(USD);
    }

    @Test(description = "USD forward points are non-negative")
    public void usdForwardPointsAreNonNegativeTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateForwardPointsAreNonNegative(USD);
    }

    @Test(description = "USD forward interest rates are within 0–20% range")
    public void usdForwardInterestWithinRangeTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateForwardInterestRatesAreWithinRange(USD);
    }

    @Test(description = "USD bidForwardRate increases with longer durations")
    public void usdBidRatesIncreaseOverTimeTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateBidRatesIncreaseOverTime(USD);
    }

    @Test(description = "USD askForwardRate increases with longer durations")
    public void usdAskRatesIncreaseOverTimeTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateAskRatesIncreaseOverTime(USD);
    }

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

    @Test(description = "Invalid locale returns 4xx")
    public void invalidLocaleReturnsErrorTest() {
        new ForwardRateSteps()
                .fetchForwardRatesExpectingError("xx-XX", 400)
                .validateStatusCode(400);
    }

    @Test(description = "Empty locale string returns 4xx")
    public void emptyLocaleReturnsErrorTest() {
        new ForwardRateSteps()
                .fetchForwardRatesExpectingError("", 400)
                .validateStatusCode(400);
    }

    @Test(description = "No locale parameter still returns a valid response")
    public void noLocaleParamReturnsValidResponseTest() {
        new ForwardRateSteps()
                .fetchForwardRatesNoParams()
                .validateStatusCode(200);
    }

    @Test(description = "en-US locale returns valid response")
    public void enUsLocaleReturnsValidResponseTest() {
        new ForwardRateSteps()
                .fetchForwardRates(EN_US)
                .validateStatusCode(200)
                .validateRatesListIsNotEmpty();
    }

    @Test(description = "Numeric locale value returns 4xx")
    public void numericLocaleReturnsErrorTest() {
        new ForwardRateSteps()
                .fetchForwardRatesExpectingError("12345", 400)
                .validateStatusCode(400);
    }

    @Test(description = "SQL injection in locale param is handled safely")
    public void sqlInjectionLocaleIsHandledSafelyTest() {
        new ForwardRateSteps()
                .fetchForwardRatesExpectingError("'; DROP TABLE rates; --", 400)
                .validateStatusCode(400);
    }
}