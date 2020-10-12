package SE2030TransitProject;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
	 * @return true if file was loaded, false otherwise
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
			String[] headers = line.split(",");

			// Finding location of each parameter in headers list
			int stop_id_array = -1;
			int level_id_array = -1;
			int location_type_array = -1;
			int parent_station_array = -1;
			int platform_code_array = -1;
			int stop_code_array = -1;
			int stop_description_array = -1;
			int stop_latitude_array = -1;
			int stop_longitude_array = -1;
			int stop_name_array = -1;
			int stop_timezone_array = -1;
			int stop_url_array = -1;
			int wheelchair_boarding_array = -1;
			// int zone_id_array = -1;

			String level_id = null;
			LocationTypeEnum location_type = null;
			String parent_station = null;
			String platform_code = null;
			String stop_code = null;
			String stop_description = null;
			String stop_id = null;
			double stop_latitude = 0;
			double stop_longitude = 0;
			String stop_name = null;
			TimeZone stop_timezone = null;
			URL stop_url = null;
			WheelchairBoardingEnum wheelchair_boarding = null;
			// String zone_id;

			// loop to find location of all headers to create stop
			for (int i = 0; i < headers.length; i++) {
				switch (headers[i].trim().toLowerCase()) {
					case "stop_id":
						stop_id_array = i;
						break;
					case "level_id":
						level_id_array = i;
						break;
					case "location_type":
						location_type_array = i;
						break;
					case "parent_station":
						parent_station_array = i;
						break;
					case "platform_code":
						platform_code_array = i;
						break;
					case "stop_code":
						stop_code_array = i;
						break;
					case "stop_description":
					case "stop_desc":
						stop_description_array = i;
						break;
					case "stop_latitude":
					case "stop_lat":
						stop_latitude_array = i;
						break;
					case "stop_longitude":
					case "stop_lon":
						stop_longitude_array = i;
						break;
					case "stop_name":
						stop_name_array = i;
						break;
					case "stop_timezone":
						stop_timezone_array = i;
						break;
					case "stop_url":
						stop_url_array = i;
						break;
					case "wheelchair_boarding":
						wheelchair_boarding_array = i;
						break;
				}
			}

			// loop through file
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				String[] data = line.split(",");
				try {
				// setting attributes, default values if not in file
				if (stop_id_array != -1) {
					stop_id = data[stop_id_array];
				} else {
					throw new IOException("No Stop ID given");
				}
				if (level_id_array != -1) {
					level_id = data[level_id_array];
				}
				if (stop_name_array != -1) {
					stop_name = data[stop_name_array];
				}
				if (parent_station_array != -1) {
					parent_station = data[parent_station_array];
				}
				if (platform_code_array != -1) {
					platform_code = data[platform_code_array];
				}
				if (stop_code_array != -1) {
					stop_code = data[stop_code_array];
				}
				if (stop_description_array != -1) {
					stop_description = data[stop_description_array];
				}
				if (stop_latitude_array != -1) {
					stop_latitude = Double.parseDouble(data[stop_latitude_array].trim());
				}
				if (stop_longitude_array != -1) {
					stop_longitude = Double.parseDouble(data[stop_longitude_array].trim());
				}
				// IMPLEMENT FOR LATER
				/*
				if(stop_timezone_array != -1){
					TimeZone time;
					stop_timezone = time.data[stop_timezone_array];
				}
				if(stop_url_array != -1){
					stop_url = data[stop_url_array];
				}
				if(wheelchair_boarding_array != -1){
					wheelchair_boarding = data[wheelchair_boarding_array];
				}
				if(location_type_array != -1){
					location_type = data[location_type_array];
				}
				*/

				Stop stop = new Stop(level_id, location_type, parent_station,
						platform_code, stop_code, stop_description, stop_id,
						stop_latitude, stop_longitude, stop_name, stop_timezone,
						stop_url, wheelchair_boarding);

				addStop(stop_id, stop);
				} catch (IOException e){
					// Error handling for later, right now will skip corrupted data
				}
			}

			if(!emptyBefore){
				throw new DataFormatException(file.getName());
			}
		} catch (DataFormatException dfe){
			throw new DataFormatException(dfe.getMessage());
		}
		return true;
	}

	/**
	 * Method to output data as a single concatenated string
	 * @author Joy Cross
	 * @return string of data
	 */
	@Override
	public String toString() {
		StringBuilder toReturn = new StringBuilder();
		toReturn.append("Stops\n");
		for(String key : stops.keySet()){
			Stop stop = stops.get(key);
			toReturn.append(stop.toString()).append("\n");
		}
		return toReturn.toString();
	}
}//end Stops