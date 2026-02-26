package ge.tbc.testautomation.tbcbankapp.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import ge.tbc.testautomation.tbcbankapp.pages.ConsumerLoanPage;

import java.util.List;
import java.util.stream.Collectors;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static ge.tbc.testautomation.tbcbankapp.data.Constants.CONSUMER_LOAN_DIGITAL_URL;

public class ConsumerLoanSteps {

    private final Page page;
    private final ConsumerLoanPage consumerLoanPage;

    public ConsumerLoanSteps(Page page) {
        this.page = page;
        this.consumerLoanPage = new ConsumerLoanPage(page);
    }

    // ── Page Verification ────────────────────────────────────────────────────

    public ConsumerLoanSteps verifyConsumerLoanPageURL() {
        assertThat(page).hasURL(CONSUMER_LOAN_DIGITAL_URL);
        return this;
    }

    public ConsumerLoanSteps verifyHeader() {
        assertThat(consumerLoanPage.pageHeader).isVisible();
        return this;
    }

    // ── Loan Amount ──────────────────────────────────────────────────────────

    public ConsumerLoanSteps resolveAndFillLoanAmountInput(String amount) {
        Locator input = consumerLoanPage.moneyAmountInput.isVisible()
                ? consumerLoanPage.moneyAmountInput
                : consumerLoanPage.moneyAmountInputMobile;
        input.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        input.fill("");
        input.fill(amount);
        return this;
    }

    public ConsumerLoanSteps verifyLoanAmountIndicator(String expectedAmount) {
        String digits = expectedAmount.replaceAll("[^0-9]", "");
        assertThat(consumerLoanPage.loanAmountIndicator)
                .containsText(digits.substring(0, 1));
        return this;
    }

    public ConsumerLoanSteps enterLoanAmount(String amount) {
        return resolveAndFillLoanAmountInput(amount)
                .verifyLoanAmountIndicator(amount);
    }

    // ── Month Count ──────────────────────────────────────────────────────────

    public ConsumerLoanSteps resolveAndFillMonthCountInput(String months) {
        Locator input = consumerLoanPage.monthCountInput.isVisible()
                ? consumerLoanPage.monthCountInput
                : consumerLoanPage.monthCountInputMobile;
        input.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        input.fill("");
        input.fill(months);
        return this;
    }

    public ConsumerLoanSteps verifyMonthCountIndicator(String expectedMonths) {
        String formattedMonths = expectedMonths + " თვე";
        String actualText = consumerLoanPage.monthCountIndicator.textContent();
        if (!actualText.contains(formattedMonths)) {
            throw new AssertionError("Month count indicator mismatch! Expected: " + formattedMonths + ", Actual: " + actualText);
        }
        return this;
    }

    public ConsumerLoanSteps enterMonthCount(String months) {
        return resolveAndFillMonthCountInput(months)
                .verifyMonthCountIndicator(months);
    }

    // ── Payment Validation ───────────────────────────────────────────────────

    public ConsumerLoanSteps waitForPaymentContainer() {
        consumerLoanPage.estimatedMonthlyPaymentContainer
                .waitFor(new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(5000));
        return this;
    }

    public ConsumerLoanSteps verifyPaymentContainerHasCurrency() {
        if (!consumerLoanPage.estimatedMonthlyPaymentContainer.textContent().contains("₾")) {
            throw new AssertionError("Payment container does not contain currency symbol ₾");
        }
        return this;
    }

    private double parsePrincipal() {
        return Double.parseDouble(
                consumerLoanPage.loanAmountIndicator
                        .textContent()
                        .replaceAll("[₾,\\s\u00A0]", "")
                        .trim()
        );
    }

    private int parseTermMonths() {
        return Integer.parseInt(
                consumerLoanPage.monthCountIndicator
                        .textContent()
                        .replaceAll("[^\\d]", "")
                        .trim()
        );
    }

    private double parseNominalRate() {
        return Double.parseDouble(
                consumerLoanPage.nominalPercentageRate
                        .textContent()
                        .replaceAll("[^\\d.,]", "")
                        .replace(",", ".")
                        .trim()
        ) / 100;
    }

    private double parseSitePayment() {
        List<String> spanTexts = consumerLoanPage.estimatedMonthlyPaymentContainer
                .locator("span")
                .allTextContents();
        return Double.parseDouble(
                spanTexts.stream()
                        .collect(Collectors.joining())
                        .replaceAll("[₾,\\s\u00A0]", "")
                        .trim()
        );
    }

    private double calculateAnnuityPayment(double principal, int termMonths, double nominalRate) {
        double monthlyRate = nominalRate / 12;
        double payment = (principal * monthlyRate * Math.pow(1 + monthlyRate, termMonths)) /
                (Math.pow(1 + monthlyRate, termMonths) - 1);
        return Math.round(payment * 100.0) / 100.0;
    }

    public ConsumerLoanSteps assertPaymentWithinTolerance(double tolerancePercent) {
        double principal = parsePrincipal();
        int termMonths = parseTermMonths();
        double nominalRate = parseNominalRate();
        double sitePayment = parseSitePayment();
        double calculatedPayment = calculateAnnuityPayment(principal, termMonths, nominalRate);

        double relativeDiff = Math.abs(calculatedPayment - sitePayment) / sitePayment;
        if (relativeDiff > tolerancePercent) {
            throw new AssertionError(String.format(
                    "Monthly payment validation failed! Calculated: %.2f, Site: %.2f, Diff: %.4f",
                    calculatedPayment, sitePayment, relativeDiff
            ));
        }
        return this;
    }

}