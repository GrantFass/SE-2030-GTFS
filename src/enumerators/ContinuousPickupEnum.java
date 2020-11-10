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
 * Indicates whether a rider can board the transit vehicle anywhere along the vehicle’s travel path. The path is described by shapes.txt on every trip of the route. Valid options are:
 *
 * 0 - Continuous stopping pickup.
 * 1 or empty - No continuous stopping pickup.
 * 2 - Must phone an agency to arrange continuous stopping pickup.
 * 3 - Must coordinate with a driver to arrange continuous stopping pickup.
 *
 * The default continuous pickup behavior defined in routes.txt can be overridden in stop_times.txt.
 *
 * @author Grant Fass
 * @version 1.0
 * @since 06-Oct-2020 10:28:15 AM
 */
public enum ContinuousPickupEnum {
    CONTINUOUS_PICKUP(0),
    NO_CONTINUOUS_PICKUP(1),
    CALL_AGENCY_TO_ARRANGE_CONTINUOUS_PICKUP(2),
    COORDINATE_WITH_DRIVER_FOR_CONTINUOUS_PICKUP(3)
    ;

    private final int value;

    /**
     * Constructor for the Enumerator
     * @param value the value to set the instance of the enum to
     * @author Grant Fass
     */
    ContinuousPickupEnum(int value) {
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
    public static ContinuousPickupEnum getValue(int value) {
        if (value == 0) {
            return CONTINUOUS_PICKUP;
        } else if (value == 2) {
            return CALL_AGENCY_TO_ARRANGE_CONTINUOUS_PICKUP;
        } else if (value == 3) {
            return COORDINATE_WITH_DRIVER_FOR_CONTINUOUS_PICKUP;
        } else {
            return NO_CONTINUOUS_PICKUP;
        }
    }

}