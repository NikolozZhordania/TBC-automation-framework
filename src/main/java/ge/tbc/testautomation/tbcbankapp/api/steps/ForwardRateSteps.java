package ge.tbc.testautomation.tbcbankapp.api.steps;

import ge.tbc.testautomation.tbcbankapp.api.client.ForwardRateAPI;
import ge.tbc.testautomation.tbcbankapp.api.data.constants.ForwardRateConstants.*;
import ge.tbc.testautomation.tbcbankapp.api.data.models.response.forwardrate.CurrencyForwardRates;
import ge.tbc.testautomation.tbcbankapp.api.data.models.response.forwardrate.ForwardRate;
import ge.tbc.testautomation.tbcbankapp.api.data.models.response.forwardrate.ForwardRateResponse;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ForwardRateSteps {

    private final ForwardRateAPI api = new ForwardRateAPI();
    private ForwardRateResponse forwardRatesResponse;
    private Response rawResponse;

    @Step("Fetch forward rates for locale: {locale}")
    public ForwardRateSteps fetchForwardRates(String locale) {
        this.rawResponse = api.getForwardRates(locale);
        this.forwardRatesResponse = rawResponse
                .then()
                .statusCode(200)
                .extract()
                .as(ForwardRateResponse.class);
        System.out.printf("Fetched forward rates for locale: %s — %d currency groups%n",
                locale, forwardRatesResponse.getRates().size());
        return this;
    }

    @Step("Fetch forward rates with invalid locale: {locale}, expecting status: {expectedStatusCode}")
    public ForwardRateSteps fetchForwardRatesExpectingError(String locale, int expectedStatusCode) {
        this.rawResponse = api.getForwardRatesWithInvalidLocale(locale);
        System.out.printf("Received %d for locale: %s%n", rawResponse.statusCode(), locale);
        return this;
    }

    @Step("Fetch forward rates with no params")
    public ForwardRateSteps fetchForwardRatesNoParams() {
        this.rawResponse = api.getForwardRatesNoParams();
        return this;
    }

    @Step("Validate status code is {expectedCode}")
    public ForwardRateSteps validateStatusCode(int expectedCode) {
        assertThat("Status code", rawResponse.statusCode(), equalTo(expectedCode));
        return this;
    }

    @Step("Validate Content-Type is application/json")
    public ForwardRateSteps validateContentTypeIsJson() {
        assertThat("Content-Type", rawResponse.contentType(), containsString("application/json"));
        return this;
    }

    @Step("Validate response time is under {maxMillis}ms")
    public ForwardRateSteps validateResponseTimeIsUnder(long maxMillis) {
        assertThat(
                String.format("Response time %dms exceeds limit of %dms", rawResponse.getTime(), maxMillis),
                rawResponse.getTime(), lessThan(maxMillis)
        );
        return this;
    }

    @Step("Validate rates list is not null")
    public ForwardRateSteps validateRatesListIsNotNull() {
        assertThat("rates list must not be null", forwardRatesResponse.getRates(), notNullValue());
        return this;
    }

    @Step("Validate rates list is not empty")
    public ForwardRateSteps validateRatesListIsNotEmpty() {
        assertThat("rates list must not be empty", forwardRatesResponse.getRates(), not(empty()));
        return this;
    }

    @Step("Validate rates count equals {expectedCount}")
    public ForwardRateSteps validateRatesCountEquals(int expectedCount) {
        assertThat("Number of currency groups",
                forwardRatesResponse.getRates().size(), equalTo(expectedCount));
        return this;
    }

    @Step("Validate updateDate is not null")
    public ForwardRateSteps validateUpdateDateIsNotNull() {
        assertThat("updateDate must not be null", forwardRatesResponse.getUpdateDate(), notNullValue());
        return this;
    }

    @Step("Validate updateDate is not empty")
    public ForwardRateSteps validateUpdateDateIsNotEmpty() {
        assertThat("updateDate must not be empty", forwardRatesResponse.getUpdateDate(), not(emptyString()));
        return this;
    }

    @Step("Validate updateDate matches ISO 8601 format")
    public ForwardRateSteps validateUpdateDateFormat() {
        assertThat("updateDate ISO 8601 format",
                forwardRatesResponse.getUpdateDate(),
                matchesPattern("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.*"));
        return this;
    }

    @Step("Validate updateDate is present (not null and not empty)")
    public ForwardRateSteps validateUpdateDateIsPresent() {
        return validateUpdateDateIsNotNull().validateUpdateDateIsNotEmpty();
    }

    @Step("Validate currency group '{isoCode}' exists")
    public ForwardRateSteps validateCurrencyGroupExists(String isoCode) {
        boolean exists = forwardRatesResponse.getRates().stream()
                .anyMatch(r -> isoCode.equalsIgnoreCase(r.getIso()));
        assertThat(String.format("Currency group '%s' should exist", isoCode), exists, is(true));
        return this;
    }

    @Step("Validate all currency groups have a non-empty ISO code")
    public ForwardRateSteps validateAllCurrencyGroupsHaveIsoCode() {
        forwardRatesResponse.getRates().forEach(group -> {
            assertThat("iso field must not be null", group.getIso(), notNullValue());
            assertThat("iso field must not be empty", group.getIso(), not(emptyString()));
        });
        return this;
    }

    @Step("Validate period count for currency '{isoCode}'")
    public ForwardRateSteps validatePeriodCount(String isoCode) {
        assertThat(
                String.format("%s should have %d periods", isoCode, ExpectedPeriods.EXPECTED_PERIOD_COUNT),
                getCurrencyGroup(isoCode).getForwardRates().size(),
                equalTo(ExpectedPeriods.EXPECTED_PERIOD_COUNT)
        );
        return this;
    }

    @Step("Validate periods are in ascending day order for currency '{isoCode}'")
    public ForwardRateSteps validatePeriodsAreInAscendingDayOrder(String isoCode) {
        List<ForwardRate> rates = getCurrencyGroup(isoCode).getForwardRates();
        for (int i = 1; i < rates.size(); i++) {
            assertThat(
                    String.format("Day[%d] should be > Day[%d]", i, i - 1),
                    rates.get(i).getDay(), greaterThan(rates.get(i - 1).getDay())
            );
        }
        return this;
    }

    @Step("Validate expected days for currency '{isoCode}'")
    public ForwardRateSteps validateExpectedDays(String isoCode) {
        List<ForwardRate> rates = getCurrencyGroup(isoCode).getForwardRates();
        int[] expectedDays = ExpectedPeriods.EXPECTED_DAYS;
        assertThat("Period count mismatch", rates.size(), equalTo(expectedDays.length));
        for (int i = 0; i < expectedDays.length; i++) {
            assertThat(
                    String.format("Day[%d] should be %d", i, expectedDays[i]),
                    rates.get(i).getDay(), equalTo(expectedDays[i])
            );
        }
        return this;
    }

    @Step("Validate all forward rate fields are not null for currency '{isoCode}'")
    public ForwardRateSteps validateAllForwardRateFieldsAreNotNull(String isoCode) {
        getCurrencyGroup(isoCode).getForwardRates().forEach(rate -> {
            assertThat("iso1",               rate.getIso1(),               notNullValue());
            assertThat("iso2",               rate.getIso2(),               notNullValue());
            assertThat("period",             rate.getPeriod(),             notNullValue());
            assertThat("day",                rate.getDay(),                notNullValue());
            assertThat("bidForwardPoint",    rate.getBidForwardPoint(),    notNullValue());
            assertThat("bidForwardInterest", rate.getBidForwardInterest(), notNullValue());
            assertThat("bidForwardRate",     rate.getBidForwardRate(),     notNullValue());
            assertThat("askForwardPoint",    rate.getAskForwardPoint(),    notNullValue());
            assertThat("askForwardInterest", rate.getAskForwardInterest(), notNullValue());
            assertThat("askForwardRate",     rate.getAskForwardRate(),     notNullValue());
        });
        return this;
    }

    @Step("Validate iso1 inside rates equals '{isoCode}'")
    public ForwardRateSteps validateIso1InsideRatesEquals(String isoCode) {
        getCurrencyGroup(isoCode).getForwardRates().forEach(rate ->
                assertThat("iso1 should match parent currency",
                        rate.getIso1(), equalToIgnoringCase(isoCode))
        );
        return this;
    }

    @Step("Validate iso2 inside rates is GEL for currency '{isoCode}'")
    public ForwardRateSteps validateIso2InsideRatesIsGel(String isoCode) {
        getCurrencyGroup(isoCode).getForwardRates().forEach(rate ->
                assertThat("iso2 should be GEL",
                        rate.getIso2(), equalTo(Currencies.GEL))
        );
        return this;
    }

    @Step("Validate ask rate is always greater than bid rate for currency '{isoCode}'")
    public ForwardRateSteps validateAskRateIsAlwaysGreaterThanBidRate(String isoCode) {
        getCurrencyGroup(isoCode).getForwardRates().forEach(rate ->
                assertThat(
                        String.format("askForwardRate > bidForwardRate for period '%s'", rate.getPeriod()),
                        rate.getAskForwardRate(), greaterThan(rate.getBidForwardRate())
                )
        );
        return this;
    }

    @Step("Validate ask point is always greater than bid point for currency '{isoCode}'")
    public ForwardRateSteps validateAskPointIsAlwaysGreaterThanBidPoint(String isoCode) {
        getCurrencyGroup(isoCode).getForwardRates().forEach(rate ->
                assertThat(
                        String.format("askForwardPoint > bidForwardPoint for period '%s'", rate.getPeriod()),
                        rate.getAskForwardPoint(), greaterThan(rate.getBidForwardPoint())
                )
        );
        return this;
    }

    @Step("Validate ask interest is always greater than bid interest for currency '{isoCode}'")
    public ForwardRateSteps validateAskInterestIsAlwaysGreaterThanBidInterest(String isoCode) {
        getCurrencyGroup(isoCode).getForwardRates().forEach(rate ->
                assertThat(
                        String.format("askForwardInterest > bidForwardInterest for period '%s'", rate.getPeriod()),
                        rate.getAskForwardInterest(), greaterThan(rate.getBidForwardInterest())
                )
        );
        return this;
    }

    @Step("Validate forward rates are positive for currency '{isoCode}'")
    public ForwardRateSteps validateForwardRatesArePositive(String isoCode) {
        getCurrencyGroup(isoCode).getForwardRates().forEach(rate -> {
            assertThat("bidForwardRate must be positive",
                    rate.getBidForwardRate(), greaterThan(ExpectedRates.MIN_FORWARD_RATE));
            assertThat("askForwardRate must be positive",
                    rate.getAskForwardRate(), greaterThan(ExpectedRates.MIN_FORWARD_RATE));
        });
        return this;
    }

    @Step("Validate forward points are non-negative for currency '{isoCode}'")
    public ForwardRateSteps validateForwardPointsAreNonNegative(String isoCode) {
        getCurrencyGroup(isoCode).getForwardRates().forEach(rate -> {
            assertThat("bidForwardPoint must be >= 0",
                    rate.getBidForwardPoint(), greaterThanOrEqualTo(ExpectedRates.MIN_FORWARD_POINT));
            assertThat("askForwardPoint must be >= 0",
                    rate.getAskForwardPoint(), greaterThanOrEqualTo(ExpectedRates.MIN_FORWARD_POINT));
        });
        return this;
    }

    @Step("Validate forward interest rates are within range for currency '{isoCode}'")
    public ForwardRateSteps validateForwardInterestRatesAreWithinRange(String isoCode) {
        getCurrencyGroup(isoCode).getForwardRates().forEach(rate -> {
            assertThat("bidForwardInterest out of range", rate.getBidForwardInterest(),
                    both(greaterThanOrEqualTo(ExpectedRates.MIN_FORWARD_INTEREST))
                            .and(lessThanOrEqualTo(ExpectedRates.MAX_FORWARD_INTEREST)));
            assertThat("askForwardInterest out of range", rate.getAskForwardInterest(),
                    both(greaterThanOrEqualTo(ExpectedRates.MIN_FORWARD_INTEREST))
                            .and(lessThanOrEqualTo(ExpectedRates.MAX_FORWARD_INTEREST)));
        });
        return this;
    }

    @Step("Validate bid rates increase over time for currency '{isoCode}'")
    public ForwardRateSteps validateBidRatesIncreaseOverTime(String isoCode) {
        List<ForwardRate> rates = getCurrencyGroup(isoCode).getForwardRates();
        for (int i = 1; i < rates.size(); i++) {
            assertThat(
                    String.format("bidForwardRate should increase: '%s' > '%s'",
                            rates.get(i).getPeriod(), rates.get(i - 1).getPeriod()),
                    rates.get(i).getBidForwardRate(), greaterThan(rates.get(i - 1).getBidForwardRate())
            );
        }
        return this;
    }

    @Step("Validate ask rates increase over time for currency '{isoCode}'")
    public ForwardRateSteps validateAskRatesIncreaseOverTime(String isoCode) {
        List<ForwardRate> rates = getCurrencyGroup(isoCode).getForwardRates();
        for (int i = 1; i < rates.size(); i++) {
            assertThat(
                    String.format("askForwardRate should increase: '%s' > '%s'",
                            rates.get(i).getPeriod(), rates.get(i - 1).getPeriod()),
                    rates.get(i).getAskForwardRate(), greaterThan(rates.get(i - 1).getAskForwardRate())
            );
        }
        return this;
    }

    private CurrencyForwardRates getCurrencyGroup(String isoCode) {
        Optional<CurrencyForwardRates> group = forwardRatesResponse.getRates().stream()
                .filter(r -> isoCode.equalsIgnoreCase(r.getIso()))
                .findFirst();
        assertThat(String.format("Currency group '%s' not found", isoCode), group.isPresent(), is(true));
        return group.get();
    }
}