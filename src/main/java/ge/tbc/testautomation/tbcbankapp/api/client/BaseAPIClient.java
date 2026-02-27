package ge.tbc.testautomation.tbcbankapp.api.client;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;

public class BaseAPIClient {
    static {
        RestAssured.filters(
                new AllureRestAssured());
    }
}
