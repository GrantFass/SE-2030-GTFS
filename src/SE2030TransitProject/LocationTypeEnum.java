package SE2030TransitProject;


/**
 * Type of the location:
 * • 0 (or empty): Stop (or Platform). A location where passengers board or disembark from a transit vehicle. Is called a platform when defined within a parent_station.
 * • 1: Station. A physical structure or area that contains one or more platform.
 * • 2: Entrance/Exit. A location where passengers can enter or exit a station from the street. If an entrance/exit belongs to multiple stations, it can be linked by pathways to both, but the data provider must pick one of them as parent.
 * • 3: Generic Node. A location within a station, not matching any other location_type, which can be used to link together pathways define in pathways.txt.
 * • 4: Boarding Area. A specific location on a platform, where passengers can board and/or alight vehicles.
 *
 * @author Grant Fass
 * @version 1.0
 * @since 06-Oct-2020 10:28:15 AM
 */
public enum LocationTypeEnum {
    STOP_OR_PLATFORM(0),
    STATION(1),
    ENTRANCE(2),
    GENERIC(3),
    BOARDING_AREA(4)
    ;

    private final int value;

    LocationTypeEnum(int value) {
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }
}