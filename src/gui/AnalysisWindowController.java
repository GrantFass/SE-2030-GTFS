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
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDateTime;
import java.util.ConcurrentModificationException;

/**
 * AnalysisWindowController Purpose: Controller for the Analysis Window
 *
 * @author Grant
 * @version Created on 10/25/2020 at 1:26 AM
 */
public class AnalysisWindowController implements Observer {
    //region FXML properties
    @FXML
    private TextField lastUpdatedTextField;
    @FXML
    private TextArea description;
    @FXML
    private ListView distanceListView;
    @FXML
    private ListView speedListView;
    @FXML
    private ListView numberTripsListView;
    //endregion

    //region displayed help information
    /**
     * set the default values of the description
     * @author Grant Fass
     */
    public void setDefaultValues() {
        description.setText("This window displays information that is calculated and derived " +
                "from the other stored data. This window automatically updates whenever " +
                "the stored data is updated.");
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
                "Analysis Window Help", "This window is used to view information that\n" +
                        "has been derived and or calculated from the various data classes.\n" +
                        "data from 'routes.txt', 'stops.txt', 'stop_times.txt', and 'trips.txt'\n" +
                        "are used for calculations. Some data can be partially displayed if not all\n" +
                        "of the files are loaded. If the data display does not look correct\n" +
                        "make sure that all files are loaded. The data displayed in this window\n" +
                        "update whenever a change is made to any of the data classes. This ensures\n" +
                        "that the most current information is always displayed.");
    }
    //endregion

    //region methods to update displayed information
    /**
     * Updates all of the displayed data
     * @param data the data used for updating
     * @author Grant Fass
     */
    private void updateData(Data data) throws ConcurrentModificationException {
        lastUpdatedTextField.setText("UPDATE IN PROGRESS");
        data.displayAnalysis(0, distanceListView);
        data.displayAnalysis(1, speedListView);
        data.displayAnalysis(2, numberTripsListView);
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
