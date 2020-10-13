package sdw.api;

import lombok.Getter;

@Getter
public class SdwRestriction {
    private final Operator operator;
    private final Object value;
    private final SdwQuery.RestrictionColumn restrictionColumn;

    public enum Operator {
        EQUAL,
        LESS_THEN_DATE,
        GREATER_THEN_DATE
    }

    SdwRestriction(Operator operator, Object value, SdwQuery.RestrictionColumn restrictionColumn) {
        if (operator == null) {
            throw new IllegalArgumentException("operator can't be null");
        }
        if (value == null) {
            throw new IllegalArgumentException("value can't be null");
        }
        if (restrictionColumn == null) {
            throw new IllegalArgumentException("restrictionColumn can't be empty");
        }
        this.operator = operator;
        this.value = value;
        this.restrictionColumn = restrictionColumn;
    }
}