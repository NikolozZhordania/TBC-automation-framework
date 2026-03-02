package ge.tbc.testautomation.tbcbankapp.ui.data.db.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class Branch {
    private Integer id;
    private String  city;
    private String  searchTerm;
    private Integer expectedMinCount;
    private String  expectedAtmName;
    private String  expectedStreet;
    private boolean enabled;
    private String  description;
}
