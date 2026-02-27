package ge.tbc.testautomation.tbcbankapp.ui.steps;

import com.microsoft.playwright.Page;
import ge.tbc.testautomation.tbcbankapp.ui.pages.ConsumerLoanOverviewPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static ge.tbc.testautomation.tbcbankapp.ui.data.constants.Constants.CONSUMER_LOAN_URL;

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