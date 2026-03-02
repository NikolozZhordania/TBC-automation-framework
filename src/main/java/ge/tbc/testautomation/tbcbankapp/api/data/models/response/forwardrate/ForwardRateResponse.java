package ge.tbc.testautomation.tbcbankapp.api.data.models.response.forwardrate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForwardRateResponse {

    @JsonProperty("rates")
    private List<CurrencyForwardRates> rates;

    @JsonProperty("updateDate")
    private String updateDate;
}
