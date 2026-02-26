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
            moneyTransferCalculatorCards;

    public MoneyTransferPage(Page page) {
        super(page);

        this.pageHeader = page.locator(
                "h1.tbcx-pw-title",
                new Page.LocatorOptions().setHasText("სწრაფი ფულადი გზავნილები")
        ).first();

        this.moneyTransferOptions = page.locator(
                "tbcx-pw-money-transfer-system-card tbcx-pw-card"
        );

        this.moneyTransferCommissionCalculator = page.locator("tbcx-pw-tab-group button")
                .filter(new Locator.FilterOptions().setHasText("გზავნილის გაგზავნის საკომისიო"));

        this.moneyInputField = page.locator(
                "tbcx-pw-money-transfer-fee-calculator div.input-with-label input"
        ).first();

        this.countrySelectionButton = page.locator("//button[@class='tbcx-field tbcx-bg-color-higher']");

        this.moneyTransferCalculatorCards = page.locator(
                "//div[@class='tbcx-pw-money-transfer-fee-calculator__cards']"
        );
    }
}