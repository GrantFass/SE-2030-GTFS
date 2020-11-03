/*
 * gui
 * File Header Contains Class ExportWindowController
 * Name: Grant
 * Created 10/25/2020
 */
package gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * ExportWindowController Purpose: Controller for the Export Window
 *
 * @author Grant
 * @version Created on 10/25/2020 at 1:26 AM
 */
public class ExportWindowController {
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
    private TextField routeTextField;
    @FXML
    private ProgressBar routesProgressBar;
    @FXML
    private CheckBox stopsCheckBox;
    @FXML
    private VBox stopVBox;
    @FXML
    private TextField stopTextField;
    @FXML
    private ProgressBar stopsProgressBar;
    @FXML
    private CheckBox stopTimesCheckBox;
    @FXML
    private VBox stopTimeVBox;
    @FXML
    private TextField stopTimeTextField;
    @FXML
    private ProgressBar stopTimesProgressBar;
    @FXML
    private CheckBox tripsCheckBox;
    @FXML
    private VBox tripVBox;
    @FXML
    private TextField tripTextField;
    @FXML
    private ProgressBar tripsProgressBar;
    private Stage analysisWindowStage;
    private AnalysisWindowController analysisWindowController;
    private Stage dataWindowStage;
    private DataWindowController dataWindowController;
    private Stage exportWindowStage;
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
     * @param dataWindowController reference to the DataWindowController in use
     * @param importWindowController reference to the ImportWindowController in use
     * @param mainWindowController reference to the MainWindowController in use
     * @param mapWindowController reference to the MapWindowController in use
     * @param searchWindowController reference to the SearchWindowController in use
     * @param updateWindowController reference to the UpdateWindowController in use
     * @author Grant Fass
     */
    public void setControllers(AnalysisWindowController analysisWindowController,
                               DataWindowController dataWindowController,
                               ImportWindowController importWindowController,
                               MainWindowController mainWindowController,
                               MapWindowController mapWindowController,
                               SearchWindowController searchWindowController,
                               UpdateWindowController updateWindowController) {
        this.analysisWindowController = analysisWindowController;
        this.dataWindowController = dataWindowController;
        this.importWindowController = importWindowController;
        this.mainWindowController = mainWindowController;
        this.mapWindowController = mapWindowController;
        this.searchWindowController = searchWindowController;
        this.updateWindowController = updateWindowController;
    }

    /**
     * set the default values of the progress bars and descriptions
     * @author Grant Fass
     */
    public void setDefaultValues() {
        routesProgressBar.setVisible(false);
        stopsProgressBar.setVisible(false);
        stopTimesProgressBar.setVisible(false);
        tripsProgressBar.setVisible(false);
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
                "Import Window Help", "This window is used to export the files in the correct format." +
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

    /**
     * Method to query the user to retrieve a GTFS file from the computer using a FileChooser
     * @author Grant Fass
     * @return The selected GTFS file from the program
     */
    private File getExportDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Export Directory");
        directoryChooser.setInitialDirectory(new File("C:\\\\users\\\\" +
                System.getProperty("user.name") + "\\\\Documents"));
        return directoryChooser.showDialog(mainWindowStage);
    }

    @FXML
    private void exportFiles() {
        alertTextArea.clear();
        alertTextArea.appendText(LocalDateTime.now().toString() + "\n");
        if (!directoryTextField.getText().isEmpty()) {
            exportFile(routesCheckBox, routesProgressBar, "Routes");
            exportFile(stopsCheckBox, stopsProgressBar, "Stops");
            exportFile(stopTimesCheckBox, stopTimesProgressBar, "StopTimes");
            exportFile(tripsCheckBox, tripsProgressBar, "Trips");
        }
        else alertTextArea.setText("SKIPPING ALL: No Directory Selected!");
    }

    private void exportFile(CheckBox checkBox, ProgressBar progressBar, String fileType) {
        progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
        if (checkBox.isSelected()) {
            alertTextArea.appendText("OUT: " + fileType + "\n");
            exportFile(new File(directoryTextField.getText()), fileType.toLowerCase());
            progressBar.setStyle("-fx-accent: green");
        } else {
            alertTextArea.appendText("SKIP: " + fileType + " - Not Selected\n");
            progressBar.setStyle("-fx-accent: red");
        }
        progressBar.setProgress(100);
    }

    private void exportFile(File file, String fileType) {
        try {
            switch (fileType) {
                case "routes":
                    mainWindowController.getData().getRoutes().exportRoutes(file);
                    routeTextField.setText(file.toString() + "//routes.txt");
                    break;
                case "stops":
                    mainWindowController.getData().getStops().exportStops(file);
                    stopTextField.setText(file.toString() + "//stops.txt");
                    break;
                case "stoptimes":
                    mainWindowController.getData().getStopTimes().exportStopTimes(file);
                    stopTimeTextField.setText(file.toString() + "//stop_times.txt");
                    break;
                case "trips":
                    mainWindowController.getData().getTrips().exportTrips(file);
                    tripTextField.setText(file.toString() + "//trips.txt");
                    break;
            }
        } catch (IOException e) {
            alertTextArea.appendText("\tERROR: IOException - " + e.getMessage() + "\n");
        }
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
            initializeProgressBar(routesProgressBar);
            initializeProgressBar(stopsProgressBar);
            initializeProgressBar(stopTimesProgressBar);
            initializeProgressBar(tripsProgressBar);
        } catch (NullPointerException ignored) {

        }
    }

    /**
     * initializes the default values of a single progress bar
     * @param progressBar the progress bar to intialize
     * @author Grant Fass
     */
    private void initializeProgressBar(ProgressBar progressBar) {
        progressBar.setProgress(0);
        progressBar.setStyle("-fx-accent: blanchedalmond");
        progressBar.setVisible(true);
    }

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
}
