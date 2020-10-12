package SE2030TransitProject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Class for a StopTime object, which is stop trip relationship for routes
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

	public Timestamp getArrivalTime(){
		return arrival_time;
	}

	public ContinuousDropOffEnum getContinuousDropOff(){
		return continuous_drop_off;
	}

	public ContinuousPickupEnum getContinuousPickup(){
		return continuous_pickup;
	}

	public Timestamp getDepartureTime(){
		return departure_time;
	}

	public DropOffTypeEnum getDropOffType(){
		return drop_off_type;
	}

	public PickupTypeEnum getPickupType(){
		return pickup_type;
	}

	public float getShapeDistTraveled(){
		return shape_dist_traveled;
	}

	public String getStopHeadsign(){
		return stop_headsign;
	}

	public String getStopID(){
		return stop_id;
	}

	public int getStopSequence(){
		return stop_sequence;
	}

	public TimepointEnum getTimepoint(){
		return timepoint;
	}

	public String getTripID(){
		return trip_id;
	}

	public void setArrivalTime(Timestamp arrival_time){
		this.arrival_time = arrival_time;
	}

	public void setContinuousDropOff(ContinuousDropOffEnum continuous_drop_off){
		this.continuous_drop_off = continuous_drop_off;
	}

	public void setContinuousPickup(ContinuousPickupEnum continuous_pickup){
		this.continuous_pickup = continuous_pickup;
	}

	public void setDepartureTime(Timestamp departure_time){
		this.departure_time = departure_time;
	}

	public void setDropOffType(DropOffTypeEnum drop_off_type){
		this.drop_off_type = drop_off_type;
	}

	public void setPickupType(PickupTypeEnum pickup_type){
		this.pickup_type = pickup_type;
	}

	public void setShapeDistTraveled(float shape_dist_traveled){
		this.shape_dist_traveled = shape_dist_traveled;
	}

	public void setStopHeadsign(String stop_headsign){
		this.stop_headsign = stop_headsign;
	}

	public void setStopID(String stop_id){
		this.stop_id = stop_id;
	}

	public void setStopSequence(int stop_sequence){
		this.stop_sequence = stop_sequence;
	}

	public void setTimepoint(TimepointEnum timepoint){
		this.timepoint = timepoint;
	}

	public void setTripID(String trip_id){
		this.trip_id = trip_id;
	}

	/**
	 * Method to output data as a single concatenated string for StopTime
	 * @author Joy Cross,
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