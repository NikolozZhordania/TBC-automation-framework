package ge.tbc.testautomation.tbcbankapp.api.data.models.response.pages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PagingDetails {

    @JsonProperty("pageNumber")
    private int pageNumber;

    @JsonProperty("pageSize")
    private int pageSize;

    @JsonProperty("totalCount")
    private int totalCount;

    @JsonProperty("totalPages")
    private int totalPages;

    @JsonProperty("hasNextPage")
    private boolean hasNextPage;

    @JsonProperty("hasPreviousPage")
    private boolean hasPreviousPage;

    @JsonProperty("isFirstPage")
    private boolean isFirstPage;

    @JsonProperty("isLastPage")
    private boolean isLastPage;
}
