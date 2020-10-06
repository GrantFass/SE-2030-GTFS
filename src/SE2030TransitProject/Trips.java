package SE2030TransitProject;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;

/**
 * @author ericksons
 * @version 1.0
 * @created 06-Oct-2020 10:28:46 AM
 */
public class Trips {

	private HashMap<String, Trips> trips;

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

	/**
	 * Method to parse Trip data from a trips.txt file
	 * @author Grant Fass,
	 * @param file the trips.txt file to be parsed
	 * @return true if file was loaded, false otherwise
	 * @throws FileNotFoundException if the file was not found
	 * @throws IOException for general File IO errors.
	 * @throws InputMismatchException if there is an issue parsing the file
	 */
	public boolean loadTrips(File file) throws FileNotFoundException, IOException,
			InputMismatchException {
		return false;
	}
}//end Trips