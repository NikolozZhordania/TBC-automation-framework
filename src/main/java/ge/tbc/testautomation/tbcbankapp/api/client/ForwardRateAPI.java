package ge.tbc.testautomation.tbcbankapp.api.client;

import ge.tbc.testautomation.tbcbankapp.api.data.constants.ForwardRateConstants.*;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ForwardRateAPI extends BaseAPIClient{

    public Response getForwardRates(String locale) {
        return given()
                .baseUri(URI.BASE)
                .queryParam(Params.LOCALE, locale)
                .get(Paths.BASE + Endpoints.GET_FORWARD_RATES);
    }

    public Response getForwardRatesNoParams() {
        return given()
                .baseUri(URI.BASE)
                .get(Paths.BASE + Endpoints.GET_FORWARD_RATES);
    }

    public Response getForwardRatesWithInvalidLocale(String invalidLocale) {
        return given()
                .baseUri(URI.BASE)
                .queryParam(Params.LOCALE, invalidLocale)
                .get(Paths.BASE + Endpoints.GET_FORWARD_RATES);
    }
}