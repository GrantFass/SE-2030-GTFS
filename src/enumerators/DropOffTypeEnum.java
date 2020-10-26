package enumerators;


/**
 * Indicates drop off method. Valid options are:
 *
 * 0 or empty - Regularly scheduled drop off.
 * 1 - No drop off available.
 * 2 - Must phone agency to arrange drop off.
 * 3 - Must coordinate with driver to arrange drop off.
 *
 * @author Grant Fass
 * @version 1.0
 * @since 06-Oct-2020 10:28:15 AM
 */
public enum DropOffTypeEnum {
    REGULARLY_SCHEDULED_DROP_OFF(0),
    NO_DROP_OFF_AVAILABLE(1),
    PHONE_AGENCY_FOR_DROP_OFF(2),
    COORDINATE_WITH_DRIVER_FOR_DROP_OFF(3)
    ;

    private final int value;

    /**
     * Constructor for the Enumerator
     * @param value the value to set the instance of the enum to
     * @author Grant Fass
     */
    DropOffTypeEnum(int value) {
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
    public static DropOffTypeEnum getValue(int value) {
        if (value == 1) {
            return NO_DROP_OFF_AVAILABLE;
        } else if (value == 2) {
            return PHONE_AGENCY_FOR_DROP_OFF;
        } else if (value == 3) {
            return COORDINATE_WITH_DRIVER_FOR_DROP_OFF;
        } else {
            return REGULARLY_SCHEDULED_DROP_OFF;
        }
    }
}