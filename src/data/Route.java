package data;


import com.sun.xml.internal.ws.commons.xmlutil.Converter;
import enumerators.ContinuousDropOffEnum;
import enumerators.ContinuousPickupEnum;
import enumerators.RouteTypeEnum;
import javafx.scene.paint.Color;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;


/**
 * Class for a Route object, which is the path that a Driver will take to various destinations
 * @author Ryan Becker
 * @version 1.0
 * @created 06-Oct-2020 10:28:37 AM
 */
public class Route {

	private String route_id;
	private String agency_id;
	private String route_short_name;
	private String route_long_name;
	private String route_desc;
	private RouteTypeEnum route_type;
	private URL route_url;
	private Color route_color;
	private Color route_text_color;
	private int route_sort_order;
	private ContinuousPickupEnum continuous_pickup;
	private ContinuousDropOffEnum continuous_drop_off;

	/**
	 * @deprecated
	 * Constructor of Route object
	 * @author Ryan Becker
	 * @param route_id ID of route
	 * @param agency_id ID of agency
	 * @param route_short_name short name of route
	 * @param route_long_name long name of route
	 * @param route_desc String of important information of route
	 * @param route_type Identifier for type of transport
	 * @param route_url URL of website of route
	 * @param route_color Color of route path
	 * @param route_text_color Color of route text
	 * @param route_sort_order Integer indicating order to be sorted and displayed (lower is higher priority)
	 * @param continuous_pickup Status of availability for pickups on route
	 * @param continuous_drop_off Status of availability for drop-offs on route
	 */
	public Route(String route_id, String agency_id, String route_short_name, String route_long_name, String route_desc,
				 RouteTypeEnum route_type, URL route_url, Color route_color, Color route_text_color,
				 int route_sort_order, ContinuousPickupEnum continuous_pickup,
				 ContinuousDropOffEnum continuous_drop_off){

		this.route_id = route_id;
		this.agency_id = agency_id;
		this.route_short_name = route_short_name;
		this.route_long_name = route_long_name;
		this.route_desc = route_desc;
		this.route_type = route_type;
		this.route_url = route_url;
		this.route_color = route_color;
		this.route_text_color = route_text_color;
		this.route_sort_order = route_sort_order;
		this.continuous_pickup = continuous_pickup;
		this.continuous_drop_off = continuous_drop_off;
	}

	/**
	 * Overloaded constructor of Route object, where all parameters are passed initially as Strings
	 * @author Ryan Becker
	 * @param route_id ID of route
	 * @param agency_id ID of agency
	 * @param route_short_name short name of route
	 * @param route_long_name long name of route
	 * @param route_desc String of important information of route
	 * @param route_type Identifier for type of transport
	 * @param route_url URL of website of route
	 * @param route_color Color of route path
	 * @param route_text_color Color of route text
	 * @param route_sort_order Integer indicating order to be sorted and displayed (lower is higher priority)
	 * @param continuous_pickup Status of availability for pickups on route
	 * @param continuous_drop_off Status of availability for drop-offs on route
	 * @throws IllegalArgumentException if there is an invalid value for a given field
	 */
	public Route(String route_id, String agency_id, String route_short_name, String route_long_name, String route_desc,
				 String route_type, String route_url, String route_color, String route_text_color,
				 String route_sort_order, String continuous_pickup, String continuous_drop_off)
			throws IllegalArgumentException{

		//Route only requires a route_id and route_color, but route_color defaults to white if empty per GTFS guidelines
		if(route_id.isEmpty() || route_color.isEmpty()){
			throw new IllegalArgumentException("Data line within routes.txt not formatted correctly.\nSkipping line");
		}


		final int DEFAULT_TYPE = 3; //bus routes
		final String DEFAULT_TEXT_COLOR = "000000"; //defaults to black
		final int DEFAULT_SORT_ORDER = 0;
		final int DEFAULT_CONTINUOUS = -1; //continuous stopping pickup or drop-off

		//final String DEFAULT_URL = "http://NULL"; //Error is thrown otherwise
		//this.route_url = new URL(!route_url.isEmpty() ? route_url : DEFAULT_URL);


		this.route_id = route_id;
		//Start can be empty
		this.agency_id = agency_id;
		this.route_short_name = route_short_name;
		this.route_long_name = route_long_name;
		this.route_desc = route_desc;
		//End can be empty
		try{
			this.route_type = RouteTypeEnum.getValue(!route_type.isEmpty() ? Integer.parseInt(route_type): DEFAULT_TYPE);
			if(!route_url.isEmpty() && !route_url.equals("null")) {
				this.route_url = new URL(route_url);
			}
			this.route_color = Color.valueOf(route_color); //!route_color.isEmpty() ? route_color : DEFAULT_COLOR
			this.route_text_color = Color.valueOf(!route_text_color.isEmpty() ? route_text_color : DEFAULT_TEXT_COLOR);
			this.route_sort_order = !route_sort_order.isEmpty() ? Integer.parseInt(route_sort_order) : DEFAULT_SORT_ORDER;
			this.continuous_pickup = ContinuousPickupEnum.getValue(!continuous_pickup.isEmpty() ? Integer.parseInt(continuous_pickup): DEFAULT_CONTINUOUS);
			this.continuous_drop_off = ContinuousDropOffEnum.getValue(!continuous_drop_off.isEmpty() ? Integer.parseInt(continuous_drop_off): DEFAULT_CONTINUOUS);

			//checks if sort order is positive
			if(this.route_sort_order < 0){
				throw new IllegalArgumentException("Data line within routes.txt not formatted correctly.\nSkipping line");
			}


		} catch (MalformedURLException failedParse){
			throw new IllegalArgumentException("Data line within routes.txt not formatted correctly.\nSkipping line");
		}
	}

