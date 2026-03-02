package ge.tbc.testautomation.tbcbankapp.api.data.models.response.forwardrate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForwardRate {

    @JsonProperty("iso1")
    private String iso1;

    @JsonProperty("iso2")
    private String iso2;

    @JsonProperty("period")
    private String period;

    @JsonProperty("day")
    private Integer day;

    @JsonProperty("bidForwardPoint")
    private Double bidForwardPoint;

    @JsonProperty("bidForwardInterest")
    private Double bidForwardInterest;

    @JsonProperty("bidForwardRate")
    private Double bidForwardRate;

    @JsonProperty("askForwardPoint")
    private Double askForwardPoint;

    @JsonProperty("askForwardInterest")
    private Double askForwardInterest;

    @JsonProperty("askForwardRate")
    private Double askForwardRate;
}
