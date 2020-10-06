/* Joy Cross */

package SE2030TransitProject;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;

/**
 * @author ericksons
 * @version 1.0
 * @created 06-Oct-2020 10:28:41 AM
 */
public class Stops {

	private HashMap<String, Stop> stops;

	public Stops(){

	}

	public void finalize() throws Throwable {

	}
	/**
	 * 
	 * @param stop
	 */
	public boolean addStop(Stop stop){
		return false;
	}

	public Stop getStop(){
		return null;
	}

	/**
	 * 
	 * @param stop
	 */
	public boolean removeStop(Stop stop){
		return false;
	}

	/**
	 * Method to parse Stop data from a stops.txt file
	 * @author Grant Fass,
	 * @param file the stops.txt file to be parsed
	 * @return true if file was loaded, false otherwise
	 * @throws FileNotFoundException if the file was not found
	 * @throws IOException for general File IO errors.
	 * @throws InputMismatchException if there is an issue parsing the file
	 */
	public boolean loadStops(File file) throws FileNotFoundException, IOException,
			InputMismatchException {
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
}//end Stops