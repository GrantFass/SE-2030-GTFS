package data;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
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
	private Headers headers = new Headers();

	/**
	 * Creates header line from input headers
	 * @param headers headers to put into a String output
	 * @return String
	 * @author Joy Cross
	 */
	public String createHeaderLine(Headers headers) {
		StringBuilder sb = new StringBuilder();
		int i;
		for(i = 0; i < headers.length()-1; i++){
			sb.append(headers.getHeaderName(i) + ",");
		}
		sb.append(headers.getHeaderName(i) + "\n");

		return sb.toString();
	}


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
	 * export the routes to a specified output directory
	 * @param file the directory to save the file to
	 * @return true
	 * @throws IOException if an issue was encountered saving the file
	 * @author Grant Fass, Joy Cross
	 */
	public boolean exportRoutes(File file) throws IOException {
		File routeFile = new File(file, "routes.txt");
		FileWriter writer = new FileWriter(routeFile.getAbsoluteFile());

		writer.append(createHeaderLine(headers));
		for(String key : routes.keySet()){
			writer.append(routes.get(key).getDataLine(headers));
		}
		writer.close();
		return true;
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
	public boolean loadRoutes(File file) throws FileNotFoundException, IOException,
            InputMismatchException, DataFormatException {
	    boolean lineSkipped;

	    Scanner read_header = new Scanner(file);
	    String full_header = read_header.nextLine();

	    // Throws IllegalArgumentException if header is not valid
        try {
            headers = validateHeader(full_header);
        } catch (IllegalArgumentException invalidHeader){
            throw new IOException(invalidHeader.getMessage());
        }
        lineSkipped = false;

        boolean emptyPrior = routes.isEmpty();
        if (!emptyPrior){
            routes.clear();
        }

        Scanner read_data = new Scanner(file);
        read_data.useDelimiter(",");
        read_data.nextLine(); //consumes header line to start at data to be parsed

        while (read_data.hasNextLine()) {

            String full_data = read_data.nextLine();

            try{
                Route newRoute = validateData(full_data, headers);
                addRoute(newRoute);
            } catch (IllegalArgumentException invalidData){
                lineSkipped = true;
            }
        }

        read_header.close();
        read_data.close();

        if(!emptyPrior && !lineSkipped){
            throw new DataFormatException(file.getName());
        } else if(!emptyPrior){
        	throw new IOException("Either data in " + file.getName() + " was overwritten, or there was a problem" +
					"reading the file");
		}
		return lineSkipped;
	}

	/**
	 * Method to output data as a single concatenated string
	 * @author Grant Fass
	 * @return string of data
	 */
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		int maxDisplay = 100;
		int count = 0;
		Iterator mapIterator = routes.entrySet().iterator();
		while (mapIterator.hasNext() && count < maxDisplay) {
			Map.Entry mapElement = (Map.Entry) mapIterator.next();
			stringBuilder.append(getRoute(mapElement.getKey().toString()).toString()).append("\n");
			count++;
		}
		return stringBuilder.toString();
	}

	/**
	 * output simplified data as a single concatenated string
	 * @return string of data
	 * @author Grant Fass
	 */
	public String toSimpleString() {
		StringBuilder stringBuilder = new StringBuilder();
		int maxDisplay = 100;
		int count = 0;
		Iterator mapIterator = routes.entrySet().iterator();
		while (mapIterator.hasNext() && count < maxDisplay) {
			Map.Entry mapElement = (Map.Entry) mapIterator.next();
			stringBuilder.append(getRoute(mapElement.getKey().toString()).toSimpleString()).append("\n");
			count++;
		}
		return stringBuilder.toString();
	}

	/**
	 * Confirms the header of the loaded routes.txt file is valid
	 * @author Ryan Becker
	 * @param header String of read-in header from file
	 * @return Headers object containing the header fields in an ordered fashion
	 * @throws IllegalArgumentException if the header is invalid
	 */
	public Headers validateHeader(String header) throws IllegalArgumentException{
		header = header.toLowerCase().replace(" ", "");

		if(header.isEmpty()){
			throw new IllegalArgumentException("Invalid Header found in routes.txt: Empty Header\n" +
					"File will not be loaded");
		}
		if(!header.contains("route_id")){
			throw new IllegalArgumentException("Invalid Header found in routes.txt: Missing route_id\n" +
					"File will not be loaded");
		}
		//route_color is needed, but also says it defaults to white per the GTFS guideline
		/*if(!header.contains("route_color")){
			throw new IllegalArgumentException("Invalid Header found in routes.txt: Missing route_color\n" +
					"File will not be loaded");
		}*/
		if(header.endsWith(",")){
			throw new IllegalArgumentException("Invalid Header found in routes.txt: Header ends with ','\n" +
					"File will not be loaded");
		}

		Headers headers = new Headers();
        String[] header_list = header.split(",");
        final String POSSIBLE_HEADERS = Route.getHeaderLine();

        for(int i = 0; i < header_list.length; i++){
        	if(!header_list[i].isEmpty() && POSSIBLE_HEADERS.contains(header_list[i])){
				headers.addHeader(new Header(header_list[i], i));
			} else if(header_list[i].isEmpty()){
        		throw new IllegalArgumentException("Invalid Header found in routes.txt: Empty header field:\n" +
						"File will not be loaded");
			} else {
				throw new IllegalArgumentException(("Invalid Header found in routes.txt: Unexpected header field:\n" +
						"File will not be loaded"));
			}
        }

        return headers;
	}

    /**
     * @author Ryan Becker
     * @param full_data String of line of data
     * @param headers Headers object of all fields within the header line in addition to their position in the header
     * @return Route object created from valid data
     * @throws IllegalFormatException if data being parsed from a String is invalid
     */
	public Route validateData(String full_data, Headers headers) throws IllegalFormatException {
        //splits while ignoring commas within description
        String[] split_data = full_data.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

        if(split_data.length != headers.length()){
            throw new IllegalArgumentException("Invalid quantity of data fields within line of data");
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
     * @author Grant Fass, Ryan Becker
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
	 * @deprecated
	 * Initializes a default Headers object that should be the order of header elements within a routes.txt file
	 * @author Ryan Becker
	 * @return Headers object containing Header objects each with a header field and index value
	 */
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
	 * @deprecated
	 * @author Ryan Becker
	 * @param headers Headers object of all fields within the header line in addition to their position in the header
	 * @param DEFAULT_HEADERS expected parameters within header
	 * @return true if the read-in header line is valid per the expected header in DEFAULT_HEADERS, false otherwise
	 */
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
}//end Routes
