package SE2030TransitProject;

import javafx.scene.control.TextArea;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.zip.DataFormatException;

/**
 * Class that holds multiple Trips stored within a hash map where a Trip's trip_id is the key,
 * and the Route object is the value for a specific slot within the hash map
 *
 * @author ericksons
 * @version 1.0
 * @created 06-Oct-2020 10:28:39 AM
 */
public class Trips {

    private HashMap<String, Trip> trips;

    /**
     * Trips constructor initialized with empty hash map
     * @author Simon Erickson
     */
    public Trips() {
        trips = new HashMap<String, Trip>();
    }

    /**
     * adds trip parameter to trips hash map with the trip_id of trip as the key, and trip as the value.
     * @author Simon Erickson
     * @param trip Trip object to be added to trips
     * @return true if new trip was added, false otherwise
     */
    public boolean addTrip(Trip trip) {
        Trip tripAdded = trips.put(trip.getTripID(), trip);
        boolean added = false;
        if(tripAdded == null){
            added = true;
        }
        return added;
    }

    /**
     * @author Simon Erickson
     * @param trip_id string identifying requested trip
     * @return trip associated with the specified trip_id
     */
    public Trip getTrip(String trip_id) {
        return trips.get(trip_id);
    }

    /**
     * Removes specified Trip object from trips
     * @author Simon Erickson
     * @param trip Trip to be removed
     * @return true if deleted, false otherwise
     */
    public boolean removeTrip(Trip trip) {
        Trip tripRemoved = trips.remove(trip.getTripID());
        boolean deleted = false;
        if(tripRemoved != null){
            deleted = true;
        }
        return deleted;
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
     * @author Grant Fass, Simon Erickson
     */
    public boolean loadTrips(File file) throws FileNotFoundException, IOException,
            InputMismatchException, DataFormatException {

        //clears trips hash map
        trips = new HashMap<>();

        //writes the items of the file to the hash map
        try (Scanner in = new Scanner(file)) {

            //checks to see if trips was empty
            boolean emptyPrior = trips.isEmpty();

            //read header: route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id
            in.next("route_id,service_id,trip_id,trip_headsign,direction_id," +
                    "block_id,shape_id");

            //read body
            int i = 0;
            while (in.hasNext()) {
                in.useDelimiter(",");

                //trip data given from file
                String route_id = in.next();
                String service_id = checkNext(in);
                String trip_id = checkNext(in);
                String trip_headsign = checkNext(in);
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
                String block_id = checkNext(in);
                String shape_id = checkNext(in);

                //trip data not given by file
                BikesAllowedEnum bikes_allowed = BikesAllowedEnum.NO_INFORMATION;
                String trip_short_name = "Trip " + i;
                i++;
                WheelchairAccessibleEnum wheelchair_accessible =
                        WheelchairAccessibleEnum.NO_ACCESSIBILITY_INFORMATION;

                //assigning variables to trip object
                trips.put(trip_id, new Trip(bikes_allowed, block_id, direction_id,
                        route_id, service_id, shape_id, trip_headsign, trip_id, trip_short_name,
                        wheelchair_accessible));
            }

            if(!emptyPrior){
                throw new DataFormatException(file.getName());
            }
        } catch (DataFormatException dfe){
            throw new DataFormatException(dfe.getMessage());
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
        for (String key : trips.keySet()) {
            toReturn.append(trips.get(key).toString() + "\n");
        }
        return toReturn.toString();
    }

    private String checkNext(Scanner in){
        String returnValue = "";
        if (in.hasNext()) {
            returnValue = in.next();
        }
        return returnValue;
    }

    /**
     * Method to print all of the individual trip objects to the textArea
     * @param textArea the textArea to print the trip objects to
     * @author Grant Fass
     */
    public void printDataToTextArea(TextArea textArea) {
        textArea.clear();
        textArea.setText(toString());
        /*
        for (String key : trips.keySet()) {
            textArea.appendText(trips.get(key).toString() + "\n");
        }

         */
    }


}//end Trips