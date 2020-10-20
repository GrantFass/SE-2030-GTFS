package SE2030TransitProject;


import javafx.scene.control.TextArea;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

/**
 * Class for a Stops database, which is the main database storing all stop objects
 * @author Joy Cross
 * @version 1.0
 */
public class Stops {

	private HashMap<String, Stop> stops;

	/**
	 * Stops Constructor: creates empty instance of stops object
	 * @author Joy Cross
	 */
	public Stops(){
		stops = new HashMap<String, Stop>();
	}

	/**
	 * Adds a stop object to the hashmap, returns false if could not be added to hashmap
	 * @author Joy Cross
	 * @param stop_id, stop add stop using stop_id for key and stop object for data
	 * @param stop stop to be added to stops
	 * @return true if added correctly
	 */
	public boolean addStop(String stop_id, Stop stop){
		Stop stopAdded = stops.put(stop_id, stop);
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
	 * Method to parse Stop data from a stops.txt file
	 * @param file the stops.txt file to be parsed
	 * @return true if a line was skipped while loading, false otherwise
	 * @throws FileNotFoundException if the file was not found
	 * @throws IOException for general File IO errors.
	 * @throws InputMismatchException if there is an issue parsing the file
	 * @throws DataFormatException if data will be overwritten
	 * @author Joy Cross
	 */
	public boolean loadStops(File file) throws IOException, DataFormatException {
		boolean emptyAtLoadStart = true;
		if (!stops.isEmpty()) {
			emptyAtLoadStart = false;
			stops.clear();
		}
		Scanner sc = new Scanner(file);
		Headers headers;
		try {
			headers = validateHeader(sc.nextLine());
		} catch (DataFormatException e) {
			throw new IOException("File not read due to invalid headers format");
		}
		boolean lineSkipped = false;

		while (sc.hasNextLine()) {
			try {
				Stop stop = validateData(sc.nextLine(), headers);
				addStop(stop.getStopID(), stop);
			} catch (DataFormatException e) {
				lineSkipped = true;
			}
		}

		if(!emptyAtLoadStart){
			throw new DataFormatException(file.getName());
		}
		return lineSkipped;
	}

	/**
	 * export the stops to a specified output directory
	 * @param file the directory to save the file to
	 * @return true
	 * @throws IOException if an issue was encountered saving the file
	 * @author Joy Cross
	 */
	public boolean exportStops(File file) throws IOException {
		File outFile = new File(file, "stops.txt");
		if (!outFile.exists()) {
			outFile.createNewFile();
		}
		FileWriter out = new FileWriter(outFile.getAbsoluteFile());
		StringBuilder outputString = new StringBuilder();
		outputString.append(Stop.getHeaderLine());
		for (String key: stops.keySet()) {
			outputString.append(stops.get(key).getDataLine());
		}
		out.append(outputString);
		out.close();
		return true;
	}

	/**
	 * checks to confirm that the header is valid and matches an expected format
	 * @param header the header text line to validate
	 * @return a Headers object containing the ordering of the headers
	 * @throws DataFormatException if the header does not match the expected format
	 * @author Joy Cross
	 */
	public Headers validateHeader(String header) throws DataFormatException {
		header = header.toLowerCase();
		if (header.isEmpty()) {
			throw new IllegalArgumentException("Input header line cannot be empty");
		} else if (!header.contains("stop_id")) {
			throw new IllegalArgumentException("Input header line must contain all expected" +
					" values for a StopTime object. Header was missing trip_id");
		} else if (!(header.contains("stop_lat") || header.contains("stop_latitude"))) {
			throw new IllegalArgumentException("Input header line must contain all expected" +
					" values for a StopTime object. Header was missing stop_id");
		} else if (!(header.contains("stop_lon") || header.contains("stop_longitude"))) {
			throw new IllegalArgumentException("Input header line must contain all expected" +
					" values for a StopTime object. Header was missing trip_id");
		}
		Headers headers = new Headers();
		String[] headerDataArray = header.split(",");
		final String possibleHeaders = Stop.getHeaderLine().toLowerCase();
		for (int i = 0; i < headerDataArray.length; i++) {
			String indivHeader = headerDataArray[i].trim();
			// checks if header is abbreviated to something else and normalizes it
			switch (indivHeader) {
				case "stop_desc":
					indivHeader = "stop_description";
					break;
				case "stop_lat":
					indivHeader = "stop_latitude";
					break;
				case "stop_lon":
					indivHeader = "stop_longitude";
					break;
			}

			// check to make sure header isn't empty or not valid
			if (!indivHeader.isEmpty() && possibleHeaders.contains(indivHeader)) {
				headers.addHeader(new Header(indivHeader, i));
			} else if (indivHeader.isEmpty()){
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
	 * @throws DataFormatException if the data does not match the expected format
	 * @throws IllegalArgumentException if there was an issue parsing a String enumerator to an double
	 * @author Joy Cross
	 */
	public Stop validateData(String data, Headers headers) throws DataFormatException, IllegalArgumentException {
		String[] dataArray = data.split(",", -1);
		if (dataArray.length != headers.length() || data.isEmpty()) {
			throw new IllegalArgumentException("Data line does not contain the proper amount of data");
		}

		//Required Fields
		String stop_id = setDefaultDataValue(dataArray, headers, "stop_id");
		String stop_latitude = setDefaultDataValue(dataArray, headers, "stop_latitude");
		String stop_longitude = setDefaultDataValue(dataArray, headers, "stop_longitude");

		//Optional Fields
		String level_id = setDefaultDataValue(dataArray, headers, "level_id");
		String location_type = setDefaultDataValue(dataArray, headers, "location_type");
		String parent_station = setDefaultDataValue(dataArray, headers, "parent_station");
		String platform_code = setDefaultDataValue(dataArray, headers, "platform_code");
		String stop_code = setDefaultDataValue(dataArray, headers, "stop_code");
		String stop_description = setDefaultDataValue(dataArray, headers, "stop_description");
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
	 * Method to output data as a single concatenated string
	 * @author Joy Cross, Grant Fass
	 * @return string of data
	 */
	@Override
	public String toString() {
		StringBuilder toReturn = new StringBuilder();
		for(String key : stops.keySet()){
			toReturn.append(stops.get(key).toString() + "\n");
		}
		return toReturn.toString();
	}

	/*
	public static String createHeaderLine(Headers headers) {
		StringBuilder sb = new StringBuilder();
		int i;
		for(i = 0; i < headers.length()-1; i++){
			sb.append(headers.getHeaderName(i) + ",");
		}
		sb.append(headers.getHeaderName(i) + "\n");

		return sb.toString();
	}
	*/
}//end Stops