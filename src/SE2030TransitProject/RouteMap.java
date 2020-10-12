package SE2030TransitProject;


import java.util.Observable;
import java.util.Observer;

/**
 * @author
 * @version 1.0
 * @created 06-Oct-2020 10:28:38 AM
 */
public class RouteMap implements Observer {

	public Data m_Data;

	public RouteMap(){

	}

	/**
	 * 
	 * @param route
	 */
	private boolean plotRoute(Route route){
		return false;
	}

	/**
	 * 
	 * @param routes
	 */
	public boolean plotRoutes(Routes routes){
		return false;
	}

	/**
	 * 
	 * @param stop
	 */
	private boolean plotStop(Stop stop){
		return false;
	}

	/**
	 * 
	 * @param stops
	 */
	public boolean plotStops(Stops stops){
		return false;
	}

	/**
	 * 
	 * @param trip
	 */
	private boolean plotTrip(Trip trip){
		return false;
	}

	/**
	 * 
	 * @param trips
	 */
	public boolean plotTrips(Trips trips){
		return false;
	}

	public void update(){

	}

	/**
	 * Method to be run when subject provides information
	 * @param o
	 * @param arg
	 */
	@Override
	public void update(Observable o, Object arg) {

	}
}//end RouteMap