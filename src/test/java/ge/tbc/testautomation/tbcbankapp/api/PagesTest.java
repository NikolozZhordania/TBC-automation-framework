package ge.tbc.testautomation.tbcbankapp.api;

import ge.tbc.testautomation.tbcbankapp.api.steps.PagesSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.tbcbankapp.api.data.constants.PagesConstants.PageIds.*;
import static ge.tbc.testautomation.tbcbankapp.api.data.constants.PagesConstants.SeoTitles.*;

@Feature("Pages API")
public class PagesTest {

    @Story("Page Structure")
    @Description("Verify that the treasury-products page returns HTTP 200 and contains all required structural fields")
    @Test(description = "treasury-products page returns 200 with valid structure")
    public void treasuryProductsPageStructureTest() {
        new PagesSteps()
                .fetchPage(TREASURY_PRODUCTS)
                .validatePageStructure();
    }

    @Story("Page Structure")
    @Description("Verify that all section components on the treasury-products page have non-blank id, type, and key fields")
    @Test(description = "treasury-products page section components have required fields")
    public void treasuryProductsSectionComponentsTest() {
        new PagesSteps()
                .fetchPage(TREASURY_PRODUCTS)
                .validateSectionComponentFields();
    }

    @Story("SEO")
    @Description("Verify that the SEO title for the treasury-products page matches the expected Georgian value")
    @Test(description = "treasury-products page SEO title matches expected value")
    public void treasuryProductsSeoTitleTest() {
        new PagesSteps()
                .fetchPage(TREASURY_PRODUCTS)
                .validateSeoTitleEquals(TREASURY_PRODUCTS_KA);
    }

    @Story("Page Structure")
    @Description("Full happy path for treasury-products — runs structure, section component, and SEO title validations in sequence")
    @Test(description = "treasury-products full happy path — all validations combined")
    public void treasuryProductsFullValidationTest() {
        new PagesSteps()
                .fetchPage(TREASURY_PRODUCTS)
                .validatePageStructure()
                .validateSectionComponentFields()
                .validateSeoTitleEquals(TREASURY_PRODUCTS_KA);
    }

    @Story("Error Handling")
    @Description("Verify that an invalid pageId results in a 4xx error response")
    @Test(description = "Invalid pageId returns 4xx")
    public void invalidPageIdReturnsErrorTest() {
        new PagesSteps()
                .fetchPageExpectingError(INVALID)
                .validateStatusCodeIs4xx();
    }
}