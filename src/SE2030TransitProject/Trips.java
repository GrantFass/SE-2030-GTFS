package SE2030TransitProject;


import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

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
     * @author Grant Fass,
     */
    public boolean loadTrips(File file) throws FileNotFoundException, IOException,
            InputMismatchException {

        //	Alerts user of overwriting files
        Alert overwriteAlert = new Alert(Alert.AlertType.CONFIRMATION);
        overwriteAlert.setTitle("Overwrite Warning");
        overwriteAlert.setHeaderText("You are about to overwrite all existing trip files");
        overwriteAlert.showAndWait();

        //clears trips hash map
        trips = new HashMap<>();

        //writes the items of the file to the hash map
        try (Scanner in = new Scanner(file)) {

            //read header
            in.nextLine();

            //read body
            while (in.hasNextLine()) {
                in.useDelimiter("\\D*,\\D*");
                trips.put(in.next(), new Trip(BikesAllowedEnum.valueOf(in.next()), in.next(),
                        DirectionIDEnum.valueOf(in.next()), in.next(), in.next(), in.next(),
                        in.next(), in.next(), in.next(),
                        WheelchairAccessibleEnum.valueOf(in.next()),
                        DirectionIDEnum.valueOf(in.next()),
                        WheelchairAccessibleEnum.valueOf(in.next()),
                        BikesAllowedEnum.valueOf(in.next())));
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
        return "Method Not Implemented Yet";
    }
}//end Trips