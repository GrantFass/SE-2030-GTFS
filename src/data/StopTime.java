package data;

import enumerators.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

/**
 * Class for a StopTime object, which is a specific stop <-> trip relationship for a route
 * @author Joy Cross
 * @version 1.0
 */
public class StopTime {

	private Timestamp arrival_time;
	private ContinuousDropOffEnum continuous_drop_off;
	private ContinuousPickupEnum continuous_pickup;
	private Timestamp departure_time;
	private DropOffTypeEnum drop_off_type;
	private PickupTypeEnum pickup_type;
	private float shape_dist_traveled;
	private String stop_headsign;
	private String stop_id;
	private int stop_sequence;
	private TimepointEnum timepoint;
	private String trip_id;

	/**
	 * StopTime Constructor
	 * @author Joy Cross, Grant Fass
	 * @param arrival_time arrival time of a specific stop on a specific trip on a route
	 * @param continuous_drop_off indicates whether a rider can get off the transit vehicle at any point in the trip
	 * @param continuous_pickup indicates whether a rider can get on the transit vehicle at any point in the trip
	 * @param departure_time departure time of stopTime
	 * @param drop_off_type indicates dropoff method
	 * @param pickup_type indicates pickup method
	 * @param shape_dist_traveled actual distance traveled along the associated shape
	 * @param stop_headsign text that appears to riders identifying trip destination
	 * @param stop_id id of the specific stop
	 * @param stop_sequence order of stop for a particular trip
	 * @param timepoint indicates whether the times are approximate or exact
	 * @param trip_id id of the specific trip
	 * @throws IllegalArgumentException if there was an issue parsing a String or if a required field is empty
	 */
	public StopTime(String arrival_time, String continuous_drop_off, String continuous_pickup,
					String departure_time, String drop_off_type, String pickup_type,
					String shape_dist_traveled, String stop_headsign, String stop_id, String stop_sequence,
					String timepoint, String trip_id) throws IllegalArgumentException {
		//stop_id & trip_id are required so error if they are empty
		if (stop_id.isEmpty() || trip_id.isEmpty()) {
			throw new IllegalArgumentException("Line in 'stop_times.txt' file not formatted" +
					" correctly. Skipping!");
		}
		this.stop_headsign = stop_headsign; //Optional and does not throw error if empty
		this.stop_id = stop_id;
		this.trip_id = trip_id;
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		try {
			if (arrival_time.isEmpty() && !departure_time.isEmpty()) {
				this.departure_time = new Timestamp(dateFormat.parse(departure_time).getTime());
				this.arrival_time = this.departure_time;
			} else if (!arrival_time.isEmpty() && departure_time.isEmpty()) {
				this.arrival_time = new Timestamp(dateFormat.parse(arrival_time).getTime());
				this.departure_time = this.arrival_time;
			} else if (arrival_time.isEmpty()) {
				//if both are empty
				throw new IllegalArgumentException("Both arrival time and departure time are empty!");
			} else {
				//neither are empty
				this.arrival_time = new Timestamp(dateFormat.parse(arrival_time).getTime());
				this.departure_time = new Timestamp(dateFormat.parse(departure_time).getTime());
			}
			this.stop_sequence = Integer.parseInt(stop_sequence); //required so should throw error if it is missing
			if (this.stop_sequence < 0) {
				throw new IllegalArgumentException("stop_sequence value must be a non-negative integer");
			}
			this.shape_dist_traveled = !shape_dist_traveled.isEmpty() ? Float.parseFloat(shape_dist_traveled) : 0;
			if (this.shape_dist_traveled < 0) {
				this.shape_dist_traveled = 0;
			}
			//Set enumerator values
			//default values are applied if empty.
			this.continuous_drop_off = ContinuousDropOffEnum.getValue(!continuous_drop_off.isEmpty() ? Integer.parseInt(continuous_drop_off) : -1);
			this.continuous_pickup = ContinuousPickupEnum.getValue(!continuous_pickup.isEmpty() ? Integer.parseInt(continuous_pickup) : -1);
			this.drop_off_type = DropOffTypeEnum.getValue(!drop_off_type.isEmpty() ? Integer.parseInt(drop_off_type) : -1);
			this.pickup_type = PickupTypeEnum.getValue(!pickup_type.isEmpty() ? Integer.parseInt(pickup_type) : -1);
			this.timepoint = TimepointEnum.getValue(!timepoint.isEmpty() ? Integer.parseInt(timepoint) : -1);
		} catch (ParseException parse) {
			throw new IllegalArgumentException("Line in 'stop_times.txt' file not formatted" +
					" correctly. Skipping!");
		}
	}

