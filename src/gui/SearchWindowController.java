/*
 * gui
 * File Header Contains Class SearchWindowController
 * Name: Grant
 * Created 10/25/2020
 */
package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

import java.util.List;

/**
 * SearchWindowController Purpose: Controller for the search window
 *
 * @author Grant
 * @version Created on 10/25/2020 at 1:24 AM
 */
public class SearchWindowController {
    @FXML
    private TextArea description;
    @FXML
    private TextArea output;
    @FXML
    private ComboBox inputType;
    @FXML
    private ComboBox outputType;
    @FXML
    private TextField input;
    private Stage analysisWindowStage;
    private AnalysisWindowController analysisWindowController;
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
     * @param exportWindowController reference to the ExportWindowController in use
     * @param importWindowController reference to the ImportWindowController in use
     * @param mainWindowController reference to the MainWindowController in use
     * @param mapWindowController reference to the MapWindowController in use
     * @param updateWindowController reference to the UpdateWindowController in use
     * @author Grant Fass
     */
    public void setControllers(AnalysisWindowController analysisWindowController,
                               DataWindowController dataWindowController,
                               ExportWindowController exportWindowController,
                               ImportWindowController importWindowController,
                               MainWindowController mainWindowController,
                               MapWindowController mapWindowController,
                               UpdateWindowController updateWindowController) {
        this.analysisWindowController = analysisWindowController;
        this.dataWindowController = dataWindowController;
        this.exportWindowController = exportWindowController;
        this.importWindowController = importWindowController;
        this.mainWindowController = mainWindowController;
        this.mapWindowController = mapWindowController;
        this.updateWindowController = updateWindowController;
    }

    /**
     * set the default values of the description
     * @author Grant Fass
     */
    public void setDefaultValues() {
        description.setText("This window is used to search for specific information. " +
                "Use the 'Output Type' dropdown to select the data type to search for. Use the 'Input Type' " +
                "dropdown to select the data type used to search for. Put your search into the Input Text " +
                "Field then click on the 'Search' button to get started.");
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
                "Import Window Help", "This window is used to search for information" +
                        "\nHow To Use:" +
                        "\n1. Click on the 'Output Type' dropdown and select the data type you are searching for" +
                        "\n2. Click on the 'Input Type' dropdown and select the data type that you are going to use as input to search" +
                        "\n3. Click on the 'Input' Text Field and enter the text to search for" +
                        "\n4. Click on the 'Search' Button. The results of the search will be displayed in the 'Output' Text Area.");
    }

    @FXML
    private void search() {
        output.setText("Searching For: " + ((String)outputType.getValue()).toUpperCase() + "S\n");
        switch (((String)outputType.getValue()).toLowerCase()) {
            case "route_id":
                if(!(inputType.getValue()).equals("stop_id")){
                    output.appendText("Input type not yet implemented");
                } else if(mainWindowController.getData().getStops().getStop(input.getText()) != null){
                    String route_ids = mainWindowController.getData().getRouteIDs_fromStopID(input.getText());

                    output.appendText(route_ids);
                } else {
                    output.appendText("stop_id does not exist");
                }
                break;
            case "stop_id":
                output.appendText("not ready");
                break;
            case "stoptime":
                output.appendText("not ready");
                break;
            case "trip_id":
                if(!(inputType.getValue().equals("stop_id"))){
                    output.appendText("Input type not yet implemented");
                } else if(mainWindowController.getData().getStops().getStop(input.getText()) == null){
                    output.appendText("stop_id does not exist");
                } else {
                    String inputText = (input.getText()).toLowerCase();
                    List<String> list = mainWindowController.getData().getStopTimes().searchStopDisplayTrips(inputText);
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < list.size(); i++) {
                        int j = i + 1;
                        sb.append(j + ": " + list.get(i) + "\n");
                    }
                    output.appendText(sb.toString());
                }
                break;
        }
    }
}
