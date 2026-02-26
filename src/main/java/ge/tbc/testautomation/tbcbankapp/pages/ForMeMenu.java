package ge.tbc.testautomation.tbcbankapp.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class ForMeMenu extends CommonPage {

    public Locator locationsButton,
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
        this.locationsButton = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("მისამართები").setExact(true));
        this.locationsButtonMobile = page.locator("(//a[@href='/ka/atms&branches']//span[text()=' მისამართები'])[3]");
        this.header = page.getByRole(AriaRole.BANNER);
        this.currencyExchangeButton = header.getByRole(
                AriaRole.LINK,
                new Locator.GetByRoleOptions().setName("ვალუტის კურსები")
        );
        this.currencyExchangeButtonMobile = page.locator("(//tbcx-pw-mega-menu-quick-acitons-item//span[text()=' ვალუტის კურსები'])[3]");
        this.moneyTransferButton = page.locator("a[href='/ka/other-products/money-transfers']");
        this.moneyTransferButtonMobile = page.locator("//tbcx-pw-accordion-item//button//span[normalize-space()='ფულადი გზავნილები']");
        this.otherProductsButton = page.locator("//tbcx-pw-accordion-item//button//span[normalize-space()='სხვა პროდუქტები']");
        this.consumerLoanButton = page.locator("a[href='/ka/loans/consumer-loan']");
        this.consumerLoanButtonMobile = page.locator("//tbcx-pw-accordion-item//button//span[normalize-space()='სამომხმარებლო']");
        this.loanCategoryButton = page.locator("//tbcx-pw-accordion-item//button//span[normalize-space()='სესხები']");
    }

}
