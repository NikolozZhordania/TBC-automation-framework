package ge.tbc.testautomation.tbcbankapp.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class ConsumerLoanPage extends CommonPage {

    public Locator pageHeader,
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

        this.pageHeader = page.getByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions()
                        .setName("სამომხმარებლო სესხი"));

        this.moneyAmountInput = page.locator("//input[@min='200' and @max='80000']");
        this.monthCountInput = page.locator("//input[@min='3' and @max='48']");

        this.moneyAmountInputMobile = page.locator("//label[contains(normalize-space(),'სესხის თანხა')]//ancestor::tbcx-text-input//input");
        this.monthCountInputMobile = page.locator("//span[normalize-space(text())='თვე']/preceding::input[1]");

        this.loanAmountIndicator = page.locator("//div[contains(text(),'სესხის თანხა')]/following-sibling::div[contains(text(),'₾')]");
        this.monthCountIndicator = page.locator("//div[contains(text(),'თვე')]/following-sibling::div[contains(text(),'თვე')]");
        this.nominalPercentageRate = page.locator("//div[contains(text(),'საპროცენტო განაკვეთი')]/following-sibling::div[contains(text(),'%')]");

        this.estimatedMonthlyPaymentContainer =
                page.locator("//div[contains(@class,'tbcx-pw-calculated-info__number') and not(contains(@class,'--old'))]");
    }

}