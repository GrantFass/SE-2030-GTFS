/*
 * Authors: Becker, Ryan; Cross, Joy; Erickson, Simon; Fass, Grant;
 * Class: SE 2030 - 021
 * Team: G
 * Affiliation: Milwaukee School Of Engineering (MSOE)
 * Program Name: General Transit Feed Specification Tool
 * Copyright (C): GNU GPLv3; 9 November 2020
 *
 * This file is a part of the General Transit Feed Specification Tool
 * written by Team G of class SE 2030 - 021 at MSOE.
 *
 * This is a free software: it can be redistributed and/or modified
 * as expressed in the GNU GPLv3 written by the Free Software Foundation.
 *
 * This software is distributed in hopes that it is useful but does
 * not include any warranties, not even implied warranties. There is more
 * information about this in the GNU GPLv3.
 *
 * To view the license go to <gnu.org/licenses/gpl-3.0.en.html>
 */
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
        //region Create the stages for the individual windows
        Stage analysisWindowStage = new Stage();
        Stage dataWindowStage = new Stage();
        Stage exportWindowStage = new Stage();
        Stage importWindowStage = new Stage();
        Stage mapWindowStage = new Stage();
        Stage searchWindowStage = new Stage();
        Stage updateWindowStage = new Stage();
        //endregion

        //region Create the loaders for the individual windows
        FXMLLoader analysisWindowLoader = new FXMLLoader(getClass().getResource("../gui/AnalysisWindow.fxml"));
        FXMLLoader dataWindowLoader = new FXMLLoader(getClass().getResource("../gui/DataWindow.fxml"));
        FXMLLoader exportWindowLoader = new FXMLLoader(getClass().getResource("../gui/ExportWindow.fxml"));
        FXMLLoader importWindowLoader = new FXMLLoader(getClass().getResource("../gui/ImportWindow.fxml"));
        FXMLLoader mainWindowLoader = new FXMLLoader(getClass().getResource("../gui/MainWindow.fxml"));
        FXMLLoader mapWindowLoader = new FXMLLoader(getClass().getResource("../gui/MapWindow.fxml"));
        FXMLLoader searchWindowLoader = new FXMLLoader(getClass().getResource("../gui/SearchWindow.fxml"));
        FXMLLoader updateWindowLoader = new FXMLLoader(getClass().getResource("../gui/UpdateWindow.fxml"));
        //endregion

        //region Load the loaders
        analysisWindowLoader.load();
        dataWindowLoader.load();
        exportWindowLoader.load();
        importWindowLoader.load();
        mainWindowLoader.load();
        mapWindowLoader.load();
        searchWindowLoader.load();
        updateWindowLoader.load();
        //endregion

        //region Set the controllers
        AnalysisWindowController analysisWindowController = analysisWindowLoader.getController();
        DataWindowController dataWindowController = dataWindowLoader.getController();
        ExportWindowController exportWindowController = exportWindowLoader.getController();
        ImportWindowController importWindowController = importWindowLoader.getController();
        MainWindowController mainWindowController = mainWindowLoader.getController();
        MapWindowController mapWindowController = mapWindowLoader.getController();
        SearchWindowController searchWindowController = searchWindowLoader.getController();
        UpdateWindowController updateWindowController = updateWindowLoader.getController();
        //endregion

        //region create Scenes
        Scene analysisWindowScene = new Scene(analysisWindowLoader.getRoot());
        Scene dataWindowScene = new Scene(dataWindowLoader.getRoot());
        Scene exportWindowScene = new Scene(exportWindowLoader.getRoot());
        Scene importWindowScene = new Scene(importWindowLoader.getRoot());
        Scene mainWindowScene = new Scene(mainWindowLoader.getRoot());
        Scene mapWindowScene = new Scene(mapWindowLoader.getRoot());
        Scene searchWindowScene = new Scene(searchWindowLoader.getRoot());
        Scene updateWindowScene = new Scene(updateWindowLoader.getRoot());
        //endregion

        //region set Stylesheets
        String stylesheetLocation = "/gui/stylesheet.css";
        analysisWindowScene.getStylesheets().add(stylesheetLocation);
        dataWindowScene.getStylesheets().add(stylesheetLocation);
        exportWindowScene.getStylesheets().add(stylesheetLocation);
        importWindowScene.getStylesheets().add(stylesheetLocation);
        mainWindowScene.getStylesheets().add(stylesheetLocation);
        mapWindowScene.getStylesheets().add(stylesheetLocation);
        searchWindowScene.getStylesheets().add(stylesheetLocation);
        updateWindowScene.getStylesheets().add(stylesheetLocation);
        //endregion

        //region set Scenes
        analysisWindowStage.setScene(analysisWindowScene);
        dataWindowStage.setScene(dataWindowScene);
        exportWindowStage.setScene(exportWindowScene);
        importWindowStage.setScene(importWindowScene);
        mainWindowStage.setScene(mainWindowScene);
        mapWindowStage.setScene(mapWindowScene);
        searchWindowStage.setScene(searchWindowScene);
        updateWindowStage.setScene(updateWindowScene);
        //endregion

        //region set Titles
        analysisWindowStage.setTitle("General Transit Feed Specification Tool: Analysis");
        dataWindowStage.setTitle("General Transit Feed Specification Tool: Data");
        exportWindowStage.setTitle("General Transit Feed Specification Tool: Export");
        importWindowStage.setTitle("General Transit Feed Specification Tool: Import");
        mainWindowStage.setTitle("General Transit Feed Specification Tool: Main");
        mapWindowStage.setTitle("General Transit Feed Specification Tool: Map");
        searchWindowStage.setTitle("General Transit Feed Specification Tool: Search");
        updateWindowStage.setTitle("General Transit Feed Specification Tool: Update");
        //endregion

        //region set Stage Associations
        exportWindowController.setStages(mainWindowStage);
        importWindowController.setStages(mainWindowStage);
        mainWindowController.setStages(analysisWindowStage, dataWindowStage, exportWindowStage, importWindowStage, mainWindowStage, mapWindowStage, searchWindowStage, updateWindowStage);
        //endregion

        //region set Controller Associations
        dataWindowController.setControllers(mainWindowController);
        exportWindowController.setControllers(mainWindowController);
        importWindowController.setControllers(mainWindowController);
        mapWindowController.setControllers(updateWindowController);
        searchWindowController.setControllers(mainWindowController);
        updateWindowController.setControllers(mainWindowController);
        //endregion

        //region set default visibility
        analysisWindowStage.hide();
        dataWindowStage.hide();
        exportWindowStage.hide();
        importWindowStage.hide();
        mainWindowStage.show();
        mapWindowStage.hide();
        searchWindowStage.hide();
        updateWindowStage.hide();
        //endregion

        //region set default values
        analysisWindowController.setDefaultValues();
        dataWindowController.setDefaultValues();
        exportWindowController.setDefaultValues();
        importWindowController.setDefaultValues();
        mainWindowController.setDefaultValues();
        mapWindowController.setDefaultValues();
        searchWindowController.setDefaultValues();
        updateWindowController.setDefaultValues();
        //endregion

        //region Observer Setup
        mainWindowController.getData().attach(dataWindowController);
        mainWindowController.getData().attach(analysisWindowController);
        mainWindowController.getData().attach(mapWindowController);
        //endregion

        //region Set the default location of the main stage
        mainWindowStage.setX(0);
        mainWindowStage.setY(0);
        //endregion
    }


    public static void main(String[] args) {
        launch(args);
    }
}
