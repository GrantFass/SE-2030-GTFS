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
package enumerators;


/**
 * Indicates the direction of travel for a trip. This field is not used in routing; it provides a way to separate trips by direction when publishing time tables. Valid options are:
 *
 * 0 - Travel in one direction (e.g. outbound travel).
 * 1 - Travel in the opposite direction (e.g. inbound travel).
 * Example: The trip_headsign and direction_id fields could be used together to assign a name to travel in each direction for a set of trips. A trips.txt file could contain these records for use in time tables:
 * trip_id,...,trip_headsign,direction_id
 * 1234,...,Airport,0
 * 1505,...,Downtown,1
 *
 * @author Grant Fass
 * @version 1.0
 * @since 06-Oct-2020 10:28:15 AM
 */
public enum DirectionIDEnum {
    OUTBOUND_TRAVEL(0),
    INBOUND_TRAVEL(1)
    ;

    private final int value;

    /**
     * Constructor for the Enumerator
     * @param value the value to set the instance of the enum to
     * @author Grant Fass
     */
    DirectionIDEnum(int value) {
        this.value = value;
    }

    /**
     * return the integer value associated with the enumerator
     * @return the integer value associated with the enumerator
     * @author Grant Fass
     */
    public int getValue(){
        return this.value;
    }

    /**
     * return the enumerator value associated with the specified integer value
     * @param value the value to use
     * @return the enumerator value associated with the integer or the default if none match
     * @author Grant Fass
     */
    public static DirectionIDEnum getValue(int value) {
        if (value == 1) {
            return INBOUND_TRAVEL;
        } else {
            return OUTBOUND_TRAVEL;
        }
    }}