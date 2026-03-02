package ge.tbc.testautomation.tbcbankapp.ui.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static ge.tbc.testautomation.tbcbankapp.ui.utils.LocatorHelpers.getValueByLabelAndSymbol;

public class ConsumerLoanPage extends CommonPage {
    public Locator
            pageHeader,
            moneyAmountInput,
            monthCountInput,
            moneyAmountInputMobile,
            monthCountInputMobile,
            loanAmountIndicator,
            monthCountIndicator,
            nominalPercentageRate,
            estimatedMonthlyPaymentContainer;

    public ConsumerLoanPage(Page page) {
        super(page);

        this.pageHeader = page.getByRole(AriaRole.HEADING)
                .filter(new Locator.FilterOptions().setHasText("სამომხმარებლო სესხი"))
                .first();

        this.moneyAmountInput = page.locator("//input[@min='200' and @max='80000']");

        this.monthCountInput = page.locator("//input[@min='3' and @max='48']");

        this.moneyAmountInputMobile = page.getByLabel("სესხის თანხა")
                .locator("xpath=//ancestor::tbcx-text-input//input");

        this.monthCountInputMobile = page.locator("span")
                .filter(new Locator.FilterOptions().setHasText("თვე"))
                .locator("xpath=/preceding::input[1]");

        this.loanAmountIndicator = getValueByLabelAndSymbol(page, "სესხის თანხა", "₾");

        this.monthCountIndicator = getValueByLabelAndSymbol(page, "თვე", "თვე");

        this.nominalPercentageRate = getValueByLabelAndSymbol(page, "საპროცენტო განაკვეთი", "%");

        this.estimatedMonthlyPaymentContainer =
                page.locator("//div[contains(@class,'tbcx-pw-calculated-info__number') and not(contains(@class,'--old'))]");
    }

}