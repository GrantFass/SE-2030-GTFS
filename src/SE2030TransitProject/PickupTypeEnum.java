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

    PickupTypeEnum(int value) {
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }
}