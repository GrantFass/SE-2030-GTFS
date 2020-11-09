package data;


import java.io.*;
import java.util.*;
import java.util.zip.DataFormatException;

/**
 * Class for a Stops database, which is the main database storing all stop objects
 * @author Joy Cross
 * @version 1.0
 */
public class Stops {

	private HashMap<String, Stop> stops;
	private Headers headers = new Headers();

	/**
	 * Stops Constructor: creates empty instance of stops object
	 * @author Joy Cross
	 */
	public Stops(){
		stops = new HashMap<String, Stop>();
	}

	/**
	 * Adds a stop object to the hashmap, returns false if could not be added to hashmap
	 * @param stop stop to be added to stops
	 * @return true if added correctly
	 * @author Joy Cross, Grant Fass
	 */
	public boolean addStop( Stop stop){
		Stop stopAdded = stops.put(stop.getStopID(), stop);
		boolean added = false;
		if(stopAdded != null){
			added = true;
		}
		return added;
	}

	/**
	 * Gets the stop from the hashmap by stop_id
	 * @author Joy Cross
	 * @param stop_id unique identifier for stop
	 * @return Stop object
	 */
	public Stop getStop(String stop_id){
		return stops.get(stop_id);
	}

	/**
	 * Removes one stop from data by stop_id
	 * @author Joy Cross
	 * @param stop_id remove stop by stop_id
	 */
	public boolean removeStop(String stop_id){
		Stop stopDeleted = stops.remove(stop_id);
		boolean delete = false;
		if(stopDeleted == null){
			delete = true;
		}
		return delete;
	}

	/**
	 * Removes all stops in database
	 * @author Joy Cross
	 * @return true if removed stops
	 */
	public boolean clearStops(){
		stops = new HashMap<String, Stop>();
		return true;
	}

	/**
	 * Method to parse data from a specified file
	 *
	 * @param file the GTFS file to be parsed
	 * @return a message containing the results of loading the file
	 * @throws IOException for general File IO errors.
	 * @author Grant Fass
	 */
	public String loadStops(File file) throws IOException {
		boolean wasLineSkipped = false;
		boolean wasFileLoaded = true;
		String failMessage = "";
		boolean emptyPrior = stops.isEmpty();
		if (!emptyPrior) {
			stops.clear();
		}
		//writes the items of the file to the hash map
		try (Scanner in = new Scanner(file)) {
			//read the headers. If they are formatted wrong then immediately throw error and stop.
			headers = validateHeader(in.nextLine());
			//read body. will skip improperly formatted lines.
			while (in.hasNextLine()) {
				try {
					addStop(validateData(in.nextLine(), headers));
				} catch (IllegalArgumentException e) {
					wasLineSkipped = true;
				}
			}
		} catch (IllegalArgumentException e) {
			wasFileLoaded = false;
			failMessage = String.format("  ERROR: Stops Not Imported\n  File Contains Invalid Header Format\n  %s\n", e.getMessage());
		}
		String successMessage = String.format("  ✓: Stops Imported Successfully.\n  %s\n  %s\n", emptyPrior ? "New Stops Data Imported" : "Stops Data Overwritten", wasLineSkipped ? "Lines Skipped During Import Of Stops" : "All Lines Imported Successfully");
		return String.format("IMPORT STOPS:\n%s", wasFileLoaded ? successMessage : failMessage);
	}

