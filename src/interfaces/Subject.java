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
package interfaces;

/**
 * Subject Purpose: The subject interface used for the observer pattern
 * Based on a guide from GeeksForGeeks
 * found here: https://www.geeksforgeeks.org/observer-pattern-set-2-implementation/
 *
 * @author Grant
 * @version Created on 11/1/2020 at 12:58 PM
 */
public interface Subject {
    /**
     * add an observer to the list of observers to update
     * Based on a guide from GeeksForGeeks
     * found here: https://www.geeksforgeeks.org/observer-pattern-set-2-implementation/
     * @param o the observer to add
     * @author Grant Fass
     */
    public void attach(Observer o);
    /**
     * remove an observer from the list of observers to update
     * Based on a guide from GeeksForGeeks
     * found here: https://www.geeksforgeeks.org/observer-pattern-set-2-implementation/
     * @param o the observer to remove
     * @author Grant Fass
     */
    public void detach(Observer o);
    /**
     * notify all observers that information was changed
     * Based on a guide from GeeksForGeeks
     * found here: https://www.geeksforgeeks.org/observer-pattern-set-2-implementation/
     * @author Grant Fass
     */
    public void notifyObservers();
}
