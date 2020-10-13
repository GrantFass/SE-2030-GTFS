package SE2030TransitProject;


import javafx.scene.control.TextArea;
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

	/**
	 * Routes constructor initialized with empty hash map
	 * @author Ryan Becker
	 */
	public Routes(){
		routes = new HashMap<>();
	}

	/**
	 * adds route parameter to routes hash map with the route_id of route as the key, and route as the value.
	 * @author Ryan Becker
	 * @param route Route object to be added to routes
     * @return true if new route was added, false otherwise
	 */
	public boolean addRoute(Route route){
		Route routeAdded = routes.put(route.getRouteID(), route);
		boolean added = false;
		if(routeAdded == null){
			added = true;
		}
		return added;
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
     * @return true if deleted, false otherwise
	 */
	public boolean removeRoute(Route route){
		Route routeRemoved = routes.remove(route.getRouteID());
		boolean deleted = false;
		if(routeRemoved != null){
			deleted = true;
		}
		return deleted;
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
	public boolean loadRoutes(File file) throws FileNotFoundException, IOException,
			InputMismatchException, DataFormatException {
		try {

			Scanner read_header = new Scanner(file);
			String full_header = read_header.nextLine();

			if(!validHeader(full_header)){
				throw new InputMismatchException("Invalid Header:\nFile was not loaded");
			}

			boolean emptyPrior = routes.isEmpty();
			routes.clear();


			String[] split_header = full_header.split(",");

			Scanner read_data = new Scanner(file);
			read_data.useDelimiter(",");
			read_data.nextLine(); //consumes header line to start at data to be parsed

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

			read_header.close();
			read_data.close();

            if(!emptyPrior){
                throw new DataFormatException(file.getName());
            }
		} catch (DataFormatException dfe){
			throw new DataFormatException(dfe.getMessage());
		}
		return true;
	}

	/**
	 * Method to output data as a single concatenated string
	 * @author GrantFass, Ryan Becker
	 * @return string of data
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(String key_ID : routes.keySet()){
			sb.append(routes.get(key_ID).toString() + "\n");
		}
		return sb.toString();
	}

	/**
	 * Method to print all of the individual route objects to the textArea
	 * @param textArea the textArea to print the route objects to
	 * @author Grant Fass
	 */
	public void printDataToTextArea(TextArea textArea) {
		textArea.clear();
		textArea.setText(toString());
        /*
		for (String key : routes.keySet()) {
			textArea.appendText(routes.get(key).toString() + "\n");
		}

         */
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

		final String DEFAULT_URL = "http://NULL_URL"; //Error is thrown otherwise

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

	/**
	 * Checks if a header of a routes.txt file is valid
	 * @author Ryan Becker
	 * @param full_header String of entire line containing header
	 * @return True if valid, false otherwise
	 */
	public boolean validHeader(String full_header){
		boolean valid = false;
		final String TARGET_HEADER = "route_id,agency_id,route_short_name,route_long_name," +
				"route_desc,route_type,route_url,route_color,route_text_color";

		if(full_header.equals(TARGET_HEADER)){
			valid = true;
		}
		return valid;
	}

	public boolean validData(){
		return false;
	}



}//end Routes
