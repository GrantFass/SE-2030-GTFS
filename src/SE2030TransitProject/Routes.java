package SE2030TransitProject;


import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
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
	 * @author Grant Fass, Ryan Becker
	 * @param file the routes.txt file to be parsed
	 * @return true if file was loaded, false otherwise
	 * @throws FileNotFoundException if the file was not found
	 * @throws IOException for general File IO errors.
	 * @throws InputMismatchException if there is an issue parsing the file
	 */
	//TODO Still needs tweaking regarding conditional check for new lines for the read_header, and process overall
	public boolean loadRoutes(File file) throws FileNotFoundException, IOException, InputMismatchException {
		Scanner read_data = new Scanner(file);
		read_data.useDelimiter(",");
		read_data.nextLine(); //consumes header line to start at data to be parsed

		HashMap<String, String> fields = new HashMap<>();



		while(read_data.hasNextLine()){
			initializeKeys(fields);

			Scanner read_header = new Scanner(file);
			read_header.useDelimiter(",");


			while (read_header.hasNext()){
				fields.put(read_header.next(), read_data.next());
			}

			Route new_route = new Route(fields.get("route_id"), fields.get("agency_id"), fields.get("route_short_name"),
					fields.get("route_long_name"), fields.get("route_desc"),
					RouteTypeEnum.valueOf(fields.get("route_type")), new URL(fields.get("route_url")),
					Color.valueOf(fields.get("route_color")), Color.valueOf("route_text_color"),
					Integer.parseInt(fields.get("route_sort_order")),
					ContinuousPickupEnum.valueOf(fields.get("continous.pickup")),
					ContinuousDropOffEnum.valueOf(fields.get("continuous_drop_off")));
			addRoute(new_route);


		}


		return false;
	}

	private void initializeKeys(HashMap<String, String> fields){
		fields.put("route_id", null);
		fields.put("agency_id", null);
		fields.put("route_short_name", null);
		fields.put("route_long_name", null);
		fields.put("route_desc", null);
		fields.put("route_type", null);
		fields.put("route_url", null);
		fields.put("route_color", null);
		fields.put("route_text_color", null);
		fields.put("route_sort_order", null);
		fields.put("continuous_pickup", null);
		fields.put("continuous_drop_off", null);


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