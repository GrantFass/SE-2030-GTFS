/*
 * Authors: Becker, Ryan; Cross, Joy; Erickson, Simon; Fass, Grant;
 * Class: SE 2030 - 021
 * Team: G
 * Affiliation: Milwaukee School Of Engineering (MSOE)
 * Program Name: General Transit Feed Specification Tool
 * Copyright (C): GNU GPLv3; 9 November 2020
 *
 * This file is a part of the General Transit Feed Specification Tool
 * written by Team G of class SE 2030 - 021 at MSOE.
 *
 * This is a free software: it can be redistributed and/or modified
 * as expressed in the GNU GPLv3 written by the Free Software Foundation.
 *
 * This software is distributed in hopes that it is useful but does
 * not include any warranties, not even implied warranties. There is more
 * information about this in the GNU GPLv3.
 *
 * To view the license go to <gnu.org/licenses/gpl-3.0.en.html>
 */
package data;

import enumerators.LocationTypeEnum;
import enumerators.WheelchairBoardingEnum;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.TimeZone;

/**
 * Class for a Stop object, which is the location where the driver's drive to for the routes
 * @author Joy Cross
 * @version 1.0
 */
public class Stop {
	//region properties
	private String level_id;
	private LocationTypeEnum location_type;
	private String parent_station;
	private String platform_code;
	private String stop_code;
	private String stop_description;
	private final String stop_id;
	private int tripsPerStop;
	private double stop_latitude;
	private double stop_longitude;
	private String stop_name;
	private TimeZone stop_timezone;
	private URL stop_url;
	private WheelchairBoardingEnum wheelchair_boarding;
	//endregion

	//region constructors
	/**
	 * Stop Constructor
	 * @author Joy Cross
	 * @param level_id level ID of the location
	 * @param location_type type of location
	 * @param parent_station defines hierarchy between the different locations in stops
	 * @param platform_code platform Identifier for a platform stop
	 * @param stop_code short text that identifies location to rider
	 * @param stop_description description of stop
	 * @param stop_id unique id of stop
	 * @param stop_latitude latitude coordinate of location of stop
	 * @param stop_longitude longitude coordinate of location of stop
	 * @param stop_name name of stop
	 * @param stop_timezone timezone of the location
	 * @param stop_url url of web page about the location
	 * @param wheelchair_boarding indicates whether wheelchair boardings are possible from the location
	 */
	public Stop(String level_id, String location_type, String parent_station,
				String platform_code, String stop_code, String stop_description, String stop_id,
				String stop_latitude, String stop_longitude, String stop_name, String stop_timezone,
				String stop_url,String wheelchair_boarding){
		//stop_id & trip_id are required so error if they are empty
		if (stop_id.isEmpty() || stop_latitude.isEmpty() || stop_longitude.isEmpty()) {
			throw new IllegalArgumentException("Line in 'stops.txt' file not formatted" +
					" correctly. Skipping!");
		}

		this.level_id = level_id;
		this.parent_station = parent_station;
		this.platform_code = platform_code;
		this.stop_code = stop_code;
		this.stop_description = stop_description;
		this.stop_id = stop_id;
		this.stop_name = stop_name;
		//this.zone_id = zone_id;

		try {
			this.stop_latitude = Double.parseDouble(stop_latitude);
			this.stop_longitude = Double.parseDouble(stop_longitude);

			//default values are applied if empty.
			this.wheelchair_boarding = WheelchairBoardingEnum.getValue(!wheelchair_boarding.isEmpty() ? Integer.parseInt(wheelchair_boarding) : -1);
			this.location_type = LocationTypeEnum.getValue(!location_type.isEmpty() ? Integer.parseInt(location_type) : -1);

			if(!stop_timezone.isEmpty() && !stop_timezone.equals("null")){
				this.stop_timezone = TimeZone.getTimeZone(stop_timezone);
				if(!this.stop_timezone.getID().equals(stop_timezone)){
					throw new ParseException("Timezone was not in correct format", 0);
				}
			}
			try {
				if(!stop_url.isEmpty() && !stop_url.equals("null")) {
					this.stop_url = new URL(stop_url);
				}
			} catch (MalformedURLException mue) {
				throw new ParseException("URL not formatted correctly", 0);
			}
		} catch (ParseException parse) {
			throw new IllegalArgumentException("Line in 'stops.txt' file not formatted" +
					" correctly. Skipping!");
		}
	}
	//endregion

	//region getters
	/**
	 * gets the file headers
	 * used for exporting stops
	 * @return file headers in a single line header format
	 * @author Joy Cross
	 */
	public static String getHeaderLine() {
		return "stop_id,stop_longitude,stop_lon,stop_latitude,stop_lat,stop_name,stop_description,stop_desc" +
				",stop_code,platform_code,level_id,location_type," +
				"stop_timezone,wheelchair_boarding,stop_url,parent_station\n";
	}

