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
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * Class for a StopTimes database, which is the main database storing all stopTime objects
 * @author Joy Cross
 * @version 1.0
 */
public class StopTimes {
	//region properties
	private HashMap<String, StopTime> stop_times;
	private HashMap<String, String> tripStartAndEnd;
	private Headers headers = new Headers();
	//endregion

	//region constructors
	/**
	 * StopTimes Constructor: creates empty instance of stop_times object
	 * @author Joy Cross
	 */
	public StopTimes(){
		stop_times = new HashMap<String, StopTime>();
		tripStartAndEnd = new HashMap<String, String>();
	}
	//endregion

	//region getters
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
	 * @author Joy Cross
	 * @return current headers of routes
	 */
	public Headers getHeaders(){
		return headers;
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

	/**
	 * Gets every stop_id that is associated with a trip_id
	 * @param trip_id related to searched route_id
	 * @return ArrayList of all stop_ids related to trip_id
	 */
	public ArrayList<String> getStopIDs_fromTripID(String trip_id){
		ArrayList<String> stop_ids = new ArrayList<>();
		for(StopTime stopTime : stop_times.values()){
			if(stopTime.getTripID().equals(trip_id)){
				stop_ids.add(stopTime.getStopID());
			}
		}
		return stop_ids;
	}

	/**
	 * Gets all trip_ids that are occurring in the future
	 * @author Ryan Becker
	 * @param all_trip_ids ArrayList of all trip_ids related to initial route_id
	 * @return ArrayList of only trip_ids that have a time in the future associated with it
	 */
	public ArrayList<String> getFutureTripIDs_fromAllTripIDs(ArrayList<String> all_trip_ids){
		ArrayList<String> future_trip_ids = new ArrayList<>();

		for(String trip_id : all_trip_ids){
			if(inFuture(trip_id)){
				future_trip_ids.add(trip_id);
			}
		}
		return future_trip_ids;
	}

	/**
	 * get the hashmap value
	 * @return the hashmap value
	 * @author Grant Fass
	 */
	public HashMap<String, StopTime> getStop_times() {
		return stop_times;
	}

	/**
	 * Searches through database of stopTimes and returns List<trip_id> related to stop
	 * @param stop_id stop to search for
	 * @return trips in order of time that contain that stop
	 * @author Joy Cross
	 */
	public List<String> getTripsFromStopID(String stop_id){
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

	/**
	 * Checks if a given trip_id has a time in the future
	 * @author Ryan Becker
	 * @param trip_id being checked
	 * @return boolean indicating future status: True if in the future, false otherwise
	 */
	private boolean inFuture(String trip_id){
		boolean inFuture = false;
		for(StopTime stopTime : stop_times.values()){
			if(stopTime.getTripID().equalsIgnoreCase(trip_id)){
				LocalDateTime currentTime = java.time.LocalDateTime.now();

				LocalTime fileTime = LocalTime.from(stopTime.getDepartureTime().toLocalDateTime());
				LocalDateTime fileDateTime = LocalDateTime.of(LocalDate.now(), fileTime);

				if(currentTime.isBefore(fileDateTime)){
					inFuture = true;
					break;
				}
			}
		}
		return inFuture;
	}
	//endregion

	//region methods for adjusting data
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
	 * Gets a stop_time from a stop_id and changes its departure/arrival time
	 * @author Simon Erickson
	 * @param stop_id for the stop_time
	 * @param departAndArrivalTime new departure/arrival time
	 * @return boolean representation of success
	 */
	public boolean changeStopTime_fromStop_ID(String stop_id,String departAndArrivalTime){
		HashMap<String, StopTime> og_stop_time = stop_times;
		try{
			for(StopTime stop_time : stop_times.values()){
				if(stop_time.getStopID().equals(stop_id)){
					stop_time = new StopTime(departAndArrivalTime,
							stop_time.getContinuousDropOff()+"",
							stop_time.getContinuousPickup()+"", departAndArrivalTime,
							stop_time.getDropOffType()+"",
							stop_time.getPickupType()+"",
							stop_time.getShapeDistTraveled()+"",
							stop_time.getStopHeadsign()+"", stop_id+"",
							stop_time.getStopSequence()+"",
							stop_time.getTimepoint()+"", stop_time.getTripID()+"");
				}
			}
			return true;
		}catch (IllegalArgumentException e){
			stop_times = og_stop_time;
			return false;
		}
	}

	/**
	 * Method that creates a HashMap with trip_id, first_time, first_stop_id,
	 * 	 * last_time, and last_stop_id
	 *
	 * @author Simon Erickson
	 * @param stopTime a StopTime object that holds the information for the HashMap
	 */
	private void addTripStartAndEnd(StopTime stopTime) {
		String valueAtTrip = tripStartAndEnd.get(stopTime.getTripID());
		long newArrive = stopTime.getArrivalTime().getTime();
		long newDepart = stopTime.getDepartureTime().getTime();
		String stop_id = stopTime.getStopID();
		try {
			String[] tripValue = valueAtTrip.split("--", -1);
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
		} catch (NullPointerException e){
			tripStartAndEnd.put(stopTime.getTripID(),
					newArrive + "--" + stopTime.getStopID() + "--"
							+ newDepart + "--" + stopTime.getStopID());
		}
	}
	//endregion

	//region methods for exporting files
	/**
	 * export the stop times to a specified output directory
	 * @param file the directory to save the file to
	 * @return true if the file was exported
	 * @author Grant Fass, Joy Cross
	 */
	public boolean exportStopTimes(File file) {
		try (PrintWriter out = new PrintWriter((new BufferedOutputStream(new FileOutputStream(new File(file, "stop_times.txt")))))) {
			out.append(headers.toString());
			for (String key: stop_times.keySet()) {
				out.append(stop_times.get(key).getDataLine(headers));
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
	public String loadStopTimes(File file) throws IOException {
		boolean wasLineSkipped = false;
		boolean wasFileLoaded = true;
		String failMessage = "";
		boolean emptyPrior = stop_times.isEmpty();
		if (!emptyPrior) {
			stop_times.clear();
			tripStartAndEnd.clear();
		}
		//writes the items of the file to the hash map
		try (Scanner in = new Scanner(file)) {
			//read the headers. If they are formatted wrong then immediately throw error and stop.
			headers = validateHeader(in.nextLine());
			//read body. will skip improperly formatted lines.
			while (in.hasNextLine()) {
				try {
					addStopTime(validateData(in.nextLine(), headers));
				} catch (IllegalArgumentException e) {
					wasLineSkipped = true;
				}
			}
		} catch (IllegalArgumentException e) {
			wasFileLoaded = false;
			failMessage = String.format("  ERROR: StopTimes Not Imported\n  File Contains Invalid Header Format\n  %s\n", e.getMessage());
		}
		String successMessage = String.format("  ???: StopTimes Imported Successfully.\n  %s\n  %s\n", emptyPrior ? "New StopTimes Data Imported" : "StopTimes Data Overwritten", wasLineSkipped ? "Lines Skipped During Import Of StopTimes" : "All Lines Imported Successfully");
		return String.format("IMPORT STOP_TIMES:\n%s", wasFileLoaded ? successMessage : failMessage);
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
					" values for a StopTime object. Header was missing stop_sequence");
		} else if (header.endsWith(",")) {
			throw new IllegalArgumentException("Input header line cannot contain blank fields");
		}
		Headers headers = new Headers();
		String regex = ",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)";
		String[] headerDataArray = header.split(regex, -1);
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
		String regex = ",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)";
		String[] dataArray = data.split(regex, -1);
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
	//endregion
}//end StopTimes