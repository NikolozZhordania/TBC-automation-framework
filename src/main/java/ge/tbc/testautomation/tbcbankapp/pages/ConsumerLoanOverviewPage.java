package ge.tbc.testautomation.tbcbankapp.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class ConsumerLoanOverviewPage extends CommonPage {

    public Locator
            conditionsButton,
            pageHeader;

    public ConsumerLoanOverviewPage(Page page) {
        super(page);

        this.pageHeader = page.getByRole(AriaRole.HEADING)
                .filter(new Locator.FilterOptions().setHasText("სამომხმარებლო სესხი"))
                .first();

        this.conditionsButton = page.getByRole(AriaRole.LINK)
                .filter(new Locator.FilterOptions().setHasText("პირობები"))
                .first();
    }
}