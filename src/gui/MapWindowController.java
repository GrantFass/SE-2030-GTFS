/*
 * gui
 * File Header Contains Class MapWindowController
 * Name: Grant
 * Created 10/25/2020
 */
package gui;

import com.sothawo.mapjfx.*;
import com.sothawo.mapjfx.event.MapViewEvent;
import data.Data;
import data.Route;
import data.Stop;
import interfaces.Observer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * MapWindowController Purpose: Controller for the Map Window
 *
 * @author Grant
 * @version Created on 10/25/2020 at 1:27 AM
 */
public class MapWindowController implements Observer {
    //region FXML properties
    @FXML
    private ToggleButton enableStopUpdateToggle;
    @FXML
    private ToggleButton useRandomRouteColorsToggle;
    @FXML
    private ListView routeListView;
    @FXML
    private ListView busListView;
    @FXML
    private Label labelCenter;
    @FXML
    private Label labelZoom;
    @FXML
    private MapView map;
    @FXML
    private TextArea description;
    //endregion

    //region class references
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
     * @param searchWindowController   reference to the SearchWindowController in use
     * @param updateWindowController   reference to the UpdateWindowController in use
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
    //endregion

    //region displayed help information
    /**
     * set the default values of the description
     * set up the map view and initialize it
     * based on Version 2.3.0 of mapjfx from https://www.sothawo.com/projects/mapjfx/
     *
     * @author Grant Fass
     */
    public void setDefaultValues() {
        description.setText("Hold 'CTRL' then drag over the map to zoom.\nMap will not update unless all classes have been loaded");
        map.setAnimationDuration(100);
        // add listener for mapView initialization state
        map.initializedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                // a map is only displayed when an initial coordinate is set

                map.setCenter(msoeAthleticField);

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
                map.addLabel(new MapLabel("Loading Map...").setPosition(msoeAthleticField)
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
            labelCenter.setText(newValue == null ? "" : String.format("Center: [lat = %10.6f, long = %10.6f]", newValue.getLatitude(), newValue.getLongitude()));
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
    //endregion

    //region methods for plotting routes
    /**
     * method to display all routeIDs to the associated List View
     * @param hashMap the mapping to get all routeIDs from
     * @author Grant Fass
     */
    private void displayRouteIDs(HashMap<Route, ArrayList<Stop>> hashMap) {
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Route route : hashMap.keySet()) {
            items.add(route.getRouteID());
        }
        routeListView.setItems(items);
    }

    /**
     * Creates a CoordinateLine from the specified values
     *
     * @param route                the route to get the default color from for the line
     * @param stops                the stops to plot on the CoordinateLine
     * @param overrideDefaultColor will set the line to a random color if true
     * @return the generated CoordinateLine
     * @author Grant Fass
     */
    private CoordinateLine getCoordinateLine(Route route, ArrayList<Stop> stops, boolean overrideDefaultColor) {
        if (stops == null) {
            return null;
        }
        final double red = Math.random();
        final double green = Math.random();
        final double blue = Math.random();
        final double alpha = 1;
        final Color color = new Color(red, green, blue, alpha);
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        for (Stop stop : stops) {
            if (stop != null) {
                coordinates.add(new Coordinate(stop.getStopLatitude(), stop.getStopLongitude()));
            }
        }
        CoordinateLine coordinateLine = new CoordinateLine(coordinates).setVisible(true);
        if (overrideDefaultColor) {
            coordinateLine.setColor(color);
        } else {
            coordinateLine.setColor(route.getRouteColor());
        }
        return coordinateLine;
    }

    /**
     * plots a specified CoordinateLine on the map and changes the map view to contain the line
     *
     * @param coordinateLine the CoordinateLine to plot
     * @author Grant Fass
     */
    private void plotCoordinateLine(CoordinateLine coordinateLine) {
        List<Coordinate> coordinates = coordinateLine.getCoordinateStream().collect(Collectors.toList());
        map.addCoordinateLine(coordinateLine);
        try {
            map.setExtent(Extent.forCoordinates(coordinates));
        } catch (IllegalArgumentException ignored) {
        }
    }

    /**
     * removes the specified CoordinateLine from the map
     *
     * @param coordinateLine the CoordinateLine to remove
     * @author Grant Fass
     */
    private void removeCoordinateLine(CoordinateLine coordinateLine) {
        map.removeCoordinateLine(coordinateLine);
    }

    /**
     * updates the displayed routes
     *
     * @param data the data to use to update the routes
     * @author Grant Fass
     */
    private void updateRoutes(Data data) {
        HashMap<Route, ArrayList<Stop>> stopsPerRoute = data.getStopsPerRoute();
        displayRouteIDs(stopsPerRoute);
        //Update the displayed route whenever a new route is clicked in the routeListView
        routeListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            CoordinateLine lastCoordinateLine;

            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (lastCoordinateLine != null) {
                    removeCoordinateLine(lastCoordinateLine);
                }
                Route route = data.getRoutes().getRoute(newValue);
                if (route != null) {
                    lastCoordinateLine = getCoordinateLine(route, stopsPerRoute.get(route), useRandomRouteColorsToggle.isSelected());
                    if (lastCoordinateLine != null) {
                        plotCoordinateLine(lastCoordinateLine);
                    }
                }
            }
        });
    }
    //endregion

    //region methods for plotting busses
    /**
     * method to display all busses to the associated List View
     * @param busLocations the mapping of all bus locations
     * @author Grant Fass
     */
    private void displayBusses(ArrayList<double[]> busLocations) {
        int count = 0;
        ObservableList<String> items = FXCollections.observableArrayList();
        for (double[] busLocation : busLocations) {
            items.add("Bus: " + count);
            count++;
        }
        busListView.setItems(items);
    }

    /**
     * plots a marker on the map at the selected bus location
     *
     * @param data the data class used to get the list of bus coordinates from
     * @author Grant Fass
     */
    private void updateBusses(Data data) {
        ArrayList<double[]> busCoordinates = data.getBusCoordinates();
        displayBusses(busCoordinates);
        final Marker marker = Marker.createProvided(Marker.Provided.ORANGE).setPosition(msoeAthleticField).setVisible(true);
        map.addMarker(marker);
        //Update the displayed bus whenever a new bus is clicked in the busListView
        busListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                double[] coordinates = busCoordinates.get(Integer.parseInt(newValue.substring(5).trim()));
                if (coordinates.length >= 2) {
                    Coordinate coordinate = new Coordinate(coordinates[0], coordinates[1]);
                    marker.setPosition(coordinate);
                }
            }
        });
    }
    //endregion

    //region methods for plotting stops
    /**
     * creates and returns the absolute value of the input value
     *
     * @param value the value to make positive
     * @return the absolute value of the input value
     * @author Grant Fass
     */
    private double absoluteValue(double value) {
        return value < 0 ? value * -1 : value;
    }

    /**
     * finds the percent difference between the two values
     *
     * @param value1 the first value to use for percent difference
     * @param value2 the second value to use for percent difference
     * @return the percent difference between the two numbers
     * @author Grant Fass
     */
    private double percentDifference(double value1, double value2) {
        double valueSum = absoluteValue(value1 + value2);
        double valueDifference = absoluteValue(value1 - value2);
        return (valueDifference / (valueSum / 2)) * 100;
    }

    /**
     * calculates the closest stop to the location the user clicked on the map at by calculating
     * the percent difference of the latitudes and longitudes between the click location and all
     * stops.
     *
     * @param stopsPerRoute the map containing all of the stops associated with all routes
     * @return the Stop that is closest to the click location
     * @author Grant Fass
     */
    private Stop getClosestStopToClick(HashMap<Route, ArrayList<Stop>> stopsPerRoute, Coordinate coordinateOfClick) {
        double lowestDifference = 9999;
        Stop closestStop = null;
        for (Route route : stopsPerRoute.keySet()) {
            for (Stop stop : stopsPerRoute.get(route)) {
                double difference = percentDifference(coordinateOfClick.getLatitude(), stop.getStopLatitude()) + percentDifference(coordinateOfClick.getLongitude(), stop.getStopLongitude());
                if (lowestDifference == 9999 || lowestDifference > difference) {
                    lowestDifference = difference;
                    closestStop = stop;
                }
            }
        }
        return closestStop;
    }

    /**
     * Will update stops if button to update stops is enabled
     * If the user clicks on the map then the closest stop to the point will be automatically passed
     * to the Update window to update values
     *
     * @param data the data class to use to find the closest stop
     * @author Grant Fass,
     */
    private void updateStop(Data data) {
        final HashMap<Route, ArrayList<Stop>> stopsPerRoute = data.getStopsPerRoute();
        map.addEventHandler(MapViewEvent.MAP_CLICKED, mapViewEvent -> {
            if (enableStopUpdateToggle.isSelected()) {
                Stop closestStop = getClosestStopToClick(stopsPerRoute, mapViewEvent.getCoordinate());
                updateWindowController.setObjectToUpdate(closestStop);
            }
        });
    }
    //endregion

    //region observer pattern update
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
        if (!data.getRoutes().getRoutes().isEmpty() && !data.getStops().getStops().isEmpty() && !data.getStopTimes().getStop_times().isEmpty() && !data.getTrips().getTrips().isEmpty()) {
            updateRoutes(data);
            updateBusses(data);
            updateStop(data);
        } else {
            //clear information
            displayBusses(new ArrayList<>());
            displayRouteIDs(new HashMap<>());
        }
    }
    //endregion
}
