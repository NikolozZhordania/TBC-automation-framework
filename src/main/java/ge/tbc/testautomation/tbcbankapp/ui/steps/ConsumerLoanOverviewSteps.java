package ge.tbc.testautomation.tbcbankapp.ui.steps;

import com.microsoft.playwright.Page;
import ge.tbc.testautomation.tbcbankapp.ui.pages.ConsumerLoanOverviewPage;
import io.qameta.allure.Step;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static ge.tbc.testautomation.tbcbankapp.ui.data.constants.Constants.URLs.*;

public class ConsumerLoanOverviewSteps {

    private final Page page;
    private final ConsumerLoanOverviewPage consumerLoanOverviewPage;

    public ConsumerLoanOverviewSteps(Page page) {
        this.page = page;
        this.consumerLoanOverviewPage = new ConsumerLoanOverviewPage(page);
    }

    @Step("Verify Consumer Loan Overview page header is visible")
    public ConsumerLoanOverviewSteps verifyHeader() {
        assertThat(consumerLoanOverviewPage.pageHeader).isVisible();
        return this;
    }

    @Step("Click conditions button")
    public ConsumerLoanOverviewSteps clickConditionsButton() {
        consumerLoanOverviewPage.conditionsButton.click();
        return this;
    }

    @Step("Verify Consumer Loan Overview page URL")
    public ConsumerLoanOverviewSteps verifyConsumerLoanOverviewPageURL() {
        assertThat(page).hasURL(CONSUMER_LOAN_URL);
        return this;
    }
}