	/**
	 * @author Joy Cross
	 */
	public Timestamp getArrivalTime(){
		return arrival_time;
	}

	/**
	 * @author Joy Cross
	 */
	public ContinuousDropOffEnum getContinuousDropOff(){
		return continuous_drop_off;
	}

	/**
	 * @author Joy Cross
	 */
	public ContinuousPickupEnum getContinuousPickup(){
		return continuous_pickup;
	}

	/**
	 * @author Joy Cross
	 */
	public Timestamp getDepartureTime(){
		return departure_time;
	}

	/**
	 * @author Joy Cross
	 */
	public DropOffTypeEnum getDropOffType(){
		return drop_off_type;
	}

	/**
	 * @author Joy Cross
	 */
	public PickupTypeEnum getPickupType(){
		return pickup_type;
	}

	/**
	 * @author Joy Cross
	 */
	public float getShapeDistTraveled(){
		return shape_dist_traveled;
	}

	/**
	 * @author Joy Cross
	 */
	public String getStopHeadsign(){
		return stop_headsign;
	}

	/**
	 * @author Joy Cross
	 */
	public String getStopID(){
		return stop_id;
	}

	/**
	 * @author Joy Cross
	 */
	public int getStopSequence(){
		return stop_sequence;
	}

	/**
	 * @author Joy Cross
	 */
	public TimepointEnum getTimepoint(){
		return timepoint;
	}

	/**
	 * @author Joy Cross
	 */
	public String getTripID(){
		return trip_id;
	}

//	/**
//	 * @author Joy Cross
//	 */
//	public void setArrivalTime(Timestamp arrival_time){
//		this.arrival_time = arrival_time;
//	}
//
//	/**
//	 * @author Joy Cross
//	 */
//	public void setContinuousDropOff(ContinuousDropOffEnum continuous_drop_off){
//		this.continuous_drop_off = continuous_drop_off;
//	}
//
//	/**
//	 * @author Joy Cross
//	 */
//	public void setContinuousPickup(ContinuousPickupEnum continuous_pickup){
//		this.continuous_pickup = continuous_pickup;
//	}
//
//	/**
//	 * @author Joy Cross
//	 */
//	public void setDepartureTime(Timestamp departure_time){
//		this.departure_time = departure_time;
//	}
//
//	/**
//	 * @author Joy Cross
//	 */
//	public void setDropOffType(DropOffTypeEnum drop_off_type){
//		this.drop_off_type = drop_off_type;
//	}
//
//	/**
//	 * @author Joy Cross
//	 */
//	public void setPickupType(PickupTypeEnum pickup_type){
//		this.pickup_type = pickup_type;
//	}
//
//	/**
//	 * @author Joy Cross
//	 */
//	public void setShapeDistTraveled(float shape_dist_traveled){
//		this.shape_dist_traveled = shape_dist_traveled;
//	}
//
//	/**
//	 * @author Joy Cross
//	 */
//	public void setStopHeadsign(String stop_headsign){
//		this.stop_headsign = stop_headsign;
//	}
//
//	/**
//	 * @author Joy Cross
//	 */
//	public void setStopID(String stop_id){
//		this.stop_id = stop_id;
//	}
//
//	/**
//	 * @author Joy Cross
//	 */
//	public void setStopSequence(int stop_sequence){
//		this.stop_sequence = stop_sequence;
//	}
//
//	/**
//	 * @author Joy Cross
//	 */
//	public void setTimepoint(TimepointEnum timepoint){
//		this.timepoint = timepoint;
//	}
//
//	/**
//	 * @author Joy Cross
//	 */
//	public void setTripID(String trip_id){
//		this.trip_id = trip_id;
//	}

