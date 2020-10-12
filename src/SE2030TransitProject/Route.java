package SE2030TransitProject;


import javafx.scene.paint.Color;

import java.net.URL;

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

	//Getters
	/**
	 * returns route_id
	 * @return ID of route as a String
	 */
	public String getRouteID() {
		return route_id;
	}

	/**
	 * returns agency_id
	 * @return ID of agency as a String
	 */
	public String getAgencyID() {
		return agency_id;
	}

	/**
	 * returns route_short_name
	 * @return short name of route as a String
	 */
	public String getRouteShortName() {
		return route_short_name;
	}

	/**
	 * returns route_long_name
	 * @return long name of route as a String
	 */
	public String getRouteLongName() {
		return route_long_name;
	}

	/**
	 * returns route_desc
	 * @return detailed description of route as a String
	 */
	public String getRouteDesc() {
		return route_desc;
	}

	/**
	 * returns route_type
	 * @return RouteTypeEnum, which is the method of transport of a route
	 */
	public RouteTypeEnum getRouteType() {
		return route_type;
	}

	/**
	 * returns route_url
	 * @return URL of webpage for the route as a URL object
	 */
	public URL getRouteURL() {
		return route_url;
	}

	/**
	 * returns route_color
	 * @return Color of route
	 */
	public Color getRouteColor() {
		return route_color;
	}

	/**
	 * returns route_text_color
	 * @return Color of text of route
	 */
	public Color getRouteTextColor() {
		return route_text_color;
	}

	/**
	 * return route_sort_order
	 * @return positive int value prioritizing order in list. Lower value has higher priority
	 */
	public int getRouteSortOrder() {
		return route_sort_order;
	}

	/**
	 * return continuous_pickup
	 * @return ContinuousPickupEnum, which is the status of pickup availability along a route
	 */
	public ContinuousPickupEnum getContinuousPickup() {
		return continuous_pickup;
	}
	/**
	 * return continuous_drop_off
	 * @return ContinuousDropOffEnum, which is the status of drop-off availability along a route
	 */
	public ContinuousDropOffEnum getContinuousDropOff() {
		return continuous_drop_off;
	}

	//Setters

	/**
	 * sets route_id
	 * @param route_id String of route's ID
	 */
	public void setRouteID(String route_id) {
		this.route_id = route_id;
	}

	/**
	 * sets agency_id
	 * @param agency_id String of agency's ID
	 */
	public void setAgencyID(String agency_id) {
		this.agency_id = agency_id;
	}

	/**
	 * sets route_short_name
	 * @param route_short_name String of the short name of route
	 */
	public void setRouteShortName(String route_short_name) {
		this.route_short_name = route_short_name;
	}

	/**
	 * sets route_long_name
	 * @param route_long_name String of the long name of route
	 */
	public void setRouteLongName(String route_long_name) {
		this.route_long_name = route_long_name;
	}

	/**
	 * sets route_desc
	 * @param route_desc String of detailed information of route
	 */
	public void setRouteDesc(String route_desc) {
		this.route_desc = route_desc;
	}

	/**
	 * sets route_type
	 * @param route_type Enumerator associated with method of transport for route
	 */
	public void setRouteType(RouteTypeEnum route_type) {
		this.route_type = route_type;
	}

	/**
	 * sets route_url
	 * @param route_url URL of web page for route
	 */
	public void setRouteURL(URL route_url) {
		this.route_url = route_url;
	}

	/**
	 * sets route_color
	 * @param route_color Color of route
	 */
	public void setRouteColor(Color route_color) {
		this.route_color = route_color;
	}

	/**
	 * sets route_text_color
	 * @param route_text_color Color of text for labels for route
	 */
	public void setRouteTextColor(Color route_text_color) {
		this.route_text_color = route_text_color;
	}

	/**
	 * sets route_sort_order
	 * @param route_sort_order positive integer, where lower number indicates a higher priority in sorting
	 */
	//TODO ensure param is positive
	public void setRouteSortOrder(int route_sort_order) {
		this.route_sort_order = route_sort_order;
	}

	/**
	 * sets continuous_pickup
	 * @param continuous_pickup Enumerator for status of ability for pickups along route
	 */
	public void setContinuousPickup(ContinuousPickupEnum continuous_pickup) {
		this.continuous_pickup = continuous_pickup;
	}

    /**
     * sets continuous_drop_off
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
		return "ROUTE ID: " + route_id + "; AGENCY ID: " + agency_id + "; Short Name: " + route_short_name
                + "; Long Name: " + route_long_name + "\n\tDescription: " + route_desc + "\n\tRoute_type: "
                + route_type + "\n\tRoute_url: " + route_url + "\n\tRoute_color: " + route_color +"\n\tRoute_text_color: "
                + route_text_color + "\n\tSort_order: " + route_sort_order + "\n\tContinuous_pickup: " + continuous_pickup
                + "\n\tContinuous_drop-off: " + continuous_drop_off;
	}
}//end Route
