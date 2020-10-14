package SE2030TransitProject;


/**
 * Indicates pickup method. Valid options are:
 *
 * 0 or empty - Regularly scheduled pickup.
 * 1 - No pickup available.
 * 2 - Must phone agency to arrange pickup.
 * 3 - Must coordinate with driver to arrange pickup.
 *
 * @author Grant Fass
 * @version 1.0
 * @since 06-Oct-2020 10:28:15 AM
 */
public enum PickupTypeEnum {
    REGULARLY_SCHEDULED_PICKUP(0),
    NO_PICKUP_AVAILABLE(1),
    PHONE_AGENCY_FOR_PICKUP(2),
    COORDINATE_WITH_DRIVER_FOR_PICKUP(3)
    ;

    private final int value;

    /**
     * Constructor for the Enumerator
     * @param value the value to set the instance of the enum to
     * @author Grant Fass
     */
    PickupTypeEnum(int value) {
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
    public static PickupTypeEnum getValue(int value) {
        if (value == 1) {
            return NO_PICKUP_AVAILABLE;
        } else if (value == 2) {
            return PHONE_AGENCY_FOR_PICKUP;
        } else if (value == 3) {
            return COORDINATE_WITH_DRIVER_FOR_PICKUP;
        } else {
            return REGULARLY_SCHEDULED_PICKUP;
        }
    }
}