/*
 * gui
 * File Header Contains Class AnalysisWindowController
 * Name: Grant
 * Created 10/25/2020
 */
package gui;

import data.Data;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import interfaces.Observer;

/**
 * AnalysisWindowController Purpose: Controller for the Analysis Window
 *
 * @author Grant
 * @version Created on 10/25/2020 at 1:26 AM
 */
public class AnalysisWindowController implements Observer {
    @FXML
    private TextArea description;
    @FXML
    private TextArea distanceTextArea;
    @FXML
    private TextArea speedTextArea;
    @FXML
    private TextArea numberTripsTextArea;
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
                "Import Window Help", "This window is used to view information that\n" +
                        "has been derived and or calculated from the various data classes.\n" +
                        "data from 'routes.txt', 'stops.txt', 'stop_times.txt', and 'trips.txt'\n" +
                        "are used for calculations. Some data can be partially displayed if not all\n" +
                        "of the files are loaded. If the data display does not look correct\n" +
                        "make sure that all files are loaded. The data displayed in this window\n" +
                        "update whenever a change is made to any of the data classes. This ensures\n" +
                        "that the most current information is always displayed.");
    }

    /**
     * update the observers when the data is changed
     * Based on a guide from GeeksForGeeks
     * found here: https://www.geeksforgeeks.org/observer-pattern-set-2-implementation/
     *
     * @param data the data object that was changed
     * @author Grant Fass
     */
    @Override
    public void update(Data data) {
        distanceTextArea.setText(data.getTripDistances().isEmpty() ? "No Data Yet!" : data.getTripDistances());
        speedTextArea.setText("Not Implemented Yet!");
        numberTripsTextArea.setText(data.getStopTimes().getTripsPerStop().isEmpty() ? "No Data Yet!" : data.getStopTimes().getTripsPerStop());
    }
}