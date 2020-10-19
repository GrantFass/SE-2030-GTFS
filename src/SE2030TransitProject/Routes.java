package SE2030TransitProject;


import javafx.scene.control.TextArea;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.HashMap;
import java.util.IllegalFormatException;
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
	 * @return true if a line was skipped while loading, false otherwise
	 * @throws FileNotFoundException if the file was not found
	 * @throws IOException for general File IO errors.
	 * @throws InputMismatchException if there is an issue parsing the file
	 * @throws DataFormatException if data will be overwritten
	 */
	//TODO cleanup, notify if line is skipped
	public boolean loadRoutes(File file) throws FileNotFoundException, IOException,
            InputMismatchException, DataFormatException {
	    boolean lineSkipped;

	    Scanner read_header = new Scanner(file);
	    String full_header = read_header.nextLine();

	    Headers headers;

	    // Throws DataFormatException if header is not valid
        try {
            headers = validateHeader(full_header);
        } catch (DataFormatException invalidHeader){
            throw new IOException(invalidHeader.getMessage());
        }
        lineSkipped = false;

        boolean emptyPrior = routes.isEmpty();
        if (!emptyPrior){
            routes.clear();
        }

        //String[] split_header = full_header.split(",");

        Scanner read_data = new Scanner(file);
        read_data.useDelimiter(",");
        read_data.nextLine(); //consumes header line to start at data to be parsed

        while (read_data.hasNextLine()) {
            //HashMap<String, String> fields = new HashMap<>();
            //initializeKeys(fields);

            String full_data = read_data.nextLine();

            try{
                Route newRoute = validateData(full_data, headers);
                addRoute(newRoute);
            } catch (DataFormatException invalidData){
                lineSkipped = true;
            }
        }

        read_header.close();
        read_data.close();

        if(!emptyPrior){
            throw new DataFormatException(file.getName());
        }

		//TODO:  true if a line was skipped while loading, false otherwise
		return lineSkipped;
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
     * @deprecated
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
	 * Confirms the header of the loaded routes.txt file is valid
	 * @author Ryan Becker
	 * @param header
	 * @return
	 * @throws DataFormatException
	 */
	//TODO add validity, finish documentation
	public Headers validateHeader(String header) throws DataFormatException{
		final Headers DEFAULT_HEADERS = createDefaultHeader();

		Headers headers = new Headers();
        String[] header_list = header.split(",");

        for(int i = 0; i < header_list.length; i++){
            headers.addHeader(new Header(header_list[i], i));
        }

        // Validates header
        if(!validHeader(headers, DEFAULT_HEADERS)){
        	throw new DataFormatException("Invalid Header found in routes.txt");
		}

		return headers;
	}

    /**
     *
     * @author Ryan Becker
     * @param full_data String of line of data
     * @param headers Headers object of all fields within the header line in addition to their position in the header
     * @return Route object created from valid data
     * @throws DataFormatException if data input is not valid
     * @throws IllegalFormatException if data being parsed from a String is invalid
     */
	//TODO
	public Route validateData(String full_data, Headers headers) throws DataFormatException, IllegalFormatException {
        //splits while ignoring commas within description
        String[] split_data = full_data.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

        if(split_data.length != headers.length()){
            throw new DataFormatException("Invalid quantity of data fields within line of data");
        }


        // Required Fields
        String route_id = setDefaultDataValue(split_data, headers, "route_id");
        String route_color = setDefaultDataValue(split_data, headers, "route_color");
        // Non-Required Fields
        String agency_id = setDefaultDataValue(split_data, headers, "agency_id");
        String route_short_name = setDefaultDataValue(split_data, headers, "route_short_name");
        String route_long_name = setDefaultDataValue(split_data, headers, "route_long_name");
        String route_desc = setDefaultDataValue(split_data, headers, "route_desc");
        String route_type = setDefaultDataValue(split_data, headers, "route_type");
        String route_url = setDefaultDataValue(split_data, headers, "route_url");
        String route_text_color = setDefaultDataValue(split_data, headers, "route_text_color");
        String route_sort_order = setDefaultDataValue(split_data, headers, "route_sort_order");
        String continuous_pickup = setDefaultDataValue(split_data, headers, "continuous_pickup");
        String continuous_drop_off = setDefaultDataValue(split_data, headers, "continuous_drop_off");



        return new Route(route_id, agency_id, route_short_name, route_long_name, route_desc, route_type, route_url,
                route_color, route_text_color, route_sort_order, continuous_pickup, continuous_drop_off);
	}

    /**
     *
     * @authors Grant Fass, Ryan Becker
     * @param split_data array of segmented data
     * @param headers Headers object of all fields within the header line in addition to their position in the header
     * @param header_element String representing the field in the header
     * @return data value for expected header
     */
    private String setDefaultDataValue(String[] split_data, Headers headers, String header_element){
	    int headerIndex = headers.getHeaderIndex(header_element);
	    if(headerIndex >= 0 && headerIndex < split_data.length){
	        return split_data[headerIndex];
        }
	    return "";
    }


	/**
	 * Initializes a default Headers object that should be the order of header elements within a routes.txt file
	 * @author Ryan Becker
	 * @return Headers object containing Header objects each with a header field and index value
	 */
	//TODO finish documentation
	private Headers createDefaultHeader(){
		final String DEFAULT_HEADER = "route_id,agency_id,route_short_name,route_long_name,route_desc,route_type," +
				"route_url,route_color,route_text_color";
		Headers default_headers = new Headers();
		String[] header_list = DEFAULT_HEADER.split(",");

		for(int i = 0; i < header_list.length; i++){
			default_headers.addHeader(new Header(header_list[i], i));
		}
		return default_headers;

	}

	/**
	 *
	 * @author Ryan Becker
	 * @param headers Headers object of all fields within the header line in addition to their position in the header
	 * @param DEFAULT_HEADERS expected parameters within header
	 * @return true if the read-in header line is valid per the expected header in DEFAULT_HEADERS, false otherwise
	 */
	//TODO finish documentation, clean up
	private boolean validHeader (Headers headers, Headers DEFAULT_HEADERS){
		boolean isEqual = true;

		if(headers.length()!=DEFAULT_HEADERS.length()){
			isEqual = false;
			return isEqual;
		}

		//System.out.println();
		for(int i = 0; i < DEFAULT_HEADERS.length(); i++){
			if(!headers.getHeaderName(i).equals(DEFAULT_HEADERS.getHeaderName(i))){
				isEqual = false;
			}
		}
		return isEqual;
	}



}//end Routes
