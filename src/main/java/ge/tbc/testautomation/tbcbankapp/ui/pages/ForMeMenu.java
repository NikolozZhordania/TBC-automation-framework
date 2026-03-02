package ge.tbc.testautomation.tbcbankapp.ui.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class ForMeMenu extends CommonPage {

    public Locator
            locationsButton,
            locationsButtonMobile,
            currencyExchangeButton,
            currencyExchangeButtonMobile,
            moneyTransferButton,
            moneyTransferButtonMobile,
            otherProductsButton,
            consumerLoanButton,
            consumerLoanButtonMobile,
            loanCategoryButton,
            header;

    public ForMeMenu(Page page) {
        super(page);

        this.header = page.getByRole(AriaRole.BANNER);

        this.locationsButton = header.getByRole(AriaRole.LINK)
                .filter(new Locator.FilterOptions()
                        .setHasText("მისამართები"));

        this.currencyExchangeButton = page.getByRole(AriaRole.LINK)
                .filter(new Locator.FilterOptions().setHasText("ვალუტის კურსები"))
                .first();

        this.moneyTransferButton = header.locator("a[href*='/money-transfers']");
        this.consumerLoanButton = header.locator("a[href*='/loans/consumer-loan']");

        this.locationsButtonMobile = page.getByRole(AriaRole.LINK)
                .filter(new Locator.FilterOptions().setHasText(" მისამართები"))
                .first();

        this.currencyExchangeButtonMobile = page.getByRole(AriaRole.LINK)
                .filter(new Locator.FilterOptions().setHasText("ვალუტის კურსები"))
                .first();

        this.moneyTransferButtonMobile = page.getByRole(AriaRole.BUTTON)
                .filter(new Locator.FilterOptions().setHasText("ფულადი გზავნილები"))
                .first();

        this.consumerLoanButtonMobile = page.getByRole(AriaRole.BUTTON)
                .filter(new Locator.FilterOptions().setHasText("სამომხმარებლო"))
                .first();

        this.otherProductsButton = page.getByRole(AriaRole.BUTTON)
                .filter(new Locator.FilterOptions().setHasText("სხვა პროდუქტები"))
                .first();

        this.loanCategoryButton = page.getByRole(AriaRole.BUTTON)
                .filter(new Locator.FilterOptions().setHasText("სესხები"))
                .first();
    }
}