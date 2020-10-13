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

    public Trip(BikesAllowedEnum bikes_allowed, String block_id, DirectionIDEnum direction_id,
                String route_id, String service_id, String shape_id, String trip_headsign,
                String trip_id, String trip_short_name,
                WheelchairAccessibleEnum wheelchair_accessible) {

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
        return "Trip Head Sign: " + trip_headsign + "\n\tBikes Allowed: " + bikes_allowed
                + "\n\tBolck ID: " + block_id + "\n\tDirection ID: " + direction_id
                + "\n\tRoute ID: " + route_id + "\n\tService ID: " + service_id
                + "\n\tShape ID: " + shape_id + "\n\tTrip ID: " + trip_id
                + "\n\tTrip Short Name: " + trip_short_name
                + "\n\tWheel Chair Accessible: " + wheelchair_accessible;
    }
}//end Trip
