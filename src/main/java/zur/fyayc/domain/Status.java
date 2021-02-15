package zur.fyayc.domain;

import lombok.Getter;

@Getter
public enum Status {

    RECEIVED("R"),
    SENT("S"),
    FAILED("F");

    public static final int MAX_RETIES_ALLOWED = 3;

    private String indicator;

    Status(String str) {
        indicator = str;
    }

    public static Status fromString(String statusString) {
        for (Status status : Status.values()) {
            if (status.indicator.equalsIgnoreCase(statusString)) {
                return status;
            }
        }
        throw new IllegalArgumentException(statusString + " is not a valid status.");
    }
    @Override
    public String toString() {
        return indicator;
    }
}
