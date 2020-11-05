package data;

import interfaces.Subject;
import interfaces.Observer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.concurrent.Task;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

/**
 * @author Simon Erickson, Grant Fass,
 * @version 1.0
 * @created 06-Oct-2020 10:28:30 AM
 */
public class Data implements Subject {
	//region properties
	private final static double EARTH_RADIUS_MILES = 3959;
	private Routes routes;
	private StopTimes stop_times;
	private Stops stops;
	private Trips trips;
	private ArrayList<Observer> observerList;
	//endregion

	//region constructors
	public Data() {
		stops = new Stops();
		stop_times = new StopTimes();
		routes = new Routes();
		trips = new Trips();
		observerList = new ArrayList<>();
	}
	//endregion

	//region internal class getters
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
	//endregion

	//region methods for exporting files
	/**
	 * exports all files to the specified directory
	 * @param directory the directory to export the files to
	 * @param routesCheck represents if routes will be exported
	 * @param stopsCheck represents if stops will be exported
	 * @param stopTimesCheck represents if stopTimes will be exported
	 * @param tripsCheck represents if trips will be exported
	 * @return a message containing the status of the File export
	 * @author Grant Fass
	 */
	public String exportFiles(String directory, boolean routesCheck, boolean stopsCheck, boolean stopTimesCheck, boolean tripsCheck) {
		Callable<String> exportRoutes = () -> {
			String message;
			if (!directory.isEmpty() && routesCheck) {
				message = routes.exportRoutes(new File(directory)) ?
						String.format("✓: Routes Exported Successfully\n  Time: %02d:%02d:%02d\n",
								LocalDateTime.now().getHour(), LocalDateTime.now().getMinute(),
								LocalDateTime.now().getSecond()) : "Routes Export Failed\n";
			} else if (directory.isEmpty()) {
				message = "Routes Export Failed\n  Empty Directory\n";
			} else {
				message = "Routes Export Skipped\n  Routes Not Selected\n";
			}
			return "EXPORT ROUTES:\n  " + message;
		};
		Callable<String> exportStops = () -> {
			String message;
			if (!directory.isEmpty() && stopsCheck) {
				message = routes.exportRoutes(new File(directory)) ?
						String.format("✓: Stops Exported Successfully\n  Time: %02d:%02d:%02d\n",
								LocalDateTime.now().getHour(), LocalDateTime.now().getMinute(),
								LocalDateTime.now().getSecond()) : "Stops Export Failed\n";
			} else if (directory.isEmpty()) {
				message = "Stops Export Failed\n  Empty Directory\n";
			} else {
				message = "Stops Export Skipped\n  Stops Not Selected\n";
			}
			return "EXPORT STOPS:\n  " + message;
		};
		Callable<String> exportStopTimes = () -> {
			String message;
			if (!directory.isEmpty() && stopTimesCheck) {
				message = routes.exportRoutes(new File(directory)) ?
						String.format("✓: StopTimes Exported Successfully\n  Time: %02d:%02d:%02d\n",
								LocalDateTime.now().getHour(), LocalDateTime.now().getMinute(),
								LocalDateTime.now().getSecond()) : "StopTimes Export Failed\n";
			} else if (directory.isEmpty()) {
				message = "StopTimes Export Failed\n  Empty Directory\n";
			} else {
				message = "StopTimes Export Skipped\n  StopTimes Not Selected\n";
			}
			return "EXPORT STOP_TIMES:\n  " + message;
		};
		Callable<String> exportTrips = () -> {
			String message;
			if (!directory.isEmpty() && tripsCheck) {
				message = routes.exportRoutes(new File(directory)) ?
						String.format("✓: Trips Exported Successfully\n  Time: %02d:%02d:%02d\n",
								LocalDateTime.now().getHour(), LocalDateTime.now().getMinute(),
								LocalDateTime.now().getSecond()) : "Trips Export Failed\n";
			} else if (directory.isEmpty()) {
				message = "Trips Export Failed\n  Empty Directory\n";
			} else {
				message = "Trips Export Skipped\n  Trips Not Selected\n";
			}
			return "EXPORT TRIPS:\n  " + message;
		};
		ExecutorService executorService = Executors.newFixedThreadPool(4);
		Future<String> routesExportMessage = executorService.submit(exportRoutes);
		Future<String> stopsExportMessage = executorService.submit(exportStops);
		Future<String> stopTimesExportMessage = executorService.submit(exportStopTimes);
		Future<String> tripsExportMessage = executorService.submit(exportTrips);
		String completionTime = String.format("COMPLETE: %02d:%02d:%02d\n", LocalDateTime.now().getHour(), LocalDateTime.now().getMinute(), LocalDateTime.now().getSecond());
		String message = "Something Went Wrong During Export";
		try {
			message = routesExportMessage.get() + stopsExportMessage.get() + stopTimesExportMessage.get() + tripsExportMessage.get() + completionTime;
		} catch (InterruptedException | ExecutionException e) {
			message += "\n" + e.getMessage();
		}
		return message;
	}
	//endregion

