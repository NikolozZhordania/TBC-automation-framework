package ge.tbc.testautomation.tbcbankapp.api.data.models.response.pages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SectionComponent {

    @JsonProperty("$id")
    private String id;

    @JsonProperty("type")
    private String type;

    @JsonProperty("key")
    private String key;
}
