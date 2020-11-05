/*
 * gui
 * File Header Contains Class ImportWindowController
 * Name: Grant
 * Created 10/25/2020
 */
package gui;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.zip.DataFormatException;

/**
 * ImportWindowController Purpose: Controller for the Input Window
 *
 * @author Grant
 * @version Created on 10/25/2020 at 1:26 AM
 */
public class ImportWindowController {
    //region FXML Properties
    @FXML
    private TextArea description;
    @FXML
    private TextArea alertTextArea;
    @FXML
    private VBox routesVBox;
    @FXML
    private VBox stopsVBox;
    @FXML
    private VBox stopTimesVBox;
    @FXML
    private VBox tripsVBox;
    @FXML
    private CheckBox routesCheckBox;
    @FXML
    private TextField routesTextField;
    @FXML
    private ProgressBar routesProgressBar;
    @FXML
    private CheckBox stopsCheckBox;
    @FXML
    private TextField stopsTextField;
    @FXML
    private ProgressBar stopsProgressBar;
    @FXML
    private CheckBox stopTimesCheckBox;
    @FXML
    private TextField stopTimesTextField;
    @FXML
    private ProgressBar stopTimesProgressBar;
    @FXML
    private CheckBox tripsCheckBox;
    @FXML
    private TextField tripsTextField;
    @FXML
    private ProgressBar tripsProgressBar;
    //endregion

    //region class references
    private Stage analysisWindowStage;
    private AnalysisWindowController analysisWindowController;
    private Stage dataWindowStage;
    private DataWindowController dataWindowController;
    private Stage exportWindowStage;
    private ExportWindowController exportWindowController;
    private Stage importWindowStage;
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
     *
     * @param analysisWindowStage the stage for the AnalysisWindow
     * @param dataWindowStage     the stage for the DataWindow
     * @param exportWindowStage   the stage for the ExportWindow
     * @param importWindowStage   the stage for the ImportWindow
     * @param mainWindowStage     the stage for the MainWindow
     * @param mapWindowStage      the stage for the MapWindow
     * @param searchWindowStage   the stage for the SearchWindow
     * @param updateWindowStage   the stage for the UpdateWindow
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
     *
     * @param analysisWindowController reference to the AnalysisWindowController in use
     * @param dataWindowController     reference to the DataWindowController in use
     * @param exportWindowController   reference to the ExportWindowController in use
     * @param mainWindowController     reference to the MainWindowController in use
     * @param mapWindowController      reference to the MapWindowController in use
     * @param searchWindowController   reference to the SearchWindowController in use
     * @param updateWindowController   reference to the UpdateWindowController in use
     * @author Grant Fass
     */
    public void setControllers(AnalysisWindowController analysisWindowController,
                               DataWindowController dataWindowController,
                               ExportWindowController exportWindowController,
                               MainWindowController mainWindowController,
                               MapWindowController mapWindowController,
                               SearchWindowController searchWindowController,
                               UpdateWindowController updateWindowController) {
        this.analysisWindowController = analysisWindowController;
        this.dataWindowController = dataWindowController;
        this.exportWindowController = exportWindowController;
        this.mainWindowController = mainWindowController;
        this.mapWindowController = mapWindowController;
        this.searchWindowController = searchWindowController;
        this.updateWindowController = updateWindowController;
    }
    //endregion