	//Getters
	/**
	 * returns route_id
	 * @author Ryan Becker
	 * @return ID of route as a String
	 */
	public String getRouteID(){

		return route_id;
	}

	/**
	 * returns agency_id
	 * @author Ryan Becker
	 * @return ID of agency as a String
	 */
	public String getAgencyID() {
		return agency_id;
	}

	/**
	 * returns route_short_name
	 * @author Ryan Becker
	 * @return short name of route as a String
	 */
	public String getRouteShortName() {
		return route_short_name;
	}

	/**
	 * returns route_long_name
	 * @author Ryan Becker
	 * @return long name of route as a String
	 */
	public String getRouteLongName() {
		return route_long_name;
	}

	/**
	 * returns route_desc
	 * @author Ryan Becker
	 * @return detailed description of route as a String
	 */
	public String getRouteDesc() {
		return route_desc;
	}

	/**
	 * returns route_type
	 * @author Ryan Becker
	 * @return RouteTypeEnum, which is the method of transport of a route
	 */
	public RouteTypeEnum getRouteType() {
		return route_type;
	}

	/**
	 * returns route_url
	 * @author Ryan Becker
	 * @return URL of webpage for the route as a URL object
	 */
	public URL getRouteURL() {
		return route_url;
	}

	/**
	 * returns route_color
	 * @author Ryan Becker
	 * @return Color of route
	 */
	public Color getRouteColor() {
		return route_color;
	}

	/**
	 * returns route_text_color
	 * @author Ryan Becker
	 * @return Color of text of route
	 */
	public Color getRouteTextColor() {
		return route_text_color;
	}

	/**
	 * return route_sort_order
	 * @author Ryan Becker
	 * @return positive int value prioritizing order in list. Lower value has higher priority
	 * @throws IllegalArgumentException if route_sort_order is negative
	 */
	public int getRouteSortOrder() throws IllegalArgumentException{
		if(route_sort_order < 0){
			throw new IllegalArgumentException("Invalid Data Field for route_sort_order");
		}
		return route_sort_order;
	}

	/**
	 * return continuous_pickup
	 * @author Ryan Becker
	 * @return ContinuousPickupEnum, which is the status of pickup availability along a route
	 */
	public ContinuousPickupEnum getContinuousPickup() {
		return continuous_pickup;
	}
	/**
	 * return continuous_drop_off
	 * @author Ryan Becker
	 * @return ContinuousDropOffEnum, which is the status of drop-off availability along a route
	 */
	public ContinuousDropOffEnum getContinuousDropOff() {
		return continuous_drop_off;
	}


	/**
	 * returns header line to be used for exporting routes.txt
	 * @author Ryan Becker
	 * @return String of the header line to be used for export
	 */
	public static String getHeaderLine(){
		return "route_id,agency_id,route_short_name,route_long_name,route_desc,route_type," +
				"route_url,route_color,route_text_color,route_sort_order,continuous_pickup,continuous_drop_off";
	}

