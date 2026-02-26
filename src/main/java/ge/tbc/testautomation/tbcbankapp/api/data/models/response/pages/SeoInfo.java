package ge.tbc.testautomation.tbcbankapp.api.data.models.response.pages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SeoInfo {

    @JsonProperty("title")
    private String title;

    @JsonProperty("key")
    private String key;
}
