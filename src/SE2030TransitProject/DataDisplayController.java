/*
 * SE2030TransitProject
 * File Header Contains Class DataDisplayController
 * Name: Grant
 * Created 10/12/2020
 */
package SE2030TransitProject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * DataDisplayController Purpose: Controller for the data display window
 *
 * @author Grant
 * @version Created on 10/12/2020 at 6:40 PM
 */
public class DataDisplayController {
    @FXML
    private TextArea dataDisplayTextArea;
    @FXML
    private Label topLabel;
    @FXML
    private Controller primaryController;
    private Stage primaryStage;
    private Stage stage;

    /**
     * Set the value of the stage associated with this file
     * @param stage the stage value to use
     * @author Grant Fass
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Set the value of the controller associated with the primary window
     * Should make sure that the same instance of the controller is used everywhere
     * @param primaryController the value of the primary window controller to set
     * @author Grant Fass
     */
    public void setPrimaryController(Controller primaryController) {
        this.primaryController = primaryController;
    }

    /**
     * Set the value of the stage associated with the primary window
     * @param primaryStage the value of the primary window stage to set
     * @author Grant Fass
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Show the information from the Routes data to the user.
     * @author Grant Fass
     */
    @FXML
    private void viewRoutes() {
        displayData("'routes.txt'", primaryController.getData().getRoutes().toString());
        //displayData(dataDisplayTextArea, primaryController.getData().getRoutes(), null, null, null);
    }

    /**
     * Show the information from the Stops data to the user.
     * @author Grant Fass
     */
    @FXML
    private void viewStops() {
        displayData("'stops.txt'", primaryController.getData().getStops().toString());
        //displayData(dataDisplayTextArea, null, primaryController.getData().getStops(), null, null);
    }

    /**
     * Show the information from the StopTimes data to the user.
     * @author Grant Fass
     */
    @FXML
    private void viewStopTimes() {
        displayData("'stop_times.txt'", primaryController.getData().getStopTimes().toString());
        //displayData(dataDisplayTextArea,null, null, primaryController.getData().getStopTimes(),  null);
    }

    /**
     * Show the information from the Trips data to the user.
     * @author Grant Fass
     */
    @FXML
    private void viewTrips() {
        displayData("'trips.txt'", primaryController.getData().getTrips().toString());
        //displayData(dataDisplayTextArea, null, null, null, primaryController.getData().getTrips());
    }

    /**
     * display the number of trips that each stop is found on
     * @author Grant Fass
     */
    public void viewTripsPerStop() {
        displayData("Trips Per Stop", primaryController.getData().getStopTimes().getTripsPerStop());
    }

    /**
     * Display help information to the user about how this window works
     * @author Grant Fass
     */
    @FXML
    private void displayHelp() {
        primaryController.displayAlert(Alert.AlertType.INFORMATION,
                "Data Display Information", null, "Not Yet Implemented");
    }

    /**
     * Resets the text in the top label and the text area to default values.
     */
    public void resetTextToDefaultValues() {
        topLabel.setText("Data Displayed Below");
        dataDisplayTextArea.setText("Data Displayed Here");
    }

    /**
     * Checks to see if the text area is empty.
     * If the text area is empty then set its value so that the user knows
     * there is no data yet.
     * @author Grant Fass
     */
    private void checkForEmptyTextArea() {
        if (dataDisplayTextArea.getText().isEmpty()) {
            dataDisplayTextArea.setText("No Data Yet!");
        }
    }

    /**
     * display the data by manually setting the value of the topLabel and the textArea
     * @param topLabelText the text value to set the top label to
     * @param textAreaText the text value to set the text area to
     * @author Grant Fass
     */
    private void displayData(String topLabelText, String textAreaText) {
        topLabel.setText(topLabelText);
        dataDisplayTextArea.setText(textAreaText);
        checkForEmptyTextArea();
    }
}
