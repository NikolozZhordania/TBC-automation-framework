package ge.tbc.testautomation.tbcbankapp.api.client;

import ge.tbc.testautomation.tbcbankapp.api.data.constants.PagesConstants.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PagesAPI extends BaseAPIClient{

    public Response getPage(String pageId, String locale) {
        return given()
                .baseUri(URI.BASE)
                .basePath(Paths.BASE + Endpoints.PAGES)
                .pathParam(PathParams.PAGE_ID, pageId)
                .queryParam(QueryParams.LOCALE, locale)
                .accept(ContentType.JSON)
                .log().uri()
                .header("Content-Type", "application/json")
                .when()
                .get();
    }
}
