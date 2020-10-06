package SE2030TransitProject;

import java.nio.file.Path;
import java.sql.Time;
import java.util.Collection;

public class Controller {
	public Data m_Data;


	/**
	 * 
	 * @param trip_id
	 */
	public void displayDistance(String trip_id){

	}

	/**
	 * 
	 * @param trip_id
	 */
	public void displaySpeeds(String trip_id){

	}

	/**
	 * 
	 * @param stop_id
	 */
	public void displayTrips(String stop_id){

	}

	/**
	 * 
	 * @param outputLocation
	 */
	public void exportGTFSfiles(Path outputLocation){

	}

	/**
	 * 
	 * @param gtfsFileLocation
	 */
	public void importGTFSfiles(Path gtfsFileLocation){

	}

	/**
	 * 
	 * @param route_id
	 */
	public void plotBus(String route_id){

	}

	/**
	 * 
	 * @param route_id
	 */
	public void plotGPSCoordinates(String route_id){

	}

	/**
	 * 
	 * @param stop_id: String
	 */
	public Collection<Route> searchRoutesByStop(String stop_id){
		return null;
	}

	/**
	 * 
	 * @param route_id: String
	 */
	public Collection<Stop> searchStopsByRoute(String route_id){
		return null;
	}

	/**
	 * 
	 * @param route_id: String
	 */
	public Collection<Trip> searchTripsByRoute(String route_id){
		return null;
	}

	/**
	 * 
	 * @param stop_id: String
	 */
	public Collection<Trip> searchTripsByStop(String stop_id){
		return null;
	}

	/**
	 * 
	 * @param route_id: String, attributes
	 */
	public void updateRoute(String route_id){

	}

	/**
	 * 
	 * @param stop_id: String, attributes
	 */
	public void updateStop(String stop_id){

	}

	/**
	 * 
	 * @param stop_ids: List<Strings>, attributes
	 */
	public void updateStops(Collection<String> stop_ids){

	}

	/**
	 * 
	 * @param stop_id: String, trip_id: String, time
	 */
	public void updateStopTime(String stop_id, String trip_id, Time time){

	}

	/**
	 * 
	 * @param trip_id: String, attributes
	 */
	public void updateTrip(String trip_id){

	}
}
