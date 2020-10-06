package SE2030TransitProject;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;

/**
 * @author ericksons
 * @version 1.0
 * @created 06-Oct-2020 10:28:39 AM
 */
public class Routes {

	private HashMap<String, Routes> routes;

	public Routes(){

	}

	public void finalize() throws Throwable {

	}
	/**
	 * 
	 * @param route
	 */
	public boolean addRoute(Route route){
		return false;
	}

	public Route getRoute(){
		return null;
	}

	/**
	 * 
	 * @param route
	 */
	public boolean removeRoute(Route route){
		return false;
	}

	/**
	 * Method to parse Route data from a routes.txt file
	 * @author Grant Fass,
	 * @param file the routes.txt file to be parsed
	 * @return true if file was loaded, false otherwise
	 * @throws FileNotFoundException if the file was not found
	 * @throws IOException for general File IO errors.
	 * @throws InputMismatchException if there is an issue parsing the file
	 */
	public boolean loadRoutes(File file) throws FileNotFoundException, IOException, InputMismatchException {
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
}//end Routes