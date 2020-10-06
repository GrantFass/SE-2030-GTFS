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

	public Trip(){

	}

	public void finalize() throws Throwable {

	}
	public BikesAllowedEnum getBikesAllowed(){
		return null;
	}

	public String getBlockID(){
		return "";
	}

	public DirectionIDEnum getDirectionID(){
		return null;
	}

	public String getRouteID(){
		return "";
	}

	public String getServiceID(){
		return "";
	}

	public String getShapeID(){
		return "";
	}

	public String getTripHeadsign(){
		return "";
	}

	public String getTripID(){
		return "";
	}

	public String getTripShortName(){
		return "";
	}

	public WheelchairAccessibleEnum getWheelchairAccessible(){
		return null;
	}
}//end Trip