	//region methods for loading files
	/**
	 * loads all of the files from the specified file locations.
	 * only loads the file if the string location is not empty
	 * @param routeFileLocation the file location in string format of routes.txt
	 * @param stopFileLocation the file location in string format of stops.txt
	 * @param stopTimeFileLocation the file location in string format of stop_times.txt
	 * @param tripFileLocation the file location in string format of trips.txt
	 * @return a message containing the import status of all files.
	 * @throws IOException for general File IO errors.
	 * @author Grant Fass
	 */
	public String loadFiles(String routeFileLocation, String stopFileLocation, String stopTimeFileLocation, String tripFileLocation) throws IOException{
		Callable<String> importRoutes = () -> {
			String routesImportMessage = "SKIP ROUTES:\n  File Location Empty\n";
			if (routeFileLocation != null && !routeFileLocation.isEmpty()) {
				routesImportMessage = routes.loadRoutes(new File(routeFileLocation));
			}
			return routesImportMessage;
		};
		Callable<String> importStops = () -> {
			String stopsImportMessage = "SKIP STOPS:\n  File Location Empty\n";
			if (stopFileLocation != null && !stopFileLocation.isEmpty()) {
				stopsImportMessage  = stops.loadStops(new File(stopFileLocation));
			}
			return stopsImportMessage;
		};
		Callable<String> importStopTimes = () -> {
			String stopTimesImportMessage = "SKIP STOP_TIMES:\n  File Location Empty\n";
			if (stopTimeFileLocation != null && !stopTimeFileLocation.isEmpty()) {
				stopTimesImportMessage = stop_times.loadStopTimes(new File(stopTimeFileLocation));
			}
			return stopTimesImportMessage;
		};
		Callable<String> importTrips = () -> {
			String tripsImportMessage = "SKIP TRIPS:\n  File Location Empty\n";
			if (tripFileLocation != null && !tripFileLocation.isEmpty()) {
				tripsImportMessage = trips.loadTrips(new File(tripFileLocation));
			}
			return tripsImportMessage;
		};
		ExecutorService executorService = Executors.newFixedThreadPool(4);
		Future<String> routesImportMessage = executorService.submit(importRoutes);
		Future<String> stopsImportMessage = executorService.submit(importStops);
		Future<String> stopTimesImportMessage = executorService.submit(importStopTimes);
		Future<String> tripsImportMessage = executorService.submit(importTrips);
		//wait to notify observers until all tasks are done
		while (!routesImportMessage.isDone() || !stopsImportMessage.isDone() || !stopTimesImportMessage.isDone() || !tripsImportMessage.isDone()) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//notify observers and return message
		notifyObservers();
		String completionTime = String.format("COMPLETE: %02d:%02d:%02d\n", LocalDateTime.now().getHour(), LocalDateTime.now().getMinute(), LocalDateTime.now().getSecond());
		String message = "Something Went Wrong During Import";
		try {
			message = routesImportMessage.get() + stopsImportMessage.get() + stopTimesImportMessage.get() + tripsImportMessage.get() + completionTime;
		} catch (InterruptedException | ExecutionException e) {
			message += "\n" + e.getMessage();
		}
		return message;
	}
	//endregion

	//region methods for data GUI
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
	//endregion

	//region methods for analysis GUI
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
		double miles = (Math.round(EARTH_RADIUS_MILES * f));

		return (int) miles;
	}
	//endregion

	//region methods for map GUI
	/**
	 * returns all of the coordinates of busses
	 * //TODO: Initialize this
	 * @return an arraylist of bus location coordinate pairs with longitude first then latitude
	 * @author Grant Fass,
	 */
	public ArrayList<double[]> getBusCoordinates() {
		return new ArrayList<>();
	}

	/**
	 * retrieves all of the stops that are associated with all routes
	 * @return a HashMap containing all of the Stops associated with all Routes
	 * @author Grant Fass
	 */
	public HashMap<Route, ArrayList<Stop>> getStopsPerRoute() {
		//Create a list of all of the keys in StopTimes. Key Format = 'stop_id;trip_id'
		String[] stopTimeKeys = stop_times.getStop_times().keySet().toArray(new String[0]);
		//separate stopTimeKeys into stop_id values and trip_id values
		ArrayList<String> stop_ids = new ArrayList<>();
		ArrayList<String> trip_ids = new ArrayList<>();
		for(String s:stopTimeKeys) {
			stop_ids.add(s.substring(0, s.indexOf(';')));
			trip_ids.add(s.substring(s.indexOf(';') + 1));
		}
		//convert stop_ids to Stops
		ArrayList<Stop> allStops = new ArrayList<>();
		for(String stop_id:stop_ids) {
			allStops.add(stops.getStop(stop_id));
		}
		//convert trip_ids to Routes
		ArrayList<Route> allRoutes = new ArrayList<>();
		for(String trip_id:trip_ids) {
			Trip trip = trips.getTrip(trip_id);
			Route route = new Route("-1", "", "-1", "Null Route", "Null Route",
					"", "", Color.web("black").toString(), "",
					"", "", "");
			if (trip != null) {
				route = routes.getRoute(trip.getRouteID());
			}
			allRoutes.add(route);
		}
		//transfer data into HashMap
		HashMap<Route, ArrayList<Stop>> stopsPerRoute = new HashMap<>();
		for (int i = 0; i < allRoutes.size(); i++) {
			//If the Route already exists in the map then add the stop to the list of stops
			//Otherwise create a new list of stops and add the route and stop to the map
			//Do not include the stop if it already exists for a route
			if (stopsPerRoute.containsKey(allRoutes.get(i)) && !stopsPerRoute.get(allRoutes.get(i)).contains(allStops.get(i))) {
				stopsPerRoute.get(allRoutes.get(i)).add(allStops.get(i));
			} else if (allRoutes.get(i) != null) {
				ArrayList<Stop> stopsInRoute = new ArrayList<>();
				stopsInRoute.add(allStops.get(i));
				stopsPerRoute.put(allRoutes.get(i), stopsInRoute);
			}
		}
		//Return Map
		return stopsPerRoute;
	}
	//endregion

	//region search functions
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
	//endregion

	//region Observer Pattern Methods
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
	//endregion

	//region toString
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
	//endregion
}//end Data