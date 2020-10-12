package SE2030TransitProject;

import java.net.URL;
import java.util.TimeZone;

/**
 * Class for a Stop object, which is the location where the driver's drive to for the routes
 * @author Joy Cross
 * @version 1.0
 */
public class Stop {

	private String level_id;
	private LocationTypeEnum location_type;
	private String parent_station;
	private String platform_code;
	private String stop_code;
	private String stop_description;
	private final String stop_id;
	private double stop_latitude;
	private double stop_longitude;
	private String stop_name;
	private TimeZone stop_timezone;
	private URL stop_url;
	private WheelchairBoardingEnum wheelchair_boarding;
	// private String zone_id; // used if we have fares.txt so I don't think we should include it
	public LocationTypeEnum m_LocationTypeEnum;
	public WheelchairBoardingEnum m_WheelchairBoardingEnum; //??? what is m_WheelchairBoardingEnum???? also do I need javadoc for getter/setter?

	public Stop(String level_id, LocationTypeEnum location_type, String parent_station,
				String platform_code, String stop_code, String stop_description, String stop_id,
				double stop_latitude, double stop_longitude, String stop_name, TimeZone stop_timezone,
				URL stop_url,WheelchairBoardingEnum wheelchair_boarding){

		this.level_id = level_id;
		this.location_type = location_type;
		this.parent_station = parent_station;
		this.platform_code = platform_code;
		this.stop_code = stop_code;
		this.stop_description = stop_description;
		this.stop_id = stop_id;
		this.stop_latitude = stop_latitude;
		this.stop_longitude = stop_longitude;
		this.stop_name = stop_name;
		this.stop_timezone = stop_timezone;
		this.stop_url = stop_url;
		this.wheelchair_boarding = wheelchair_boarding;
		this.stop_name = stop_name;
		//this.zone_id = zone_id;
		this.m_LocationTypeEnum = null;
		this.m_WheelchairBoardingEnum = null;
	}

	public String getLevelID(){
		return level_id;
	}

	public LocationTypeEnum getLocationType(){
		return location_type;
	}

	public LocationTypeEnum getM_LocationTypeEnum(){
		return m_LocationTypeEnum;
	}

	public String getParentStation(){
		return parent_station;
	}

	public String getPlatformCode(){
		return platform_code;
	}

	public String getStopCode(){
		return stop_code;
	}

	public String getStopDescription(){
		return stop_description;
	}

	public String getStopID(){
		return stop_id;
	}

	public double getStopLatitude(){
		return stop_latitude;
	}

	public double getStopLongitude(){
		return stop_longitude;
	}

	public String getStopName(){
		return stop_name;
	}

	public TimeZone getStopTimezone(){
		return stop_timezone;
	}

	public URL getStopURL(){
		return stop_url;
	}

	public WheelchairBoardingEnum getWheelchairBoarding(){
		return wheelchair_boarding;
	}

	public WheelchairBoardingEnum getM_WheelchairBoarding(){
		return m_WheelchairBoardingEnum;
	}

	public void setLevelID(String level_id){
		this.level_id = level_id;
	}

	public void setLocationType(LocationTypeEnum location_type){
		this.location_type = location_type;
	}

	public void setM_LocationTypeEnum(LocationTypeEnum m_LocationTypeEnum){
		this.m_LocationTypeEnum = m_LocationTypeEnum;
	}

	public void setParentStation(String parent_station){
		this.parent_station = parent_station;
	}

	public void setPlatformCode(String platform_code){
		this.platform_code = platform_code;
	}

	public void setStopCode(String stop_code){
		this.stop_code = stop_code;
	}

	public void setStopDescription(String stop_description){
		this.stop_description = stop_description;
	}

	public void setStopLatitude(double stop_latitude){
		this.stop_latitude = stop_latitude;
	}

	public void setStopLongitude(double stop_longitude){
		this.stop_longitude = stop_longitude;
	}

	public void setStopName(String stop_name){
		this.stop_name = stop_name;
	}

	public void setStopTimezone(TimeZone stop_timezone){
		this.stop_timezone = stop_timezone;
	}

	public void setStopURL(URL stop_url){
		this.stop_url = stop_url;
	}

	public void setWheelchairBoarding(WheelchairBoardingEnum wheelchair_boarding){
		this.wheelchair_boarding = wheelchair_boarding;
	}

	public void setM_WheelchairBoarding(WheelchairBoardingEnum m_WheelchairBoardingEnum){
		this.m_WheelchairBoardingEnum = m_WheelchairBoardingEnum;
	}

	/**
	 * Method to output data as a single concatenated string for Stop
	 * @author Joy Cross,
	 * @return string of data
	 */
	@Override
	public String toString() {
		 return "ID: " + stop_id + "; NAME: " + stop_name + "\n\tlevel_is: " + level_id + "; location_type: "
				 + location_type + "; parent_station: " + parent_station + "; platform_code: " +
				 platform_code + "; stop_code: " + stop_code + "; stop_desc: " + stop_description + "; stop_lat: " +
				stop_latitude + "; stop_lon: " + stop_longitude + "; stop_timezone: " + stop_timezone
				 + "; stop_url: " + stop_url + "; wheelchair_boarding: " + wheelchair_boarding;
	}
}//end Stop