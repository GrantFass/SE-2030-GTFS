package SE2030TransitProject;


import javafx.scene.input.DataFormat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Class for a StopTimes database, which is the main database storing all stopTime objects
 * @author Joy Cross
 * @version 1.0
 */
public class StopTimes {

	private HashMap<String, StopTime> stop_times;

	/**
	 * StopTimes Constructor
	 */
	public StopTimes(){
		stop_times = new HashMap<String, StopTime>();
	}

	/**
	 * Adds a StopTime object to the hashmap, returns false if could not be added to hashmap
	 * Key is in format "stopid;tripid"
	 * @param stop_id stop id associated with stoptime
	 * @param trip_id trip id associated with stoptime
	 * @param stopTime stopTime to be added
	 * @return true if added correctly
	 */
	public boolean addStopTime(String stop_id, String trip_id, StopTime stopTime){
		StopTime stopTimeAdded = stop_times.put(stop_id + ";" + trip_id,stopTime);
		boolean added = false;
		if(stopTimeAdded != null){
			added = true;
		}
		return added;
	}

	/**
	 * Gets the stop time from the hashmap by "stop_id;route_id"
	 * @param stop_id stop_id associated with stoptime
	 * @param trip_id trip_id associated with stoptime
	 * @return StopTime object related to stop_id and trip_id
	 */
	public StopTime getStopTime(String stop_id, String trip_id){
		return stop_times.get(stop_id + ";" + trip_id);
	}

	/**
	 * Removes stop from data
	 * @param stop_id stop_id associated with stoptime
	 * @param trip_id trip_id associated with stoptime
	 * @return true if removal was completed
	 */
	public boolean removeStopTIme(String stop_id, String trip_id){
		StopTime stopTimeDeleted = stop_times.remove(stop_id + ";" + trip_id);
		boolean delete = false;
		if(stopTimeDeleted == null){
			delete = true;
		}
		return delete;
	}

