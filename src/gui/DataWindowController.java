/*
 * SE2030TransitProject
 * File Header Contains Class DataDisplayController
 * Name: Grant
 * Created 10/12/2020
 */
package gui;

import data.Data;
import interfaces.Observer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.ConcurrentModificationException;

/**
 * DataDisplayController Purpose: Controller for the data display window
 *
 * @author Grant
 * @version Created on 10/12/2020 at 6:40 PM
 */
public class DataWindowController implements Observer {
    @FXML
    private TextField lastUpdatedTextField;
    @FXML
    private ListView stopTimesListView;
    @FXML
    private TextArea description;
    @FXML
    private ToggleButton expandedToggleButton;
    @FXML
    private ListView routesListView;
    @FXML
    private ListView stopsListView;
    @FXML
    private ListView tripsListView;
    private Stage analysisWindowStage;
    private AnalysisWindowController analysisWindowController;
    private Stage dataWindowStage;
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
     * @param analysisWindowController reference to the AnalysisWindowController in use
     * @param exportWindowController reference to the ExportWindowController in use
     * @param importWindowController reference to the ImportWindowController in use
     * @param mainWindowController reference to the MainWindowController in use
     * @param mapWindowController reference to the MapWindowController in use
     * @param searchWindowController reference to the SearchWindowController in use
     * @param updateWindowController reference to the UpdateWindowController in use
     * @author Grant Fass
     */
    public void setControllers(AnalysisWindowController analysisWindowController,
                               ExportWindowController exportWindowController,
                               ImportWindowController importWindowController,
                               MainWindowController mainWindowController,
                               MapWindowController mapWindowController,
                               SearchWindowController searchWindowController,
                               UpdateWindowController updateWindowController) {
        this.analysisWindowController = analysisWindowController;
        this.exportWindowController = exportWindowController;
        this.importWindowController = importWindowController;
        this.mainWindowController = mainWindowController;
        this.mapWindowController = mapWindowController;
        this.searchWindowController = searchWindowController;
        this.updateWindowController = updateWindowController;
    }

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
                "Import Window Help", "This window displays all of the data stored in\n" +
                        "the four data classes: 'routes.txt', 'stops.txt', 'stop_times.txt', and 'trips.txt'.\n" +
                        "The information that is displayed in this window is always up to date since it\n" +
                        "automatically updates whenever any information in the data classes is changed.\n" +
                        "There are two viewing modes for this window. The mode can be selected through\n" +
                        "the use of the toggle buttons.\n'View Snapshot' displays only critical information " +
                        "in a single line format.\n'View Expanded Data' displays all information for each entry " +
                        "in the dta classes.");
    }

    /**
     * method to run when one of the toggle buttons is pressed
     * used to make sure that the displayed data is up to date
     * @author Grant Fass
     */
    @FXML
    private void buttonToggled() {
        updateData(mainWindowController.getData());
    }

    /**
     * update the data if a toggle button is clicked to reflect the latest format
     * @param data the data that was changed
     * @author Grant Fass
     */
    private void updateData(Data data) throws ConcurrentModificationException {
        updateStatus(lastUpdatedTextField, "UPDATE IN PROGRESS");
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
        updateStatus(lastUpdatedTextField, String.format("Last Updated at: %s", LocalDateTime.now()));
    }

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
        //TODO: undo multithread, only thread first command to set progress bar / text field at start.
//        Thread thread = new Thread(() -> {
//            try {
                updateStatus(lastUpdatedTextField, "UPDATE IN PROGRESS");
                updateData(data);
//            } catch (ConcurrentModificationException e) {
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException interruptedException) {
//                    interruptedException.printStackTrace();
//                }
//                update(data);
//            }
//        });
//        thread.start();
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
