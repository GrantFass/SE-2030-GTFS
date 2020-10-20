package SE2030TransitProject;

import java.io.*;
import java.nio.file.Path;
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
     * @return true if a line was skipped while loading, false otherwise
     * @throws FileNotFoundException  if the file was not found
     * @throws IOException            for general File IO errors.
     * @throws InputMismatchException if there is an issue parsing the file
     * @throws DataFormatException    if data will be overwritten
     * @author Simon Erickson
     */
    public boolean loadTrips(File file) throws FileNotFoundException,
            InputMismatchException, DataFormatException {

        //checks to see if trips was empty
        boolean emptyPrior = trips.isEmpty();

        //empties List if list was not empty
        if (!emptyPrior) {
            trips.clear();
        }

        //sets initial skip value to false
        boolean wasLineSkipped = false;

        //writes the items of the file to the hash map
        try (Scanner in = new Scanner(file)) {

            //Header object
            Headers headers;
            try {
                headers = validateHeader(in.nextLine());
            } catch (IllegalArgumentException e) {
                throw new IOException("File not read due to invalid headers format");
            }

            //read body
            int i = 0;
            while (in.hasNextLine()) {
                try {
                    Trip trip = validateData(in.nextLine(), headers);
                    addTrip(trip);
                } catch (IllegalArgumentException e) {
                    wasLineSkipped = true;
                }
            }

            if (!emptyPrior) {
                throw new DataFormatException(file.getName());
            }
        } catch (DataFormatException | IOException dfe) {
            throw new DataFormatException(dfe.getMessage());
        }
        return wasLineSkipped;
    }

    /**
     * Method to write Trip data to a trips.txt file
     *
     * @param file the trips.txt file desired location
     * @return true if file was written successfully, false otherwise
     * @throws IOException if something went wrong
     * @author Simon Erickson
     */
    public boolean exportTrips(File file) throws IOException {

        //writes the items of the file to the hash map
        try (PrintWriter write = new PrintWriter(new BufferedOutputStream(new FileOutputStream(new File(file, "trips.txt"))))) {

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
     * @author GrantFass, Simon Erickson
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
     * checks to confirm that the header is valid and matches an expected format
     *
     * @param header the header text line to validate
     * @return a Headers object containing the ordering of the headers
     * @throws IllegalArgumentException if the header does not match the expected format
     * @author Simon Erickson
     */
    public Headers validateHeader(String header) throws IllegalArgumentException {
        header = header.toLowerCase().replace(" ", "");
        if (header.isEmpty()) {
            throw new IllegalArgumentException("Input header line cannot be empty");
        } else if (!header.contains("route_id")) {
            throw new IllegalArgumentException("Input header line must contain all expected" +
                    " values for a Trip object. Header was missing route_id");
        } else if (!header.contains("service_id")) {
            throw new IllegalArgumentException("Input header line must contain all expected" +
                    " values for a Trip object. Header was missing service_id");
        } else if (!header.contains("trip_headsign")) {
            throw new IllegalArgumentException("Input header line must contain all expected" +
                    " values for a Trip object. Header was missing trip_headsign");
        } else if (!header.contains("direction_id")) {
            throw new IllegalArgumentException("Input header line must contain all expected" +
                    " values for a Trip object. Header was missing direction_id");
        } else if (!header.contains("block_id")) {
            throw new IllegalArgumentException("Input header line must contain all expected" +
                    " values for a Trip object. Header was missing block_id");
        } else if (!header.contains("shape_id")) {
            throw new IllegalArgumentException("Input header line must contain all expected" +
                    " values for a Trip object. Header was missing shape_id");
        } else if (header.endsWith(",")) {
            throw new IllegalArgumentException("Input header line cannot contain blank fields");
        }
        Headers headers = new Headers();
        String[] headerDataArray = header.split(",");
        final String possibleHeaders = "route_id,service_id,trip_id,trip_headsign,direction_id," +
                "block_id,shape_id";
        for (int i = 0; i < headerDataArray.length; i++) {
            if (!headerDataArray[i].isEmpty() && possibleHeaders.contains(headerDataArray[i])) {
                headers.addHeader(new Header(headerDataArray[i], i));
            } else if (headerDataArray[i].isEmpty()) {
                throw new IllegalArgumentException("Input header line cannot contain blank fields");
            } else {
                throw new IllegalArgumentException("Header field contains unexpected field: "
                        + headerDataArray[i]);
            }
        }
        return headers;
    }

    /**
     * checks to confirm that a data line is valid and matches
     * the expected format set by the header line
     *
     * @param data    the line of data to parse
     * @param headers the headers values to use to parse the data
     * @return a Trip object constructed from the data
     * @throws IllegalArgumentException if there was an issue
     *                                  parsing or the wrong amount of data was passed
     * @author Simon Erickson
     */
    public Trip validateData(String data, Headers headers) throws IllegalArgumentException {
        String[] dataArray = data.split(",");
        if (dataArray.length != headers.length()) {
            throw new IllegalArgumentException("Data line does not contain the " +
                    "proper amount of data");
        }

        //trip data given from file
        String route_id = setDefaultDataValue(dataArray, headers, "route_id");
        String service_id = setDefaultDataValue(dataArray, headers, "service_id");
        String trip_id = setDefaultDataValue(dataArray, headers, "trip_id");
        String trip_headsign
                = setDefaultDataValue(dataArray, headers, "trip_headsign");
        String direction_id
                = setDefaultDataValue(dataArray, headers, "direction_id");
        String block_id = setDefaultDataValue(dataArray, headers, "block_id");
        String shape_id = setDefaultDataValue(dataArray, headers, "shape_id");

        //trip data not given by file
        String bikes_allowed
                = setDefaultDataValue(dataArray, headers, "bikes_allowed");
        String wheelchair_accessible
                = setDefaultDataValue(dataArray, headers, "wheelchair_accessible");

        //assigning variables to trip object
        return new Trip(bikes_allowed, block_id, direction_id,
                route_id, service_id, shape_id, trip_headsign, trip_id,
                wheelchair_accessible);
    }

    /**
     * Searches for the expectedHeader in the Headers object and will return the associated value
     * if it is found or will return empty String if it was not found.
     *
     * @param dataArray      the data values to be used
     * @param headers        the headers to search through
     * @param expectedHeader the expected header value
     * @return data value for the expected header
     * @author Simon Erickson
     */
    public String setDefaultDataValue(String[] dataArray, Headers headers, String expectedHeader) {
        int index = headers.getHeaderIndex(expectedHeader);
        if (index >= 0 && index < dataArray.length) {
            return dataArray[index];
        }
        return "";
    }

}//end Trips