	/**
	 * Method to parse StopTime data from a stop_times.txt file
	 * @author Joy Cross,
	 * @param file the stop_times.txt file to be parsed
	 * @return true if file was loaded, false otherwise
	 * @throws FileNotFoundException if the file was not found
	 * @throws IOException for general File IO errors.
	 * @throws InputMismatchException if there is an issue parsing the file
	 */
	public boolean loadStopTimes(File file) throws FileNotFoundException, IOException,
			InputMismatchException {
		try {
			Scanner sc = new Scanner(file);
			String line = sc.nextLine();
			String[] headers = line.split(",");

			// Finding location of each parameter in headers list
			int arrival_time_array = -1;
			int continuous_drop_off_array = -1;
			int continuous_pickup_array = -1;
			int departure_time_array = -1;
			int drop_off_type_array = -1;
			int pickup_type_array = -1;
			int shape_dist_traveled_array = -1;
			int stop_headsign_array = -1;
			int stop_id_array = -1;
			int stop_sequence_array = -1;
			int timepoint_array = -1;
			int trip_id_array = -1;
			int m_PickupTypeEnum_array = -1;
			int m_DropOffTypeEnum_array = -1;
			int m_TimepointEnum_array = -1;
			int m_ContinuousPickupEnum_array = -1;
			int m_ContinuousDropOffEnum_array = -1;
			Timestamp arrival_time = null;
			Timestamp departure_time = null;
			float shape_dist_traveled = 0;
			String stop_headsign = null;
			String stop_id = null;
			int stop_sequence = 0;
			TimepointEnum timepoint = null;
			String trip_id = null;
			DropOffTypeEnum drop_off_type = null;
			PickupTypeEnum pickup_type = null;
			ContinuousDropOffEnum continuous_drop_off = null;
			ContinuousPickupEnum continuous_pickup = null;
			PickupTypeEnum m_PickupTypeEnum = null;
			DropOffTypeEnum m_DropOffTypeEnum = null;
			TimepointEnum m_TimepointEnum = null;
			ContinuousPickupEnum m_ContinuousPickupEnum = null;
			ContinuousDropOffEnum m_ContinuousDropOffEnum = null;


			// loop to find location of all headers to create stop
			for (int i = 0; i < headers.length; i++) {
				switch (headers[i].trim().toLowerCase()) {
					case "stop_id":
						stop_id_array = i;
						break;
					case "arrival_time":
						arrival_time_array = i;
						break;
					case "trip_id":
						trip_id_array = i;
						break;
					case "continuous_drop_off":
						continuous_drop_off_array = i;
						break;
					case "continuous_pickup":
						continuous_pickup_array = i;
						break;
					case "departure_time":
						departure_time_array = i;
						break;
					case "drop_off_type":
						drop_off_type_array = i;
						break;
					case "pickup_type":
						pickup_type_array = i;
						break;
					case "shape_dist_traveled":
						shape_dist_traveled_array = i;
						break;
					case "stop_headsign":
						stop_headsign_array = i;
						break;
					case "stop_sequence":
						stop_sequence_array = i;
						break;
					case "timepoint":
						timepoint_array = i;
						break;
					case "m_PickupTypeEnum":
						m_PickupTypeEnum_array = i;
						break;
					case "m_DropOffTypeEnum":
						m_DropOffTypeEnum_array = i;
						break;
					case "m_TimepointEnum":
						m_TimepointEnum_array = i;
						break;
					case "m_ContinuousPickupEnum":
						m_ContinuousPickupEnum_array = i;
						break;
					case "m_ContinuousDropOffEnum":
						m_ContinuousDropOffEnum_array = i;
						break;
				}
			}

			// loop through file
			SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				String[] data = line.split(",");
				try {
					// setting attributes, default values if not in file
					if (stop_id_array != -1) {
						stop_id = data[stop_id_array];
					}
					if (trip_id_array != -1 && stop_id_array != -1) {
						trip_id = data[trip_id_array];
					} else {
						throw new IOException("No Stop ID/Route ID given");
					}
					if (arrival_time_array != -1) {
						try {
							arrival_time = new Timestamp(df.parse(data[arrival_time_array]).getTime());
						} catch (ParseException parse) {
							throw new IOException("Arrival Time not correct format should be hh:mm:ss");
						}
					}
					if (departure_time_array != -1) {
						try {
							departure_time = new Timestamp(df.parse(data[departure_time_array]).getTime());
						} catch (ParseException parse) {
							throw new IOException("Departure Time not correct format should be hh:mm:ss");
						}
					}
					if (drop_off_type_array != -1) {
						try {
							int drop_off_type_int = Integer.parseInt(data[drop_off_type_array]);
							if (drop_off_type_int == 3) {
								drop_off_type = DropOffTypeEnum.COORDINATE_WITH_DRIVER_FOR_DROP_OFF;
							} else if (drop_off_type_int == 2) {
								drop_off_type = DropOffTypeEnum.PHONE_AGENCY_FOR_DROP_OFF;
							} else if (drop_off_type_int == 1) {
								drop_off_type = DropOffTypeEnum.NO_DROP_OFF_AVAILABLE;
							} else if (drop_off_type_int == 0) {
								drop_off_type = DropOffTypeEnum.REGULARLY_SCHEDULED_DROP_OFF;
							} else {
								throw new IOException("Drop off type should be between 0-3");
							}
						} catch (NumberFormatException number) {
							throw new IOException("Stop Sequence not correct data should be integer");
						}
					}
					if (pickup_type_array != -1) {
						try {
							int pickup_type_int = Integer.parseInt(data[pickup_type_array]);
							if (pickup_type_int == 3) {
								pickup_type = PickupTypeEnum.COORDINATE_WITH_DRIVER_FOR_PICKUP;
							} else if (pickup_type_int == 2) {
								pickup_type = PickupTypeEnum.PHONE_AGENCY_FOR_PICKUP;
							} else if (pickup_type_int == 1) {
								pickup_type = PickupTypeEnum.NO_PICKUP_AVAILABLE;
							} else if (pickup_type_int == 0) {
								pickup_type = PickupTypeEnum.REGULARLY_SCHEDULED_PICKUP;
							} else {
								throw new IOException("Drop off type should be between 0-3");
							}
						} catch (NumberFormatException number) {
							throw new IOException("Stop Sequence not correct data should be integer");
						}
					}
					if (stop_sequence_array != -1) {
						try {
							stop_sequence = Integer.parseInt(data[stop_sequence_array]);
						} catch (NumberFormatException number) {
							throw new IOException("Stop Sequence not correct data should be integer");
						}
					}
					if (stop_headsign_array != -1) {
						stop_headsign = data[stop_headsign_array];
					}
					// IMPLEMENT FOR LATER
					/*
					if (timepoint_array != -1) {
						timepoint = data[timepoint_array];
					}
					if (continuous_pickup_array != -1) {
						continuous_pickup = data[continuous_pickup_array];
					}
					if (shape_dist_traveled_array != -1) {
						shape_dist_traveled = data[shape_dist_traveled_array];
					}
					if (m_PickupTypeEnum_array != -1) {
						m_PickupTypeEnum = data[m_PickupTypeEnum_array];
					}
					if (m_DropOffTypeEnum_array != -1) {
						m_DropOffTypeEnum = data[m_DropOffTypeEnum_array];
					}
					if (m_TimepointEnum_array != -1) {
						m_TimepointEnum = data[m_TimepointEnum_array]
					}
					if(m_ContinuousPickupEnum_array != -1){
						TimeZone time;
						m_ContinuousPickupEnum = time.data[m_ContinuousPickupEnum_array];
					}
					if(m_ContinuousDropOffEnum_array != -1){
						m_ContinuousDropOffEnum = data[m_ContinuousDropOffEnum_array];
					}
					*/

					StopTime stopTime = new StopTime(arrival_time, continuous_drop_off, continuous_pickup,
							departure_time, drop_off_type, pickup_type, shape_dist_traveled, stop_headsign, stop_id,
							stop_sequence, timepoint, trip_id);

					addStopTime(stop_id, trip_id, stopTime);
				} catch (IOException e){
					// Error handling for later, right now will skip corrupted data
				}

			}

		} catch (FileNotFoundException fnfe){
			return false;
		} catch (InputMismatchException ime){
			return false;
		}
		/*
		catch (IOException ioe){
			return false;
		}
		*/

		return true;
	}

	/**
	 * Method to output data as a single concatenated string
	 * @author Joy Cross,
	 * @return string of data
	 */
	@Override
	public String toString() {
		StringBuilder toReturn = new StringBuilder();
		toReturn.append("StopTimes\n");
		for(String key : stop_times.keySet()){
			StopTime stopTime = stop_times.get(key);
			toReturn.append(stopTime.toString()).append("\n");
		}
		return toReturn.toString();
	}
}//end StopTimes