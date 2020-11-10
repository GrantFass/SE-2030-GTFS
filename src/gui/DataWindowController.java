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
package gui;

import data.Data;
import interfaces.Observer;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDateTime;
import java.util.ConcurrentModificationException;

/**
 * DataDisplayController Purpose: Controller for the data display window
 *
 * @author Grant
 * @version Created on 10/12/2020 at 6:40 PM
 */
public class DataWindowController implements Observer {
    //region FXML properties
    @FXML
    private TextField lastUpdatedTextField;
    @FXML
    private TextArea description;
    @FXML
    private ToggleButton expandedToggleButton;
    @FXML
    private ListView stopTimesListView;
    @FXML
    private ListView routesListView;
    @FXML
    private ListView stopsListView;
    @FXML
    private ListView tripsListView;
    //endregion

    //region class references
    private MainWindowController mainWindowController;

    /**
     * Sets the values of the controller associated with the respective files
     * Makes sure the same instance of the controller is used everywhere
     *
     * @param mainWindowController     reference to the MainWindowController in use
     * @author Grant Fass
     */
    public void setControllers(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }
    //endregion

    //region displayed help information
    /**
     * set the default values of the description
     * @author Grant Fass
     */
    public void setDefaultValues() {
        description.setText("This window displays the currently stored data. The values " +
                "automatically update so that the most current data values are always displayed. Click on " +
                "the 'View Snapshot' toggle button to view single line snapshots of the data. Click on the " +
                "'View Expanded Data' toggle button to view all of the data associated with a given " +
                "reference.");
    }

    /**
     * display help values to the program
     * Activates when help menu button is clicked
     *
     * @author Grant Fass
     */
    @FXML
    private void displayHelp() {
        MainWindowController.displayAlert(Alert.AlertType.INFORMATION, "General Transit Feed Specification Tool Information",
                "Data Window Help", "This window displays all of the data stored in\n" +
                        "the four data classes: 'routes.txt', 'stops.txt', 'stop_times.txt', and 'trips.txt'.\n" +
                        "The information that is displayed in this window is always up to date since it\n" +
                        "automatically updates whenever any information in the data classes is changed.\n" +
                        "There are two viewing modes for this window. The mode can be selected through\n" +
                        "the use of the toggle buttons.\n'View Snapshot' displays only critical information " +
                        "in a single line format.\n'View Expanded Data' displays all information for each entry " +
                        "in the dta classes.");
    }
    //endregion

    //region methods to toggle data display format
    /**
     * method to run when one of the toggle buttons is pressed
     * used to make sure that the displayed data is up to date
     * toggles the format of the data as well
     * @author Grant Fass
     */
    @FXML
    private void buttonToggled() {
        updateData(mainWindowController.getData());
    }
    //endregion

    //region methods to update displayed information
    /**
     * update the data if a toggle button is clicked to reflect the latest format
     * @param data the data that was changed
     * @author Grant Fass
     */
    private void updateData(Data data) throws ConcurrentModificationException {
        lastUpdatedTextField.setText("UPDATE IN PROGRESS");
        if (expandedToggleButton.isSelected()) {
            data.displayData(0, 1, routesListView);
            data.displayData(1, 1, stopsListView);
            data.displayData(2, 1, stopTimesListView);
            data.displayData(3, 1, tripsListView);
        } else {
            data.displayData(0, 0, routesListView);
            data.displayData(1, 0, stopsListView);
            data.displayData(2, 0, stopTimesListView);
            data.displayData(3, 0, tripsListView);
        }
        lastUpdatedTextField.setText(String.format("Last Updated at: %s", LocalDateTime.now()));
    }
    //endregion

    //region observer pattern methods
    /**
     * update the observers when the data is changed
     * Based on a guide from GeeksForGeeks
     * found here: https://www.geeksforgeeks.org/observer-pattern-set-2-implementation/
     * Will update the observers in a separate thread so program does not stall
     * If there is an error it will try to update again after a short delay
     *
     * @param data the data object that was changed
     * @author Grant Fass
     */
    @Override
    public void update(Data data) {
        updateData(data);
    }
    //endregion
}
