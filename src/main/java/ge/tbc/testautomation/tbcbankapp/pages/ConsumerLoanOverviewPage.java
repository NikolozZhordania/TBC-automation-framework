package ge.tbc.testautomation.tbcbankapp.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ConsumerLoanOverviewPage extends CommonPage {

    public Locator conditionsButton,
            pageHeader;

    public ConsumerLoanOverviewPage(Page page) {
        super(page);

        this.conditionsButton = page.locator("a[href='/ka/loans/consumer-loan/digital']");
        this.pageHeader = page.locator("//h1[text()=' სამომხმარებლო სესხი ']");
    }
}