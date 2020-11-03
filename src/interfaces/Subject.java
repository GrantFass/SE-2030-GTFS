/*
 * GTFS
 * File Header Contains Class Subject
 * Name: Grant
 * Created 11/1/2020
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
