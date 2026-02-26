package ge.tbc.testautomation.tbcbankapp.tests;

import ge.tbc.testautomation.tbcbankapp.base.BaseTest;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.tbcbankapp.data.Constants.*;

@Test(description = "DEV-T4 Validate Monthly Loan Calculator")
public class ConsumerLoanCalculatorTest extends BaseTest {

    @Test(description = "DEV-T4 Step 1: Homepage access", priority = 1)
    public void homepageAccess() {
        homeSteps
                .openHomepage()
                .verifyHomepageLoaded()
                .openNavigationMenu();
    }

    @Test(description = "DEV-T4 Step 2: Consumer loan page selection", priority = 2)
    public void consumerLoanPageSelection() {
        homeSteps
                .openConsumerLoanPage();

        consumerLoanOverviewSteps
                .verifyHeader()
                .verifyConsumerLoanOverviewPageURL();
    }

    @Test(description = "DEV-T4 Step 3: Consumer Loan page navigation", priority = 3)
    public void consumerLoanPageNavigation() {
        consumerLoanOverviewSteps
                .clickConditionsButton();

        consumerLoanSteps
                .verifyConsumerLoanPageURL()
                .verifyHeader();
    }

    @Test(description = "DEV-T4 Step 4: Loan amount entry", priority = 4)
    public void loanAmountEntry() {
        consumerLoanSteps
                .resolveAndFillLoanAmountInput(LOAN_AMOUNT)
                .verifyLoanAmountIndicator(LOAN_AMOUNT);
    }

    @Test(description = "DEV-T4 Step 5: Months count entry", priority = 5)
    public void monthsCountEntry() {
        consumerLoanSteps
                .resolveAndFillMonthCountInput(MONTH_COUNT)
                .verifyMonthCountIndicator(MONTH_COUNT);
    }

    @Test(description = "DEV-T4 Step 6: Loan calculator validation", priority = 6)
    public void loanCalculatorValidation() {
        consumerLoanSteps
                .waitForPaymentContainer()
                .verifyPaymentContainerHasCurrency()
                .assertPaymentWithinTolerance(DELTA);
    }
}