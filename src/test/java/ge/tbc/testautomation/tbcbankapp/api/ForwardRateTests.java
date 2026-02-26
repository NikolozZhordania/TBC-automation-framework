package ge.tbc.testautomation.tbcbankapp.api;

import ge.tbc.testautomation.tbcbankapp.api.steps.ForwardRateSteps;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.tbcbankapp.api.data.constants.ForwardRateConstants.Currencies.EUR;
import static ge.tbc.testautomation.tbcbankapp.api.data.constants.ForwardRateConstants.Currencies.USD;
import static ge.tbc.testautomation.tbcbankapp.api.data.constants.ForwardRateConstants.Locales.EN_US;
import static ge.tbc.testautomation.tbcbankapp.api.data.constants.ForwardRateConstants.Locales.KA_GE;

public class ForwardRateTests {

    @Test(description = "TC-FR-01: Verify getForwardRates with ka-GE locale returns 200")
    public void forwardRatesKaGeReturns200Test() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateStatusCode(200);
    }

    @Test(description = "TC-FR-02: Verify response Content-Type is application/json")
    public void forwardRatesContentTypeIsJsonTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateContentTypeIsJson();
    }
    @Test(description = "TC-FR-03: Verify rates list is not empty")
    public void forwardRatesListNotEmptyTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateRatesListIsNotEmpty();
    }

    @Test(description = "TC-FR-04: Verify response contains exactly 2 currency groups (EUR and USD)")
    public void forwardRatesContainsTwoCurrencyGroupsTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateRatesCountEquals(2);
    }

    @Test(description = "TC-FR-05: Verify EUR currency group exists in response")
    public void forwardRatesEurGroupExistsTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateCurrencyGroupExists(EUR);
    }

    @Test(description = "TC-FR-06: Verify USD currency group exists in response")
    public void forwardRatesUsdGroupExistsTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateCurrencyGroupExists(USD);
    }

    @Test(description = "TC-FR-07: Verify updateDate is present and not empty")
    public void forwardRatesUpdateDatePresentTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateUpdateDateIsPresent();
    }

    @Test(description = "TC-FR-08: Verify updateDate follows ISO 8601 format")
    public void forwardRatesUpdateDateFormatTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateUpdateDateFormat();
    }

    @Test(description = "TC-FR-09: Verify all currency groups have non-empty iso codes")
    public void forwardRatesAllGroupsHaveIsoCodeTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateAllCurrencyGroupsHaveIsoCode();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Happy Path — EUR Period Count & Days
    // ─────────────────────────────────────────────────────────────────────────

    @Test(description = "TC-FR-10: Verify EUR has exactly 9 forward rate periods")
    public void eurHasExpectedPeriodCountTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateEachCurrencyHasExpectedPeriodCount(EUR);
    }

    @Test(description = "TC-FR-11: Verify EUR period days are in ascending order")
    public void eurPeriodsAreAscendingTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validatePeriodsAreInAscendingDayOrder(EUR);
    }

    @Test(description = "TC-FR-12: Verify EUR period days match expected values: 7,14,31,60,91,181,273,365,730")
    public void eurExpectedDaysMatchTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateExpectedDaysForCurrency(EUR);
    }

    @Test(description = "TC-FR-13: Verify USD has exactly 9 forward rate periods")
    public void usdHasExpectedPeriodCountTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateEachCurrencyHasExpectedPeriodCount(USD);
    }

    @Test(description = "TC-FR-14: Verify USD period days are in ascending order")
    public void usdPeriodsAreAscendingTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validatePeriodsAreInAscendingDayOrder(USD);
    }

    @Test(description = "TC-FR-15: Verify USD period days match expected values: 7,14,31,60,91,181,273,365,730")
    public void usdExpectedDaysMatchTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateExpectedDaysForCurrency(USD);
    }

    @Test(description = "TC-FR-16: Verify all EUR forward rate fields are non-null")
    public void eurAllFieldsNonNullTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateAllForwardRateFieldsAreNotNull(EUR);
    }

    @Test(description = "TC-FR-17: Verify EUR forward rate iso1 is EUR and iso2 is GEL")
    public void eurIsoCodesInsideRatesTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateIsoCodesInsideForwardRates(EUR);
    }

    @Test(description = "TC-FR-18: Verify EUR askForwardRate > bidForwardRate for all periods")
    public void eurAskRateGreaterThanBidRateTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateAskRateIsAlwaysGreaterThanBidRate(EUR);
    }

    @Test(description = "TC-FR-19: Verify EUR askForwardPoint > bidForwardPoint for all periods")
    public void eurAskPointGreaterThanBidPointTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateAskPointIsAlwaysGreaterThanBidPoint(EUR);
    }

    @Test(description = "TC-FR-20: Verify EUR askForwardInterest > bidForwardInterest for all periods")
    public void eurAskInterestGreaterThanBidInterestTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateAskInterestIsAlwaysGreaterThanBidInterest(EUR);
    }

    @Test(description = "TC-FR-21: Verify EUR bid/ask forward rates are positive")
    public void eurForwardRatesArePositiveTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateForwardRatesArePositive(EUR);
    }

    @Test(description = "TC-FR-22: Verify EUR forward points are non-negative")
    public void eurForwardPointsAreNonNegativeTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateForwardPointsArePositive(EUR);
    }

    @Test(description = "TC-FR-23: Verify EUR forward interest rates are within 0–20% range")
    public void eurForwardInterestWithinRangeTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateForwardInterestRatesAreWithinRange(EUR);
    }

    @Test(description = "TC-FR-24: Verify all USD forward rate fields are non-null")
    public void usdAllFieldsNonNullTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateAllForwardRateFieldsAreNotNull(USD);
    }

    @Test(description = "TC-FR-25: Verify USD forward rate iso1 is USD and iso2 is GEL")
    public void usdIsoCodesInsideRatesTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateIsoCodesInsideForwardRates(USD);
    }

    @Test(description = "TC-FR-26: Verify USD askForwardRate > bidForwardRate for all periods")
    public void usdAskRateGreaterThanBidRateTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateAskRateIsAlwaysGreaterThanBidRate(USD);
    }

    @Test(description = "TC-FR-27: Verify USD askForwardPoint > bidForwardPoint for all periods")
    public void usdAskPointGreaterThanBidPointTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateAskPointIsAlwaysGreaterThanBidPoint(USD);
    }

    @Test(description = "TC-FR-28: Verify USD askForwardInterest > bidForwardInterest for all periods")
    public void usdAskInterestGreaterThanBidInterestTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateAskInterestIsAlwaysGreaterThanBidInterest(USD);
    }

    @Test(description = "TC-FR-29: Verify USD bid/ask forward rates are positive")
    public void usdForwardRatesArePositiveTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateForwardRatesArePositive(USD);
    }

    @Test(description = "TC-FR-30: Verify USD forward points are non-negative")
    public void usdForwardPointsAreNonNegativeTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateForwardPointsArePositive(USD);
    }

    @Test(description = "TC-FR-31: Verify USD forward interest rates are within 0–20% range")
    public void usdForwardInterestWithinRangeTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateForwardInterestRatesAreWithinRange(USD);
    }

    @Test(description = "TC-FR-32: Verify EUR bidForwardRate increases with longer durations")
    public void eurBidRatesIncreaseOverTimeTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateBidForwardRatesIncreaseOverTime(EUR);
    }

    @Test(description = "TC-FR-33: Verify EUR askForwardRate increases with longer durations")
    public void eurAskRatesIncreaseOverTimeTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateAskForwardRatesIncreaseOverTime(EUR);
    }

    @Test(description = "TC-FR-34: Verify USD bidForwardRate increases with longer durations")
    public void usdBidRatesIncreaseOverTimeTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateBidForwardRatesIncreaseOverTime(USD);
    }

    @Test(description = "TC-FR-35: Verify USD askForwardRate increases with longer durations")
    public void usdAskRatesIncreaseOverTimeTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateAskForwardRatesIncreaseOverTime(USD);
    }

    @Test(description = "TC-FR-36: Full happy path — EUR all validations combined")
    public void eurFullValidationTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateStatusCode(200)
                .validateContentTypeIsJson()
                .validateRatesListIsNotEmpty()
                .validateCurrencyGroupExists(EUR)
                .validateEachCurrencyHasExpectedPeriodCount(EUR)
                .validateExpectedDaysForCurrency(EUR)
                .validatePeriodsAreInAscendingDayOrder(EUR)
                .validateAllForwardRateFieldsAreNotNull(EUR)
                .validateIsoCodesInsideForwardRates(EUR)
                .validateAskRateIsAlwaysGreaterThanBidRate(EUR)
                .validateAskPointIsAlwaysGreaterThanBidPoint(EUR)
                .validateAskInterestIsAlwaysGreaterThanBidInterest(EUR)
                .validateForwardRatesArePositive(EUR)
                .validateForwardPointsArePositive(EUR)
                .validateForwardInterestRatesAreWithinRange(EUR)
                .validateBidForwardRatesIncreaseOverTime(EUR)
                .validateAskForwardRatesIncreaseOverTime(EUR);
    }

    @Test(description = "TC-FR-37: Full happy path — USD all validations combined")
    public void usdFullValidationTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateStatusCode(200)
                .validateContentTypeIsJson()
                .validateRatesListIsNotEmpty()
                .validateCurrencyGroupExists(USD)
                .validateEachCurrencyHasExpectedPeriodCount(USD)
                .validateExpectedDaysForCurrency(USD)
                .validatePeriodsAreInAscendingDayOrder(USD)
                .validateAllForwardRateFieldsAreNotNull(USD)
                .validateIsoCodesInsideForwardRates(USD)
                .validateAskRateIsAlwaysGreaterThanBidRate(USD)
                .validateAskPointIsAlwaysGreaterThanBidPoint(USD)
                .validateAskInterestIsAlwaysGreaterThanBidInterest(USD)
                .validateForwardRatesArePositive(USD)
                .validateForwardPointsArePositive(USD)
                .validateForwardInterestRatesAreWithinRange(USD)
                .validateBidForwardRatesIncreaseOverTime(USD)
                .validateAskForwardRatesIncreaseOverTime(USD);
    }

    @Test(description = "TC-FR-38: Verify forward rates response time is under 3000ms")
    public void forwardRatesResponseTimeTest() {
        new ForwardRateSteps()
                .fetchForwardRates(KA_GE)
                .validateResponseTimeIsUnder(3000);
    }

    @Test(description = "TC-FR-39: Verify request with invalid locale returns 4xx or fallback response")
    public void invalidLocaleReturnsErrorTest() {
        new ForwardRateSteps()
                .fetchForwardRatesExpectingError("xx-XX", 400);
    }

    @Test(description = "TC-FR-40: Verify request with empty locale string returns 4xx error")
    public void emptyLocaleReturnsErrorTest() {
        new ForwardRateSteps()
                .fetchForwardRatesExpectingError("", 400);
    }

    @Test(description = "TC-FR-41: Verify request with no locale parameter still returns a valid response")
    public void noLocaleParamReturnsValidResponseTest() {
        new ForwardRateSteps()
                .fetchForwardRatesNoParams()
                .validateStatusCode(200);
    }

    @Test(description = "TC-FR-42: Verify request with en-US locale returns valid response")
    public void enUsLocaleReturnsValidResponseTest() {
        new ForwardRateSteps()
                .fetchForwardRates(EN_US)
                .validateStatusCode(200)
                .validateRatesListIsNotEmpty();
    }

    @Test(description = "TC-FR-43: Verify request with numeric locale value returns 4xx error")
    public void numericLocaleReturnsErrorTest() {
        new ForwardRateSteps()
                .fetchForwardRatesExpectingError("12345", 400);
    }

    @Test(description = "TC-FR-44: Verify request with SQL injection in locale param is handled safely")
    public void sqlInjectionLocaleIsHandledSafelyTest() {
        new ForwardRateSteps()
                .fetchForwardRatesExpectingError("'; DROP TABLE rates; --", 400);
    }
}

