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
 * @created 06-Oct-2020 10:28:43 AM
 */
public class StopTimes {

	private HashMap<String, StopTime> stop_times;

	public StopTimes(){

	}

	public void finalize() throws Throwable {

	}
	/**
	 * 
	 * @param stopTime
	 */
	public boolean addStopTime(StopTime stopTime){
		return false;
	}

	public StopTime getStopTime(){
		return null;
	}

	/**
	 * 
	 * @param stopTime
	 */
	public boolean removeStopTIme(StopTime stopTime){
		return false;
	}

	/**
	 * Method to parse StopTime data from a stop_times.txt file
	 * @author Grant Fass,
	 * @param file the stop_times.txt file to be parsed
	 * @return true if file was loaded, false otherwise
	 * @throws FileNotFoundException if the file was not found
	 * @throws IOException for general File IO errors.
	 * @throws InputMismatchException if there is an issue parsing the file
	 */
	public boolean loadStopTimes(File file) throws FileNotFoundException, IOException,
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
}//end StopTimes