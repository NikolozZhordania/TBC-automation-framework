package ge.tbc.testautomation.tbcbankapp.tests;

import ge.tbc.testautomation.tbcbankapp.base.BaseTest;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.tbcbankapp.data.Constants.TRANSFER_AMOUNT;
import static ge.tbc.testautomation.tbcbankapp.data.Constants.TRANSFER_COUNTRY;

@Test(description = "DEV-T3 Validate Money Transfer Options")
public class MoneyTransferTest extends BaseTest {

    @Test(description = "DEV-T3 Step 1: Homepage access", priority = 1)
    public void homepageAccess() {
        homeSteps
                .openHomepage()
                .verifyHomepageLoaded()
                .openNavigationMenu();
    }

    @Test(description = "DEV-T3 Step 2: Navigation menu access", priority = 2)
    public void navMenuAccess() {
        homeSteps.verifyDropDownMenuVisibility();
    }

    @Test(description = "DEV-T3 Step 3: Money Transfer page selection", priority = 3)
    public void moneyTransferPageSelection() {
        homeSteps.openMoneyTransferPage();

        moneyTransferSteps
                .verifyMoneyTransferPageOpened()
                .verifyMoneyTransferPageURL();
    }

    @Test(description = "DEV-T3 Step 4: Transfer options check", priority = 4)
    public void transferOptionsCheck() {
        moneyTransferSteps
                .waitForMainPageOptions()
                .validateMainPageOptionsCurrencies()
                .storeMainPageOptions();
    }

    @Test(description = "DEV-T3 Step 5: Commission Calculator access", priority = 5)
    public void commissionCalculatorAccess() {
        moneyTransferSteps.openCommissionCalculator();
    }

    @Test(description = "DEV-T3 Step 6: Transfer amount entry & Country Selection", priority = 6)
    public void amountEntryAndCountrySelection() {
        moneyTransferSteps
                .enterTransferAmount(TRANSFER_AMOUNT)
                .openCountryDropdown()
                .chooseCountryOption(TRANSFER_COUNTRY)
                .waitForSkeletonToDisappear()
                .waitForCalculatorCardsVisible()
                .assertCalculatorCardsNotEmpty()
                .storeCalculatorOptions();
    }

    @Test(description = "DEV-T3 Step 7: List comparison", priority = 7)
    public void listComparison() {
        moneyTransferSteps
                .assertCalculatorOptionsNotEmpty()
                .assertEachCalculatorOptionHasGelCommission()
                .assertEachCalculatorOptionExistsOnMainPage();
    }
}