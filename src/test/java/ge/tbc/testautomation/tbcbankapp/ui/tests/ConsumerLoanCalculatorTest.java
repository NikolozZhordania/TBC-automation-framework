package ge.tbc.testautomation.tbcbankapp.ui.tests;

import ge.tbc.testautomation.tbcbankapp.ui.base.BaseDeviceTest;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.tbcbankapp.ui.data.constants.Constants.ConsumerLoan.*;

@Epic("TBC Bank Web Application")
@Feature("Consumer Loan Calculator")
@Test(description = "DEV-T4 - End-to-End Validation of Monthly Consumer Loan Calculator Flow")
public class ConsumerLoanCalculatorTest extends BaseDeviceTest {

    public ConsumerLoanCalculatorTest(String device, String browser) {
        super(device, browser);
    }

    public ConsumerLoanCalculatorTest() {
        super();
    }

    @Story("Homepage Access and Navigation Menu Validation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that the homepage loads successfully, main UI elements are visible, " +
            "and navigation menu can be opened before starting the consumer loan flow.")
    @Test(description = "Step 1: Validate homepage loads correctly and navigation menu is accessible", priority = 1)
    public void homepageAccess() {
        homeSteps
                .openHomepage()
                .verifyHomepageLoaded()
                .verifyMenuVisibility()
                .openNavigationMenu();
    }

    @Story("Consumer Loan Overview Page Validation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that the user can navigate to the Consumer Loan Overview page " +
            "and validate that the correct header and URL are displayed.")
    @Test(description = "Step 2: Navigate to Consumer Loan Overview page and validate header and URL", priority = 2)
    public void consumerLoanPageSelection() {
        homeSteps
                .openConsumerLoanPage();

        consumerLoanOverviewSteps
                .verifyHeader()
                .verifyConsumerLoanOverviewPageURL();
    }

    @Story("Consumer Loan Detailed Page Navigation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that clicking the conditions button redirects the user to the " +
            "detailed Consumer Loan page and validates correct page URL and header.")
    @Test(description = "Step 3: Navigate to detailed Consumer Loan page via Conditions button", priority = 3)
    public void consumerLoanPageNavigation() {
        consumerLoanOverviewSteps
                .clickConditionsButton();

        consumerLoanSteps
                .verifyConsumerLoanPageURL()
                .verifyHeader();
    }

    @Story("Loan Amount Input Validation")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify that entering a valid loan amount updates the loan amount indicator correctly " +
            "and reflects the expected principal value.")
    @Test(description = "Step 4: Enter loan amount and validate loan amount indicator updates correctly", priority = 4)
    public void loanAmountEntry() {
        consumerLoanSteps
                .resolveAndFillLoanAmountInput(LOAN_AMOUNT)
                .verifyLoanAmountIndicator(LOAN_AMOUNT);
    }

    @Story("Loan Duration Input Validation")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify that entering the number of months correctly updates the duration indicator " +
            "and reflects the expected loan term.")
    @Test(description = "Step 5: Enter month count and validate month count indicator updates correctly", priority = 5)
    public void monthsCountEntry() {
        consumerLoanSteps
                .resolveAndFillMonthCountInput(MONTH_COUNT)
                .verifyMonthCountIndicator(MONTH_COUNT);
    }

    @Story("Monthly Payment Calculation Verification")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify that the calculated monthly payment is displayed, contains the correct currency, " +
            "and is within the allowed tolerance compared to the expected calculated value.")
    @Test(description = "Step 6: Validate calculated monthly payment is displayed and within tolerance", priority = 6)
    public void loanCalculatorValidation() {
        consumerLoanSteps
                .waitForPaymentContainer()
                .verifyPaymentContainerHasCurrency()
                .assertPaymentWithinTolerance(DELTA);
    }
}