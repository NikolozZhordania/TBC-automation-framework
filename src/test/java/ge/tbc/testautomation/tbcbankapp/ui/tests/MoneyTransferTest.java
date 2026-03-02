package ge.tbc.testautomation.tbcbankapp.ui.tests;

import ge.tbc.testautomation.tbcbankapp.ui.base.BaseDeviceTest;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.tbcbankapp.ui.data.constants.Constants.MoneyTransfer.*;

@Epic("TBC Bank Web Application")
@Feature("Money Transfer")
@Test(description = "DEV-T3 Validate Money Transfer Options")
public class MoneyTransferTest extends BaseDeviceTest {

    public MoneyTransferTest(String device, String browser) {
        super(device, browser);
    }

    public MoneyTransferTest(){
        super();
    }

    @Story("Homepage Access")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Open the homepage and verify that the main page elements and navigation menu are visible.")
    @Test(description = "DEV-T3 Step 1: Homepage access", priority = 1)
    public void homepageAccess() {
        homeSteps
                .openHomepage()
                .verifyHomepageLoaded()
                .verifyMenuVisibility()
                .openNavigationMenu();
    }

    @Story("Navigation Menu Validation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that the dropdown navigation menu is visible before accessing Money Transfer page.")
    @Test(description = "DEV-T3 Step 2: Navigation menu access", priority = 2)
    public void navMenuAccess() {
        homeSteps.verifyDropDownMenuVisibility();
    }

    @Story("Money Transfer Page Access")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Navigate to the Money Transfer page and verify that the page URL and header are correct.")
    @Test(description = "DEV-T3 Step 3: Money Transfer page selection", priority = 3)
    public void moneyTransferPageSelection() {
        homeSteps.openMoneyTransferPage();

        moneyTransferSteps
                .verifyMoneyTransferPageOpened()
                .verifyMoneyTransferPageURL();
    }

    @Story("Main Transfer Options Validation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Ensure all main transfer options are visible and contain supported currencies, then store them for comparison.")
    @Test(description = "DEV-T3 Step 4: Transfer options check", priority = 4)
    public void transferOptionsCheck() {
        moneyTransferSteps
                .waitForMainPageOptions()
                .validateMainPageOptionsCurrencies()
                .storeMainPageOptions();
    }

    @Story("Commission Calculator Access")
    @Severity(SeverityLevel.NORMAL)
    @Description("Open the commission calculator to verify available options and prepare for transfer calculations.")
    @Test(description = "DEV-T3 Step 5: Commission Calculator access", priority = 5)
    public void commissionCalculatorAccess() {
        moneyTransferSteps.openCommissionCalculator();
    }

    @Story("Transfer Amount and Country Selection")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Enter the transfer amount, select the destination country, wait for the results to load, and store the calculator options.")
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

    @Story("Calculator Results Validation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Compare the main page options and calculator options, ensuring all have GEL commission and match main page offerings.")
    @Test(description = "DEV-T3 Step 7: List comparison", priority = 7)
    public void listComparison() {
        moneyTransferSteps
                .assertCalculatorOptionsNotEmpty()
                .assertEachCalculatorOptionHasGelCommission()
                .assertEachCalculatorOptionExistsOnMainPage();
    }
}