package data;

import interfaces.Subject;
import interfaces.Observer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.zip.DataFormatException;

/**
 * @author Simon Erickson, Grant Fass,
 * @version 1.0
 * @created 06-Oct-2020 10:28:30 AM
 */
public class Data implements Subject {

	//Radius of the earth in miles
	private final static double EARTH_RADIUS = 3959;
	private Collection<Observer> observers;
	private Routes routes;
	private StopTimes stop_times;
	private Stops stops;
	private Trips trips;
	private ArrayList<Observer> observerList;

	public Data() {
		stops = new Stops();
		stop_times = new StopTimes();
		routes = new Routes();
		trips = new Trips();
		observerList = new ArrayList<>();
	}

	public Routes getRoutes() {
		return routes;
	}

	public Stops getStops() {
		return stops;
	}

	public StopTimes getStopTimes() {
		return stop_times;
	}

	public Trips getTrips() {
		return trips;
	}

	/**
	 * compile all of the trip distances into a single string
	 * @return the trip distances as a single string
	 * @author Grant Fass
	 */
	public String getTripDistances() {
		StringBuilder stringBuilder = new StringBuilder();
		Iterator mapIterator = tripDistances().entrySet().iterator();
		while (mapIterator.hasNext()) {
			Map.Entry mapElement = (Map.Entry) mapIterator.next();
			stringBuilder.append("Trip id: ").append(mapElement.getKey()).append(" | ")
					.append(mapElement.getValue()).append(" miles\n");
		}
		return stringBuilder.toString();
	}

	/**
	 * Method that creates a HashMap with all trips with there lengths.
	 * TODO: @FassG Use this method for GUI
	 * @author Simon Erickson
	 * @return tripAndDistance The HashMap with all trips with there lengths.
	 */
	private HashMap<String, Integer> tripDistances(){
		HashMap<String, Integer> returnHashMap = new HashMap();
		if(!(stop_times == null | stops == null)){
			//HashMap<trip_id, first_time--first_stop_id--last_time--last_stop_id>
			HashMap<String, String> tripStartAndEnd = stop_times.getTripStartAndEnd();

			//HashMap<trip_id, distance in miles>
			HashMap<String, Integer> tripAndDistance = new HashMap<>();

			tripStartAndEnd.forEach((k,v)->{
				String[] value = v.split("--");
				tripAndDistance.put(k, tripDistance(stops.getStop(value[1]), stops.getStop(value[3])));
			});
			returnHashMap = tripAndDistance;
		}

		return returnHashMap;
	}

	/**
	 * Method to find the distance between two stops
	 * @author Simon Erickson
	 * @param  fromThisStop the first stop
	 * @param  toThisStop the second stop
	 * @return The distance between two stops in miles
	 */
	private int tripDistance(Stop fromThisStop, Stop toThisStop) {
		if(fromThisStop == null || toThisStop == null) {
			return -1;
		}
		double fromThisLat = fromThisStop.getStopLatitude();
		double toThisLat = toThisStop.getStopLatitude();
		double fromThisLng = fromThisStop.getStopLongitude();
		double toThisLng = toThisStop.getStopLongitude();

		double distanceLat = Math.toRadians(fromThisLat - toThisLat);
		double distanceLng = Math.toRadians(fromThisLng - toThisLng);

		//Haversine formula
		double a = Math.pow(Math.sin(distanceLat / 2),2);
		double b = Math.pow(Math.sin(distanceLng / 2),2);
		double c = Math.cos(Math.toRadians(fromThisLat));
		double d = Math.cos(Math.toRadians(toThisLat));
		double e = (a + c * d * b);
		double f = 2 * Math.atan2(Math.sqrt(e), Math.sqrt(1 - e));
		double miles = (Math.round(EARTH_RADIUS * f));

		return (int) miles;
	}

	/**
	 * Method to parse Route data from a routes.txt file
	 * @param file the routes.txt file to be parsed
	 * @return true if a line was skipped while loading, false otherwise
	 * @throws FileNotFoundException if the file was not found
	 * @throws IOException for general File IO errors.
	 * @throws InputMismatchException if there is an issue parsing the file
	 * @throws DataFormatException if data will be overwritten
	 * @author Grant Fass
	 */
	public boolean loadRoutes(File file) throws FileNotFoundException, IOException,
			InputMismatchException, DataFormatException {
		boolean wasLineSkipped = routes.loadRoutes(file);
		notifyObservers();
		return wasLineSkipped;
	}
	/**
	 * Method to parse Stop data from a stops.txt file
	 * @param file the routes.txt file to be parsed
	 * @return true if a line was skipped while loading, false otherwise
	 * @throws FileNotFoundException if the file was not found
	 * @throws IOException for general File IO errors.
	 * @throws InputMismatchException if there is an issue parsing the file
	 * @throws DataFormatException if data will be overwritten
	 * @author Grant Fass
	 */
	public boolean loadStops(File file) throws FileNotFoundException, IOException,
			InputMismatchException, DataFormatException {
		boolean wasLineSkipped = stops.loadStops(file);
		notifyObservers();
		return wasLineSkipped;
	}
	/**
	 * Method to parse StopTimes data from a stop_times.txt file
	 * @param file the routes.txt file to be parsed
	 * @return true if a line was skipped while loading, false otherwise
	 * @throws FileNotFoundException if the file was not found
	 * @throws IOException for general File IO errors.
	 * @throws InputMismatchException if there is an issue parsing the file
	 * @throws DataFormatException if data will be overwritten
	 * @author Grant Fass
	 */
	public boolean loadStopTimes(File file) throws FileNotFoundException, IOException,
			InputMismatchException, DataFormatException {
		boolean wasLineSkipped = stop_times.loadStopTimes(file);
		notifyObservers();
		return wasLineSkipped;
	}
	/**
	 * Method to parse Trip data from a trips.txt file
	 * @param file the routes.txt file to be parsed
	 * @return true if a line was skipped while loading, false otherwise
	 * @throws FileNotFoundException if the file was not found
	 * @throws IOException for general File IO errors.
	 * @throws InputMismatchException if there is an issue parsing the file
	 * @throws DataFormatException if data will be overwritten
	 * @author Grant Fass
	 */
	public boolean loadTrips(File file) throws FileNotFoundException, IOException,
			InputMismatchException, DataFormatException {
		boolean wasLineSkipped = trips.loadTrips(file);
		notifyObservers();
		return wasLineSkipped;
	}

