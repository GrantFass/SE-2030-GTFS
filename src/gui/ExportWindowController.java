/*
 * gui
 * File Header Contains Class ExportWindowController
 * Name: Grant
 * Created 10/25/2020
 */
package gui;

import javafx.application.Platform;
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
    private TextField routeTextField;
    @FXML
    private CheckBox stopsCheckBox;
    @FXML
    private VBox stopVBox;
    @FXML
    private TextField stopTextField;
    @FXML
    private CheckBox stopTimesCheckBox;
    @FXML
    private VBox stopTimeVBox;
    @FXML
    private TextField stopTimeTextField;
    @FXML
    private CheckBox tripsCheckBox;
    @FXML
    private VBox tripVBox;
    @FXML
    private TextField tripTextField;
    //endregion

    //region class references
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
