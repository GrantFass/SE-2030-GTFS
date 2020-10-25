package enumerators;


/**
 * Indicates if arrival and departure times for a stop are strictly adhered to by the vehicle or if they are instead approximate and/or interpolated times. This field allows a GTFS producer to provide interpolated stop-times, while indicating that the times are approximate. Valid options are:
 *
 * 0 - Times are considered approximate.
 * 1 or empty - Times are considered exact.
 *
 * @author Grant Fass
 * @version 1.0
 * @since 06-Oct-2020 10:28:45 AM
 */
public enum TimepointEnum {
    APPROXIMATE_TIME(0),
    EXACT_TIME(1)
    ;

    private final int value;

    /**
     * Constructor for the Enumerator
     * @param value the value to set the instance of the enum to
     * @author Grant Fass
     */
    TimepointEnum(int value) {
        this.value = value;
    }

    /**
     * return the integer value associated with the enumerator
     * @return the integer value associated with the enumerator
     * @author Grant Fass
     */
    public int getValue(){
        return this.value;
    }

    /**
     * return the enumerator value associated with the specified integer value
     * @param value the value to use
     * @return the enumerator value associated with the integer or the default if none match
     * @author Grant Fass
     */
    public static TimepointEnum getValue(int value) {
        if (value == 0) {
            return APPROXIMATE_TIME;
        }
        return EXACT_TIME;
    }
}