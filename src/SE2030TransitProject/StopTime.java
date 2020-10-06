/* Joy Cross */

package SE2030TransitProject;


import java.sql.Time;

/**
 * @author ericksons
 * @version 1.0
 * @created 06-Oct-2020 10:28:42 AM
 */
public class StopTime {

	private Time arrival_time;
	private ContinuousDropOffEnum continuous_drop_off;
	private ContinuousPickupEnum continuous_pickup;
	private Time departure_time;
	private DropOffTypeEnum drop_off_type;
	private PickupTypeEnum pickup_type;
	private float shape_dist_traveled;
	private String stop_headsign;
	private String stop_id;
	private int stop_sequence;
	private Enum timepoint;
	private String trip_id;
	public PickupTypeEnum m_PickupTypeEnum;
	public DropOffTypeEnum m_DropOffTypeEnum;
	public TimepointEnum m_TimepointEnum;
	public ContinuousPickupEnum m_ContinuousPickupEnum;
	public ContinuousDropOffEnum m_ContinuousDropOffEnum;

	public StopTime(){

	}

	public void finalize() throws Throwable {

	}
	public Time getArrivalTime(){
		return null;
	}

	public ContinuousDropOffEnum getContinuousDropOff(){
		return null;
	}

	public ContinuousPickupEnum getContinuousPickup(){
		return null;
	}

	public Time getDepartureTime(){
		return null;
	}

	public DropOffTypeEnum getDropOffType(){
		return null;
	}

	public PickupTypeEnum getPickupType(){
		return null;
	}

	public float getShapeDistTraveled(){
		return 0;
	}

	public String getStopHeadsign(){
		return "";
	}

	public String getStopID(){
		return "";
	}

	public int getStopSequence(){
		return 0;
	}

	public TimepointEnum getTimepoint(){
		return null;
	}

	public String getTripID(){
		return "";
	}

	/**
	 * Method to output data as a single concatenated string
	 * @author GrantFass,
	 * @return string of data
	 */
	@Override
	public String toString() {
		return "Method Not Implemented Yet";
	}
}//end StopTime