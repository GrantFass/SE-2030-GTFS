package SE2030TransitProject;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.zip.DataFormatException;

/**
 * @author ericksons
 * @version 1.0
 * @created 06-Oct-2020 10:28:46 AM
 */
public class Trips {

    private HashMap<String, Trip> trips;

    public Trips() {
        trips = new HashMap<String, Trip>();
    }

    public void finalize() throws Throwable {

    }

    /**
     * @param trip
     */
    public boolean addTrip(Trip trip) {
        trips.put(trip.getRouteID(), trip);
        return true;
    }

    public Trip getTrip(String trip_id) {
        return trips.get(trip_id);
    }

    /**
     * @param trip
     */
    public boolean removeTrip(Trip trip) {
        trips.remove(trip.getTripID());
        return true;
    }

    /**
     * Method to parse Trip data from a trips.txt file
     *
     * @param file the trips.txt file to be parsed
     * @return true if file was loaded, false otherwise
     * @throws FileNotFoundException  if the file was not found
     * @throws IOException            for general File IO errors.
     * @throws InputMismatchException if there is an issue parsing the file
     * @throws DataFormatException    if data will be overwritten
     * @author Grant Fass, ericksons
     */
    public boolean loadTrips(File file) throws FileNotFoundException, IOException,
            InputMismatchException, DataFormatException {

        //clears trips hash map
        trips = new HashMap<>();

        //writes the items of the file to the hash map
        try (Scanner in = new Scanner(file)) {

            //read header: route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id
            in.next("route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id");


            //read body
            while (in.hasNext()) {
                int i = 0;
                in.useDelimiter(",");
                //trip data given from file
                String route_id = in.next();
                String service_id;

                if (in.hasNext()) {
                    service_id = in.next();
                } else {
                    service_id = "";
                }

                String trip_id;
                if (in.hasNext()) {
                    trip_id = in.next();
                } else {
                    trip_id = "";
                }

                String trip_headsign;
                if (in.hasNext()) {
                    trip_headsign = in.next();
                } else {
                    trip_headsign = "";
                }

                DirectionIDEnum direction_id;

                if (in.hasNext()) {
                    String next = in.next();
                    if (next.equals("0")) {
                        direction_id = DirectionIDEnum.OUTBOUND_TRAVEL;
                    } else {
                        direction_id = DirectionIDEnum.INBOUND_TRAVEL;
                    }
                } else {
                    direction_id = DirectionIDEnum.OUTBOUND_TRAVEL;
                }

                String block_id;
                if (in.hasNext()) {
                    block_id = in.next();
                } else {
                    block_id = "";
                }

                String shape_id;
                if (in.hasNext()) {
                    shape_id = in.next();
                } else {
                    shape_id = "";
                }

                //trip data not given by file
                BikesAllowedEnum bikes_allowed = BikesAllowedEnum.NO_INFORMATION;
                String trip_short_name = "Trip " + i;
                i++;
                WheelchairAccessibleEnum wheelchair_accessible =
                        WheelchairAccessibleEnum.NO_ACCESSIBILITY_INFORMATION;

                trips.put(trip_id + ";" + route_id, new Trip(bikes_allowed, block_id, direction_id,
                        route_id, service_id, shape_id, trip_headsign, trip_id, trip_short_name,
                        wheelchair_accessible));
            }
        }
        return true;
    }

    /**
     * Method to output data as a single concatenated string
     *
     * @return string of data
     * @author GrantFass,
     */
    @Override
    public String toString() {
        StringBuilder toReturn = new StringBuilder();
        toReturn.append("Trips\n");
        for (String key : trips.keySet()) {
            Trip trip = trips.get(key);
            toReturn.append(trip.toString()).append("\n");
        }
        return toReturn.toString();
    }

}//end Trips


//throw new data format exception