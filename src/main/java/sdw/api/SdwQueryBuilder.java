package sdw.api;

import java.util.ArrayList;
import java.util.List;

public class SdwQueryBuilder {

    private List<SdwRestriction> sdwRestrictions;
    private List<SdwQuery.GroupBy> groupByList;
    private List<SdwQuery.Sum> sums;

    public SdwQueryBuilder() {
        sdwRestrictions = new ArrayList<>();
        groupByList = new ArrayList<>();
        sums = new ArrayList<>();
    }

    public SdwQueryBuilder withRestriction(SdwRestriction sdwRestriction) {
        if (sdwRestriction == null) {
            throw new IllegalArgumentException("restriction can't be null");
        }
        sdwRestrictions.add(sdwRestriction);
        return this;
    }

    public SdwQueryBuilder withSum(SdwQuery.Sum sum) {
        if (sum == null) {
            throw new IllegalArgumentException("sum can't be null");
        }
        sums.add(sum);
        return this;
    }

    public SdwQueryBuilder withGroupBy(SdwQuery.GroupBy groupBy) {
        if (groupBy == null) {
            throw new IllegalArgumentException("groupBy can't be null");
        }
        groupByList.add(groupBy);
        return this;
    }

    public SdwQuery build() {
        return new SdwQuery(sdwRestrictions, groupByList, sums);
    }

}
