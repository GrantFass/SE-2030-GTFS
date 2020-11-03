package data;

import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

/**
 * Class for a StopTimes database, which is the main database storing all stopTime objects
 * @author Joy Cross
 * @version 1.0
 */
public class StopTimes {

	private HashMap<String, StopTime> stop_times;
	private HashMap<String, String> tripStartAndEnd;
<<<<<<< HEAD
	private final int maxDisplay = 2000;
=======
	private Headers headers = new Headers();
	private final int MAX_DISPLAY = 100;
>>>>>>> 67c6fda4e7a185cfd5a784391dc106f9070f9892

	/**
	 * StopTimes Constructor: creates empty instance of stop_times object
	 * @author Joy Cross
	 */
	public StopTimes(){
		stop_times = new HashMap<String, StopTime>();
		tripStartAndEnd = new HashMap<String, String>();
	}

	/**
	 * Adds a StopTime object to the hashmap, returns false if could not be added to hashmap
	 * Key is in format "stopid;tripid"
	 * @author Joy Cross, Grant Fass
	 * @param stopTime stopTime to be added
	 * @return true if added correctly
	 */
	public boolean addStopTime(StopTime stopTime){
		addTripStartAndEnd(stopTime);
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
		tripStartAndEnd.clear();
		//stop_times = new HashMap<String, StopTime>();
		return true;
	}

