package ge.tbc.testautomation.tbcbankapp.api.data.models.response.pages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageResponse {

    @JsonProperty("$id")
    private String id;

    @JsonProperty("key")
    private String key;

    @JsonProperty("slug")
    private String slug;

    @JsonProperty("seo")
    private SeoInfo seo;

    @JsonProperty("sectionComponents")
    private List<SectionComponent> sectionComponents;
}
