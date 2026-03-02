package ge.tbc.testautomation.tbcbankapp.api.data.models.response.forwardrate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyForwardRates {

    @JsonProperty("iso")
    private String iso;

    @JsonProperty("forwardRates")
    private List<ForwardRate> forwardRates;
}
