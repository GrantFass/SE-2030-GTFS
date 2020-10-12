package SE2030TransitProject;


import com.sun.javafx.image.IntPixelGetter;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.zip.DataFormatException;

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
	 * @throws DataFormatException if data will be overwritten
	 */
	//TODO incorporate try and catches
	public boolean loadRoutes(File file) throws FileNotFoundException, IOException,
			InputMismatchException, DataFormatException {
		try {

			if (!routes.isEmpty()) {
				routes.clear();
				throw new DataFormatException("Routes.txt");
			}

		} finally {
			Scanner read_data = new Scanner(file);
			read_data.useDelimiter(",");
			read_data.nextLine(); //consumes header line to start at data to be parsed

			Scanner read_header = new Scanner(file);
			String full_header = read_header.nextLine();
			String[] split_header = full_header.split(",");

			while (read_data.hasNextLine()) {
				HashMap<String, String> fields = new HashMap<>();
				initializeKeys(fields);

				String full_data = read_data.nextLine();
				//splits while ignoring commas within description
				String[] split_data = full_data.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

				for (int i = 0; i < split_header.length; ++i) {
					if (!split_data[i].equals("")) {
						fields.put(split_header[i], split_data[i]);
					}
				}

				Route new_route = new Route(fields.get("route_id"), fields.get("agency_id"),
						fields.get("route_short_name"), fields.get("route_long_name"), fields.get("route_desc"),
						RouteTypeEnum.values()[Integer.parseInt(fields.get("route_type"))],
						new URL(fields.get("route_url")), Color.valueOf(fields.get("route_color")),
						Color.valueOf(fields.get("route_text_color")), Integer.parseInt(fields.get("route_sort_order")),
						ContinuousPickupEnum.values()[Integer.parseInt(fields.get("continuous_pickup"))],
						ContinuousDropOffEnum.values()[Integer.parseInt(fields.get("continuous_drop_off"))]);

				addRoute(new_route);
			}
			return true;
		}
		//return false;

		/*} finally {
			throw new DataFormatException("Routes.txt");
		}*/



        /*
        TODO: DataFormatException should be thrown after everything else is done and let the user know that previous data was overwritten
        Note: For the exception text put in the name of the text file ie: stops.txt I will do the rest in the controller
        - Grant
         */
	}

	/**
	 * Method to output data as a single concatenated string
	 * @author GrantFass,
	 * @return string of data
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(String key_ID : routes.keySet()){
			sb.append(routes.get(key_ID).toString()).append("\n");
		}
		return sb.toString();
	}

	/**
	 * Helper method for loadRoutes(). Refreshes hashmap to default parameters for use in a new line of data
	 * @author Ryan Becker
	 * @param fields hash-map where the key value is stored as a String of a header element,
	 *               and the value is a String of the data element associated with the key
	 */
	private void initializeKeys(HashMap<String, String> fields){
		final String DEFAULT_TYPE = "3"; //bus routes
		final String DEFAULT_COLOR = "FFFFFF";
		final String DEFAULT_TEXT_COLOR = "000000";
		final String DEFAULT_SORT_ORDER = "0";
		final String DEFAULT_CONTINUOUS = "0"; //continuous stopping pickup or drop-off

		final String DEFAULT_URL = "http://"; //Error is thrown otherwise

		fields.put("route_id", null);
		fields.put("agency_id", null);
		fields.put("route_short_name", null);
		fields.put("route_long_name", null);
		fields.put("route_desc", null);
		fields.put("route_type", DEFAULT_TYPE);
		fields.put("route_url", DEFAULT_URL);
		fields.put("route_color", DEFAULT_COLOR);
		fields.put("route_text_color", DEFAULT_TEXT_COLOR);
		fields.put("route_sort_order", DEFAULT_SORT_ORDER);
		fields.put("continuous_pickup", DEFAULT_CONTINUOUS);
		fields.put("continuous_drop_off", DEFAULT_CONTINUOUS);
	}

}//end Routes
