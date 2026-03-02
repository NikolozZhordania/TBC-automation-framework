package ge.tbc.testautomation.tbcbankapp.api.data.models.response.commercial;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommercialListResponse {

    @JsonProperty("sectionComponents")
    private List<SectionComponent> sectionComponents;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SectionComponent {

        @JsonProperty("inputs")
        private Inputs inputs;

        @Getter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Inputs {

            @JsonProperty("popularCurrencyTitle")
            private String popularCurrencyTitle;
        }
    }
}
