package ge.tbc.testautomation.tbcbankapp.api;

import ge.tbc.testautomation.tbcbankapp.api.steps.PagesSteps;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.tbcbankapp.api.data.constants.PagesConstants.PageIds.*;
public class PagesTest {

    @Test(description = "Happy path: treasury-products page returns 200 with valid fields")
    public void treasuryProductsPageHappyPathTest() {
        new PagesSteps()
                .fetchPage(TREASURY_PRODUCTS)
                .validatePageStructure()
                .validateSectionComponents()
                .validateExpectedSeoTitle("სახაზინო პროდუქტები");
    }

    @Test(description = "Negative: invalid pageId returns 4xx")
    public void invalidPageIdTest() {
        new PagesSteps()
                .validateInvalidPageReturns4xx(INVALID);
    }
}