	/**
	 * export the stop times to a specified output directory
	 * @param file the directory to save the file to
	 * @return true
	 * @throws IOException if an issue was encountered saving the file
	 * @author Grant Fass, Joy Cross
	 */
	public boolean exportStopTimes(File file) throws IOException {
		File outFile = new File(file, "stop_times.txt");
		if (!outFile.exists()) {
			outFile.createNewFile();
		}
		FileWriter out = new FileWriter(outFile.getAbsoluteFile());
		StringBuilder outputString = new StringBuilder();
		outputString.append(createHeaderLine(headers));
		for (String key: stop_times.keySet()) {
			outputString.append(stop_times.get(key).getDataLine(headers));
		}
		out.append(outputString);
		out.close();
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
			tripStartAndEnd.clear();
		}
		Scanner fileInput = new Scanner(file);
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
		if (!emptyAtLoadStart && !wasLineSkipped) {
			throw new DataFormatException(file.getName());
		} else if (!emptyAtLoadStart) {
			throw new IOException("Either data in " + file.getName() + " was overwritten or " +
					"there was a problem reading the file");
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
<<<<<<< HEAD
	 * get the hashmap value
	 * @return the hashmap value
	 * @author Grant Fass
	 */
	public HashMap<String, StopTime> getStop_times() {
		return stop_times;
=======
	 * Method to output first 1000 StopTimes as a single concatenated string
	 * @author Joy Cross, Grant Fass
	 * @return string of data
	 */
	@Override
	public String toString() {
		StringBuilder toReturn = new StringBuilder();
		int maxDisplay = 100;
		Object[] keys = stop_times.keySet().toArray();
		if (keys.length == 0) {
			return "";
		}
		for(int i = 0; i < maxDisplay; i++){
			toReturn.append(stop_times.get(keys[i]).toString() + "\n");
		}
		return toReturn.toString();
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

	/**
	 * output simplified data as a single concatenated string
	 * @return string of data
	 * @author Grant Fass
	 */
	public String toSimpleString() {
		StringBuilder toReturn = new StringBuilder();
		Object[] keys = stop_times.keySet().toArray();
		if (keys.length == 0) {
			return "";
		}
		for(int i = 0; i < MAX_DISPLAY; i++){
			toReturn.append(stop_times.get(keys[i]).toSimpleString() + "\n");
		}
		return toReturn.toString();
	}

	/**
	 * Gets the count of trips that is associated with a stop
	 * @return the number of trips that each stop is found on
	 * @author Joy Cross
	 */
	public HashMap<String, String> getTripsPerStop() {
		Object[] keys = stop_times.keySet().toArray();

		// separate stop_id from trip_id
		for(int i = 0; i < keys.length; i++){
			String value = keys[i].toString();
			keys[i] = value.substring(0, value.indexOf(';'));
		}

		// count distinct stops which returns number of how much a stop is used by trips
		StringBuilder sb = new StringBuilder();
		HashMap<String, String> tripsPerStop = new HashMap<>();
		int[] i = {0};
		Arrays.stream(keys).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
				.entrySet()
				.forEach(object -> {
						String number = object.toString().substring(object.toString().indexOf('=') + 1);
						String stop_id = object.toString().substring(0, object.toString().indexOf('='));
						if(i[0]<=MAX_DISPLAY){
							sb.append(String.format("%s Trips contain Stop ID: %s\n", number, stop_id));
						}
						tripsPerStop.put(stop_id, number);
						i[0] = i[0] + 1;
				});
		return tripsPerStop;
	}

	/**
	 * Returns a String of the count of trips that contain a stop
	 * @param tripsPerStop hashmap obtained from calling getTripsPerStop()
	 * @author Joy Cross
	 */
	public String getTripsPerStop(HashMap<String, String> tripsPerStop) {
		Object[] keys = tripsPerStop.keySet().toArray();
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < MAX_DISPLAY && i < keys.length; i++){
			sb.append(tripsPerStop.get(keys[i]) + " Trips contain Stop ID: " + keys[i].toString() + "\n");
		}
		return sb.toString();
>>>>>>> 67c6fda4e7a185cfd5a784391dc106f9070f9892
	}

	/**
	 * Method that creates a HashMap with trip_id, first_time, first_stop_id,
	 * 	 * last_time, and last_stop_id
	 *
	 * @author Simon Erickson
	 * @param stopTime a StopTime object that holds the information for the HashMap
	 */
	private void addTripStartAndEnd(StopTime stopTime) {
		//HashMap<trip_id, first_time--first_stop_id--last_time--last_stop_id>
		String valueAtTrip = tripStartAndEnd.get(stopTime.getTripID());
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		long newArrive = stopTime.getArrivalTime().getTime();
		long newDepart = stopTime.getDepartureTime().getTime();
		String stop_id = stopTime.getStopID();
		try {
			String[] tripValue = valueAtTrip.split("--");
			long currentFirstArrive = Long.parseLong(tripValue[0]);
			long currentLastDepart = Long.parseLong(tripValue[2]);
			String first_stop_id = tripValue[1];
			String last_stop_id = tripValue[3];
			if(newArrive < currentFirstArrive){
				currentFirstArrive = newArrive;
				first_stop_id = stop_id;
			}
			if(newDepart > currentLastDepart){
				currentLastDepart = newDepart;
				last_stop_id = stop_id;
			}
			tripStartAndEnd.remove(stopTime.getTripID());
			tripStartAndEnd.put(stopTime.getTripID(),
					currentFirstArrive + "--" + first_stop_id + "--"
							+ currentLastDepart + "--" + last_stop_id);
		}catch (NullPointerException e){
			tripStartAndEnd.put(stopTime.getTripID(),
					newArrive + "--" + stopTime.getStopID() + "--"
							+ newDepart + "--" + stopTime.getStopID());
		}
	}

	/**
	 * Method that returns a HashMap with trip_id, first_time, first_stop_id,
	 * last_time, and last_stop_id
	 *
	 * @author Simon Erickson
	 * @return tripAndDistance The HashMap with trip_id, first_time, first_stop_id,
	 * 	 * last_time, and last_stop_id.
	 */
	public HashMap<String, String> getTripStartAndEnd(){
		return tripStartAndEnd;
	}



	//Start feature five

	/**
	 * Gets every trip_id that is paired with the searched stop_id within a given StopTime object
	 * @author Ryan Becker
	 * @param stop_id associated with a Stop that is used to search for paired trip_id within a given StopTime object
	 * @return ArrayList of every trip_id that is within a StopTime object that also contains the searched stop_id
	 */
	public ArrayList<String> getTripIDs_fromStop_ID(String stop_id){
		ArrayList<String> trip_ids = new ArrayList<>();
		for(StopTime stop_time : stop_times.values()){
			if(stop_time.getStopID().equals(stop_id)){
				trip_ids.add(stop_time.getTripID());
			}



		}

		return trip_ids;
	}

	//End feature five


	/**
	 * Searches through database of stopTimes and returns List<trip_id> related to stop
	 * @param stop_id stop to search for
	 * @return trips in order of time that contain that stop
	 * @author Joy Cross
	 */
	public List<String> searchStopDisplayTrips(String stop_id){
		// gets current time to compare
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());

		// gets hashmap of trips id and arrival times for comparison
		HashMap<Timestamp, String> tripsByStop = new HashMap<>();
		ArrayList<Timestamp> times = new ArrayList<>();
		Object[] keys = stop_times.keySet().toArray();

		Arrays.stream(keys)
				.forEach(object -> {
							if(object.toString().startsWith(stop_id + ";")){
								tripsByStop.put(stop_times.get(object.toString()).getArrivalTime(),
										object.toString().replace(stop_id + ";", "").trim());
								times.add(stop_times.get(object.toString()).getArrivalTime());
							}
						});

		// get index of everything after or at current time in sorted list
		Collections.sort(times);
		int index = 0;
		for(int i = 0; i < times.size(); i++){
			if((times.get(i).getTime() - currentTime.getTime())>=0){
				index = i;
				break;
			}
		}

		// sort list by comparing to current time
		ArrayList<String> toReturn = new ArrayList<>();
		for(int i = index; i < times.size(); i++){
			toReturn.add(tripsByStop.get(times.get(i)));
		}
		for(int i = 0; i < index; i++){
			toReturn.add(tripsByStop.get(times.get(i)));
		}

		return toReturn;
	}

}//end StopTimes