	/**
	 * gets the data line for a single stop
	 * used for exporting stops
	 * @return stop time data in a single line data format
	 * @author Joy Cross
	 */
	public String getDataLine(Headers headers) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < headers.length(); i++){
			switch (headers.getHeaderName(i)) {
				case "stop_id":
					sb.append(stop_id);
					break;
				case "stop_longitude":
				case "stop_lon":
					sb.append(stop_longitude);
					break;
				case "stop_latitude":
				case "stop_lat":
					sb.append(stop_latitude);
					break;
				case "stop_name":
					sb.append(stop_name);
					break;
				case "stop_description":
				case "stop_desc":
					sb.append(stop_description);
					break;
				case "stop_code":
					sb.append(stop_code);
					break;
				case "platform_code":
					sb.append(platform_code);
					break;
				case "level_id":
					sb.append(level_id);
					break;
				case "location_type":
					sb.append(location_type.getValue());
					break;
				case "stop_timezone":
					String timezone = null;
					if(stop_timezone != null){
						timezone = stop_timezone.getID();
					}
					sb.append(timezone);
					break;
				case "wheelchair_boarding":
					sb.append(wheelchair_boarding.getValue());
					break;
				case "stop_url":
					String url = "";
					if(stop_url != null){
						url = stop_url.toString();
					}
					sb.append(url);
					break;
				case "parent_station":
					sb.append(parent_station);
					break;
			}
			sb.append(',');
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append('\n');
		return sb.toString();
	}

	/**
	 * @author Joy Cross
	 */
	public String getLevelID(){
		return level_id;
	}

	/**
	 * @author Joy Cross
	 */
	public LocationTypeEnum getLocationType(){
		return location_type;
	}

	/**
	 * @author Joy Cross
	 */
	public String getParentStation(){
		return parent_station;
	}

	/**
	 * @author Joy Cross
	 */
	public String getPlatformCode(){
		return platform_code;
	}

	/**
	 * @author Joy Cross
	 */
	public String getStopCode(){
		return stop_code;
	}

	/**
	 * @author Joy Cross
	 */
	public String getStopDescription(){
		return stop_description;
	}

	/**
	 * @author Joy Cross
	 */
	public String getStopID(){
		return stop_id;
	}

	/**
	 * @author Joy Cross
	 */
	public double getStopLatitude(){
		return stop_latitude;
	}

	/**
	 * @author Joy Cross
	 */
	public double getStopLongitude(){
		return stop_longitude;
	}

	/**
	 * @author Joy Cross
	 */
	public String getStopName(){
		return stop_name;
	}

	/**
	 * @author Joy Cross
	 */
	public TimeZone getStopTimezone(){
		return stop_timezone;
	}

	/**
	 * @author Joy Cross
	 */
	public URL getStopURL(){
		return stop_url;
	}

	/**
	 * @author Joy Cross
	 */
	public WheelchairBoardingEnum getWheelchairBoarding(){
		return wheelchair_boarding;
	}
	//endregion

	//region setters
	/**
	 * @author Joy Cross
	 */
	public void setTripsPerStop(int tripsPerStop){
		this.tripsPerStop = tripsPerStop;
	}

	/**
	 * @author Joy Cross
	 */
	public void setLevelID(String level_id){
		this.level_id = level_id;
	}

	/**
	 * @author Joy Cross
	 */
	public void setLocationType(LocationTypeEnum location_type){
		this.location_type = location_type;
	}

	/**
	 * @author Joy Cross
	 */
	public void setParentStation(String parent_station){
		this.parent_station = parent_station;
	}

	/**
	 * @author Joy Cross
	 */
	public void setPlatformCode(String platform_code){
		this.platform_code = platform_code;
	}

	/**
	 * @author Joy Cross
	 */
	public void setStopCode(String stop_code){
		this.stop_code = stop_code;
	}

	/**
	 * @author Joy Cross
	 */
	public void setStopDescription(String stop_description){
		this.stop_description = stop_description;
	}

	/**
	 * @author Joy Cross
	 */
	public void setStopLatitude(double stop_latitude){
		this.stop_latitude = stop_latitude;
	}

	/**
	 * @author Joy Cross
	 */
	public void setStopLongitude(double stop_longitude){
		this.stop_longitude = stop_longitude;
	}

	/**
	 * @author Joy Cross
	 */
	public void setStopName(String stop_name){
		this.stop_name = stop_name;
	}

	/**
	 * @author Joy Cross
	 */
	public void setStopTimezone(TimeZone stop_timezone){
		this.stop_timezone = stop_timezone;
	}

	/**
	 * @author Joy Cross
	 */
	public void setStopURL(URL stop_url){
		this.stop_url = stop_url;
	}

	/**
	 * @author Joy Cross
	 */
	public void setWheelchairBoarding(WheelchairBoardingEnum wheelchair_boarding){
		this.wheelchair_boarding = wheelchair_boarding;
	}
	//endregion

	//region string outputs
	/**
	 * Method to output data as a single concatenated string for Stop
	 * @author Joy Cross, Grant Fass
	 * @return string of data
	 */
	@Override
	public String toString() {
		return String.format("Stop Name: %s | Stop ID: %s\n\t" +
				"Level ID: %s\n\t" +
				"Location Type: %s\n\t" +
				"Parent Station: %s\n\t" +
				"Platform Code: %s\n\t" +
				"Stop Code: %s\n\t" +
				"Stop Description: %s\n\t" +
				"Stop Latitude: %s\n\t" +
				"Stop Longitude: %s\n\t" +
				"Stop Timezone: %s\n\t" +
				"Stop URL: %s\n\t" +
				"Wheelchair Boarding: %s\n\t" +
				"Trips that contain stop: %s\n", stop_name, stop_id, level_id, location_type,
				parent_station, platform_code, stop_code, stop_description, stop_latitude,
				stop_longitude, stop_timezone, stop_url, wheelchair_boarding,tripsPerStop);
	}

	/**
	 * output simplified data as a single concatenated string
	 * @return string of data
	 * @author Grant Fass
	 */
	public String toSimpleString() {
		return String.format("Stop ID: %s | Latitude: %s | Longitude: %s\n", stop_id, stop_latitude, stop_longitude);
	}
	//endregion
}//end Stop