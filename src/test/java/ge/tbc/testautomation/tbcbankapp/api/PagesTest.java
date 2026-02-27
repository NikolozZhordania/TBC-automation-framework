package ge.tbc.testautomation.tbcbankapp.api;

import ge.tbc.testautomation.tbcbankapp.api.steps.PagesSteps;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.tbcbankapp.api.data.constants.PagesConstants.PageIds.*;

public class PagesTest {

    @Test(description = "treasury-products page returns 200 with valid structure")
    public void treasuryProductsPageStructureTest() {
        new PagesSteps()
                .fetchPage(TREASURY_PRODUCTS)
                .validatePageStructure();
    }

    @Test(description = "treasury-products page section components have required fields")
    public void treasuryProductsSectionComponentsTest() {
        new PagesSteps()
                .fetchPage(TREASURY_PRODUCTS)
                .validateSectionComponentFields();
    }

    @Test(description = "treasury-products page SEO title matches expected value")
    public void treasuryProductsSeoTitleTest() {
        new PagesSteps()
                .fetchPage(TREASURY_PRODUCTS)
                .validateSeoTitleEquals("სახაზინო პროდუქტები");
    }

    @Test(description = "treasury-products full happy path — all validations combined")
    public void treasuryProductsFullValidationTest() {
        new PagesSteps()
                .fetchPage(TREASURY_PRODUCTS)
                .validatePageStructure()
                .validateSectionComponentFields()
                .validateSeoTitleEquals("სახაზინო პროდუქტები");
    }


    @Test(description = "Invalid pageId returns 4xx")
    public void invalidPageIdReturnsErrorTest() {
        new PagesSteps()
                .fetchPageExpectingError(INVALID)
                .validateStatusCodeIs4xx();
    }
}