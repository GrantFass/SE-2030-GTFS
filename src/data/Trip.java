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

import enumerators.BikesAllowedEnum;
import enumerators.DirectionIDEnum;
import enumerators.WheelchairAccessibleEnum;

/**
 * Class for a Trip object, which is the path that a Driver will take from one stop to another.
 *
 * @author ericksons
 * @version 1.0
 * @created 06-Oct-2020 10:28:39 AM
 */
public class Trip {
    //region properties
    private BikesAllowedEnum bikes_allowed;
    private String block_id;
    private DirectionIDEnum direction_id;
    private String route_id;
    private String service_id;
    private String shape_id;
    private String trip_headsign;
    private String trip_id;
    private WheelchairAccessibleEnum wheelchair_accessible;
    //endregion

    //region constructors
    /**
     * @param bikes_allowed         Enum for if bikes are allowed
     * @param block_id              The block ID
     * @param direction_id          The direction ID
     * @param route_id              The route ID
     * @param service_id            The service ID
     * @param shape_id              The shape ID
     * @param trip_headsign         The trip head sign
     * @param trip_id               The ID for the trip
     * @param wheelchair_accessible Enum for if it is accessible for wheelchairs
     * @author Simon Erickson
     */
    public Trip(String bikes_allowed, String block_id, String direction_id,
                String route_id, String service_id, String shape_id, String trip_headsign,
                String trip_id, String wheelchair_accessible) {

        //stop_id & trip_id are required so error if they are empty
        if (route_id.isEmpty() || trip_id.isEmpty() || service_id.isEmpty()) {
            throw new IllegalArgumentException("Line in 'trips.txt' file not formatted" +
                    " correctly. Skipping!");
        }
        this.route_id = route_id;
        this.trip_id = trip_id;
        this.service_id = service_id;

        //Optional and does not throw error if empty.
        this.trip_headsign = trip_headsign;
        this.block_id = block_id;
        this.shape_id = shape_id;
        this.service_id = service_id;
        //Set enumerator values (default values are applied if empty).
        this.bikes_allowed = BikesAllowedEnum.getValue(!bikes_allowed.isEmpty() ? Integer.parseInt(bikes_allowed) : -1);
        this.direction_id = DirectionIDEnum.getValue(!direction_id.isEmpty() ? Integer.parseInt(direction_id) : -1);
        this.wheelchair_accessible = WheelchairAccessibleEnum.getValue(!wheelchair_accessible.isEmpty() ? Integer.parseInt(wheelchair_accessible) : -1);
    }
    //endregion

    //region getters
    /**
     * returns bikes_allowed
     *
     * @return Enum for if bikes are allowed
     * @author Simon Erickson
     */
    public BikesAllowedEnum getBikesAllowed() {
        return bikes_allowed;
    }

    /**
     * returns block_id
     *
     * @return The block ID
     * @author Simon Erickson
     */
    public String getBlockID() {
        return block_id;
    }

    /**
     * returns direction_id
     *
     * @return The direction ID
     * @author Simon Erickson
     */
    public DirectionIDEnum getDirectionID() {
        return direction_id;
    }

    /**
     * returns route_id
     *
     * @return The Route ID
     * @author Simon Erickson
     */
    public String getRouteID() {
        return route_id;
    }

    /**
     * returns service_id
     *
     * @return The service id
     * @author Simon Erickson
     */
    public String getServiceID() {
        return service_id;
    }

    /**
     * returns shape_id
     *
     * @return The id for the shape
     * @author Simon Erickson
     */
    public String getShapeID() {
        return shape_id;
    }

    /**
     * returns trip_headsign
     *
     * @return the headsign for the trip
     * @author Simon Erickson
     */
    public String getTripHeadsign() {
        return trip_headsign;
    }

    /**
     * returns trip_id
     *
     * @return The ID for the trip
     * @author Simon Erickson
     */
    public String getTripID() {
        return trip_id;
    }

    /**
     * returns wheelchair_accessible
     *
     * @return Enum for if it is accessible for wheelchairs
     * @author Simon Erickson
     */
    public WheelchairAccessibleEnum getWheelchairAccessible() {
        return wheelchair_accessible;
    }

