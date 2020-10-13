/*
 * SE2030TransitProject
 * File Header Contains Class DataDisplayController
 * Name: Grant
 * Created 10/12/2020
 */
package SE2030TransitProject;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * DataDisplayController Purpose: Controller for the data display window
 *
 * @author Grant
 * @version Created on 10/12/2020 at 6:40 PM
 */
public class DataDisplayController {
    @FXML
    public TextArea dataDisplayTextArea;
    @FXML
    public Label topLabel;
    @FXML
    private VBox dataDisplayMainPane;
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

    public void viewRoutes() {
        topLabel.setText("'routes.txt'");
        dataDisplayTextArea.setText(primaryController.getData().getRoutes().toString());
    }

    public void viewStops() {
        topLabel.setText("'routes.txt'");
        dataDisplayTextArea.setText(primaryController.getData().getRoutes().toString());
    }

    public void viewStopTimes() {
        topLabel.setText("'routes.txt'");
        dataDisplayTextArea.setText(primaryController.getData().getRoutes().toString());
    }

    public void viewTrips() {
        topLabel.setText("'routes.txt'");
        dataDisplayTextArea.setText(primaryController.getData().getRoutes().toString());
    }

    public void displayHelp() {
        primaryController.displayAlert(Alert.AlertType.INFORMATION,
                "Data Display Information", null, "Not Yet Implemented");
    }
}
