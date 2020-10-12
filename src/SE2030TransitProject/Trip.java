package SE2030TransitProject;


/**
 * @author ericksons
 * @version 1.0
 * @created 06-Oct-2020 10:28:45 AM
 */
public class Trip {

    private BikesAllowedEnum bikes_allowed;
    private String block_id;
    private DirectionIDEnum direction_id;
    private String route_id;
    private String service_id;
    private String shape_id;
    private String trip_headsign;
    private String trip_id;
    private String trip_short_name;
    private WheelchairAccessibleEnum wheelchair_accessible;
    public DirectionIDEnum m_DirectionIDEnum;
    public WheelchairAccessibleEnum m_WheelchairAccessibleEnum;
    public BikesAllowedEnum m_BikesAllowedEnum;

    public Trip(BikesAllowedEnum bikes_allowed, String block_id, DirectionIDEnum direction_id,
                String route_id, String service_id, String shape_id, String trip_headsign,
                String trip_id, String trip_short_name,
                WheelchairAccessibleEnum wheelchair_accessible,
                DirectionIDEnum m_DirectionIDEnum,
                WheelchairAccessibleEnum m_WheelchairAccessibleEnum,
                BikesAllowedEnum m_BikesAllowedEnum) {

        this.bikes_allowed = bikes_allowed;
        this.block_id = block_id;
        this.direction_id = direction_id;
        this.route_id = route_id;
        this.service_id = service_id;
        this.shape_id = shape_id;
        this.trip_headsign = trip_headsign;
        this.trip_id = trip_id;
        this.trip_short_name = trip_short_name;
        this.wheelchair_accessible = wheelchair_accessible;
        this.m_DirectionIDEnum = m_DirectionIDEnum;
        this.m_WheelchairAccessibleEnum = m_WheelchairAccessibleEnum;
        this.m_BikesAllowedEnum = m_BikesAllowedEnum;
    }

    public void finalize() throws Throwable {

    }

    public BikesAllowedEnum getBikesAllowed() {
        return bikes_allowed;
    }

    public String getBlockID() {
        return block_id;
    }

    public DirectionIDEnum getDirectionID() {
        return direction_id;
    }

    public String getRouteID() {
        return route_id;
    }

    public String getServiceID() {
        return service_id;
    }

    public String getShapeID() {
        return shape_id;
    }

    public String getTripHeadsign() {
        return trip_headsign;
    }

    public String getTripID() {
        return trip_id;
    }

    public String getTripShortName() {
        return trip_short_name;
    }

    public WheelchairAccessibleEnum getWheelchairAccessible() {
        return wheelchair_accessible;
    }

    /**
     * Method to output data as a single concatenated string
     *
     * @return string of data
     * @author GrantFass,
     */
    @Override
    public String toString() {
        return bikes_allowed + block_id + direction_id + route_id + service_id + shape_id
                + trip_headsign + trip_id + trip_short_name + wheelchair_accessible
                + m_DirectionIDEnum + m_WheelchairAccessibleEnum + m_BikesAllowedEnum;
    }
}//end Trip
