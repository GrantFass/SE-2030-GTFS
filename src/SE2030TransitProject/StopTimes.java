package SE2030TransitProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.zip.DataFormatException;

/**
 * Class for a StopTimes database, which is the main database storing all stopTime objects
 * @author Joy Cross
 * @version 1.0
 */
public class StopTimes {

	private HashMap<String, StopTime> stop_times;

	/**
	 * StopTimes Constructor: creates empty instance of stop_times object
	 * @author Joy Cross
	 */
	public StopTimes(){
		stop_times = new HashMap<String, StopTime>();
	}

	/**
	 * Adds a StopTime object to the hashmap, returns false if could not be added to hashmap
	 * Key is in format "stopid;tripid"
	 * @author Joy Cross, Grant Fass
	 * @param stopTime stopTime to be added
	 * @return true if added correctly
	 */
	public boolean addStopTime(StopTime stopTime){
		StopTime stopTimeAdded = stop_times.put(stopTime.getStopID() + ";" + stopTime.getTripID(), stopTime);
		boolean added = false;
		if(stopTimeAdded != null){
			added = true;
		}
		return added;
	}

	/**
	 * Gets the stop time from the hashmap by "stop_id;route_id"
	 * @author Joy Cross
	 * @param stop_id stop_id associated with stoptime
	 * @param trip_id trip_id associated with stoptime
	 * @return StopTime object related to stop_id and trip_id
	 */
	public StopTime getStopTime(String stop_id, String trip_id){
		return stop_times.get(stop_id + ";" + trip_id);
	}

	/**
	 * Removes stop from data
	 * @author Joy Cross
	 * @param stop_id stop_id associated with stoptime
	 * @param trip_id trip_id associated with stoptime
	 * @return true if removal was completed
	 */
	public boolean removeStopTime(String stop_id, String trip_id){
		StopTime stopTimeDeleted = stop_times.remove(stop_id + ";" + trip_id);
		boolean delete = false;
		if(stopTimeDeleted == null){
			delete = true;
		}
		return delete;
	}

	/**
	 * Removes all stopTimes in database
	 * @author Joy Cross
	 * @return true if removed stopTimes
	 */
	public boolean clearStopTimes(){
		stop_times.clear();
		//stop_times = new HashMap<String, StopTime>();
		return true;
	}

	/**
	 * Method to parse StopTime data from a stop_times.txt file, resets database for every file
	 * @author Joy Cross, Grant Fass
	 * @param file the stop_times.txt file to be parsed
	 * @return true if a line was skipped while loading, false otherwise
	 * @throws FileNotFoundException if the file was not found
	 * @throws IOException for general File IO errors.
	 * @throws InputMismatchException if there is an issue parsing the file
	 * @throws DataFormatException if data will be overwritten
	 */
	public boolean loadStopTimes(File file) throws DataFormatException, IOException {
		boolean emptyAtLoadStart = true;
		if (!stop_times.isEmpty()) {
			emptyAtLoadStart = false;
			stop_times.clear();
		}
		Scanner fileInput = new Scanner(file);
		Headers headers;
		try {
			headers = validateHeader(fileInput.nextLine());
		} catch (IllegalArgumentException e) {
			throw new IOException("File not read due to invalid headers format");
		}
		boolean wasLineSkipped = false;
		while (fileInput.hasNextLine()) {
			try {
				StopTime stopTime = validateData(fileInput.nextLine(), headers);
				addStopTime(stopTime);
			} catch (IllegalArgumentException e) {
				wasLineSkipped = true;
			}
		}
		if (!emptyAtLoadStart) {
			throw new DataFormatException(file.getName());
		}
		return wasLineSkipped;
	}

	/**
	 * checks to confirm that the header is valid and matches an expected format
	 * @param header the header text line to validate
	 * @return a Headers object containing the ordering of the headers
	 * @throws IllegalArgumentException if the header does not match the expected format
	 * @author Grant Fass
	 */
	public Headers validateHeader(String header) throws IllegalArgumentException {
		header = header.toLowerCase().replace(" ", "");
		if (header.isEmpty()) {
			throw new IllegalArgumentException("Input header line cannot be empty");
		} else if (!header.contains("trip_id")) {
			throw new IllegalArgumentException("Input header line must contain all expected" +
					" values for a StopTime object. Header was missing trip_id");
		} else if (!header.contains("stop_id")) {
			throw new IllegalArgumentException("Input header line must contain all expected" +
					" values for a StopTime object. Header was missing stop_id");
		} else if (!header.contains("stop_sequence")) {
			throw new IllegalArgumentException("Input header line must contain all expected" +
					" values for a StopTime object. Header was missing trip_id");
		} else if (header.endsWith(",")) {
			throw new IllegalArgumentException("Input header line cannot contain blank fields");
		}
		Headers headers = new Headers();
		String[] headerDataArray = header.split(",");
		final String possibleHeaders = "trip_id,stop_id,stop_sequence,arrival_time,departure_time," +
				"stop_headsign,continuous_drop_off,continuous_pickup,drop_off_type," +
				"pickup_type,timepoint,shape_distance";
		for (int i = 0; i < headerDataArray.length; i++) {
			if (!headerDataArray[i].isEmpty() && possibleHeaders.contains(headerDataArray[i])) {
				headers.addHeader(new Header(headerDataArray[i], i));
			} else if (headerDataArray[i].isEmpty()){
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
	 * @return a StopTime object constructed from the data
	 * @throws IllegalArgumentException if there was an issue parsing or the wrong amount of data was passed
	 * @author Grant Fass
	 */
	public StopTime validateData(String data, Headers headers) throws IllegalArgumentException {
		String[] dataArray = data.split(",");
		if (dataArray.length != headers.length() || data.isEmpty()) {
			throw new IllegalArgumentException("Data line does not contain the proper amount of data");
		}
		//Required Fields
		String trip_id = setDefaultDataValue(dataArray, headers, "trip_id");
		String stop_id = setDefaultDataValue(dataArray, headers, "stop_id");
		String stop_sequence = setDefaultDataValue(dataArray, headers, "stop_sequence");

		//Conditionally Required Fields
		String arrival_time = setDefaultDataValue(dataArray, headers, "arrival_time");
		String departure_time = setDefaultDataValue(dataArray, headers, "departure_time");

		//Optional Fields
		String stop_headsign = setDefaultDataValue(dataArray, headers, "stop_headsign");
		String continuous_drop_off = setDefaultDataValue(dataArray, headers, "continuous_drop_off");
		String continuous_pickup = setDefaultDataValue(dataArray, headers, "continuous_pickup");
		String drop_off_type = setDefaultDataValue(dataArray, headers, "drop_off_type");
		String pickup_type = setDefaultDataValue(dataArray, headers, "pickup_type");
		String timepoint = setDefaultDataValue(dataArray, headers, "timepoint");
		String shape_distance = setDefaultDataValue(dataArray, headers, "shape_dist_traveled");

		return new StopTime(arrival_time, continuous_drop_off, continuous_pickup, departure_time,
				drop_off_type, pickup_type, shape_distance, stop_headsign,
				stop_id, stop_sequence, timepoint, trip_id);
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
	 * @author Joy Cross
	 * @return string of data
	 */
	@Override
	public String toString() {
		StringBuilder toReturn = new StringBuilder();
		int maxDisplay = 1000;
		Object[] keys = stop_times.keySet().toArray();
		for(int i = 0; i < maxDisplay; i++){
			toReturn.append(stop_times.get(keys[i]).toString() + "\n");
		}
		return toReturn.toString();
	}

}//end StopTimes