	/**
	 * Method to output data as a single concatenated string for StopTime
	 * @author Joy Cross, Grant Fass
	 * @return string of data
	 */
	@Override
	public String toString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		return String.format("Trip ID: %s | Stop ID: %s \n\t" +
				"Arrival Time: %s\n\t" +
				"Departure Time: %s\n\t" +
				"Continuous Drop Off: %s\n\t" +
				"Continuous Pickup: %s\n\t" +
				"Drop Off Type: %s\n\t" +
				"Pickup Type: %s\n\t" +
				"Shape Distance Traveled: %s\n\t" +
				"Stop Headsign: %s\n\t" +
				"Stop Sequence: %s\n\t" +
				"Timepoint: %s\n", trip_id, stop_id, dateFormat.format(arrival_time),
				dateFormat.format(departure_time), continuous_drop_off, continuous_pickup,
				drop_off_type.toString(), pickup_type.toString(), shape_dist_traveled,
				stop_headsign, stop_sequence, timepoint);
	}

	/**
	 * output simplified data as a single concatenated string
	 * @return string of data
	 * @author Grant Fass
	 */
	public String toSimpleString() {
		return String.format("Trip ID: %s | Stop ID: %s | Stop Sequence: %s\n", trip_id, stop_id, stop_sequence);
	}

	/**
	 * gets the file headers
	 * used for exporting stop times
	 * @return file headers in a single line header format
	 * @author Grant Fass
	 */
	public static String getHeaderLine() {
		return "trip_id,stop_id,stop_sequence,arrival_time,departure_time,continuous_drop_off," +
				"continuous_pickup,drop_off_type,pickup_type," +
				"shape_distance,stop_headsign,timepoint\n";
	}

	/**
	 * gets the data line for a single stop
	 * used for exporting stop times
	 * @return stop time data in a single line data format
	 * @author Grant Fass
	 */
	public String getDataLine() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n", trip_id, stop_id, stop_sequence,
				dateFormat.format(arrival_time), dateFormat.format(departure_time), continuous_drop_off.getValue(), continuous_pickup.getValue(), drop_off_type.getValue(),
				pickup_type.getValue(), shape_dist_traveled, stop_headsign, timepoint.getValue());
	}

	/**
	 * equals method override
	 * @param o the object to compare to the current object
	 * @return if the objects are equal
	 * @author Grant Fass
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		StopTime stopTime = (StopTime) o;
		return Float.compare(stopTime.shape_dist_traveled, shape_dist_traveled) == 0 &&
				stop_sequence == stopTime.stop_sequence &&
				Objects.equals(arrival_time, stopTime.arrival_time) &&
				continuous_drop_off == stopTime.continuous_drop_off &&
				continuous_pickup == stopTime.continuous_pickup &&
				Objects.equals(departure_time, stopTime.departure_time) &&
				drop_off_type == stopTime.drop_off_type &&
				pickup_type == stopTime.pickup_type &&
				Objects.equals(stop_headsign, stopTime.stop_headsign) &&
				stop_id.equals(stopTime.stop_id) &&
				timepoint == stopTime.timepoint &&
				trip_id.equals(stopTime.trip_id);
	}

	/**
	 * hashCode method override
	 * @return the hashcode generated from the object
	 * @author Grant Fass
	 */
	@Override
	public int hashCode() {
		return Objects.hash(arrival_time, continuous_drop_off, continuous_pickup, departure_time, drop_off_type, pickup_type, shape_dist_traveled, stop_headsign, stop_id, stop_sequence, timepoint, trip_id);
	}
}//end StopTime