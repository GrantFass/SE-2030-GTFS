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

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDateTime;

/**
 * ExportWindowController Purpose: Controller for the Export Window
 *
 * @author Grant
 * @version Created on 10/25/2020 at 1:26 AM
 */
public class ExportWindowController {
    //region FXML properties
    @FXML
    private TextArea description;
    @FXML
    private TextArea alertTextArea;
    @FXML
    private TextField directoryTextField;
    @FXML
    private CheckBox routesCheckBox;
    @FXML
    private VBox routeVBox;
    @FXML
    private CheckBox stopsCheckBox;
    @FXML
    private VBox stopVBox;
    @FXML
    private CheckBox stopTimesCheckBox;
    @FXML
    private VBox stopTimeVBox;
    @FXML
    private CheckBox tripsCheckBox;
    @FXML
    private VBox tripVBox;
    //endregion

    //region class references
    private Stage mainWindowStage;
    private MainWindowController mainWindowController;

    /**
     * set the local values of all of the stages.
     * @param mainWindowStage the stage for the MainWindow
     * @author Grant Fass
     */
    public void setStages(Stage mainWindowStage) {
        this.mainWindowStage = mainWindowStage;
    }

    /**
     * Sets the values of the controller associated with the respective files
     * Makes sure the same instance of the controller is used everywhere
     * @param mainWindowController reference to the MainWindowController in use
     * @author Grant Fass
     */
    public void setControllers(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }
    //endregion

    //region displayed help information
    /**
     * set the default values of the descriptions
     * @author Grant Fass
     */
    public void setDefaultValues() {
        description.setText("This window is used to export files to a given directory. " +
                "Use the 'browse' button to select the folder to save the exported file(s) to. Use the " +
                "checkboxes to select which file(s) you would like to export. Use the 'Export' button to " +
                "export the files.");
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
                "Export Window Help", "This window is used to export the files in the correct format." +
                        "\nHow to use:" +
                        "\n1. Click on the 'browse' button to open a Directory Chooser." +
                        "\n2. Use the Directory Chooser to navigate to the location you would like" +
                        " to save the exported files to." +
                        "\n3. Click 'Select Folder' in the Directory Chooser to select the directory." +
                        " After the Directory Chooser closes the selected directory will be shown in" +
                        " the 'Directory' Text Field." +
                        "\n4. Click on the check boxes of the files you would like to export." +
                        "\n5. Click on Export. Once the files have finished exporting the progress" +
                        " bars will turn green and the path to the saved file will be displayed" +
                        " next to the checkbox. Any errors will be displayed to the 'Alert' Text" +
                        " Area.");
    }
    //endregion

    //region methods for file export
    /**
     * Exports all selected files to the specified directory
     * @author Grant Fass
     */
    @FXML
    private void exportFiles() {
        MainWindowController.displayAlert(Alert.AlertType.INFORMATION, "EXPORT START", null, "STARTING EXPORT");
        alertTextArea.clear();
        alertTextArea.appendText("Times Formatted in HH:MM:SS\n");
        alertTextArea.appendText(String.format("START: %02d:%02d:%02d\n", LocalDateTime.now().getHour(), LocalDateTime.now().getMinute(), LocalDateTime.now().getSecond()));
        alertTextArea.appendText(mainWindowController.getData().exportFiles(directoryTextField.getText(), routesCheckBox.isSelected(), stopsCheckBox.isSelected(), stopTimesCheckBox.isSelected(), tripsCheckBox.isSelected()));
    }

    /**
     * Method to query the user to retrieve a GTFS file from the computer using a FileChooser
     * @return The selected GTFS file from the program
     * @author Grant Fass
     */
    private File getExportDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Export Directory");
        directoryChooser.setInitialDirectory(new File("C:\\\\users\\\\" +
                System.getProperty("user.name") + "\\\\Documents"));
        return directoryChooser.showDialog(mainWindowStage);
    }

    /**
     * opens a DirectoryChooser to select the directory location to save the files to
     * outputs the directory to the directoryTextField
     * @author Grant Fass
     */
    @FXML
    private void browseDirectory() {
        try {
            File file = getExportDirectory();
            directoryTextField.setText(file.toString());
        } catch (NullPointerException ignored) {

        }
    }
    //endregion

    //region check box controls
    /**
     * update the disabled status of the route data based on CheckBox toggle
     * @author Grant Fass
     */
    @FXML
    private void toggleRouteCheckBox() {
        toggleCheckBox(routesCheckBox, routeVBox);
    }

    /**
     * update the disabled status of the stop data based on CheckBox toggle
     * @author Grant Fass
     */
    @FXML
    private void toggleStopCheckBox() {
        toggleCheckBox(stopsCheckBox, stopVBox);
    }

    /**
     * update the disabled status of the stopTime data based on CheckBox toggle
     * @author Grant Fass
     */
    @FXML
    private void toggleStopTimeCheckBox() {
        toggleCheckBox(stopTimesCheckBox, stopTimeVBox);
    }

    /**
     * update the disabled status of the trip data based on CheckBox toggle
     * @author Grant Fass
     */
    @FXML
    private void toggleTripCheckBox() {
        toggleCheckBox(tripsCheckBox, tripVBox);
    }

    /**
     * update the disabled status of the data based on the toggled status of the checkBox
     * @param checkBox to check the status of
     * @param vBox to set the disabled status of
     * @author Grant Fass
     */
    private void toggleCheckBox(CheckBox checkBox, VBox vBox) {
        vBox.setDisable(!checkBox.isSelected());
    }
    //endregion
}
