package SE2030TransitProject;

import java.util.Collection;
import java.util.Observer;

/**
 * @author ericksons
 * @version 1.0
 * @created 06-Oct-2020 10:28:30 AM
 */
public class Data {

	private Collection<Observer> observers;
	private Routes routes;
	private StopTimes stop_times;
	private Stops stops;
	private Trips trips;

	public Data(){
		stops = new Stops();
		stop_times = new StopTimes();
	}

	public void finalize() throws Throwable {

	}
	/**
	 * 
	 * @param observer
	 */
	public void attach(java.util.Observer observer){

	}

	/**
	 * 
	 * @param observer
	 */
	public void detach(java.util.Observer observer){

	}

	public Routes getRoutes(){
		return routes;
	}

	public Stops getStops(){
		return stops;
	}

	public StopTimes getStopTimes(){
		return stop_times;
	}

	public Trips getTrips(){
		return trips;
	}

	public void setRoutes(Routes routes) {
		this.routes = routes;
	}

	public void setStops(Stops stops){
		this.stops = stops;
	}

	public void setStop_times(StopTimes stop_times) {
		this.stop_times = stop_times;
	}

	public void setTrips(Trips trips) {
		this.trips = trips;
	}

	public void notifyObservers(){

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