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

	private String agency_id;
	private ContinuousDropOffEnum continuous_drop_off;
	private ContinuousPickupEnum continuous_pickup;
	private Color route_color;
	private String route_desc;
	private String route_id;
	private String route_long_name;
	private String route_short_name;
	private int route_sort_order;
	private Color route_text_color;
	private RouteTypeEnum route_type;
	private URL route_url;


	public Route(String agency_id, ContinuousDropOffEnum continuous_drop_off, ContinuousPickupEnum continuous_pickup,
				 Color route_color, String route_desc, String route_id, String route_long_name,
				 String route_short_name, int route_sort_order, Color route_text_color, RouteTypeEnum route_type,
				 URL route_url){

		this.agency_id = agency_id;
		this.continuous_drop_off = continuous_drop_off;
		this.continuous_pickup = continuous_pickup;
		this.route_color = route_color;
		this.route_desc = route_desc;
		this.route_id = route_id;
		this.route_long_name = route_long_name;
		this.route_short_name = route_short_name;
		this.route_sort_order = route_sort_order;
		this.route_text_color = route_text_color;
		this.route_type = route_type;
		this.route_url = route_url;
	}

	public void finalize() throws Throwable {

	}
	public String getAgencyID(){
		return agency_id;
	}

	public ContinuousDropOffEnum getContinuousDropOffStatus(){
		return continuous_drop_off;
	}

	public ContinuousPickupEnum getContinuousPickupStatus(){
		return continuous_pickup;
	}

	public Color getRouteColor(){
		return route_color;
	}

	public String getRouteDescription(){
		return route_desc;
	}

	public String getRouteID(){
		return route_id;
	}

	public String getRouteLongName(){
		return route_long_name;
	}

	public String getRouteShortName(){
		return route_short_name;
	}

	public int getRouteSortOrder(){
		return route_sort_order;
	}

	public Color getRouteTextColor(){
		return route_text_color;
	}

	public RouteTypeEnum getRouteType(){
		return route_type;
	}

	public URL getRouteURL(){
		return route_url;
	}

	/**
	 * Method to output data as a single concatenated string
	 * @author GrantFass,
	 * @return string of data
	 */
	@Override
	public String toString() {
		return "Method Not Implemented Yet";
	}
}//end Route