package SE2030TransitProject;


/**
 * @author ericksons
 * @version 1.0
 * @created 06-Oct-2020 10:28:30 AM
 */
public class Data {

	private List<Observer> observers;
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
	 * @param obs: Observer
	 */
	public void attach(obs: Observer obs: Observer){

	}

	/**
	 * 
	 * @param obs: Observer
	 */
	public void detach(obs: Observer obs: Observer){

	}

	public Routes getRoutes(){
		return null;
	}

	public Stops getStops(){
		return null;
	}

	public StopsTimes getStopTimes(){
		return null;
	}

	public Trips getTrips(){
		return null;
	}

	public void notifyObservers(){

	}
}//end Data