/*
 * gui
 * File Header Contains Class UpdateWindowController
 * Name: Grant
 * Created 10/25/2020
 */
package gui;

import data.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * UpdateWindowController Purpose: Controller for the Update Window
 *
 * @author Grant
 * @version Created on 10/25/2020 at 2:14 AM
 */
public class UpdateWindowController {
    @FXML
    private TextArea description;
    @FXML
    private ComboBox attributes;
    @FXML
    private TextField inputAttribute;
    @FXML
    private ComboBox pickType;
    @FXML
    private TextField inputPickType;
    @FXML
    private TextArea output;
    @FXML
    private Button updateButton;
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
    private SearchWindowController searchWindowController;
    private Stage updateWindowStage;

    private ArrayList<String> ids = new ArrayList<>();
    private int update;

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
     * @param importWindowController   reference to the ImportWindowController in use
     * @param mainWindowController     reference to the MainWindowController in use
     * @param mapWindowController      reference to the MapWindowController in use
     * @param searchWindowController   reference to the SearchWindowController in use
     * @author Grant Fass
     */
    public void setControllers(AnalysisWindowController analysisWindowController,
                               DataWindowController dataWindowController,
                               ExportWindowController exportWindowController,
                               ImportWindowController importWindowController,
                               MainWindowController mainWindowController,
                               MapWindowController mapWindowController,
                               SearchWindowController searchWindowController) {
        this.analysisWindowController = analysisWindowController;
        this.dataWindowController = dataWindowController;
        this.exportWindowController = exportWindowController;
        this.importWindowController = importWindowController;
        this.mainWindowController = mainWindowController;
        this.mapWindowController = mapWindowController;
        this.searchWindowController = searchWindowController;
    }

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
                "Import Window Help", "This window is used to update information" +
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

    public ArrayList<String> convertHeadersToArray(Headers headers){
        ArrayList<String> arrayList = new ArrayList<>();
        for(int i = 0; i < headers.length(); i++){
            arrayList.add(headers.getHeaderName(i));
        }
        return arrayList;
    }

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
        if(choice.equals("stop_id")){
            Stops stops = mainWindowController.getData().getStops();
            headers = stops.getHeaders();
        } else if (choice.equals("route_id")){
            Routes routes = mainWindowController.getData().getRoutes();
            headers = routes.getHeaders();
        } else if (choice.equals("trip_id")){
            Trips trips = mainWindowController.getData().getTrips();
            headers = trips.getHeaders();
        } else { // stoptime
            StopTimes stopTimes = mainWindowController.getData().getStopTimes();
            headers = stopTimes.getHeaders();
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
     * Allows access to GUI features by un-disabling them
     * @author Joy Cross
     */
    @FXML
    private void unDisableAttribute(){
        inputAttribute.setDisable(false);
        updateButton.setDisable(false);
    }
}
