package SE2030TransitProject;


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

    TimepointEnum(int value) {
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }
}