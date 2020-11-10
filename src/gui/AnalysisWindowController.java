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
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

    //region class references
    private Stage analysisWindowStage;
    private Stage dataWindowStage;
    private DataWindowController dataWindowController;
    private Stage exportWindowStage;
    private ExportWindowController exportWindowController;
    private Stage importWindowStage;
    private ImportWindowController importWindowController;
    private Stage mainWindowStage;
    private MainWindowController mainWindowController;
    private Stage mapWindowStage;
    private MapWindowController mapWindowController;
    private Stage searchWindowStage;
    private SearchWindowController searchWindowController;
    private Stage updateWindowStage;
    private UpdateWindowController updateWindowController;

    /**
     * set the local values of all of the stages.
     * @param analysisWindowStage the stage for the AnalysisWindow
     * @param dataWindowStage the stage for the DataWindow
     * @param exportWindowStage the stage for the ExportWindow
     * @param importWindowStage the stage for the ImportWindow
     * @param mainWindowStage the stage for the MainWindow
     * @param mapWindowStage the stage for the MapWindow
     * @param searchWindowStage the stage for the SearchWindow
     * @param updateWindowStage the stage for the UpdateWindow
     * @author Grant Fass
     */
    public void setStages(Stage analysisWindowStage, Stage dataWindowStage,
                          Stage exportWindowStage, Stage importWindowStage,
                          Stage mainWindowStage, Stage mapWindowStage,
                          Stage searchWindowStage, Stage updateWindowStage) {
        this.analysisWindowStage = analysisWindowStage;
        this.dataWindowStage = dataWindowStage;
        this.exportWindowStage = exportWindowStage;
        this.importWindowStage = importWindowStage;
        this.mainWindowStage = mainWindowStage;
        this.mapWindowStage = mapWindowStage;
        this.searchWindowStage = searchWindowStage;
        this.updateWindowStage = updateWindowStage;
    }

    /**
     * Sets the values of the controller associated with the respective files
     * Makes sure the same instance of the controller is used everywhere
     * @param dataWindowController reference to the DataWindowController in use
     * @param exportWindowController reference to the ExportWindowController in use
     * @param importWindowController reference to the ImportWindowController in use
     * @param mainWindowController reference to the MainWindowController in use
     * @param mapWindowController reference to the MapWindowController in use
     * @param searchWindowController reference to the SearchWindowController in use
     * @param updateWindowController reference to the UpdateWindowController in use
     * @author Grant Fass
     */
    public void setControllers(DataWindowController dataWindowController,
                               ExportWindowController exportWindowController,
                               ImportWindowController importWindowController,
                               MainWindowController mainWindowController,
                               MapWindowController mapWindowController,
                               SearchWindowController searchWindowController,
                               UpdateWindowController updateWindowController) {
        this.dataWindowController = dataWindowController;
        this.exportWindowController = exportWindowController;
        this.importWindowController = importWindowController;
        this.mainWindowController = mainWindowController;
        this.mapWindowController = mapWindowController;
        this.searchWindowController = searchWindowController;
        this.updateWindowController = updateWindowController;
    }
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
        updateStatus(lastUpdatedTextField, "UPDATE IN PROGRESS");
        updateData(data);
    }

    /**
     * Updates all of the displayed data
     * @param data the data used for updating
     * @author Grant Fass
     */
    private void updateData(Data data) throws ConcurrentModificationException {
        updateStatus(lastUpdatedTextField, "UPDATE IN PROGRESS");
        data.displayAnalysis(0, distanceListView);
        data.displayAnalysis(1, speedListView);
        data.displayAnalysis(2, numberTripsListView);
        updateStatus(lastUpdatedTextField, String.format("Last Updated at: %s", LocalDateTime.now()));
    }

    /**
     * sets a message to the specified textField
     * format changes depending on if a task is running
     * based on https://stackoverflow.com/questions/36638617/javafx-textarea-update-immediately
     * @param textField the text field to update
     * @param message the message to post
     * @author Grant Fass
     */
    private void updateStatus(TextField textField, String message) {
        if (Platform.isFxApplicationThread()) {
            textField.setText(message);
        } else {
            Platform.runLater(() -> textField.setText(message));
        }
    }
}
