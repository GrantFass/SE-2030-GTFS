package SE2030TransitProject;


/**
 * Indicates the type of transportation used on a route. Valid options are:
 *
 * 0 - Tram, Streetcar, Light rail. Any light rail or street level system within a metropolitan area.
 * 1 - Subway, Metro. Any underground rail system within a metropolitan area.
 * 2 - Rail. Used for intercity or long-distance travel.
 * 3 - Bus. Used for short- and long-distance bus routes.
 * 4 - Ferry. Used for short- and long-distance boat service.
 * 5 - Cable tram. Used for street-level rail cars where the cable runs beneath the vehicle, e.g., cable car in San Francisco.
 * 6 - Aerial lift, suspended cable car (e.g., gondola lift, aerial tramway). Cable transport where cabins, cars, gondolas or open chairs are suspended by means of one or more cables.
 * 7 - Funicular. Any rail system designed for steep inclines.
 * 11 - Trolleybus. Electric buses that draw power from overhead wires using poles.
 * 12 - Monorail. Railway in which the track consists of a single rail or a beam.
 *
 * @author Grant Fass
 * @version 1.0
 * @since 06-Oct-2020 10:28:40 AM
 */
public enum RouteTypeEnum {
    STREETCAR(0),
    SUBWAY(1),
    RAIL(2),
    BUS(3),
    FERRY(4),
    CABLE_CAR(5),
    AERIAL_LIFT(6),
    FUNICULAR(7),
    TROLLEYBUS(8),
    MONORAIL(12)
    ;

    private final int value;

    /**
     * Constructor for the Enumerator
     * @param value the value to set the instance of the enum to
     * @author Grant Fass
     */
    RouteTypeEnum(int value) {
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
     * @author Grant Fass, Ryan Becker
     */
    public static RouteTypeEnum getValue(int value) {
        if (value == 0) {
            return STREETCAR;
        } else if (value == 1) {
            return SUBWAY;
        } else if (value == 2) {
            return RAIL;
        } else if (value == 4) {
            return FERRY;
        } else if (value == 5) {
            return CABLE_CAR;
        } else if (value == 6) {
            return AERIAL_LIFT;
        } else if (value == 7) {
            return FUNICULAR;
        } else if (value == 8) {
            return TROLLEYBUS;
        } else if (value == 12) {
            return MONORAIL;
        } else {
            return BUS;
        }
    }}