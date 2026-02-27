package ge.tbc.testautomation.tbcbankapp.api.steps;

import ge.tbc.testautomation.tbcbankapp.api.client.PagesAPI;
import ge.tbc.testautomation.tbcbankapp.api.data.constants.PagesConstants.*;
import ge.tbc.testautomation.tbcbankapp.api.data.models.response.pages.PageResponse;
import io.restassured.response.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PagesSteps {

    private final PagesAPI api = new PagesAPI();
    private PageResponse pageResponse;
    private Response rawResponse;

    public PagesSteps fetchPage(String pageId) {
        this.rawResponse = api.getPage(pageId, QueryParams.KA);
        this.pageResponse = rawResponse
                .then()
                .statusCode(200)
                .extract()
                .as(PageResponse.class);
        return this;
    }

    public PagesSteps fetchPageExpectingError(String pageId) {
        this.rawResponse = api.getPage(pageId, QueryParams.KA);
        return this;
    }

    public PagesSteps validateStatusCode(int expectedCode) {
        assertThat("Status code", rawResponse.statusCode(), equalTo(expectedCode));
        return this;
    }

    public PagesSteps validateStatusCodeIs4xx() {
        assertThat("Status code should be 4xx",
                rawResponse.statusCode(), anyOf(is(400), is(404)));
        return this;
    }

    public PagesSteps validateIdIsPresent() {
        assertThat("Page id must not be blank", pageResponse.getId(), not(blankOrNullString()));
        return this;
    }

    public PagesSteps validateKeyIsPresent() {
        assertThat("Page key must not be blank", pageResponse.getKey(), not(blankOrNullString()));
        return this;
    }

    public PagesSteps validateSlugIsPresent() {
        assertThat("Page slug must not be blank", pageResponse.getSlug(), not(blankOrNullString()));
        return this;
    }

    public PagesSteps validateSeoIsPresent() {
        assertThat("SEO info must not be null", pageResponse.getSeo(), notNullValue());
        return this;
    }

    public PagesSteps validateSeoTitleIsPresent() {
        assertThat("SEO title must not be blank",
                pageResponse.getSeo().getTitle(), not(blankOrNullString()));
        return this;
    }

    public PagesSteps validateSeoTitleEquals(String expectedTitle) {
        assertThat("SEO title", pageResponse.getSeo().getTitle(), equalTo(expectedTitle));
        return this;
    }

    public PagesSteps validateSectionComponentsAreNotEmpty() {
        assertThat("sectionComponents must not be empty",
                pageResponse.getSectionComponents(), is(not(empty())));
        return this;
    }

    public PagesSteps validateSectionComponentFields() {
        pageResponse.getSectionComponents().forEach(section -> {
            assertThat("Section id must not be blank",   section.getId(),   not(blankOrNullString()));
            assertThat("Section type must not be blank", section.getType(), not(blankOrNullString()));
            assertThat("Section key must not be blank",  section.getKey(),  not(blankOrNullString()));
        });
        return this;
    }

    public PagesSteps validatePageStructure() {
        return validateIdIsPresent()
                .validateKeyIsPresent()
                .validateSlugIsPresent()
                .validateSeoIsPresent()
                .validateSeoTitleIsPresent()
                .validateSectionComponentsAreNotEmpty();
    }
}