	/**
	 * export the stops to a specified output directory
	 * @param file the directory to save the file to
	 * @return true if the file was exported
	 * @author Grant Fass, Joy Cross
	 */
	public boolean exportStops(File file) {
		try (PrintWriter out = new PrintWriter((new BufferedOutputStream(new FileOutputStream(new File(file, "stops.txt")))))) {
			out.append(createHeaderLine(headers));
			for (String key: stops.keySet()) {
				out.append(stops.get(key).getDataLine(headers));
			}
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	/**
	 * checks to confirm that the header is valid and matches an expected format
	 * @param header the header text line to validate
	 * @return a Headers object containing the ordering of the headers
	 * @throws IllegalArgumentException if the header does not match the expected format
	 * @author Joy Cross, Grant Fass
	 */
	public Headers validateHeader(String header) throws IllegalArgumentException {
		header = header.toLowerCase();
		if (header.isEmpty()) {
			throw new IllegalArgumentException("Input header line cannot be empty");
		} else if (!header.contains("stop_id")) {
			throw new IllegalArgumentException("Input header line must contain all expected" +
					" values for a Stop object. Header was missing stop_id");
		} else if (!(header.contains("stop_lat") || header.contains("stop_latitude"))) {
			throw new IllegalArgumentException("Input header line must contain all expected" +
					" values for a Stop object. Header was missing latitude");
		} else if (!(header.contains("stop_lon") || header.contains("stop_longitude"))) {
			throw new IllegalArgumentException("Input header line must contain all expected" +
					" values for a Stop object. Header was missing longitude");
		}
		Headers headers = new Headers();
		String regex = ",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)";
		String[] headerDataArray = header.split(regex, -1);
		final String possibleHeaders = Stop.getHeaderLine().toLowerCase();
		for (int i = 0; i < headerDataArray.length; i++) {
			String individualHeader = headerDataArray[i].trim();
			// check to make sure header isn't empty or not valid
			if (!individualHeader.isEmpty() && possibleHeaders.contains(individualHeader)) {
				headers.addHeader(new Header(individualHeader, i));
			} else if (individualHeader.isEmpty()){
				throw new IllegalArgumentException("Input header line cannot contain blank fields");
			} else {
				throw new IllegalArgumentException("Header field contains unexpected field: " + headerDataArray[i]);
			}
		}
		return headers;
	}

	/**
	 * checks to confirm that a data line is valid and matches the expected format set by the header line
	 * @param data the line of data to parse
	 * @param headers the headers values to use to parse the data
	 * @return a Stop object constructed from the data
	 * @throws IllegalArgumentException if the data does not match the expected format
	 * @author Joy Cross
	 */
	public Stop validateData(String data, Headers headers) throws IllegalArgumentException {
		String regex = ",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)";
		String[] dataArray = data.split(regex, -1);
		if (dataArray.length != headers.length() || data.isEmpty()) {
			throw new IllegalArgumentException("Data line does not contain the proper amount of data");
		}

		//Required Fields
		String stop_id = setDefaultDataValue(dataArray, headers, "stop_id");
		String stop_latitude = setDefaultDataValue(dataArray, headers, "stop_latitude");
		if(stop_latitude.isEmpty()){
			stop_latitude = setDefaultDataValue(dataArray, headers, "stop_lat");
		}
		String stop_longitude = setDefaultDataValue(dataArray, headers, "stop_longitude");
		if(stop_longitude.isEmpty()){
			stop_longitude = setDefaultDataValue(dataArray, headers, "stop_lon");
		}

		//Optional Fields
		String level_id = setDefaultDataValue(dataArray, headers, "level_id");
		String location_type = setDefaultDataValue(dataArray, headers, "location_type");
		String parent_station = setDefaultDataValue(dataArray, headers, "parent_station");
		String platform_code = setDefaultDataValue(dataArray, headers, "platform_code");
		String stop_code = setDefaultDataValue(dataArray, headers, "stop_code");
		String stop_description = setDefaultDataValue(dataArray, headers, "stop_description");
		if(stop_description.isEmpty()){
			stop_description = setDefaultDataValue(dataArray, headers, "stop_desc");
		}
		String stop_name = setDefaultDataValue(dataArray, headers, "stop_name");
		String stop_timezone = setDefaultDataValue(dataArray, headers, "stop_timezone");
		String stop_url = setDefaultDataValue(dataArray, headers, "stop_url");
		String wheelchair_boarding = setDefaultDataValue(dataArray, headers, "wheelchair_boarding");

		return new Stop(level_id, location_type, parent_station, platform_code, stop_code, stop_description, stop_id,
		stop_latitude, stop_longitude, stop_name, stop_timezone, stop_url, wheelchair_boarding);
	}

	/**
	 * Searches for the expectedHeader in the Headers object and will return the associated value
	 * if it is found or will return empty String if it was not found.
	 * @param dataArray the data values to be used
	 * @param headers the headers to search through
	 * @param expectedHeader the expected header value
	 * @return data value for the expected header
	 * @author Grant Fass
	 */
	public String setDefaultDataValue(String[] dataArray, Headers headers, String expectedHeader) {
		int index = headers.getHeaderIndex(expectedHeader);
		if (index >= 0 && index < dataArray.length) {
			return dataArray[index];
		}
		return "";
	}

	/**
	 * get the hashmap value
	 * @return the hashmap value
	 * @author Grant Fass
	 */
	public HashMap<String, Stop> getStops() {
		return stops;
	}

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

	//start feature 13

	/**
	 * Gets a stop from a stop_id and changes its longitude and latitude
	 * @author Simon Erickson
	 * @param stop_id for the stop_time
	 * @param longitude the longitude
	 * @param latitude the latitude
	 * @return boolean representation of success
	 */
	public boolean changeLocationFromStop_ID(String stop_id, String longitude, String latitude){
		HashMap<String, Stop> og_stops = stops;
		try{
			for(Stop stop : stops.values()){
				if(stop.getStopID().equals(stop_id)){
					stop = new Stop(stop.getLevelID()+"",
							stop.getLocationType()+"",
							stop.getParentStation()+"",
							stop.getPlatformCode()+"",
							stop.getStopCode()+"",
							stop.getStopDescription()+"",
							stop_id,latitude, longitude, stop.getStopName()+"",
							stop.getStopTimezone()+"",
							stop.getStopURL()+"",
							stop.getWheelchairBoarding()+"");
				}
			}
			return true;
		}catch (IllegalArgumentException e){
			stops = og_stops;
			return false;
		}
	}

	//end feature 13


}//end Stops