	/**
	 * Method to output data as a single concatenated string
	 * @author GrantFass,
	 * @return string of data
	 */
	@Override
	public String toString() {
		return getStopTimes().toString() + getStops().toString()
				+ getTrips().toString() + getRoutes().toString();
	}


	//start feature 5

	/**
	 * Searches for every route_id associated with a Stop given stop_id
	 * @param stop_id of Stop being searched
	 * @return String of formatted route_ids associated with stop_id
	 */
	public String getRouteIDs_fromStopID(String stop_id){
	    ArrayList<String> route_ids = searchStopForRoute_IDs(stop_id);
	    return formatRoute_IDs(stop_id, route_ids);
	}

	/**
	 * Helper method for getRouteIDs_fromStopID() that gets all route_ids associated with a given stop_id
	 * @author Ryan Becker
	 * @param stop_id for a Stop used in searching for all route_ids that are paired with the given stop_id
	 * @return ArrayList of every route_id that is associated with stop_id
	 */
	private ArrayList<String> searchStopForRoute_IDs(String stop_id){
		ArrayList<String> trip_ids = stop_times.getTripIDs_fromStop_ID(stop_id);

		ArrayList<String> all_route_ids = new ArrayList<>();

		for(String trip_id : trip_ids){
			ArrayList<String> route_ids = trips.getRouteIDs_fromTripIDs(trip_id);
			all_route_ids.addAll(onlyAddNew(all_route_ids, route_ids));
		}

		return all_route_ids;
	}

	/**
	 * Helper method for getRouteIDs_fromStopID() that formats a string similarly to a toString() method
	 * to display every route_id associated with a stop_id
	 * @param stop_id of a Stop being searched
	 * @param route_ids ArrayList of every route_id associated with stop_id
	 * @return String formatting every route_id associated with stop_id on new lines
	 */
	private String formatRoute_IDs(String stop_id, ArrayList<String> route_ids){
		StringBuilder sb = new StringBuilder();
		sb.append("route_id(s) that contain the stop_id: " + stop_id);
		for(String route_id : route_ids){
			sb.append("\n- " + route_id);
		}
		return sb.toString();
	}

	/**
	 * Helper method for searchStopForRoute_IDs() that will help remove duplicate occurences of a route_id
	 * @author Ryan Becker
	 * @param allRouteIDs ArrayList of all current unique route_ids associated with stop_id
	 * @param route_ids all newly read route_ids, which are only added to returned list if they do not
	 *                  already occur within allRouteIDs
	 * @return ArrayList of all route_ids currently not found in allRouteIDs
	 */
	private ArrayList<String> onlyAddNew(ArrayList<String> allRouteIDs, ArrayList<String> route_ids){
		ArrayList<String> uniqueIDs = new ArrayList<>();
		for(String route_id : route_ids){
			if(!allRouteIDs.contains(route_id)){
				uniqueIDs.add(route_id);
			}
		}
		return uniqueIDs;
	}

	//end feature 5

	/**
	 * add an observer to the list of observers to update
	 * Based on a guide from GeeksForGeeks
	 * found here: https://www.geeksforgeeks.org/observer-pattern-set-2-implementation/
	 * @param o the observer to add
	 * @author Grant Fass
	 */
	@Override
	public void attach(Observer o) {
		observerList.add(o);
	}

	/**
	 * remove an observer from the list of observers to update
	 * Based on a guide from GeeksForGeeks
	 * found here: https://www.geeksforgeeks.org/observer-pattern-set-2-implementation/
	 * @param o the observer to remove
	 * @author Grant Fass
	 */
	@Override
	public void detach(Observer o) {
		observerList.remove(observerList.indexOf(o));
	}

	/**
	 * notify all observers that information was changed
	 * Based on a guide from GeeksForGeeks
	 * found here: https://www.geeksforgeeks.org/observer-pattern-set-2-implementation/
	 * @author Grant Fass
	 */
	@Override
	public void notifyObservers() {
		for (Iterator<Observer> observerIterator = observerList.iterator(); observerIterator.hasNext();) {
			Observer o = observerIterator.next();
			o.update(this);
		}
	}
}//end Data