/*
 * GTFS
 * File Header Contains Class Observer
 * Name: Grant
 * Created 11/1/2020
 */
package interfaces;

import data.Data;

/**
 * Observer Purpose: observer interface for the observer pattern implementation
 * Based on a guide from GeeksForGeeks
 * found here: https://www.geeksforgeeks.org/observer-pattern-set-2-implementation/
 *
 * @author Grant
 * @version Created on 11/1/2020 at 1:06 PM
 */
public interface Observer {
    /**
     * update the observers when the data is changed
     * Based on a guide from GeeksForGeeks
     * found here: https://www.geeksforgeeks.org/observer-pattern-set-2-implementation/
     * @param data the data object that was changed
     * @author Grant Fass
     */
    public void update(Data data);
}
