package sdw.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@Getter
@AllArgsConstructor
public class SdwQuery {

    private List<SdwRestriction> sdwRestrictions;
    private List<GroupBy> groupByList;
    private List<Sum> sums;

    public enum Sum {
        clicks,
        impressions
    }

    public enum GroupBy {
        datasource, campaign, daily
    }

    public enum RestrictionColumn {
        clicks, impressions, datasource, campaign, daily
    }

}
