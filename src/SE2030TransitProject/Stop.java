package SE2030TransitProject;

import java.net.URL;

/**
 * @author ericksons
 * @version 1.0
 * @created 06-Oct-2020 10:28:41 AM
 */
public class Stop {

	private String level_id;
	private LocationTypeEnum location_type;
	private String parent_station;
	private String platform_code;
	private String stop_code;
	private String stop_description;
	private String stop_id;
	private Latitude stop_latitude;
	private Longitude stop_longitude;
	private String stop_name;
	private Timezone stop_timezone;
	private URL stop_url;
	private WheelchairBoardingEnum wheelchair_boarding;
	private String zone_id;
	public LocationTypeEnum m_LocationTypeEnum;
	public WheelchairBoardingEnum m_WheelchairBoardingEnum;

	public Stop(){

	}

	public void finalize() throws Throwable {

	}
	public String getLevelID(){
		return "";
	}

	public LocationTypeEnum getLocationType(){
		return null;
	}

	public String getParentStation(){
		return "";
	}

	public String getPlatformCode(){
		return "";
	}

	public String getStopCode(){
		return "";
	}

	public String getStopDescription(){
		return "";
	}

	public String getStopID(){
		return "";
	}

	public Latitude getStopLatitude(){
		return null;
	}

	public Location getStopLocation(){
		return null;
	}

	public Longitude getStopLongitude(){
		return null;
	}

	public String getStopName(){
		return "";
	}

	public Timezone getStopTimezone(){
		return null;
	}

	public URL getStopURL(){
		return null;
	}

	public WheelchairBoardingEnum getWheelchairBoarding(){
		return null;
	}

	public String getZoneID(){
		return "";
	}
}//end Stop