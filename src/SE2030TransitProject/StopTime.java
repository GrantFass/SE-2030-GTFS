package SE2030TransitProject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

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
	 * @author Joy Cross
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
	 */
	public StopTime(Timestamp arrival_time, ContinuousDropOffEnum continuous_drop_off, ContinuousPickupEnum
					continuous_pickup, Timestamp departure_time, DropOffTypeEnum drop_off_type, PickupTypeEnum
					pickup_type, float shape_dist_traveled, String stop_headsign, String stop_id, int stop_sequence,
					TimepointEnum timepoint, String trip_id){
		this.arrival_time = arrival_time;
		this.continuous_drop_off = continuous_drop_off;
		this.continuous_pickup = continuous_pickup;
		this.departure_time = departure_time;
		this.drop_off_type = drop_off_type;
		this.pickup_type = pickup_type;
		this.shape_dist_traveled = shape_dist_traveled;
		this.stop_headsign = stop_headsign;
		this.stop_id = stop_id;
		this.stop_sequence = stop_sequence;
		this.timepoint = timepoint;
		this.trip_id = trip_id;
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

	/**
	 * @author Joy Cross
	 */
	public void setArrivalTime(Timestamp arrival_time){
		this.arrival_time = arrival_time;
	}

	/**
	 * @author Joy Cross
	 */
	public void setContinuousDropOff(ContinuousDropOffEnum continuous_drop_off){
		this.continuous_drop_off = continuous_drop_off;
	}

	/**
	 * @author Joy Cross
	 */
	public void setContinuousPickup(ContinuousPickupEnum continuous_pickup){
		this.continuous_pickup = continuous_pickup;
	}

	/**
	 * @author Joy Cross
	 */
	public void setDepartureTime(Timestamp departure_time){
		this.departure_time = departure_time;
	}

	/**
	 * @author Joy Cross
	 */
	public void setDropOffType(DropOffTypeEnum drop_off_type){
		this.drop_off_type = drop_off_type;
	}

	/**
	 * @author Joy Cross
	 */
	public void setPickupType(PickupTypeEnum pickup_type){
		this.pickup_type = pickup_type;
	}

	/**
	 * @author Joy Cross
	 */
	public void setShapeDistTraveled(float shape_dist_traveled){
		this.shape_dist_traveled = shape_dist_traveled;
	}

	/**
	 * @author Joy Cross
	 */
	public void setStopHeadsign(String stop_headsign){
		this.stop_headsign = stop_headsign;
	}

	/**
	 * @author Joy Cross
	 */
	public void setStopID(String stop_id){
		this.stop_id = stop_id;
	}

	/**
	 * @author Joy Cross
	 */
	public void setStopSequence(int stop_sequence){
		this.stop_sequence = stop_sequence;
	}

	/**
	 * @author Joy Cross
	 */
	public void setTimepoint(TimepointEnum timepoint){
		this.timepoint = timepoint;
	}

	/**
	 * @author Joy Cross
	 */
	public void setTripID(String trip_id){
		this.trip_id = trip_id;
	}

	/**
	 * Method to output data as a single concatenated string for StopTime
	 * @author Joy Cross
	 * @return string of data
	 */
	@Override
	public String toString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		return "Trip ID: " + trip_id + "; Stop ID: " + stop_id + "\n\tarrival_time: " + dateFormat.format(arrival_time)
				+ "; continuous_drop_off: "	+ continuous_drop_off + "; continuous_pickup: " + continuous_pickup
				+ "; departure_time: " + dateFormat.format(departure_time) + "; drop_off_type: " + drop_off_type.toString()
				+ "; pickup_type: " + pickup_type.toString() + "; shape_dist_traveled: " +	shape_dist_traveled
				+ "; stop_headsign: " + stop_headsign + "; stop_sequence: " + stop_sequence + "; timepoint: "
				+ timepoint;
	}
}//end StopTime