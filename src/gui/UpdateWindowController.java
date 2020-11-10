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

import data.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * UpdateWindowController Purpose: Controller for the Update Window
 *
 * @author Grant
 * @version Created on 10/25/2020 at 2:14 AM
 */
public class UpdateWindowController {
    //region FXML properties
    @FXML
    private TextField inputPickType;
    @FXML
    private TextField inputAttribute;
    @FXML
    private TextArea description;
    @FXML
    private TextArea output;
    @FXML
    private Button updateButton;
    @FXML
    private ComboBox attributes;
    @FXML
    private ComboBox pickType;
    //endregion

    //region properties
    private ArrayList<String> ids = new ArrayList<>();
    private int update;
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
     *
     * @author Grant Fass
     */
    public void setDefaultValues() {
        description.setText("This window is used to update for specific information. " +
                "Use the 'Pick Type' dropdown to select the data type to search for. Use the 'Update Attribute' " +
                "dropdown to select the attribute type used to update for. Put your update value into the " +
                "Update Attribute Text Field then click on the 'Update' button to get started.");
    }

    /**
     * display help values to the program
     * Activates when help menu button is clicked
     *
     * @author Joy Cross
     */
    @FXML
    private void displayHelp() {
        MainWindowController.displayAlert(Alert.AlertType.INFORMATION, "General Transit Feed Specification Tool Information",
                "Update Window Help", "This window is used to update information" +
                        "\nHow To Use:" +
                        "\n1. Click on the 'Pick Type' dropdown and select the data type you are updating for" +
                        "\n2. Click on the 'Pick Type' Text Field and enter the id to search and update" +
                        "\n3. Click on the 'Add ID' Button to add to update list, repeat 2-3 for every id you wish to update" +
                        "\n4. Click on the 'Clear' Button to clear current update list to start again" +
                        "\n1. Click on the 'Update Attribute' dropdown and select the attribute type you are updating for" +
                        "\n5. Click on the 'Update Attribute' Text Field and enter the text that will update attribute" +
                        "\n6. Click on the 'Update' Button. The results of the update will be displayed in the 'Output' Text Area." +
                        "\nNote: StopTime must be entered as 'stop_id, trip_id'");
    }
    //endregion

    //region methods used for GUI updates
    /**
     * Clears all text fields and current id list
     * @author Joy Cross
     */
    @FXML
    private void clear() {
        output.setText("");
        inputAttribute.setText("");
        inputPickType.setText("");
        ids.clear();
    }

    /**
     * Allows access to GUI features by un-disabling them
     * @author Joy Cross
     */
    @FXML
    private void unDisableAttribute(){
        inputAttribute.setDisable(false);
        updateButton.setDisable(false);
    }
    //endregion

    //region methods for selecting object to update
    /**
     * adds a stop object to the update window
     * @param closestStop the stop to add
     * @author Grant Fass
     */
    public void setObjectToUpdate(Stop closestStop) {
        if (closestStop != null) {
            pickType.setValue("stop_id");
            inputPickType.setText(closestStop.getStopID());
        }
    }

    /**
     * Adds id into list of ids
     * @author Joy Cross
     */
    @FXML
    private void addID() {
        String choiceText = inputPickType.getText().trim();
        if(update == 1){
            output.setText("");
            update = 0;
        }
        if (!choiceText.equals("")) {
            ids.add(choiceText);
            inputPickType.setText("");
            output.setText(output.getText() + choiceText + "\n");
        }
    }
    //endregion

    //region methods for selecting the object properties to update
    /**
     * Sets the attributes based on pick type
     * @author Joy Cross
     */
    @FXML
    private void setAttributes() {
        String choice = (String)pickType.getValue();
        clear();
        inputPickType.setDisable(false);
        Headers headers;
        switch (choice) {
            case "stop_id":
                Stops stops = mainWindowController.getData().getStops();
                headers = stops.getHeaders();
                break;
            case "route_id":
                Routes routes = mainWindowController.getData().getRoutes();
                headers = routes.getHeaders();
                break;
            case "trip_id":
                Trips trips = mainWindowController.getData().getTrips();
                headers = trips.getHeaders();
                break;
            default:  // stoptime
                StopTimes stopTimes = mainWindowController.getData().getStopTimes();
                headers = stopTimes.getHeaders();
                break;
        }
        if(headers.length() == 0){
            output.setText("No available data in import, must import at least one " + choice);
            attributes.setDisable(true);
            updateButton.setDisable(true);
        } else {
            attributes.setItems(FXCollections.observableArrayList(convertHeadersToArray(headers)));
            attributes.setDisable(false);
        }
    }

    /**
     * converts the headers to an arrayList
     * @param headers the headers to convert
     * @return the converted headers
     * @author Joy Cross
     */
    public ArrayList<String> convertHeadersToArray(Headers headers){
        ArrayList<String> arrayList = new ArrayList<>();
        for(int i = 0; i < headers.length(); i++){
            arrayList.add(headers.getHeaderName(i));
        }
        return arrayList;
    }
    //endregion

    //region method used to update values
    /**
     * Updates fields
     * @author Joy Cross
     */
    @FXML
    private void update() {
        String choice = (String) pickType.getValue();
        String attributeChosen = (String) attributes.getValue();
        String attributeValue = inputAttribute.getText();
        output.setText("");
        if(attributeChosen.equals("Select Attribute")){
            output.setText("Choose a attribute to change");
        } else if (attributeValue.equals("")){
            output.setText("Enter the text you want to update the attribute");
        } else if (ids.size() == 0){
            output.setText("Add ids to update");
        }
        else {
            for (String id : ids) {
                int success = mainWindowController.getData().update(choice, id, attributeChosen, attributeValue);
                if (success == 0) {
                    output.setText(output.getText() + "Could not find specified " + choice + ": " + id + "\n");
                } else {
                    output.setText(output.getText() + "Updated " + choice + ": " + id + "\n");
                }
            }
            inputAttribute.setText("");
            inputPickType.setText("");
            ids.clear();
            update = 1;
        }
    }
    //endregion
}
