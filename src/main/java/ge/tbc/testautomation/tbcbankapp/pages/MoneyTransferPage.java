package ge.tbc.testautomation.tbcbankapp.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MoneyTransferPage extends CommonPage {

    public Locator
            pageHeader,
            moneyTransferOptions,
            moneyTransferCommissionCalculator,
            moneyInputField,
            countrySelectionButton,
            moneyTransferCalculatorCards,
            countryOptions;

    public MoneyTransferPage(Page page) {
        super(page);

        this.pageHeader = page.getByRole(com.microsoft.playwright.options.AriaRole.HEADING)
                .filter(new Locator.FilterOptions().setHasText("სწრაფი ფულადი გზავნილები"))
                .first();

        this.moneyTransferOptions = page.locator("tbcx-pw-money-transfer-system-card >> tbcx-pw-card");

        this.moneyTransferCommissionCalculator = page.getByRole(com.microsoft.playwright.options.AriaRole.BUTTON)
                .filter(new Locator.FilterOptions().setHasText("გზავნილის გაგზავნის საკომისიო"))
                .first();

        this.moneyInputField = page.locator("tbcx-pw-money-transfer-fee-calculator")
                .locator("div.input-with-label input")
                .first();

        this.countrySelectionButton = page.locator("tbcx-pw-money-transfer-fee-calculator button")
                .filter(new Locator.FilterOptions().setHasText("აირჩიე ქვეყანა"))
                .first();

        this.moneyTransferCalculatorCards = page.locator("//div[@class='tbcx-pw-money-transfer-fee-calculator__cards']");

        this.countryOptions = page.locator("div.tbcx-dropdown-popover-item__title-container");
    }
}