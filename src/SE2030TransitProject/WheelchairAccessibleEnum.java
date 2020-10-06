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

    WheelchairAccessibleEnum(int value) {
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }
}