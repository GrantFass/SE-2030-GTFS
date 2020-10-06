package SE2030TransitProject;


/**
 * @author ericksons
 * @version 1.0
 * @created 06-Oct-2020 10:28:37 AM
 */
public class Route {

	private int agency_id;
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
	public RouteTypeEnum m_RouteTypeEnum;
	public ContinuousPickupEnum m_ContinuousPickupEnum;
	public ContinousDropDffEnum m_ContinousDropDffEnum;

	public Route(){

	}

	public void finalize() throws Throwable {

	}
	public String getAgencyID(){
		return "";
	}

	public ContinuousDropOffEnum getContinuousDropOffStatus(){
		return null;
	}

	public ContinuousPickupEnum getContinuousPickupStatus(){
		return null;
	}

	public Color getRouteColor(){
		return null;
	}

	public String getRouteDescription(){
		return "";
	}

	public String getRouteID(){
		return "";
	}

	public String getRouteLongName(){
		return "";
	}

	public String getRouteShortName(){
		return "";
	}

	public int getRouteSortOrder(){
		return 0;
	}

	public Color getRouteTextColor(){
		return null;
	}

	public RouteTypeEnum getRouteType(){
		return null;
	}

	public URL getRouteURL(){
		return null;
	}
}//end Route