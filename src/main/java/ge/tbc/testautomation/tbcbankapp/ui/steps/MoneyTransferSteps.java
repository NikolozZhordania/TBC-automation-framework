package ge.tbc.testautomation.tbcbankapp.ui.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import ge.tbc.testautomation.tbcbankapp.ui.pages.MoneyTransferPage;
import ge.tbc.testautomation.tbcbankapp.ui.utils.StringHelper;

import java.util.ArrayList;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static ge.tbc.testautomation.tbcbankapp.ui.data.Constants.MONEY_TRANSFER_PAGE_URL;

public class MoneyTransferSteps {

    private final Page page;
    private final MoneyTransferPage moneyTransferPage;
    private final List<String> mainPageOptions = new ArrayList<>();
    private final List<String> calculatorOptions = new ArrayList<>();

    public MoneyTransferSteps(Page page) {
        this.page = page;
        this.moneyTransferPage = new MoneyTransferPage(page);
    }

    public MoneyTransferSteps verifyMoneyTransferPageOpened() {
        Locator header = moneyTransferPage.pageHeader;
        header.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        assertThat(header).isVisible();
        return this;
    }

    public MoneyTransferSteps verifyMoneyTransferPageURL() {
        assertThat(page).hasURL(MONEY_TRANSFER_PAGE_URL);
        return this;
    }


    public MoneyTransferSteps waitForMainPageOptions() {
        moneyTransferPage.moneyTransferOptions.first()
                .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        return this;
    }

    public MoneyTransferSteps validateMainPageOptionsCurrencies() {
        int count = moneyTransferPage.moneyTransferOptions.count();
        if (count == 0) throw new AssertionError("No transfer options found on main page.");

        for (int i = 0; i < count; i++) {
            Locator option = moneyTransferPage.moneyTransferOptions.nth(i);
            assertThat(option).isVisible();

            String text = option.textContent().trim();
            if (!(text.contains("EUR") || text.contains("USD") || text.contains("GEL") ||
                    text.contains("GBP") || text.contains("RUB"))) {
                throw new AssertionError("Transfer option '" + text + "' does not list any supported currency!");
            }
        }
        System.out.println("All main page transfer options are visible and have supported currencies.");
        return this;
    }

    public MoneyTransferSteps storeMainPageOptions() {
        mainPageOptions.clear();

        int count = moneyTransferPage.moneyTransferOptions.count();
        for (int i = 0; i < count; i++) {
            Locator option = moneyTransferPage.moneyTransferOptions.nth(i);
            assertThat(option).isVisible();
            String title = StringHelper.normalize(option.textContent().split("\n")[0]);
            if (!title.isEmpty()) mainPageOptions.add(title);
        }

        System.out.println("Main Page Transfer Options Stored: " + mainPageOptions);
        return this;
    }


    public MoneyTransferSteps openCommissionCalculator() {
        Locator calculatorButton = moneyTransferPage.moneyTransferCommissionCalculator;
        calculatorButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        calculatorButton.click();
        return this;
    }

    public MoneyTransferSteps enterTransferAmount(String amount) {
        Locator input = moneyTransferPage.moneyInputField;
        input.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        input.fill(amount);
        return this;
    }

    public MoneyTransferSteps openCountryDropdown() {
        Locator button = moneyTransferPage.countrySelectionButton;
        button.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        button.click();
        return this;
    }

    public MoneyTransferSteps chooseCountryOption(String country) {
        Locator countryOption = moneyTransferPage.countryOptions
                .filter(new Locator.FilterOptions().setHasText(country));
        countryOption.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(45000)
        );
        countryOption.click();
        return this;
    }

    public MoneyTransferSteps selectCountry(String country) {
        return openCountryDropdown()
                .chooseCountryOption(country);
    }

    public MoneyTransferSteps waitForSkeletonToDisappear() {
        page.locator("tbcx-pw-money-transfer-system-card-loading")
                .first()
                .waitFor(new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.HIDDEN)
                        .setTimeout(45000)
                );
        return this;
    }

    public MoneyTransferSteps waitForCalculatorCardsVisible() {
        moneyTransferPage.moneyTransferCalculatorCards
                .first()
                .waitFor(new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(45000)
                );
        return this;
    }

    public MoneyTransferSteps assertCalculatorCardsNotEmpty() {
        int count = moneyTransferPage.moneyTransferCalculatorCards.count();
        if (count == 0) throw new AssertionError("No calculator results found.");
        return this;
    }

    public MoneyTransferSteps storeCalculatorOptions() {
        calculatorOptions.clear();

        int count = moneyTransferPage.moneyTransferCalculatorCards.count();
        for (int i = 0; i < count; i++) {
            Locator card = moneyTransferPage.moneyTransferCalculatorCards.nth(i);
            card.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

            String title = StringHelper.normalize(card.textContent());
            if (!title.isEmpty()) calculatorOptions.add(title);
        }

        System.out.println("Calculator Options Stored: " + calculatorOptions);
        return this;
    }


    public MoneyTransferSteps assertCalculatorOptionsNotEmpty() {
        if (calculatorOptions.isEmpty()) {
            throw new AssertionError("Calculator options list is empty, nothing to validate.");
        }
        return this;
    }

    public MoneyTransferSteps assertEachCalculatorOptionHasGelCommission() {
        for (String calcOption : calculatorOptions) {
            String serviceName = calcOption.split(" ")[0];
            if (!calcOption.contains("GEL")) {
                throw new AssertionError("Commission fee in GEL not visible for: " + serviceName);
            }
        }
        return this;
    }

    public MoneyTransferSteps assertEachCalculatorOptionExistsOnMainPage() {
        for (String calcOption : calculatorOptions) {
            String serviceName = calcOption.split(" ")[0];
            boolean foundOnMainPage = mainPageOptions.stream()
                    .anyMatch(mainOption -> mainOption.contains(serviceName));
            if (!foundOnMainPage) {
                throw new AssertionError("Calculator option '" + serviceName + "' not found on main page options.");
            }
            System.out.println("Validated calculator option with GEL commission: " + serviceName);
        }
        return this;
    }

    public MoneyTransferSteps compareListsAndValidate() {
        return assertCalculatorOptionsNotEmpty()
                .assertEachCalculatorOptionHasGelCommission()
                .assertEachCalculatorOptionExistsOnMainPage();
    }
}