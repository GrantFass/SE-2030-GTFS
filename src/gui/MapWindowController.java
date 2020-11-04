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
import data.Trip;
import interfaces.Observer;
import javafx.application.Platform;
import javafx.concurrent.Task;
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
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

/**
 * MapWindowController Purpose: Controller for the Map Window
 *
 * @author Grant
 * @version Created on 10/25/2020 at 1:27 AM
 */
public class MapWindowController implements Observer {
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

    /**
     * plots a single coordinate as a marker onto the map
     * @param latitude the latitude to place the marker at
     * @param longitude the longitude to place the marker at
     * @author Grant Fass
     */
    private void plotCoordinate(double latitude, double longitude) {
        map.addMarker(Marker.createProvided(Marker.Provided.BLUE)
                .setPosition(new Coordinate(latitude, longitude))
                .setVisible(true));
    }

    /**
     * Plots a coordinate line onto the map
     * @param route the route that all of the stops are associated to (Used for route color)
     *              TODO: Fix This!
     * @param stops the stops to plot on the line
     * @author Grant Fass
     */
    private void plotCoordinateLine (Route route, ArrayList<Stop> stops) {
        final boolean fillRoutes = false;
        final int routeWidth = 3;
        /*
         * Task to generate the coordinate line that is created from all of the stops associated
         * with a single route
         */
        Task<CoordinateLine> task = new Task<>() {
            @Override
            protected CoordinateLine call() throws Exception {
                ArrayList<Coordinate> coordinates = new ArrayList<>();
                for (Stop stop : stops) {
                    coordinates.add(new Coordinate(stop.getStopLatitude(), stop.getStopLongitude()));
                }
                return new CoordinateLine(coordinates)
                        .setVisible(true)
                        .setColor(route.getRouteColor())
                        .setWidth(routeWidth)
                        .setClosed(fillRoutes)
                        .setFillColor(Color.web("lawngreen", 0.5));
            }
        };
        task.setOnSucceeded(e -> {
            map.addCoordinateLine(task.getValue());
            try {
                map.setExtent(Extent.forCoordinates(task.getValue().getCoordinateStream().collect(Collectors.toList())));
            } catch (IllegalArgumentException ignored) { }
        });
        new Thread(task).start();
    }

    /**
     * plots all of the stops associated with each route in a hashmap
     * @param hashMap the route and stops combinations to plot
     * @author Grant Fass
     */
    private void plotStops(@NotNull HashMap<Route, ArrayList<Stop>> hashMap) {
        for(Route route: hashMap.keySet()) {
            plotCoordinateLine(route, hashMap.get(route));
        }
    }

    /**
     * retrieves all of the stops that are associated with all routes
     * @return a HashMap containing all of the Stops associated with all Routes
     * @author Grant Fass
     */
    private HashMap<Route, ArrayList<Stop>> getStopsPerRoute() {
        Data data = mainWindowController.getData();
        //Create a list of all of the keys in StopTimes. Key Format = 'stop_id;trip_id'
        String[] stopTimeKeys = data.getStopTimes().getStop_times().keySet().toArray(new String[0]);
        //separate stopTimeKeys into stop_id values and trip_id values
        ArrayList<String> stop_ids = new ArrayList<>();
        ArrayList<String> trip_ids = new ArrayList<>();
        for(String s:stopTimeKeys) {
            stop_ids.add(s.substring(0, s.indexOf(';')));
            trip_ids.add(s.substring(s.indexOf(';') + 1));
        }
        //convert stop_ids to Stops
        ArrayList<Stop> stops = new ArrayList<>();
        for(String stop_id:stop_ids) {
            stops.add(data.getStops().getStop(stop_id));
        }
        //convert trip_ids to Routes
        ArrayList<Route> routes = new ArrayList<>();
        for(String trip_id:trip_ids) {
            Trip trip = data.getTrips().getTrip(trip_id);
            Route route = new Route("-1", "", "-1", "Null Route", "Null Route",
                    "", "", Color.web("black").toString(), "",
                    "", "", "");
            if (trip != null) {
                route = data.getRoutes().getRoute(trip.getRouteID());
            }
            routes.add(route);
        }
        //transfer data into HashMap
        HashMap<Route, ArrayList<Stop>> stopsPerRoute = new HashMap<>();
        for (int i = 0; i < routes.size(); i++) {
            //If the Route already exists in the map then add the stop to the list of stops
            //Otherwise create a new list of stops and add the route and stop to the map
            //Do not include the stop if it already exists for a route
            if (stopsPerRoute.containsKey(routes.get(i)) && !stopsPerRoute.get(routes.get(i)).contains(stops.get(i))) {
                stopsPerRoute.get(routes.get(i)).add(stops.get(i));
            } else if (routes.get(i) != null) {
                ArrayList<Stop> stopsInRoute = new ArrayList<>();
                stopsInRoute.add(stops.get(i));
                stopsPerRoute.put(routes.get(i), stopsInRoute);
            }
        }
        //Return Map
        return stopsPerRoute;
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
        try {
            if (data.getRoutes() != null && data.getStops() != null && data.getStopTimes() != null && data.getTrips()!= null) {
                if (Platform.isFxApplicationThread()) {
                    plotStops(getStopsPerRoute());
                } else {
                    Platform.runLater(() -> plotStops(getStopsPerRoute()));
                }
            }
        } catch (ConcurrentModificationException e) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            update(data);
        }
    }
}
