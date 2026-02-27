package ge.tbc.testautomation.tbcbankapp.api.client;

import ge.tbc.testautomation.tbcbankapp.api.data.constants.ExchangeRateConstants.*;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ExchangeRateAPI extends BaseAPIClient{

    public Response getExchangeRate(String iso1, String iso2) {
        return given()
                .baseUri(URI.BASE)
                .queryParam(Params.ISO1, iso1)
                .queryParam(Params.ISO2, iso2)
                .get(Paths.BASE + Endpoints.GET_EXCHANGE_RATE);
    }

    public Response getExchangeRateWithoutIso1(String iso2) {
        return given()
                .baseUri(URI.BASE)
                .queryParam(Params.ISO2, iso2)
                .get(Paths.BASE + Endpoints.GET_EXCHANGE_RATE);
    }

    public Response getExchangeRateWithoutIso2(String iso1) {
        return given()
                .baseUri(URI.BASE)
                .queryParam(Params.ISO1, iso1)
                .get(Paths.BASE + Endpoints.GET_EXCHANGE_RATE);
    }

    public Response getExchangeRateNoParams() {
        return given()
                .baseUri(URI.BASE)
                .get(Paths.BASE + Endpoints.GET_EXCHANGE_RATE);
    }

    public Response getExchangeRateWithInvalidCurrency(String iso1, String iso2) {
        return given()
                .baseUri(URI.BASE)
                .queryParam(Params.ISO1, iso1)
                .queryParam(Params.ISO2, iso2)
                .get(Paths.BASE + Endpoints.GET_EXCHANGE_RATE);
    }
}
