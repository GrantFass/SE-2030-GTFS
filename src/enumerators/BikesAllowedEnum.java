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
 * Indicates whether bikes are allowed. Valid options are:
 *
 * 0 or empty - No bike information for the trip.
 * 1 - Vehicle being used on this particular trip can accommodate at least one bicycle.
 * 2 - No bicycles are allowed on this trip.
 *
 * @author Grant Fass
 * @version 1.0
 * @since 06-Oct-2020 10:28:15 AM
 */
public enum BikesAllowedEnum {
    NO_INFORMATION(0),
    AT_LEAST_ONE(1),
    NO_BIKES(2);

    private final int value;

    BikesAllowedEnum(int value) {
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }

    /**
     * return the enumerator value associated with the specified integer value
     * @param value the value to use
     * @return the enumerator value associated with the integer or the default if none match
     * @author Simon Erickson
     */
    public static BikesAllowedEnum getValue(int value) {
        if (value == 1) {
            return AT_LEAST_ONE;
        } else if (value == 2) {
            return NO_BIKES;
        } else {
            return NO_INFORMATION;
        }
    }
}