package ge.tbc.testautomation.tbcbankapp.api.data.models.response.currencyexchange;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRateResponse {

    @JsonProperty("iso1")
    private String iso1;

    @JsonProperty("iso2")
    private String iso2;

    @JsonProperty("buyRate")
    private Double buyRate;

    @JsonProperty("sellRate")
    private Double sellRate;

    @JsonProperty("conversionType")
    private Integer conversionType;

    @JsonProperty("currencyWeight")
    private Double currencyWeight;

    @JsonProperty("updateDate")
    private String updateDate;
}
