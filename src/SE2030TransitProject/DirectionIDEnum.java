package SE2030TransitProject;


/**
 * Indicates the direction of travel for a trip. This field is not used in routing; it provides a way to separate trips by direction when publishing time tables. Valid options are:
 *
 * 0 - Travel in one direction (e.g. outbound travel).
 * 1 - Travel in the opposite direction (e.g. inbound travel).
 * Example: The trip_headsign and direction_id fields could be used together to assign a name to travel in each direction for a set of trips. A trips.txt file could contain these records for use in time tables:
 * trip_id,...,trip_headsign,direction_id
 * 1234,...,Airport,0
 * 1505,...,Downtown,1
 *
 * @author Grant Fass
 * @version 1.0
 * @since 06-Oct-2020 10:28:15 AM
 */
public enum DirectionIDEnum {
    OUTBOUND_TRAVEL(0),
    INBOUND_TRAVEL(1)
    ;

    private final int value;

    DirectionIDEnum(int value) {
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }
}