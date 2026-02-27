package ge.tbc.testautomation.tbcbankapp.api.steps;

import ge.tbc.testautomation.tbcbankapp.api.client.PagesAPI;
import ge.tbc.testautomation.tbcbankapp.api.data.constants.PagesConstants.*;
import ge.tbc.testautomation.tbcbankapp.api.data.models.response.commercial.CommercialListResponse;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static ge.tbc.testautomation.tbcbankapp.api.data.constants.PagesConstants.PageIds.TREASURY_PRODUCTS;
import static ge.tbc.testautomation.tbcbankapp.api.data.constants.PagesConstants.QueryParams.KA;

public class CommercialListSteps {

    private final PagesAPI api = new PagesAPI();
    private CommercialListResponse pageResponse;
    private Response rawResponse;

    @Step("Fetch treasury-products page and extract popularCurrencyTitle")
    public CommercialListSteps fetchPopularCurrencyTitle() {
        this.rawResponse = api.getPage(TREASURY_PRODUCTS, KA);
        Allure.addAttachment("Raw Response Status", String.valueOf(rawResponse.statusCode()));

        this.pageResponse = rawResponse
                .then()
                .statusCode(200)
                .extract()
                .as(CommercialListResponse.class);

        Allure.addAttachment("popularCurrencyTitle", getPopularCurrencyTitle());
        return this;
    }

    public String getPopularCurrencyTitle() {
        return pageResponse.getSectionComponents()
                .get(0)
                .getInputs()
                .getPopularCurrencyTitle();
    }

}