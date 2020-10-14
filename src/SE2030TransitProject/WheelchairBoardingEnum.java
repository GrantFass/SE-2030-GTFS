package SE2030TransitProject;


/**
 * Indicates whether wheelchair boardings are possible from the location. Valid options are:
 *
 * For parentless stops:
 * 0 or empty - No accessibility information for the stop.
 * 1 - Some vehicles at this stop can be boarded by a rider in a wheelchair.
 * 2 - Wheelchair boarding is not possible at this stop.
 *
 * For child stops:
 * 0 or empty - Stop will inherit its wheelchair_boarding behavior from the parent station, if specified in the parent.
 * 1 - There exists some accessible path from outside the station to the specific stop/platform.
 * 2 - There exists no accessible path from outside the station to the specific stop/platform.
 *
 * For station entrances/exits:
 * 0 or empty - Station entrance will inherit its wheelchair_boarding behavior from the parent station, if specified for the parent.
 * 1 - Station entrance is wheelchair accessible.
 * 2 - No accessible path from station entrance to stops/platforms.
 *
 * @author Grant Fass
 * @version 1.0
 * @since 06-Oct-2020 10:28:48 AM
 */
public enum WheelchairBoardingEnum {
    PARENTLESS_NO_INFORMATION(0),
    PARENTLESS_WHEELCHAIR_BOARDING_POSSIBLE(1),
    PARENTLESS_WHEELCHAIR_BOARDING_NOT_POSSIBLE(2),
    CHILD_INHERIT_FROM_PARENT(0),
    CHILD_WHEELCHAIR_BOARDING_POSSIBLE_FROM_EXTERIOR_PATH(1),
    CHILD_WHEELCHAIR_BOARDING_NOT_POSSIBLE_FROM_EXTERIOR_PATH(2),
    STATION_INHERIT_FROM_PARENT(0),
    STATION_WHEELCHAIR_ACCESSIBLE(1),
    STATION_NOT_WHEELCHAIR_ACCESSIBLE(2)
    ;

    private final int value;

    /**
     * Constructor for the Enumerator
     * @param value the value to set the instance of the enum to
     * @author Grant Fass
     */
    WheelchairBoardingEnum(int value) {
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
    public static WheelchairBoardingEnum getValue(int value) {
        if (value == 1) {
            return PARENTLESS_WHEELCHAIR_BOARDING_POSSIBLE;
        } else if (value == 2) {
            return PARENTLESS_WHEELCHAIR_BOARDING_NOT_POSSIBLE;
        } else {
            return PARENTLESS_NO_INFORMATION;
        }
    }}