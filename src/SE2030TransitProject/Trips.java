package SE2030TransitProject;

import javafx.scene.control.TextArea;

import java.io.*;
import java.nio.file.Path;
import java.text.ParseException;
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
     *
     * @author Simon Erickson
     */
    public Trips() {
        trips = new HashMap<String, Trip>();
    }

    /**
     * adds trip parameter to trips hash map with the trip_id of trip as the key,
     * and trip as the value.
     *
     * @param trip Trip object to be added to trips
     * @return true if new trip was added, false otherwise
     * @author Simon Erickson
     */
    public boolean addTrip(Trip trip) {
        Trip tripAdded = trips.put(trip.getTripID(), trip);
        boolean added = false;
        if (tripAdded == null) {
            added = true;
        }
        return added;
    }

    /**
     * @param trip_id string identifying requested trip
     * @return trip associated with the specified trip_id
     * @author Simon Erickson
     */
    public Trip getTrip(String trip_id) {
        return trips.get(trip_id);
    }

    /**
     * Removes specified Trip object from trips
     *
     * @param trip Trip to be removed
     * @return true if deleted, false otherwise
     * @author Simon Erickson
     */
    public boolean removeTrip(Trip trip) {
        Trip tripRemoved = trips.remove(trip.getTripID());
        boolean deleted = false;
        if (tripRemoved != null) {
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
    public boolean loadTrips(File file) throws FileNotFoundException,
            InputMismatchException, DataFormatException, ParseException {

        //writes the items of the file to the hash map
        try (Scanner in = new Scanner(file)) {

            //checks to see if trips was empty
            boolean emptyPrior = trips.isEmpty();

            //clears trips hash map
            trips = new HashMap<>();

            //read header: route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id
            validateHeader(in.nextLine());

            //read body
            int i = 0;
            while (in.hasNextLine()) {
                String[] line = in.nextLine().split(",");

                //trip data given from file
                String route_id = line[0];
                String service_id = line[1];
                String trip_id = line[2];
                String trip_headsign = line[3];
                DirectionIDEnum direction_id;
                if (line[4].equals("0")) {
                    direction_id = DirectionIDEnum.OUTBOUND_TRAVEL;
                } else {
                    direction_id = DirectionIDEnum.INBOUND_TRAVEL;
                }
                String block_id = line[5];
                String shape_id = line[6];

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

            if (!emptyPrior) {
                throw new DataFormatException(file.getName());
            }
        } catch (DataFormatException dfe) {
            throw new DataFormatException(dfe.getMessage());
        }
        return true;
    }

    /**
     * Method to write Trip data to a trips.txt file
     *
     * @param path the trips.txt file desired location
     * @return true if file was written successfully, false otherwise
     * @author Simon Erickson
     */
    public boolean exportTrips(Path path) throws FileNotFoundException {

        //writes the items of the file to the hash map
        try (PrintWriter write = new PrintWriter(new BufferedOutputStream(new FileOutputStream(String.valueOf(path))))) {

            //write header: route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id
            write.println("route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id");

            //write body
            int i = 0;
            for (String key : trips.keySet()) {
                if (i == 7) {
                    write.print(trips.get(key) + "\n");
                    i = 0;
                } else {
                    write.print(trips.get(key) + ",");
                    i++;
                }
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
        for (String key : trips.keySet()) {
            toReturn.append(trips.get(key).toString() + "\n");
        }
        return toReturn.toString();
    }

    /**
     * Checks to confirm that the header is valid and matches an expected format
     *
     * @param header the header text line to validate
     * @return a Header object containing the ordering of the headers
     * @throws ParseException if the header does not match the expected format
     * @author GrantFass, Simon Erickson
     */
    public Header validateHeader(String header) throws ParseException {
        Header tripHeader;
        if (header.equals("route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id")) {
            tripHeader = new Header("Trip Header", 0);
        } else {
            throw new ParseException("Invalid Trip Header", 1);
        }
        return tripHeader;
    }

    /**
     * Method to print all of the individual trip objects to the textArea
     *
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