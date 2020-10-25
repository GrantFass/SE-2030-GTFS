package SE2030TransitProject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Simon Erickson, Grant Fass,
 * @version 1.0
 * @created 06-Oct-2020 10:28:30 AM
 */
public class Data extends Observable {

	//Radius of the earth in miles
	private final static double EARTH_RADIUS = 3959;
	private Collection<Observer> observers;
	private Routes routes;
	private StopTimes stop_times;
	private Stops stops;
	private Trips trips;

	public Data() {
		stops = new Stops();
		stop_times = new StopTimes();
		routes = new Routes();
		trips = new Trips();
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
	 * Method that creates a HashMap with all trips with there lengths.
	 * @author Simon Erickson
	 * @return tripAndDistance The HashMap with all trips with there lengths.
	 */
	public HashMap<String, Integer> tripDistances(){

		//HashMap<trip_id, first_time--first_stop_id--last_time--last_stop_id>
		HashMap<String, String> tripStartAndEnd = stop_times.getTripStartAndEnd();

		//HashMap<trip_id, distance in miles>
		HashMap<String, Integer> tripAndDistance = new HashMap<>();

		tripStartAndEnd.forEach((k,v)->{
			String[] value = v.split("--");
			tripAndDistance.put(k, tripDistance(stops.getStop(value[1]), stops.getStop(value[3])));
		});

		return tripAndDistance;
	}

	/**
	 * Method to find the distance between two stops
	 * @author Simon Erickson
	 * @param  fromThisStop the first stop
	 * @param  toThisStop the second stop
	 * @return The distance between two stops in miles
	 */
	private int tripDistance(Stop fromThisStop, Stop toThisStop) {
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
	 * Method to output data as a single concatenated string
	 * @author GrantFass,
	 * @return string of data
	 */
	@Override
	public String toString() {
		return getStopTimes().toString() + getStops().toString()
				+ getTrips().toString() + getRoutes().toString();
	}
}//end Data