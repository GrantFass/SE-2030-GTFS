package SE2030TransitProject;


import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author ericksons
 * @version 1.0
 * @created 06-Oct-2020 10:28:46 AM
 */
public class Trips {

	private HashMap<String, Trip> trips;

	public Trips(){
		trips = new HashMap<String, Trip>();
	}

	public void finalize() throws Throwable {

	}
	/**
	 * 
	 * @param trip
	 */
	public boolean addTrip(Trip trip){
		trips.put(trip.getRouteID(), trip);
		return true;
	}

	public Trip getTrip(String trip_id){
		return trips.get(trip_id);
	}

	/**
	 * 
	 * @param trip
	 */
	public boolean removeTrip(Trip trip){
		trips.remove(trip.getTripID());
		return true;
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
		try(Scanner in = new Scanner(new File(String.valueOf(file)))){
			while(in.hasNextLine()){
				String line = in.nextLine();
				line.split("\\D*");

			}
			in.nextLine();
		}
		return false;
	}

	/**
	 * Method to output data as a single concatenated string
	 * @author GrantFass,
	 * @return string of data
	 */
	@Override
	public String toString() {
		return "Method Not Implemented Yet";
	}
}//end Trips