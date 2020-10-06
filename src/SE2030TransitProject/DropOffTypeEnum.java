package SE2030TransitProject;


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

    DropOffTypeEnum(int value) {
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }
}