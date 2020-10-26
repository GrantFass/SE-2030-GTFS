package enumerators;


/**
 * 	Indicates whether a rider can alight from the transit vehicle at any point along the vehicle’s travel path. The path is described by shapes.txt on every trip of the route. Valid options are:
 *
 * 0- Continuous stopping drop-off.
 * 1 or empty - No continuous stopping drop-off.
 * 2 - Must phone an agency to arrange continuous stopping drop-off.
 * 3 - Must coordinate with a driver to arrange continuous stopping drop-off.
 *
 * The default continuous drop-off behavior defined in routes.txt can be overridden in stop_times.txt.
 *
 * @author Grant Fass
 * @version 1.0
 * @since 06-Oct-2020 10:28:15 AM
 */
public enum ContinuousDropOffEnum {
    CONTINUOUS_DROP_OFF(0),
    NO_CONTINUOUS_DROP_OFF(1),
    CALL_AGENCY_TO_ARRANGE_CONTINUOUS_DROP_OFF(2),
    COORDINATE_WITH_DRIVER_FOR_CONTINUOUS_DROP_OFF(3)
    ;

    private final int value;

    /**
     * Constructor for the Enumerator
     * @param value the value to set the instance of the enum to
     * @author Grant Fass
     */
    ContinuousDropOffEnum(int value) {
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
    public static ContinuousDropOffEnum getValue(int value) {
        if (value == 0) {
            return CONTINUOUS_DROP_OFF;
        } else if (value == 2) {
            return CALL_AGENCY_TO_ARRANGE_CONTINUOUS_DROP_OFF;
        } else if (value == 3) {
            return COORDINATE_WITH_DRIVER_FOR_CONTINUOUS_DROP_OFF;
        } else {
            return NO_CONTINUOUS_DROP_OFF;
        }
    }
}