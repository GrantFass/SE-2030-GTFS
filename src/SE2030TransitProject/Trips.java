package SE2030TransitProject;


import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
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
     * @throws DataFormatException if data will be overwritten
     * @author Grant Fass, ericksons
     */
    public boolean loadTrips(File file) throws FileNotFoundException, IOException,
            InputMismatchException, DataFormatException {

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
        /*
        TODO: DataFormatException should be thrown after everything else is done and let the user know that previous data was overwritten
        Note: For the exception text put in the name of the text file ie: stops.txt I will do the rest in the controller
        - Grant
         */
    }

    /**
     * Method to output data as a single concatenated string
     *
     * @return string of data
     * @author GrantFass,
     */
    @Override
    public String toString() {
        String concatenatedString = "";
        Set<String> setKeys = trips.keySet();
        for (String key : setKeys) {
            concatenatedString += key;
        }
        return concatenatedString;
    }
}//end Trips