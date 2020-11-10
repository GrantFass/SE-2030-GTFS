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
 * 	Indicates wheelchair accessibility. Valid options are:
 *
 * 0 or empty - No accessibility information for the trip.
 * 1 - Vehicle being used on this particular trip can accommodate at least one rider in a wheelchair.
 * 2 - No riders in wheelchairs can be accommodated on this trip.
 *
 * @author Grant Fass
 * @version 1.0
 * @since 06-Oct-2020 10:28:47 AM
 */
public enum WheelchairAccessibleEnum {
    NO_ACCESSIBILITY_INFORMATION(0),
    AT_LEAST_ONE_WHEELCHAIR(1),
    NO_WHEELCHAIRS(2)
    ;

    private final int value;

    /**
     * Constructor for the Enumerator
     * @param value the value to set the instance of the enum to
     * @author Grant Fass
     */
    WheelchairAccessibleEnum(int value) {
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
    public static WheelchairAccessibleEnum getValue(int value) {
        if (value == 1) {
            return AT_LEAST_ONE_WHEELCHAIR;
        } else if (value == 2) {
            return NO_WHEELCHAIRS;
        } else {
            return NO_ACCESSIBILITY_INFORMATION;
        }
    }}