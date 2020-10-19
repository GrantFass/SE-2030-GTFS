package SE2030TransitProject;


import javafx.scene.control.TextArea;

import java.io.*;
import java.net.URL;
import java.util.*;
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
	 * @author Joy Cross
	 * @param file the stops.txt file to be parsed
	 * @return true if a line was skipped while loading, false otherwise
	 * @throws FileNotFoundException if the file was not found
	 * @throws IOException for general File IO errors.
	 * @throws InputMismatchException if there is an issue parsing the file
	 * @throws DataFormatException if data will be overwritten
	 * @author Joy Cross, Grant Fass
	 */
	public boolean loadStops(File file) throws IOException, DataFormatException {
		try {
			boolean emptyBefore = stops.isEmpty();
			clearStops();
			Scanner sc = new Scanner(file);
			String line = sc.nextLine();
			String[] headersData = line.split(",");
			Headers headers = new Headers();

			// loop to find location of all headers to create stop
			for (int i = 0; i < headersData.length; i++) {
				switch (headersData[i].trim().toLowerCase()) {
					case "stop_id":
						headers.addHeader(new Header("stop_id", i));
						break;
					case "level_id":
						headers.addHeader(new Header("level_id", i));
						break;
					case "location_type":
						headers.addHeader(new Header("location_type", i));
						break;
					case "parent_station":
						headers.addHeader(new Header("parent_station", i));
						break;
					case "platform_code":
						headers.addHeader(new Header("platform_code", i));
						break;
					case "stop_code":
						headers.addHeader(new Header("stop_code", i));
						break;
					case "stop_description":
					case "stop_desc":
						headers.addHeader(new Header("stop_desc", i));
						break;
					case "stop_latitude":
					case "stop_lat":
						headers.addHeader(new Header("stop_lat", i));
						break;
					case "stop_longitude":
					case "stop_lon":
						headers.addHeader(new Header("stop_lon", i));
						break;
					case "stop_name":
						headers.addHeader(new Header("stop_name", i));
						break;
					case "stop_timezone":
						headers.addHeader(new Header("stop_timezone", i));
						break;
					case "stop_url":
						headers.addHeader(new Header("stop_url", i));
						break;
					case "wheelchair_boarding":
						headers.addHeader(new Header("wheelchair_boarding", i));
						break;
				}
			}

			// loop through file
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				String[] data = line.split(",");

				// create and initialize stop object
				Stop stop;
				if (headers.getHeaderIndex("stop_id") != -1) {
					stop = new Stop(data[headers.getHeaderIndex("stop_id")]);
				} else {
					throw new IOException("No Stop ID given");
				}

				try {
				// setting attributes, default values if not in file
				if (headers.getHeaderIndex("level_id") != -1) {
					stop.setLevelID(data[headers.getHeaderIndex("level_id")]);
				}
				if (headers.getHeaderIndex("stop_name") != -1) {
					stop.setStopName(data[headers.getHeaderIndex("stop_name")]);
				}
				if (headers.getHeaderIndex("parent_station") != -1) {
					stop.setParentStation(data[headers.getHeaderIndex("parent_station")]);
				}
				if (headers.getHeaderIndex("platform_code") != -1) {
					stop.setPlatformCode(data[headers.getHeaderIndex("platform_code")]);
				}
				if (headers.getHeaderIndex("stop_code") != -1) {
					stop.setStopCode(data[headers.getHeaderIndex("stop_code")]);
				}
				if (headers.getHeaderIndex("stop_desc") != -1) {
					stop.setStopDescription(data[headers.getHeaderIndex("stop_desc")]);
				}
				if (headers.getHeaderIndex("stop_lat") != -1) {
					stop.setStopLatitude(Double.parseDouble(data[headers.getHeaderIndex("stop_lat")].trim()));
				}
				if (headers.getHeaderIndex("stop_lon") != -1) {
					stop.setStopLongitude(Double.parseDouble(data[headers.getHeaderIndex("stop_lon")].trim()));
				}
				// IMPLEMENT FOR LATER
				/*
				if(headers.getHeaderIndex("stop_timezone") != -1){
					TimeZone time;
					stop.setStopTimezone(time.data[headers.getHeaderIndex("stop_timezone")]);
				}
				if(headers.getHeaderIndex("stop_url") != -1){
					stop.setStopURL(data[headers.getHeaderIndex("stop_url")]);
				}
				if(headers.getHeaderIndex("wheelchair_boarding") != -1){
					stop.setWheelchairBoarding(data[headers.getHeaderIndex("wheelchair_boarding")]);
				}
				if(headers.getHeaderIndex("location_type") != -1){
					stop.setLocationType(data[headers.getHeaderIndex("location_type")]);
				}
				*/

				addStop(stop.getStopID(), stop);
				} catch (Exception e){
					// Error handling for later, right now will skip corrupted data
				}
			}

			if(!emptyBefore){
				throw new DataFormatException(file.getName());
			}
		} catch (DataFormatException dfe){
			throw new DataFormatException(dfe.getMessage());
		}
		//TODO:  true if a line was skipped while loading, false otherwise
		return true;
	}

	public boolean exportStops(File file) throws IOException{
		try {
			File outputLocation = new File(file, "stops.txt");
			BufferedWriter output = new BufferedWriter(new FileWriter(outputLocation));

			for(String key : stops.keySet()){
				Stop stop = stops.get(key);

			}
			output.close();
		} catch (Exception e){
			// pass
		}
		return true;
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

}//end Stops