    //region displayed help information
    /**
     * set the default values of the progress bars
     * @author Grant Fass
     */
    public void setDefaultValues() {
        description.setText("This window is used to import files into the program. " +
                "Use the checkboxes to select which file(s) to import. Use the 'browse' buttons to select " +
                "the location of the given file to import. Use the 'Import' button to import the files.");
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
                "Import Window Help", "This window is used to import GTFS files into the program" +
                        "\nHow To Use:" +
                        "\n1. Click the checkbox next to the file type you would like to import" +
                        "\n2. Click the 'browse' button to open a File Chooser" +
                        "\n3. Use the File Chooser to navigate to the file that you would like to import." +
                        " Note that files are expected to be named 'routes.txt', 'stops.txt', 'stop_times.txt', or 'trips.txt'" +
                        "\n4. Select the file in the File Chooser and click 'Open' The File chooser will" +
                        " then close and the path will be displayed in the Text Field." +
                        "\n5. Repeat steps 1 - 4 for all file types you would like to import." +
                        "\n6. Click the 'Import' Button to load the selected GTFS files into the program." +
                        " Note that this will take some time. After files are loaded the progress" +
                        " bars will turn green and alerts will be displayed to the Alerts Text Area.");
    }
    //endregion

    //region getters for locations of files to import
    /**
     * opens a FileChooser with the specified extension filter and outputs the file path to the corresponding TextArea
     *
     * @author Grant Fass
     */
    @FXML
    private void browseRoutes() {
        browse(routesTextField, "ROUTES", "routes.txt", routesProgressBar);
    }

    /**
     * opens a FileChooser with the specified extension filter and outputs the file path to the corresponding TextArea
     *
     * @author Grant Fass
     */
    @FXML
    private void browseStops() {
        browse(stopsTextField, "STOPS", "stops.txt", stopsProgressBar);
    }

    /**
     * opens a FileChooser with the specified extension filter and outputs the file path to the corresponding TextArea
     *
     * @author Grant Fass
     */
    @FXML
    private void browseStopTimes() {
        browse(stopTimesTextField, "STOP_TIMES", "stop_times.txt", stopTimesProgressBar);
    }

    /**
     * opens a FileChooser with the specified extension filter and outputs the file path to the corresponding TextArea
     *
     * @author Grant Fass
     */
    @FXML
    private void browseTrips() {
        browse(tripsTextField, "TRIPS", "trips.txt", tripsProgressBar);
    }

    /**
     * opens a FileChooser to browse to the file location and set initial load values
     * @param textField the TextField to display the file location to
     * @param description the description to use for the extension for the FileChooser
     * @param extension the extension to use for the FileChooser
     * @param progressBar the progress bar to set initial values for
     * @author Grant Fass
     */
    private void browse(TextField textField, String description, String extension, ProgressBar progressBar) {
        try {
            File file = getGTFSFileLocationUsingFileChooser(description, extension);
            textField.setText(file.toString());
            progressBar.setProgress(0);
            progressBar.setStyle("-fx-accent: blanchedalmond");
            progressBar.setVisible(true);
        } catch (NullPointerException ignored) {

        }
    }

    /**
     * query the user to retrieve a GTFS file from the computer using a FileChooser
     *
     * @param description the description for the primary extension filter
     * @param expected    the extension for the primary extension filter
     * @return the selected GTFS file from the program
     * @author Grant Fass
     */
    private File getGTFSFileLocationUsingFileChooser(String description, String expected) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(description, expected.toLowerCase()),
                new FileChooser.ExtensionFilter("TXT", "*.txt".toLowerCase()));
        fileChooser.setTitle("Load GTFS File");
        fileChooser.setInitialDirectory(new File("C:\\\\users\\\\" +
                System.getProperty("user.name") + "\\\\Documents"));
        return fileChooser.showOpenDialog(mainWindowStage);
    }
    //endregion

    //region Methods For Importing Files
    /**
     * Import selected files into the program
     * Will not import file if corresponding checkbox is not selected
     * Outputs success results to Alerts TextArea
     * @author Grant Fass
     */
    @FXML
    private void importFiles() {
        alertTextArea.clear();
        String importMessage = "";
        alertTextArea.appendText("Times Formatted in HH:MM.SS\n");
        alertTextArea.appendText(String.format("START: %02d:%02d.%02d\n", LocalDateTime.now().getHour(), LocalDateTime.now().getMinute(), LocalDateTime.now().getSecond()));
        try {
            importMessage = mainWindowController.getData().loadFiles(
                    importFile(routesTextField, routesCheckBox),
                    importFile(stopsTextField, stopsCheckBox),
                    importFile(stopTimesTextField, stopTimesCheckBox),
                    importFile(tripsTextField, tripsCheckBox));
        } catch (IOException e) {
            importMessage = e.getMessage() + "\n";
        }
        alertTextArea.appendText(importMessage);
    }

    /**
     * method to prepare the file location from a given text field depending on checkbox status
     * @param textField the textfield to get the file location from
     * @param checkBox the checkbox to use the status of
     * @return the prepared file location
     * @author Grant Fass
     */
    private String importFile(TextField textField, CheckBox checkBox) {
        return checkBox.isSelected() ? checkFileName(textField.getText()) : "";
    }

    /**
     * Method to check that an appropriate file was selected
     * @param fileLocation the file location to check
     * @return the name of the appropriate file or empty string if it was not appropriate
     * @author Grant Fass
     */
    private String checkFileName(String fileLocation) {
        String tempLocation = fileLocation.toLowerCase();
        if (tempLocation.contains("routes.txt") || tempLocation.contains("stops.txt") || tempLocation.contains("stop_times.txt") || tempLocation.contains("trips.txt")) {
            return fileLocation;
        }
        return "";
    }
    //endregion

    //region Checkbox Controls
    /**
     * update the disabled status of the route data based on CheckBox toggle
     * @author Grant Fass
     */
    @FXML
    private void toggleRouteCheckBox() {
        toggleCheckBox(routesCheckBox, routesVBox);
    }

    /**
     * update the disabled status of the stop data based on CheckBox toggle
     * @author Grant Fass
     */
    @FXML
    private void toggleStopCheckBox() {
        toggleCheckBox(stopsCheckBox, stopsVBox);
    }

    /**
     * update the disabled status of the stopTime data based on CheckBox toggle
     * @author Grant Fass
     */
    @FXML
    private void toggleStopTimeCheckBox() {
        toggleCheckBox(stopTimesCheckBox, stopTimesVBox);
    }

    /**
     * update the disabled status of the trip data based on CheckBox toggle
     * @author Grant Fass
     */
    @FXML
    private void toggleTripCheckBox() {
        toggleCheckBox(tripsCheckBox, tripsVBox);
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
