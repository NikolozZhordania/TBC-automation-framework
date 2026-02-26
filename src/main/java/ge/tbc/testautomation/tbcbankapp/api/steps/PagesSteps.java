package ge.tbc.testautomation.tbcbankapp.api.steps;
import ge.tbc.testautomation.tbcbankapp.api.client.PagesAPI;
import ge.tbc.testautomation.tbcbankapp.api.data.constants.PagesConstants.*;
import ge.tbc.testautomation.tbcbankapp.api.data.models.response.pages.PageResponse;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PagesSteps {

    private final PagesAPI api = new PagesAPI();
    private PageResponse response;

    public PagesSteps fetchPage(String pageId) {
        response = api.getPage(pageId, QueryParams.KA)
                .then()
                .statusCode(200)
                .extract()
                .as(PageResponse.class);

        return this;
    }

    public PagesSteps validatePageStructure() {
        assertThat("Page id must not be blank", response.getId(), not(blankOrNullString()));
        assertThat("Page key must not be blank", response.getKey(), not(blankOrNullString()));
        assertThat("Page slug must not be blank", response.getSlug(), not(blankOrNullString()));
        assertThat("SEO info must not be null", response.getSeo(), notNullValue());
        assertThat("SEO title must not be blank", response.getSeo().getTitle(), not(blankOrNullString()));
        assertThat("sectionComponents must not be empty", response.getSectionComponents(), is(not(empty())));
        return this;
    }

    public PagesSteps validateSectionComponents() {
        response.getSectionComponents().forEach(section -> {
            assertThat("Section id must not be blank", section.getId(), not(blankOrNullString()));
            assertThat("Section type must not be blank", section.getType(), not(blankOrNullString()));
            assertThat("Section key must not be blank", section.getKey(), not(blankOrNullString()));
        });
        return this;
    }

    public PagesSteps validateExpectedSeoTitle(String expectedTitle) {
        assertThat(response.getSeo().getTitle(), equalTo(expectedTitle));
        return this;
    }

    public PagesSteps validateInvalidPageReturns4xx(String pageId) {
        api.getPage(pageId, QueryParams.KA)
                .then()
                .statusCode(anyOf(is(400), is(404)));

        return this;
    }
}
