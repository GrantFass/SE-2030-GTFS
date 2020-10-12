package SE2030TransitProject;


import java.sql.Time;
import java.util.Observable;
import java.util.Observer;

/**
 * @author ericksons
 * @version 1.0
 * @created 06-Oct-2020 10:28:23 AM
 */
public class BusMap implements Observer {

	public Data m_Data;

	public BusMap(){

	}

	/**
	 * 
	 * @param route
	 * @param time
	 */
	public boolean plotBus(Route route, Time time){
		return false;
	}

	public void update(){

	}

	@Override
	public void update(Observable o, Object arg) {

	}
}//end BusMap