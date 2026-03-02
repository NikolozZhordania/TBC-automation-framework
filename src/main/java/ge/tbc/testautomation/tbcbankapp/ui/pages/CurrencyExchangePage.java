package ge.tbc.testautomation.tbcbankapp.ui.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class CurrencyExchangePage extends CommonPage {

    public Locator
            pageHeader,
            fromCurrencyInput,
            toCurrencyInput,
            swapButton,
            currencyRates,
            fromCurrencyListUSD,
            fromCurrencyListGEL,
            fromCurrencyListButton;

    public CurrencyExchangePage(Page page) {
        super(page);

        this.pageHeader = page.getByText(" კომერციული ვალუტის კურსები ");

        this.fromCurrencyInput = page.locator("//input[@type='text']").first();

        this.toCurrencyInput = page.locator("//input[@type='text']").nth(1);

        this.swapButton = page.getByText("swap-alt-outlined");

        this.currencyRates = page.locator("//div[contains(@class,'calculator__description')]");

        this.fromCurrencyListUSD = page.getByRole(AriaRole.BUTTON)

                .filter(new Locator.FilterOptions().setHasText("USD"));
        this.fromCurrencyListGEL = page.getByRole(AriaRole.BUTTON)
                .filter(new Locator.FilterOptions().setHasText("GEL"));

        this.fromCurrencyListButton = page.locator("//button[contains(@class,'tbcx-field')]");
    }
}