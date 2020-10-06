package SE2030TransitProject;


/**
 * Indicates whether bikes are allowed. Valid options are:
 *
 * 0 or empty - No bike information for the trip.
 * 1 - Vehicle being used on this particular trip can accommodate at least one bicycle.
 * 2 - No bicycles are allowed on this trip.
 *
 * @author Grant Fass
 * @version 1.0
 * @since 06-Oct-2020 10:28:15 AM
 */
public enum BikesAllowedEnum {
    NO_INFORMATION(0),
    AT_LEAST_ONE(1),
    NO_BIKES(2)
    ;

    private final int value;

    BikesAllowedEnum(int value) {
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }
}