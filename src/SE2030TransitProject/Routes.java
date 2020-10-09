package SE2030TransitProject;


import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class that holds multiple routes stored within a hash map where a Route's route_id is the key,
 * and the Route object is the value for a specific slot within the hash map
 * @author Ryan Becker
 * @version 1.0
 * @created 06-Oct-2020 10:28:39 AM
 */
public class Routes {

	private HashMap<String, Route> routes;

	public Routes(){
		routes = new HashMap<>();
	}

	public void finalize() throws Throwable {

	}
	/**
	 * adds route parameter to routes hash map
	 * with the route_id of route as the key, and route as the value
	 * @author Ryan Becker
	 * @param route Route object to be added to routes
	 */
	public boolean addRoute(Route route){
		routes.put(route.getRouteID(), route);
		return true;
	}

	/**
	 * @author Ryan Becker
	 * @param route_id string identifying requested route
	 * @return route associated with the specified route_id
	 */
	public Route getRoute(String route_id){
		return routes.get(route_id);
	}

	/**
	 * Removes specified Route object from routes
	 * @author Ryan Becker
	 * @param route Route to be removed
	 */
	public boolean removeRoute(Route route){
		routes.remove(route.getRouteID());
		return true;
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

		//	Alerts user of overwriting files
		Alert overwriteAlert = new Alert(Alert.AlertType.CONFIRMATION);
		overwriteAlert.setTitle("Overwrite Warning");
		overwriteAlert.setHeaderText("You are about to overwrite all existing files");
		overwriteAlert.showAndWait();

		//creates new routes
		routes = new HashMap<>();

//		try(Scanner in = new Scanner(new File(String.valueOf(file)))){
//			while(in.hasNextLine()){
//				String line = in.nextLine();
//				line.split("\\D*");
//
//			}
//			in.nextLine();
//		}
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