    /**
     * gets the file header for Trips
     * @return file header in a single line header format
     * @author Simon Erickson
     */
    public static String getHeaderLine() {
        return "route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id";
    }

    /**
     * gets the data line for a single trip for trips
     * @return trip data in a single line data format
     * @author Simon Erickson, Joy Cross
     */
    public String getDataLine(Headers headers) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < headers.length(); i++){
            switch (headers.getHeaderName(i)) {
                case "route_id":
                    sb.append(route_id);
                    break;
                case "service_id":
                    sb.append(service_id);
                    break;
                case "trip_id":
                    sb.append(trip_id);
                    break;
                case "trip_headsign":
                    sb.append(trip_headsign);
                    break;
                case "direction_id":
                    sb.append(direction_id.getValue());
                    break;
                case "block_id":
                    sb.append(block_id);
                    break;
                case "shape_id":
                    sb.append(shape_id);
                    break;
            }
            sb.append(',');
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append('\n');
        return sb.toString();
    }
    //endregion

    //region setters

    /**
     * sets the value of the Bikes Allowed enum
     * @param bikes_allowed the value to set
     * @author Grant Fass
     */
    public void setBikes_allowed(BikesAllowedEnum bikes_allowed) {
        this.bikes_allowed = bikes_allowed;
    }

    /**
     * sets the value for the block_id
     * @param block_id the value to set
     * @author Grant Fass
     */
    public void setBlock_id(String block_id) {
        this.block_id = block_id;
    }

    /**
     * sets the value for the direction_id
     * @param direction_id the value to set
     * @author Grant Fass
     */
    public void setDirection_id(DirectionIDEnum direction_id) {
        this.direction_id = direction_id;
    }

    /**
     * sets the value for the route_id
     * @param route_id the value to set
     * @author Grant Fass
     */
    public void setRoute_id(String route_id) {
        this.route_id = route_id;
    }

    /**
     * sets the value for the service_id
     * @param service_id the value to set
     * @author Grant Fass
     */
    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    /**
     * sets the value for the shape_id
     * @param shape_id the value to set
     * @author Grant Fass
     */
    public void setShape_id(String shape_id) {
        this.shape_id = shape_id;
    }

    /**
     * sets the value for the trip_headsign
     * @param trip_headsign the value to set
     * @author Grant Fass
     */
    public void setTrip_headsign(String trip_headsign) {
        this.trip_headsign = trip_headsign;
    }

    /**
     * sets the value for the trip_id
     * @param trip_id the value to set
     * @author Grant Fass
     */
    public void setTrip_id(String trip_id) {
        this.trip_id = trip_id;
    }

    /**
     * sets the value for wheelchair accessible enum
     * @param wheelchair_accessible the value to set
     * @author Grant Fass
     */
    public void setWheelchair_accessible(WheelchairAccessibleEnum wheelchair_accessible) {
        this.wheelchair_accessible = wheelchair_accessible;
    }
    //endregion

    //region string outputs
    /**
     * Method to output data as a single concatenated string
     *
     * @return string of data
     * @author GrantFass, Simon Erickson
     */
    @Override
    public String toString() {
        return String.format("Trip ID: %s\n\t" +
                        "Bikes Allowed: %s\n\t" +
                        "Block ID: %s\n\t" +
                        "Direction ID: %s\n\t" +
                        "Route ID: %s\n\t" +
                        "Service ID: %s\n\t" +
                        "Shape ID: %s\n\t" +
                        "Trip Headsign: %s\n\t" +
                        "Wheelchair Accessible: %s\n", trip_id, bikes_allowed, block_id,
                direction_id, route_id, service_id, shape_id, trip_headsign, wheelchair_accessible);
    }

    /**
     * output simplified data as a single concatenated string
     * @return string of data
     * @author Grant Fass
     */
    public String toSimpleString() {
        return String.format("Trip ID: %s | Route ID: %s\n", trip_id, route_id);
    }
    //endregion
}//end Trip
