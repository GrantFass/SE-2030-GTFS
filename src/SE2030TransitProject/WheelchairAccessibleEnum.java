package SE2030TransitProject;


/**
 * 	Indicates wheelchair accessibility. Valid options are:
 *
 * 0 or empty - No accessibility information for the trip.
 * 1 - Vehicle being used on this particular trip can accommodate at least one rider in a wheelchair.
 * 2 - No riders in wheelchairs can be accommodated on this trip.
 *
 * @author Grant Fass
 * @version 1.0
 * @since 06-Oct-2020 10:28:47 AM
 */
public enum WheelchairAccessibleEnum {
    NO_ACCESSIBILITY_INFORMATION(0),
    AT_LEAST_ONE_WHEELCHAIR(1),
    NO_WHEELCHAIRS(2)
    ;

    private final int value;

    /**
     * Constructor for the Enumerator
     * @param value the value to set the instance of the enum to
     * @author Grant Fass
     */
    WheelchairAccessibleEnum(int value) {
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
    public static WheelchairAccessibleEnum getValue(int value) {
        if (value == 1) {
            return AT_LEAST_ONE_WHEELCHAIR;
        } else if (value == 2) {
            return NO_WHEELCHAIRS;
        } else {
            return NO_ACCESSIBILITY_INFORMATION;
        }
    }}