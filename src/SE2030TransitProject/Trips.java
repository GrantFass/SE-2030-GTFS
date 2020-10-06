package SE2030TransitProject;


import java.util.HashMap;

/**
 * @author ericksons
 * @version 1.0
 * @created 06-Oct-2020 10:28:46 AM
 */
public class Trips {

	private HashMap<String, Trips> trips;
	public Trip m_Trip;

	public Trips(){

	}

	public void finalize() throws Throwable {

	}
	/**
	 * 
	 * @param trip
	 */
	public boolean addTrip(Trip trip){
		return false;
	}

	public Trip getTrip(){
		return null;
	}

	/**
	 * 
	 * @param trip
	 */
	public boolean removeTrip(Trip trip){
		return false;
	}
}//end Trips