	/**
	 * returns String of the data used in exporting routes.txt
	 * @author Ryan Becker, Joy Cross
	 * @return String of the data line read from file, and defaulted values where applicable
	 */
	public String getDataLine(Headers headers){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < headers.length(); i++){
			switch (headers.getHeaderName(i)) {
				case "route_id":
					sb.append(route_id);
					break;
				case "agency_id":
					sb.append(agency_id);
					break;
				case "route_short_name":
					sb.append(route_short_name);
					break;
				case "route_long_name":
					sb.append(route_long_name);
					break;
				case "route_desc":
				case "route_description":
					sb.append(route_desc);
					break;
				case "route_type":
					sb.append(route_type.getValue());
					break;
				case "route_color":
					int r = (int)(route_color.getRed()*256);
					int b = (int)(route_color.getBlue()*256);
					int g = (int)(route_color.getGreen()*256);
					String hex = String.format("%02x%02x%02x", r, g, b);
					sb.append(hex);
					break;
				case "route_text_color":
					r = (int)(route_text_color.getRed()*256);
					b = (int)(route_text_color.getBlue()*256);
					g = (int)(route_text_color.getGreen()*256);
					hex = String.format("%02x%02x%02x", r, g, b);
					sb.append(hex);
					break;
				case "route_sort_order":
					sb.append(route_sort_order);
					break;
				case "continuous_pickup":
					sb.append(continuous_pickup.getValue());
					break;
				case "route_url":
					String url = "";
					if(route_url != null){
						url = route_url.toString();
					}
					sb.append(url);
					break;
				case "continuous_drop_off":
					sb.append(continuous_drop_off.getValue());
					break;
			}
			sb.append(',');
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append('\n');
		return sb.toString();
	}


	//Setters

	/**
	 * sets route_id
	 * @author Ryan Becker
	 * @param route_id String of route's ID
	 */
	public void setRouteID(String route_id) {
		this.route_id = route_id;
	}

	/**
	 * sets agency_id
	 * @author Ryan Becker
	 * @param agency_id String of agency's ID
	 */
	public void setAgencyID(String agency_id) {
		this.agency_id = agency_id;
	}

	/**
	 * sets route_short_name
	 * @author Ryan Becker
	 * @param route_short_name String of the short name of route
	 */
	public void setRouteShortName(String route_short_name) {
		this.route_short_name = route_short_name;
	}

	/**
	 * sets route_long_name
	 * @author Ryan Becker
	 * @param route_long_name String of the long name of route
	 */
	public void setRouteLongName(String route_long_name) {
		this.route_long_name = route_long_name;
	}

	/**
	 * sets route_desc
	 * @author Ryan Becker
	 * @param route_desc String of detailed information of route
	 */
	public void setRouteDesc(String route_desc) {
		this.route_desc = route_desc;
	}

	/**
	 * sets route_type
	 * @author Ryan Becker
	 * @param route_type Enumerator associated with method of transport for route
	 */
	public void setRouteType(RouteTypeEnum route_type) {
		this.route_type = route_type;
	}

	/**
	 * sets route_url
	 * @author Ryan Becker
	 * @param route_url URL of web page for route
	 */
	public void setRouteURL(URL route_url) {
		this.route_url = route_url;
	}

	/**
	 * sets route_color
	 * @author Ryan Becker
	 * @param route_color Color of route
	 */
	public void setRouteColor(Color route_color) {
		this.route_color = route_color;
	}

	/**
	 * sets route_text_color
	 * @author Ryan Becker
	 * @param route_text_color Color of text for labels for route
	 */
	public void setRouteTextColor(Color route_text_color) {
		this.route_text_color = route_text_color;
	}

	/**
	 * sets route_sort_order
	 * @author Ryan Becker
	 * @param route_sort_order integer, where lower number indicates a higher priority in sorting
	 */
	public void setRouteSortOrder(int route_sort_order) {
		this.route_sort_order = route_sort_order;
	}

	/**
	 * sets continuous_pickup
	 * @author Ryan Becker
	 * @param continuous_pickup Enumerator for status of ability for pickups along route
	 */
	public void setContinuousPickup(ContinuousPickupEnum continuous_pickup) {
		this.continuous_pickup = continuous_pickup;
	}

    /**
     * sets continuous_drop_off
	 * @author Ryan Becker
     * @param continuous_drop_off Enumerator for status of ability for drop-offs along route
     */
	public void setContinuousDropOff(ContinuousDropOffEnum continuous_drop_off) {
		this.continuous_drop_off = continuous_drop_off;
	}

	/**
	 * Method to output data as a single concatenated string
	 * @author GrantFass, Ryan Becker
	 * @return string of data
	 */
	@Override
	public String toString() {
		return String.format("Route Name: %s | Route Short Name: %s | Route Agency: %s | Route ID: %s\n\t" +
				"Route Description: %s\n\t" +
				"Route Type: %s\n\t" +
				"Route URL: %s\n\t" +
				"Route Color: %s\n\t" +
				"Route Text Color: %s\n\t" +
				"Route Sort Order: %s\n\t" +
				"Continuous Pickup: %s\n\t" +
				"Continuous Drop Off: %s\n", route_long_name, route_short_name, agency_id, route_id,
				route_desc, route_type, route_url, route_color, route_text_color, route_sort_order,
				continuous_pickup, continuous_drop_off);
	}

	/**
	 * output simplified data as a single concatenated string
	 * @return string of data
	 * @author Grant Fass
	 */
	public String toSimpleString() {
		return String.format("Route ID: %s | Route Color: %s\n", route_id, route_color);
	}
}//end Route
