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

	public void finalize() throws Throwable {

	}

	public String getRouteID() {
		return route_id;
	}

	public String getAgencyID() {
		return agency_id;
	}

	public String getRouteShortName() {
		return route_short_name;
	}

	public String getRouteLongName() {
		return route_long_name;
	}

	public String getRouteDesc() {
		return route_desc;
	}

	public RouteTypeEnum getRouteType() {
		return route_type;
	}

	public URL getRouteURL() {
		return route_url;
	}

	public Color getRouteColor() {
		return route_color;
	}

	public Color getRouteTextColor() {
		return route_text_color;
	}

	public int getRouteSortOrder() {
		return route_sort_order;
	}

	public ContinuousPickupEnum getContinuousPickup() {
		return continuous_pickup;
	}

	public ContinuousDropOffEnum getContinuousDropOff() {
		return continuous_drop_off;
	}

	/**
	 * Method to output data as a single concatenated string
	 * @author GrantFass, Ryan Becker
	 * @return string of data
	 */
	@Override
	public String toString() {
		return "Method Not Implemented Yet";
	}
}//end Route
