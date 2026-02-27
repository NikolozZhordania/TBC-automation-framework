package ge.tbc.testautomation.tbcbankapp.api.steps;

import ge.tbc.testautomation.tbcbankapp.api.client.PagesAPI;
import ge.tbc.testautomation.tbcbankapp.api.data.constants.PagesConstants.*;
import ge.tbc.testautomation.tbcbankapp.api.data.models.response.pages.PageResponse;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PagesSteps {

    private final PagesAPI api = new PagesAPI();
    private PageResponse pageResponse;
    private Response rawResponse;

    @Step("Fetch page with id: {pageId}")
    public PagesSteps fetchPage(String pageId) {
        this.rawResponse = api.getPage(pageId, QueryParams.KA);
        this.pageResponse = rawResponse
                .then()
                .statusCode(200)
                .extract()
                .as(PageResponse.class);
        return this;
    }

    @Step("Fetch page with id: {pageId}, expecting error")
    public PagesSteps fetchPageExpectingError(String pageId) {
        this.rawResponse = api.getPage(pageId, QueryParams.KA);
        return this;
    }

    @Step("Validate status code is {expectedCode}")
    public PagesSteps validateStatusCode(int expectedCode) {
        assertThat("Status code", rawResponse.statusCode(), equalTo(expectedCode));
        return this;
    }

    @Step("Validate status code is 4xx")
    public PagesSteps validateStatusCodeIs4xx() {
        assertThat("Status code should be 4xx",
                rawResponse.statusCode(), anyOf(is(400), is(404)));
        return this;
    }

    @Step("Validate page id is present")
    public PagesSteps validateIdIsPresent() {
        assertThat("Page id must not be blank", pageResponse.getId(), not(blankOrNullString()));
        return this;
    }

    @Step("Validate page key is present")
    public PagesSteps validateKeyIsPresent() {
        assertThat("Page key must not be blank", pageResponse.getKey(), not(blankOrNullString()));
        return this;
    }

    @Step("Validate page slug is present")
    public PagesSteps validateSlugIsPresent() {
        assertThat("Page slug must not be blank", pageResponse.getSlug(), not(blankOrNullString()));
        return this;
    }

    @Step("Validate SEO object is present")
    public PagesSteps validateSeoIsPresent() {
        assertThat("SEO info must not be null", pageResponse.getSeo(), notNullValue());
        return this;
    }

    @Step("Validate SEO title is present")
    public PagesSteps validateSeoTitleIsPresent() {
        assertThat("SEO title must not be blank",
                pageResponse.getSeo().getTitle(), not(blankOrNullString()));
        return this;
    }

    @Step("Validate SEO title equals '{expectedTitle}'")
    public PagesSteps validateSeoTitleEquals(String expectedTitle) {
        assertThat("SEO title", pageResponse.getSeo().getTitle(), equalTo(expectedTitle));
        return this;
    }

    @Step("Validate section components are not empty")
    public PagesSteps validateSectionComponentsAreNotEmpty() {
        assertThat("sectionComponents must not be empty",
                pageResponse.getSectionComponents(), is(not(empty())));
        return this;
    }

    @Step("Validate all section component fields (id, type, key) are non-blank")
    public PagesSteps validateSectionComponentFields() {
        pageResponse.getSectionComponents().forEach(section -> {
            assertThat("Section id must not be blank",   section.getId(),   not(blankOrNullString()));
            assertThat("Section type must not be blank", section.getType(), not(blankOrNullString()));
            assertThat("Section key must not be blank",  section.getKey(),  not(blankOrNullString()));
        });
        return this;
    }

    @Step("Validate full page structure (id, key, slug, SEO, section components)")
    public PagesSteps validatePageStructure() {
        return validateIdIsPresent()
                .validateKeyIsPresent()
                .validateSlugIsPresent()
                .validateSeoIsPresent()
                .validateSeoTitleIsPresent()
                .validateSectionComponentsAreNotEmpty();
    }
}