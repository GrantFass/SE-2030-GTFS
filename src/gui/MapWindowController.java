/*
 * gui
 * File Header Contains Class MapWindowController
 * Name: Grant
 * Created 10/25/2020
 */
package gui;

import com.sothawo.mapjfx.*;
import com.sothawo.mapjfx.event.MapViewEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * MapWindowController Purpose: Controller for the Map Window
 *
 * @author Grant
 * @version Created on 10/25/2020 at 1:27 AM
 */
public class MapWindowController {
    @FXML
    private Label labelCenter;
    @FXML
    private Label labelZoom;
    @FXML
    private MapView map;
    @FXML
    private VBox primaryVBox;
    @FXML
    private TextArea description;
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
    private Stage searchWindowStage;
    private SearchWindowController searchWindowController;
    private Stage updateWindowStage;
    private UpdateWindowController updateWindowController;
    private static final Coordinate msoeAthleticField = new Coordinate(43.044056044993994, -87.90621316829471);

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
     * @param searchWindowController reference to the SearchWindowController in use
     * @param updateWindowController reference to the UpdateWindowController in use
     * @author Grant Fass
     */
    public void setControllers(AnalysisWindowController analysisWindowController,
                               DataWindowController dataWindowController,
                               ExportWindowController exportWindowController,
                               ImportWindowController importWindowController,
                               MainWindowController mainWindowController,
                               SearchWindowController searchWindowController,
                               UpdateWindowController updateWindowController) {
        this.analysisWindowController = analysisWindowController;
        this.dataWindowController = dataWindowController;
        this.exportWindowController = exportWindowController;
        this.importWindowController = importWindowController;
        this.mainWindowController = mainWindowController;
        this.searchWindowController = searchWindowController;
        this.updateWindowController = updateWindowController;
    }

    /**
     * set the default values of the description
     * set up the map view and initialize it
     * based on Version 2.3.0 of mapjfx from https://www.sothawo.com/projects/mapjfx/
     * @author Grant Fass
     */
    public void setDefaultValues() {
        description.setText("Hold 'CTRL' then drag over the map to zoom.");
        map.setAnimationDuration(100);
        // add listener for mapView initialization state
        map.initializedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                // a map is only displayed when an initial coordinate is set

                map.setCenter(msoeAthleticField);
//                mapView.setExtent(extentAll);
//                mapView.setZoom(0);

                // add two markers without keeping a ref to them, they should disappear from the map when gc'ed
                map.addMarker(Marker.createProvided(Marker.Provided.GREEN).setPosition(msoeAthleticField)
                        .setVisible(true));
                map.addMarker(
                        Marker.createProvided(Marker.Provided.ORANGE).setPosition(msoeAthleticField).setVisible(
                                true));

                // add a coordinate line to be gc'ed
                map.addCoordinateLine(
                        new CoordinateLine(msoeAthleticField)
                                .setVisible(true)
                                .setColor(Color.FUCHSIA).setWidth(5));

                // add a label to be gc'ed
                map.addLabel(new MapLabel("clean me up").setPosition(msoeAthleticField)
                        .setVisible(true));
            }
        });
        map.initialize();
        //change zoom to match when the user holds 'ctrl' and drags over the map
        map.addEventHandler(MapViewEvent.MAP_EXTENT, event -> {
            event.consume();
            map.setExtent(event.getExtent());
        });
        // add an observer for the map's center property to adjust the corresponding label
        map.centerProperty().addListener((observable, oldValue, newValue) -> {
            labelCenter.setText(newValue == null ? "" : ("center: " + newValue.toString()));
        });
        // add an observer to adjust the label
        map.zoomProperty().addListener((observable, oldValue, newValue) -> {
            labelZoom.setText(null == newValue ? "" : ("zoom: " + newValue.toString()));
        });
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
                "Import Window Help", "Not Implemented Yet");
    }
}
