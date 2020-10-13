package SE2030TransitProject;

/**
 * Class for a Trip object, which is the path that a Driver will take from one stop to another.
 *
 * @author ericksons
 * @version 1.0
 * @created 06-Oct-2020 10:28:39 AM
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

    /**
     * @author Ryan Becker
     * @param bikes_allowed Enum for if bikes are allowed
     * @param block_id The block ID
     * @param direction_id The direction ID
     * @param route_id The route ID
     * @param service_id The service ID
     * @param shape_id The shape ID
     * @param trip_headsign The trip head sign
     * @param trip_id The ID for the trip
     * @param trip_short_name The shorter version of the trip name
     * @param wheelchair_accessible Enum for if it is accessible for wheelchairs
     */
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

    /**
     * returns bikes_allowed
     * @author Simon Erickson
     * @return Enum for if bikes are allowed
     */
    public BikesAllowedEnum getBikesAllowed() {
        return bikes_allowed;
    }

    /**
     * returns block_id
     * @author Simon Erickson
     * @return The block ID
     */
    public String getBlockID() {
        return block_id;
    }

    /**
     * returns direction_id
     * @author Simon Erickson
     * @return The direction ID
     */
    public DirectionIDEnum getDirectionID() {
        return direction_id;
    }

    /**
     * returns route_id
     * @author Simon Erickson
     * @return The Route ID
     */
    public String getRouteID() {
        return route_id;
    }

    /**
     * returns service_id
     * @author Simon Erickson
     * @return The service id
     */
    public String getServiceID() {
        return service_id;
    }

    /**
     * returns shape_id
     * @author Simon Erickson
     * @return The id for the shape
     */
    public String getShapeID() {
        return shape_id;
    }

    /**
     * returns trip_headsign
     * @author Simon Erickson
     * @return the headsign for the trip
     */
    public String getTripHeadsign() {
        return trip_headsign;
    }

    /**
     * returns trip_id
     * @author Simon Erickson
     * @return The ID for the trip
     */
    public String getTripID() {
        return trip_id;
    }

    /**
     * returns trip_short_name
     * @author Simon Erickson
     * @return The short version of the trip name
     */
    public String getTripShortName() {
        return trip_short_name;
    }

    /**
     * returns wheelchair_accessible
     * @author Simon Erickson
     * @return Enum for if it is accessible for wheelchairs
     */
    public WheelchairAccessibleEnum getWheelchairAccessible() {
        return wheelchair_accessible;
    }

    /**
     * Method to output data as a single concatenated string
     *
     * @return string of data
     * @author GrantFass, Simon Erickson
     */
    @Override
    public String toString() {
        return "Trip ID: " + trip_id + "\n\tBikes Allowed: " + bikes_allowed
                + "\n\tBolck ID: " + block_id + "\n\tDirection ID: " + direction_id
                + "\n\tRoute ID: " + route_id + "\n\tService ID: " + service_id
                + "\n\tShape ID: " + shape_id + "\n\tTrip ID: " + trip_id
                + "\n\tTrip Short Name: " + trip_short_name + "Trip Head Sign: " + trip_headsign
                + "\n\tWheel Chair Accessible: " + wheelchair_accessible;
    }
}//end Trip
