package data;

import interfaces.Subject;
import interfaces.Observer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.concurrent.Task;
import javafx.scene.control.ListView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

/**
 * @author Simon Erickson, Grant Fass,
 * @version 1.0
 * @created 06-Oct-2020 10:28:30 AM
 */
public class Data implements Subject {
	//Radius of the earth in miles
	private final static double EARTH_RADIUS = 3959;
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
	 * outputs all of the data for a class to the specified listView
	 * @param dataType selects which data to output.
	 *             0 = Routes
	 *             1 = Stops
	 *             2 = StopTimes
	 *             3 = Trips
	 * @param stringType selects which string format to output in.
	 *                   0 = One Line Simple String
	 *                   1 = Full Expanded Data
	 * @param listView the list view to add the entries to
	 * @author Grant Fass
	 */
	public void displayData(int dataType, int stringType, ListView listView) {
		int count = 0;
		Iterator mapIterator;
		ObservableList<String> items = FXCollections.observableArrayList();
		switch (dataType) {
			case 0:
				mapIterator = routes.getRoutes().entrySet().iterator();
				if (stringType == 1) {
					while (mapIterator.hasNext()) {
						Map.Entry mapElement = (Map.Entry) mapIterator.next();
						items.add(routes.getRoute(mapElement.getKey().toString()).toString() + "\n");
						count++;
					}
				} else {
					while (mapIterator.hasNext()) {
						Map.Entry mapElement = (Map.Entry) mapIterator.next();
						items.add(routes.getRoute(mapElement.getKey().toString()).toSimpleString() + "\n");
						count++;
					}
				}
				break;
			case 1:
				mapIterator = stops.getStops().entrySet().iterator();
				if (stringType == 1) {
					while (mapIterator.hasNext()) {
						Map.Entry mapElement = (Map.Entry) mapIterator.next();
						items.add(stops.getStop(mapElement.getKey().toString()).toString() + "\n");
						count++;
					}
				} else {
					while (mapIterator.hasNext()) {
						Map.Entry mapElement = (Map.Entry) mapIterator.next();
						items.add(stops.getStop(mapElement.getKey().toString()).toSimpleString() + "\n");
						count++;
					}
				}
				break;
			case 2:
				mapIterator = stop_times.getStop_times().entrySet().iterator();
				if (stringType == 1) {
					while (mapIterator.hasNext()) {
						Map.Entry mapElement = (Map.Entry) mapIterator.next();
						items.add(stop_times.getStop_times().get(mapElement.getKey()).toString() + "\n");
						count++;
					}
				} else {
					while (mapIterator.hasNext()) {
						Map.Entry mapElement = (Map.Entry) mapIterator.next();
						items.add(stop_times.getStop_times().get(mapElement.getKey()).toSimpleString() + "\n");
						count++;
					}
				}
				break;
			case 3:
				mapIterator = trips.getTrips().entrySet().iterator();
				if (stringType == 1) {
					while (mapIterator.hasNext()) {
						Map.Entry mapElement = (Map.Entry) mapIterator.next();
						items.add(trips.getTrip(mapElement.getKey().toString()).toString() + "\n");
						count++;
					}
				} else {
					while (mapIterator.hasNext()) {
						Map.Entry mapElement = (Map.Entry) mapIterator.next();
						items.add(trips.getTrip(mapElement.getKey().toString()).toSimpleString() + "\n");
						count++;
					}
				}
				break;
		}
		if (items.isEmpty()) {
			items.add("No Data Yet");
		}
		if(Platform.isFxApplicationThread()) {
			listView.setItems(items);
		} else {
			Platform.runLater(() -> listView.setItems(items));
		}
	}

