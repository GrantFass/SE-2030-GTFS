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
 * Indicates pickup method. Valid options are:
 *
 * 0 or empty - Regularly scheduled pickup.
 * 1 - No pickup available.
 * 2 - Must phone agency to arrange pickup.
 * 3 - Must coordinate with driver to arrange pickup.
 *
 * @author Grant Fass
 * @version 1.0
 * @since 06-Oct-2020 10:28:15 AM
 */
public enum PickupTypeEnum {
    REGULARLY_SCHEDULED_PICKUP(0),
    NO_PICKUP_AVAILABLE(1),
    PHONE_AGENCY_FOR_PICKUP(2),
    COORDINATE_WITH_DRIVER_FOR_PICKUP(3)
    ;

    private final int value;

    /**
     * Constructor for the Enumerator
     * @param value the value to set the instance of the enum to
     * @author Grant Fass
     */
    PickupTypeEnum(int value) {
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
    public static PickupTypeEnum getValue(int value) {
        if (value == 1) {
            return NO_PICKUP_AVAILABLE;
        } else if (value == 2) {
            return PHONE_AGENCY_FOR_PICKUP;
        } else if (value == 3) {
            return COORDINATE_WITH_DRIVER_FOR_PICKUP;
        } else {
            return REGULARLY_SCHEDULED_PICKUP;
        }
    }
}