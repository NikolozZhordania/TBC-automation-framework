package ge.tbc.testautomation.tbcbankapp.api.steps;

import ge.tbc.testautomation.tbcbankapp.api.client.ForwardRateAPI;
import ge.tbc.testautomation.tbcbankapp.api.data.constants.ForwardRateConstants.*;
import ge.tbc.testautomation.tbcbankapp.api.data.models.response.forwardrate.CurrencyForwardRates;
import ge.tbc.testautomation.tbcbankapp.api.data.models.response.forwardrate.ForwardRate;
import ge.tbc.testautomation.tbcbankapp.api.data.models.response.forwardrate.ForwardRateResponse;
import io.restassured.response.Response;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ForwardRateSteps {

    private final ForwardRateAPI api = new ForwardRateAPI();
    private ForwardRateResponse forwardRatesResponse;
    private Response rawResponse;

    public ForwardRateSteps fetchForwardRates(String locale) {
        this.rawResponse = api.getForwardRates(locale);
        this.forwardRatesResponse = rawResponse
                .then()
                .statusCode(200)
                .extract()
                .as(ForwardRateResponse.class);
        System.out.printf("Fetched forward rates for locale: %s — %d currency groups found%n",
                locale, forwardRatesResponse.getRates().size());
        return this;
    }

    public ForwardRateSteps fetchForwardRatesExpectingError(String locale, int expectedStatusCode) {
        this.rawResponse = api.getForwardRatesWithInvalidLocale(locale);
        rawResponse.then().statusCode(expectedStatusCode);
        System.out.printf("Received expected status %d for locale: %s%n", expectedStatusCode, locale);
        return this;
    }

    public ForwardRateSteps fetchForwardRatesNoParams() {
        this.rawResponse = api.getForwardRatesNoParams();
        return this;
    }

    public ForwardRateSteps validateStatusCode(int expectedCode) {
        assertThat("Status code mismatch", rawResponse.statusCode(), equalTo(expectedCode));
        return this;
    }

    public ForwardRateSteps validateContentTypeIsJson() {
        assertThat("Content-Type should be JSON",
                rawResponse.contentType(), containsString("application/json"));
        return this;
    }

    public ForwardRateSteps validateRatesListIsNotEmpty() {
        assertThat("rates list must not be null", forwardRatesResponse.getRates(), notNullValue());
        assertThat("rates list must not be empty", forwardRatesResponse.getRates(), not(empty()));
        return this;
    }

    public ForwardRateSteps validateRatesCountEquals(int expectedCount) {
        assertThat("Unexpected number of currency groups in rates",
                forwardRatesResponse.getRates().size(), equalTo(expectedCount));
        return this;
    }

    public ForwardRateSteps validateUpdateDateIsPresent() {
        assertThat("updateDate must not be null", forwardRatesResponse.getUpdateDate(), notNullValue());
        assertThat("updateDate must not be empty", forwardRatesResponse.getUpdateDate(), not(emptyString()));
        return this;
    }

    public ForwardRateSteps validateUpdateDateFormat() {
        assertThat("updateDate should match ISO 8601 format",
                forwardRatesResponse.getUpdateDate(), matchesPattern("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.*"));
        return this;
    }


    public ForwardRateSteps validateCurrencyGroupExists(String isoCode) {
        boolean exists = forwardRatesResponse.getRates().stream()
                .anyMatch(r -> isoCode.equalsIgnoreCase(r.getIso()));
        assertThat(String.format("Currency group '%s' should exist in response", isoCode), exists, is(true));
        return this;
    }

    public ForwardRateSteps validateAllCurrencyGroupsHaveIsoCode() {
        forwardRatesResponse.getRates().forEach(group -> {
            assertThat("iso field in currency group must not be null", group.getIso(), notNullValue());
            assertThat("iso field must not be empty", group.getIso(), not(emptyString()));
        });
        return this;
    }

    public ForwardRateSteps validateEachCurrencyHasExpectedPeriodCount(String isoCode) {
        CurrencyForwardRates group = getCurrencyGroup(isoCode);
        assertThat(String.format("%s should have %d forward rate periods", isoCode, ExpectedPeriods.EXPECTED_PERIOD_COUNT),
                group.getForwardRates().size(), equalTo(ExpectedPeriods.EXPECTED_PERIOD_COUNT));
        return this;
    }

    public ForwardRateSteps validatePeriodsAreInAscendingDayOrder(String isoCode) {
        List<ForwardRate> rates = getCurrencyGroup(isoCode).getForwardRates();
        for (int i = 1; i < rates.size(); i++) {
            assertThat(
                    String.format("Day at index %d should be greater than at index %d", i, i - 1),
                    rates.get(i).getDay(), greaterThan(rates.get(i - 1).getDay())
            );
        }
        return this;
    }

    public ForwardRateSteps validateExpectedDaysForCurrency(String isoCode) {
        List<ForwardRate> rates = getCurrencyGroup(isoCode).getForwardRates();
        int[] expectedDays = ExpectedPeriods.EXPECTED_DAYS;
        assertThat("Period count mismatch", rates.size(), equalTo(expectedDays.length));
        for (int i = 0; i < expectedDays.length; i++) {
            assertThat(
                    String.format("Day at index %d should be %d", i, expectedDays[i]),
                    rates.get(i).getDay(), equalTo(expectedDays[i])
            );
        }
        return this;
    }

    public ForwardRateSteps validateAllForwardRateFieldsAreNotNull(String isoCode) {
        getCurrencyGroup(isoCode).getForwardRates().forEach(rate -> {
            assertThat("iso1 must not be null", rate.getIso1(), notNullValue());
            assertThat("iso2 must not be null", rate.getIso2(), notNullValue());
            assertThat("period must not be null", rate.getPeriod(), notNullValue());
            assertThat("day must not be null", rate.getDay(), notNullValue());
            assertThat("bidForwardPoint must not be null", rate.getBidForwardPoint(), notNullValue());
            assertThat("bidForwardInterest must not be null", rate.getBidForwardInterest(), notNullValue());
            assertThat("bidForwardRate must not be null", rate.getBidForwardRate(), notNullValue());
            assertThat("askForwardPoint must not be null", rate.getAskForwardPoint(), notNullValue());
            assertThat("askForwardInterest must not be null", rate.getAskForwardInterest(), notNullValue());
            assertThat("askForwardRate must not be null", rate.getAskForwardRate(), notNullValue());
        });
        return this;
    }

    public ForwardRateSteps validateIsoCodesInsideForwardRates(String isoCode) {
        getCurrencyGroup(isoCode).getForwardRates().forEach(rate -> {
            assertThat("iso1 inside forwardRate should match parent currency",
                    rate.getIso1(), equalToIgnoringCase(isoCode));
            assertThat("iso2 inside forwardRate should be GEL",
                    rate.getIso2(), equalTo(Currencies.GEL));
        });
        return this;
    }

    public ForwardRateSteps validateAskRateIsAlwaysGreaterThanBidRate(String isoCode) {
        getCurrencyGroup(isoCode).getForwardRates().forEach(rate -> {
            assertThat(
                    String.format("askForwardRate should be > bidForwardRate for period '%s'", rate.getPeriod()),
                    rate.getAskForwardRate(), greaterThan(rate.getBidForwardRate())
            );
        });
        return this;
    }

    public ForwardRateSteps validateAskPointIsAlwaysGreaterThanBidPoint(String isoCode) {
        getCurrencyGroup(isoCode).getForwardRates().forEach(rate -> {
            assertThat(
                    String.format("askForwardPoint should be > bidForwardPoint for period '%s'", rate.getPeriod()),
                    rate.getAskForwardPoint(), greaterThan(rate.getBidForwardPoint())
            );
        });
        return this;
    }

    public ForwardRateSteps validateAskInterestIsAlwaysGreaterThanBidInterest(String isoCode) {
        getCurrencyGroup(isoCode).getForwardRates().forEach(rate -> {
            assertThat(
                    String.format("askForwardInterest should be > bidForwardInterest for period '%s'", rate.getPeriod()),
                    rate.getAskForwardInterest(), greaterThan(rate.getBidForwardInterest())
            );
        });
        return this;
    }

    public ForwardRateSteps validateForwardRatesArePositive(String isoCode) {
        getCurrencyGroup(isoCode).getForwardRates().forEach(rate -> {
            assertThat("bidForwardRate must be positive", rate.getBidForwardRate(),
                    greaterThan(ExpectedRates.MIN_FORWARD_RATE));
            assertThat("askForwardRate must be positive", rate.getAskForwardRate(),
                    greaterThan(ExpectedRates.MIN_FORWARD_RATE));
        });
        return this;
    }

    public ForwardRateSteps validateForwardPointsArePositive(String isoCode) {
        getCurrencyGroup(isoCode).getForwardRates().forEach(rate -> {
            assertThat("bidForwardPoint must be >= 0", rate.getBidForwardPoint(),
                    greaterThanOrEqualTo(ExpectedRates.MIN_FORWARD_POINT));
            assertThat("askForwardPoint must be >= 0", rate.getAskForwardPoint(),
                    greaterThanOrEqualTo(ExpectedRates.MIN_FORWARD_POINT));
        });
        return this;
    }

    public ForwardRateSteps validateForwardInterestRatesAreWithinRange(String isoCode) {
        getCurrencyGroup(isoCode).getForwardRates().forEach(rate -> {
            assertThat("bidForwardInterest should be within range",
                    rate.getBidForwardInterest(), both(
                            greaterThanOrEqualTo(ExpectedRates.MIN_FORWARD_INTEREST))
                            .and(lessThanOrEqualTo(ExpectedRates.MAX_FORWARD_INTEREST)));
            assertThat("askForwardInterest should be within range",
                    rate.getAskForwardInterest(), both(
                            greaterThanOrEqualTo(ExpectedRates.MIN_FORWARD_INTEREST))
                            .and(lessThanOrEqualTo(ExpectedRates.MAX_FORWARD_INTEREST)));
        });
        return this;
    }

    public ForwardRateSteps validateBidForwardRatesIncreaseOverTime(String isoCode) {
        List<ForwardRate> rates = getCurrencyGroup(isoCode).getForwardRates();
        for (int i = 1; i < rates.size(); i++) {
            assertThat(
                    String.format("bidForwardRate should increase at period '%s' vs '%s'",
                            rates.get(i).getPeriod(), rates.get(i - 1).getPeriod()),
                    rates.get(i).getBidForwardRate(), greaterThan(rates.get(i - 1).getBidForwardRate())
            );
        }
        return this;
    }

    public ForwardRateSteps validateAskForwardRatesIncreaseOverTime(String isoCode) {
        List<ForwardRate> rates = getCurrencyGroup(isoCode).getForwardRates();
        for (int i = 1; i < rates.size(); i++) {
            assertThat(
                    String.format("askForwardRate should increase at period '%s' vs '%s'",
                            rates.get(i).getPeriod(), rates.get(i - 1).getPeriod()),
                    rates.get(i).getAskForwardRate(), greaterThan(rates.get(i - 1).getAskForwardRate())
            );
        }
        return this;
    }


    public ForwardRateSteps validateResponseTimeIsUnder(long maxMillis) {
        long responseTime = rawResponse.getTime();
        assertThat(String.format("Response time %d ms should be under %d ms", responseTime, maxMillis),
                responseTime, lessThan(maxMillis));
        return this;
    }

    private CurrencyForwardRates getCurrencyGroup(String isoCode) {
        Optional<CurrencyForwardRates> group = forwardRatesResponse.getRates().stream()
                .filter(r -> isoCode.equalsIgnoreCase(r.getIso()))
                .findFirst();
        assertThat(String.format("Currency group '%s' not found in response", isoCode),
                group.isPresent(), is(true));
        return group.get();
    }
}
