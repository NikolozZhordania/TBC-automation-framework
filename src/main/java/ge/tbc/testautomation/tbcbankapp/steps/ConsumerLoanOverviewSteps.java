package ge.tbc.testautomation.tbcbankapp.steps;

import com.microsoft.playwright.Page;
import ge.tbc.testautomation.tbcbankapp.pages.ConsumerLoanOverviewPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static ge.tbc.testautomation.tbcbankapp.data.Constants.CONSUMER_LOAN_URL;

public class ConsumerLoanOverviewSteps {

    private final Page page;
    private final ConsumerLoanOverviewPage consumerLoanOverviewPage;

    public ConsumerLoanOverviewSteps(Page page) {
        this.page = page;
        this.consumerLoanOverviewPage = new ConsumerLoanOverviewPage(page);
    }

    public ConsumerLoanOverviewSteps verifyHeader() {
        assertThat(consumerLoanOverviewPage.pageHeader).isVisible();
        return this;
    }

    public ConsumerLoanOverviewSteps clickConditionsButton() {
        consumerLoanOverviewPage.conditionsButton.click();
        return this;
    }

    public ConsumerLoanOverviewSteps verifyConsumerLoanOverviewPageURL() {
        assertThat(page).hasURL(CONSUMER_LOAN_URL);
        return this;
    }
}