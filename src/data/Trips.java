/*
 * Authors: Becker, Ryan; Cross, Joy; Erickson, Simon; Fass, Grant;
 * Class: SE 2030 - 021
 * Team: G
 * Affiliation: Milwaukee School Of Engineering (MSOE)
 * Program Name: General Transit Feed Specification Tool
 * Copyright (C): GNU GPLv3; 9 November 2020
 *
 * This file is a part of the General Transit Feed Specification Tool
 * written by Team G of class SE 2030 - 021 at MSOE.
 *
 * This is a free software: it can be redistributed and/or modified
 * as expressed in the GNU GPLv3 written by the Free Software Foundation.
 *
 * This software is distributed in hopes that it is useful but does
 * not include any warranties, not even implied warranties. There is more
 * information about this in the GNU GPLv3.
 *
 * To view the license go to <gnu.org/licenses/gpl-3.0.en.html>
 */
package data;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

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
    private Headers headers = new Headers();

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
     * Method to parse data from a specified file
     *
     * @param file the GTFS file to be parsed
     * @return a message containing the results of loading the file
     * @throws IOException for general File IO errors.
     * @author Simon Erickson, Grant Fass
     */
    public String loadTrips(File file) throws IOException {
        boolean wasLineSkipped = false;
        boolean wasFileLoaded = true;
        String failMessage = "";
        boolean emptyPrior = trips.isEmpty();
        if (!emptyPrior) {
            trips.clear();
        }
        //writes the items of the file to the hash map
        try (Scanner in = new Scanner(file)) {
            //read the headers. If they are formatted wrong then immediately throw error and stop.
            headers = validateHeader(in.nextLine());
            //read body. will skip improperly formatted lines.
            while (in.hasNextLine()) {
                try {
                    addTrip(validateData(in.nextLine(), headers));
                } catch (IllegalArgumentException e) {
                    wasLineSkipped = true;
                }
            }
        } catch (IllegalArgumentException e) {
            wasFileLoaded = false;
            failMessage = String.format("  ERROR: Trips Not Imported\n  File Contains Invalid Header Format\n  %s\n", e.getMessage());
        }
        String successMessage = String.format("  ✓: Trips Imported Successfully.\n  %s\n  %s\n", emptyPrior ? "New Trips Data Imported" : "Trip Data Overwritten", wasLineSkipped ? "Lines Skipped During Import Of Trips" : "All Lines Imported Successfully");
        return String.format("IMPORT TRIPS:\n%s", wasFileLoaded ? successMessage : failMessage);
    }

    /**
     * Method to write Trip data to a trips.txt file
     *
     * @param file the trips.txt file desired location
     * @return true if the file was exported
     * @author Simon Erickson, Joy Cross, Grant Fass
     */
    public boolean exportTrips(File file) {
        try (PrintWriter out = new PrintWriter((new BufferedOutputStream(new FileOutputStream(new File(file, "trips.txt")))))) {
            out.append(createHeaderLine(headers));
            for (String key: trips.keySet()) {
                out.append(trips.get(key).getDataLine(headers));
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Creates header line from input headers
     * @param headers headers to put into a String output
     * @return String
     * @author Joy Cross
     */
    public String createHeaderLine(Headers headers) {
        StringBuilder sb = new StringBuilder();
        int i;
        for(i = 0; i < headers.length()-1; i++){
            sb.append(headers.getHeaderName(i) + ",");
        }
        sb.append(headers.getHeaderName(i) + "\n");

        return sb.toString();
    }

    /**
     * output simplified data as a single concatenated string
     * @return string of data
     * @author Grant Fass
     */
    public HashMap<String, Trip> getTrips() {
        return trips;
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
        } else if (!header.contains("trip_id")) {
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
        String regex = ",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)";
        String[] headerDataArray = header.split(regex, -1);
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
        String regex = ",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)";
        String[] dataArray = data.split(regex, -1);
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



    //Start feature five

    /**
     * Gets every route_id that is paired with the searched trip_id within a given Trip object
     * @author Ryan Becker
     * @param trip_id associated with a Trip that is used to search for paired route_id within a given Trip object
     * @return ArrayList of every route_id that is within a Trip object that also contains the searched trip_id
     */
    public ArrayList<String> getRouteIDs_fromTripIDs(String trip_id){
        ArrayList<String> route_ids = new ArrayList<>();
        for(Trip trip : trips.values()){
            if(trip.getTripID().equals(trip_id)){
                route_ids.add(trip.getRouteID());
            }
        }
        return route_ids;
    }

    //End feature five

    //Start feature six

    /**
     * Gets every trip_id that is paired with a given route_id
     * @author Ryan Becker
     * @param route_id to be searched for, and find associated trip_ids
     * @return ArrayList of all trip_ids related to route_id
     */
    public ArrayList<String> getTripIDs_fromRouteID(String route_id){
        ArrayList<String> trip_ids = new ArrayList<>();
        for(Trip trip : trips.values()){
            if(trip.getRouteID().equalsIgnoreCase(route_id)){
                trip_ids.add(trip.getTripID());
            }
        }
        return trip_ids;
    }

    //End feature six

}//end Trips