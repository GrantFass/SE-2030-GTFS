/*
 * Authors: Becker, Ryan; Cross, Joy; Erickson, Simon; Fass, Grant;
 * Class: SE 2030 - 021
 * Team: G
 * Affiliation: Milwaukee School Of Engineering (MSOE)
 * Program Name: General Transit Feed Specification Tool
 * Copyright (C): GNU GPLv3; 9 November 2020
 *
 * This file is a part of the General Transit Feed Specification Tool
 * written by Team G of class SE 2030 - 021 at MSOE.
 *
 * This is a free software: it can be redistributed and/or modified
 * as expressed in the GNU GPLv3 written by the Free Software Foundation.
 *
 * This software is distributed in hopes that it is useful but does
 * not include any warranties, not even implied warranties. There is more
 * information about this in the GNU GPLv3.
 *
 * To view the license go to <gnu.org/licenses/gpl-3.0.en.html>
 */
package data;


import java.io.*;
import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.Scanner;

/**
 * Class that holds multiple routes stored within a hash map where a Route's route_id is the key,
 * and the Route object is the value for a specific slot within the hash map
 * @author Ryan Becker
 * @version 1.0
 * @created 06-Oct-2020 10:28:39 AM
 */
public class Routes {
	//region properties
	private HashMap<String, Route> routes;
	private Headers headers = new Headers();
	//endregion

	//region constructors
	/**
	 * Routes constructor initialized with empty hash map
	 * @author Ryan Becker
	 */
	public Routes(){
		routes = new HashMap<>();
	}
	//endregion

	//region getters
	/**
	 * @author Joy Cross
	 * @return current headers of routes
	 */
	public Headers getHeaders(){
		return headers;
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
	 * get the hashmap value
	 * @return the hashmap value
	 * @author Grant Fass
	 */
	public HashMap<String, Route> getRoutes() {
		return routes;
	}
	//endregion

	//region methods for adjusting data
	/**
	 * Removes specified Route object from routes
	 * @author Ryan Becker
	 * @param route_id Route to be removed
	 */
	public void removeRoute(String route_id){
		Route routeRemoved = routes.remove(route_id);
		boolean deleted = false;
		if(routeRemoved != null){
			deleted = true;
		}
	}

	/**
	 * adds route parameter to routes hash map with the route_id of route as the key, and route as the value.
	 * @author Ryan Becker
	 * @param route Route object to be added to routes
	 */
	public void addRoute(Route route){
		Route routeAdded = routes.put(route.getRouteID(), route);
		boolean added = false;
		if(routeAdded == null){
			added = true;
		}
	}

	/**
	 * clears the routes data
	 * @author Grant Fass
	 */
	public void clearRoutes() {
		routes.clear();
	}
	//endregion

	//region methods for exporting files
	/**
	 * export the routes to a specified output directory
	 * @param file the directory to save the file to
	 * @return true if the file was exported
	 * @author Grant Fass, Joy Cross
	 */
	public boolean exportRoutes(File file) {
		try (PrintWriter out = new PrintWriter((new BufferedOutputStream(new FileOutputStream(new File(file, "routes.txt")))))) {
			out.append(headers.toString());
			for (String key: routes.keySet()) {
				out.append(routes.get(key).getDataLine(headers));
			}
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	//endregion

	//region methods for loading files
	/**
	 * Method to parse data from a specified file
	 *
	 * @param file the GTFS file to be parsed
	 * @return a message containing the results of loading the file
	 * @throws IOException for general File IO errors.
	 * @author Grant Fass
	 */
	public String loadRoutes(File file) throws IOException {
		boolean wasLineSkipped = false;
		boolean wasFileLoaded = true;
		String failMessage = "";
		boolean emptyPrior = routes.isEmpty();
		if (!emptyPrior) {
			routes.clear();
		}
		//writes the items of the file to the hash map
		try (Scanner in = new Scanner(file)) {
			//read the headers. If they are formatted wrong then immediately throw error and stop.
			headers = validateHeader(in.nextLine());
			//read body. will skip improperly formatted lines.
			while (in.hasNextLine()) {
				try {
					addRoute(validateData(in.nextLine(), headers));
				} catch (IllegalArgumentException e) {
					wasLineSkipped = true;
				}
			}
		} catch (IllegalArgumentException e) {
			wasFileLoaded = false;
			failMessage = String.format("  ERROR: Routes Not Imported\n  File Contains Invalid Header Format\n  %s\n", e.getMessage());
		}
		String successMessage = String.format("  ???: Routes Imported Successfully.\n  %s\n  %s\n", emptyPrior ? "New Routes Data Imported" : "Routes Data Overwritten", wasLineSkipped ? "Lines Skipped During Import Of Routes" : "All Lines Imported Successfully");
		return String.format("IMPORT ROUTES:\n%s", wasFileLoaded ? successMessage : failMessage);
	}

	/**
	 * Confirms the header of the loaded routes.txt file is valid
	 * @param header String of read-in header from file
	 * @return Headers object containing the header fields in an ordered fashion
	 * @throws IllegalArgumentException if the header is invalid
	 * @author Ryan Becker, Grant Fass
	 */
	public Headers validateHeader(String header) throws IllegalArgumentException{
		header = header.toLowerCase().replace(" ", "");

		if(header.isEmpty()){
			throw new IllegalArgumentException("Invalid Header found in routes.txt: Empty Header");
		}
		if(!header.contains("route_id")){
			throw new IllegalArgumentException("Invalid Header found in routes.txt: Missing route_id");
		}
		if(header.endsWith(",")){
			throw new IllegalArgumentException("Invalid Header found in routes.txt: Header ends with ','");
		}

		Headers headers = new Headers();
		String regex = ",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)";
        String[] header_list = header.split(regex, -1);
        final String POSSIBLE_HEADERS = Route.getHeaderLine();

        for(int i = 0; i < header_list.length; i++){
        	if(!header_list[i].isEmpty() && POSSIBLE_HEADERS.contains(header_list[i])){
				headers.addHeader(new Header(header_list[i], i));
			} else if(header_list[i].isEmpty()){
        		throw new IllegalArgumentException("Invalid Header found in routes.txt: Empty header field:");
			} else {
				throw new IllegalArgumentException("Invalid Header found in routes.txt: Unexpected header field:");
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
		String regex = ",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)";
        String[] split_data = full_data.split(regex, -1);

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
    //endregion
}//end Routes
