package SE2030TransitProject;


/**
 * Indicates whether a rider can board the transit vehicle anywhere along the vehicle’s travel path. The path is described by shapes.txt on every trip of the route. Valid options are:
 *
 * 0 - Continuous stopping pickup.
 * 1 or empty - No continuous stopping pickup.
 * 2 - Must phone an agency to arrange continuous stopping pickup.
 * 3 - Must coordinate with a driver to arrange continuous stopping pickup.
 *
 * The default continuous pickup behavior defined in routes.txt can be overridden in stop_times.txt.
 *
 * @author Grant Fass
 * @version 1.0
 * @since 06-Oct-2020 10:28:15 AM
 */
public enum ContinuousPickupEnum {
    CONTINUOUS_PICKUP(0),
    NO_CONTINUOUS_PICKUP(1),
    CALL_AGENCY_TO_ARRANGE_CONTINUOUS_PICKUP(2),
    COORDINATE_WITH_DRIVER_FOR_CONTINUOUS_PICKUP(3)
    ;

    private final int value;

    ContinuousPickupEnum(int value) {
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }
}