package main;

import gui.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * Start the program and load all relevant controllers and windows
     * @param mainWindowStage The initial stage to load
     * @throws Exception if something goes wrong
     * @author Grant Fass
     */
    @Override
    public void start(Stage mainWindowStage) throws Exception{
        //Create the stages for the individual windows
        Stage analysisWindowStage = new Stage();
        Stage dataWindowStage = new Stage();
        Stage exportWindowStage = new Stage();
        Stage importWindowStage = new Stage();
        Stage mapWindowStage = new Stage();
        Stage searchWindowStage = new Stage();
        Stage updateWindowStage = new Stage();

        //Create the loaders for the individual windows
        FXMLLoader analysisWindowLoader = new FXMLLoader(getClass().getResource("../gui/AnalysisWindow.fxml"));
        FXMLLoader dataWindowLoader = new FXMLLoader(getClass().getResource("../gui/DataWindow.fxml"));
        FXMLLoader exportWindowLoader = new FXMLLoader(getClass().getResource("../gui/ExportWindow.fxml"));
        FXMLLoader importWindowLoader = new FXMLLoader(getClass().getResource("../gui/ImportWindow.fxml"));
        FXMLLoader mainWindowLoader = new FXMLLoader(getClass().getResource("../gui/MainWindow.fxml"));
        FXMLLoader mapWindowLoader = new FXMLLoader(getClass().getResource("../gui/MapWindow.fxml"));
        FXMLLoader searchWindowLoader = new FXMLLoader(getClass().getResource("../gui/SearchWindow.fxml"));
        FXMLLoader updateWindowLoader = new FXMLLoader(getClass().getResource("../gui/UpdateWindow.fxml"));

        //Load the loaders
        analysisWindowLoader.load();
        dataWindowLoader.load();
        exportWindowLoader.load();
        importWindowLoader.load();
        mainWindowLoader.load();
        mapWindowLoader.load();
        searchWindowLoader.load();
        updateWindowLoader.load();

        //Set the controllers
        AnalysisWindowController analysisWindowController = analysisWindowLoader.getController();
        DataWindowController dataWindowController = dataWindowLoader.getController();
        ExportWindowController exportWindowController = exportWindowLoader.getController();
        ImportWindowController importWindowController = importWindowLoader.getController();
        MainWindowController mainWindowController = mainWindowLoader.getController();
        MapWindowController mapWindowController = mapWindowLoader.getController();
        SearchWindowController searchWindowController = searchWindowLoader.getController();
        UpdateWindowController updateWindowController = updateWindowLoader.getController();

        //create Scenes
        Scene analysisWindowScene = new Scene(analysisWindowLoader.getRoot());
        Scene dataWindowScene = new Scene(dataWindowLoader.getRoot());
        Scene exportWindowScene = new Scene(exportWindowLoader.getRoot());
        Scene importWindowScene = new Scene(importWindowLoader.getRoot());
        Scene mainWindowScene = new Scene(mainWindowLoader.getRoot());
        Scene mapWindowScene = new Scene(mapWindowLoader.getRoot());
        Scene searchWindowScene = new Scene(searchWindowLoader.getRoot());
        Scene updateWindowScene = new Scene(updateWindowLoader.getRoot());

        //set Stylesheets
        String stylesheetLocation = "/gui/stylesheet.css";
        analysisWindowScene.getStylesheets().add(stylesheetLocation);
        dataWindowScene.getStylesheets().add(stylesheetLocation);
        exportWindowScene.getStylesheets().add(stylesheetLocation);
        importWindowScene.getStylesheets().add(stylesheetLocation);
        mainWindowScene.getStylesheets().add(stylesheetLocation);
        mapWindowScene.getStylesheets().add(stylesheetLocation);
        searchWindowScene.getStylesheets().add(stylesheetLocation);
        updateWindowScene.getStylesheets().add(stylesheetLocation);

        //set Scenes
        analysisWindowStage.setScene(analysisWindowScene);
        dataWindowStage.setScene(dataWindowScene);
        exportWindowStage.setScene(exportWindowScene);
        importWindowStage.setScene(importWindowScene);
        mainWindowStage.setScene(mainWindowScene);
        mapWindowStage.setScene(mapWindowScene);
        searchWindowStage.setScene(searchWindowScene);
        updateWindowStage.setScene(updateWindowScene);

        //set Titles
        analysisWindowStage.setTitle("General Transit Feed Specification Tool: Analysis");
        dataWindowStage.setTitle("General Transit Feed Specification Tool: Data");
        exportWindowStage.setTitle("General Transit Feed Specification Tool: Export");
        importWindowStage.setTitle("General Transit Feed Specification Tool: Import");
        mainWindowStage.setTitle("General Transit Feed Specification Tool: Main");
        mapWindowStage.setTitle("General Transit Feed Specification Tool: Map");
        searchWindowStage.setTitle("General Transit Feed Specification Tool: Search");
        updateWindowStage.setTitle("General Transit Feed Specification Tool: Update");

        //set Stage Associations
        analysisWindowController.setStages(analysisWindowStage, dataWindowStage, exportWindowStage, importWindowStage, mainWindowStage, mapWindowStage, searchWindowStage, updateWindowStage);
        dataWindowController.setStages(analysisWindowStage, dataWindowStage, exportWindowStage, importWindowStage, mainWindowStage, mapWindowStage, searchWindowStage, updateWindowStage);
        exportWindowController.setStages(analysisWindowStage, dataWindowStage, exportWindowStage, importWindowStage, mainWindowStage, mapWindowStage, searchWindowStage, updateWindowStage);
        importWindowController.setStages(analysisWindowStage, dataWindowStage, exportWindowStage, importWindowStage, mainWindowStage, mapWindowStage, searchWindowStage, updateWindowStage);
        mainWindowController.setStages(analysisWindowStage, dataWindowStage, exportWindowStage, importWindowStage, mainWindowStage, mapWindowStage, searchWindowStage, updateWindowStage);
        mapWindowController.setStages(analysisWindowStage, dataWindowStage, exportWindowStage, importWindowStage, mainWindowStage, mapWindowStage, searchWindowStage, updateWindowStage);
        searchWindowController.setStages(analysisWindowStage, dataWindowStage, exportWindowStage, importWindowStage, mainWindowStage, mapWindowStage, searchWindowStage, updateWindowStage);
        updateWindowController.setStages(analysisWindowStage, dataWindowStage, exportWindowStage, importWindowStage, mainWindowStage, mapWindowStage, searchWindowStage, updateWindowStage);

        //set Controller Associations
        analysisWindowController.setControllers(dataWindowController, exportWindowController, importWindowController, mainWindowController, mapWindowController, searchWindowController, updateWindowController);
        dataWindowController.setControllers(analysisWindowController, exportWindowController, importWindowController, mainWindowController, mapWindowController, searchWindowController, updateWindowController);
        exportWindowController.setControllers(analysisWindowController, dataWindowController, importWindowController, mainWindowController, mapWindowController, searchWindowController, updateWindowController);
        importWindowController.setControllers(analysisWindowController, dataWindowController, exportWindowController, mainWindowController, mapWindowController, searchWindowController, updateWindowController);
        mainWindowController.setControllers(analysisWindowController, dataWindowController, exportWindowController, importWindowController, mapWindowController, searchWindowController, updateWindowController);
        mapWindowController.setControllers(analysisWindowController, dataWindowController, exportWindowController, importWindowController, mainWindowController, searchWindowController, updateWindowController);
        searchWindowController.setControllers(analysisWindowController, dataWindowController, exportWindowController, importWindowController, mainWindowController, mapWindowController, updateWindowController);
        updateWindowController.setControllers(analysisWindowController, dataWindowController, exportWindowController, importWindowController, mainWindowController, mapWindowController, searchWindowController);

        //set default visibility
        analysisWindowStage.hide();
        dataWindowStage.hide();
        exportWindowStage.hide();
        importWindowStage.hide();
        mainWindowStage.show();
        mapWindowStage.hide();
        searchWindowStage.hide();
        updateWindowStage.hide();

        //set default values
        analysisWindowController.setDefaultValues();
        dataWindowController.setDefaultValues();
        exportWindowController.setDefaultValues();
        importWindowController.setDefaultValues();
        mainWindowController.setDefaultValues();
        mapWindowController.setDefaultValues();
        searchWindowController.setDefaultValues();
        updateWindowController.setDefaultValues();

        //set up observers
        mainWindowController.getData().attach(dataWindowController);
        mainWindowController.getData().attach(analysisWindowController);
        mainWindowController.getData().attach(mapWindowController);

        //Set the default location of the main stage
        mainWindowStage.setX(0);
        mainWindowStage.setY(0);

        //functions
        //dataWindowStage.setOnCloseRequest(event -> mainWindowController.viewDataDisplayButton.setText("Show Data Display"));
    }


    public static void main(String[] args) {
        launch(args);
    }
}