	/**
	 * outputs all of the data for a class to the specified listView
	 * @param analysisType the type of data to output
	 *                     0 = Trip Distance
	 *                     1 = Trip Speed
	 *                     2 = Trips Per Stop
	 * @param listView the listview to output the data to
	 * @author Grant Fass
	 */
	public void displayAnalysis(int analysisType, ListView listView) {
		int count = 0;
		Iterator mapIterator;
		ObservableList<String> items = FXCollections.observableArrayList();
		switch (analysisType) {
			case 0:
				mapIterator = tripDistances().entrySet().iterator();
				while (mapIterator.hasNext()) {
					Map.Entry mapElement = (Map.Entry) mapIterator.next();
					items.add(String.format("Trip ID: %s | %s miles\n", mapElement.getKey().toString(), mapElement.getValue().toString()));
					count++;
				}
				break;
			case 1:
				mapIterator = tripSpeeds().entrySet().iterator();
				while (mapIterator.hasNext()) {
					Map.Entry mapElement = (Map.Entry) mapIterator.next();
					items.add(String.format("%s | %s\n", mapElement.getKey().toString(), mapElement.getValue().toString()));
					count++;
				}
				break;
			case 2:
				Object[] keys = stop_times.getStop_times().keySet().toArray();

				// separate stop_id from trip_id
				for(int i = 0; i < keys.length; i++){
					String value = keys[i].toString();
					keys[i] = value.substring(0, value.indexOf(';'));
				}

				// count distinct stops which returns number of how much a stop is used by trips
				int[] i = {0};
				Arrays.stream(keys).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
						.entrySet()
						.forEach(object -> {
							String stop_id = object.toString().substring(0, object.toString().indexOf('='));
							int tripCount = Integer.parseInt(object.toString().substring(object.toString().indexOf('=') + 1));
								items.add(String.format("%s Trips contain Stop ID: %s\n", tripCount, stop_id));
							i[0] = i[0] + 1;
							if (stops.getStop(stop_id) != null) {
								stops.getStop(stop_id).setTripsPerStop(tripCount);
							}
						});
				break;
		}
		if (items.isEmpty()) {
			items.add("No Data Yet");
		}
		if (Platform.isFxApplicationThread()) {
			listView.setItems(items);
		} else {
			Platform.runLater(() -> listView.setItems(items));
		}
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
		Platform.runLater(this::notifyObservers);
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
		Platform.runLater(this::notifyObservers);
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
		Platform.runLater(this::notifyObservers);
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
		Platform.runLater(this::notifyObservers);
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

	/**
	 * Searches for every route_id associated with a Stop given stop_id
	 * @param stop_id of Stop being searched
	 * @return String of formatted route_ids associated with stop_id
	 */
	public String getRouteIDs_fromStopID(String stop_id){
	    ArrayList<String> route_ids = searchStopForRoute_IDs(stop_id);
	    return formatRoute_IDs(route_ids);
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
	 * @param route_ids ArrayList of every route_id associated with stop_id
	 * @return String formatting every route_id associated with stop_id on new lines
	 */
	private String formatRoute_IDs(ArrayList<String> route_ids){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < route_ids.size(); ++i){
		    sb.append((i+1) + ": " + route_ids.get(i) + "\n");
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

	/**
	 * Method that creates a HashMap with all trips with there lengths.
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
	 * Method that calculates the average speed based on the start and end times of a trip
	 *
	 * @author Simon Erickson, Grant Fass
	 * @return tripAndSpeed The HashMap with all trips with there average speeds.
	 */
	private HashMap<String, String> tripSpeeds(){
		HashMap<String, String> returnHashMap = new HashMap();
		if(!(stop_times == null | stops == null)){
			//HashMap<trip_id, first_time--first_stop_id--last_time--last_stop_id>
			HashMap<String, String> tripStartAndEnd = stop_times.getTripStartAndEnd();

			//HashMap<trip_id, speed in miles per hour>
			HashMap<String, String> tripAndSpeed = new HashMap<>();

			tripStartAndEnd.forEach((k,v)->{
				String[] value = v.split("--");
				long time = Long.parseLong(value[2]) - Long.parseLong(value[1]);

				int miles = tripDistance(stops.getStop(value[1]), stops.getStop(value[3]));

				final double timeConstant = 3600000.0;
				double hours = time/timeConstant;
				tripAndSpeed.put(k, String.format("%.2f mph", miles/hours));

			});
			returnHashMap = tripAndSpeed;
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