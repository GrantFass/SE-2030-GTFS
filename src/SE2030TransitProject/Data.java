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
	public Routes m_Routes;
	public Stops m_Stops;
	public Trips m_Trips;
	public StopTimes m_StopTimes;

	public Data(){

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
		return null;
	}

	public Stops getStops(){
		return null;
	}

	public StopTimes getStopTimes(){
		return null;
	}

	public Trips getTrips(){
		return null;
	}

	public void notifyObservers(){

	}
}//end Data