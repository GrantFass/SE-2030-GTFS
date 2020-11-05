package gui;

import data.Data;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainWindowController {
	//region FXML properties
	@FXML
	private TextArea description;
	//endregion

	//region class references
	private Data data = new Data();
	private Stage analysisWindowStage;
	private AnalysisWindowController analysisWindowController;
	private Stage dataWindowStage;
	private DataWindowController dataWindowController;
	private Stage exportWindowStage;
	private ExportWindowController exportWindowController;
	private Stage importWindowStage;
	private ImportWindowController importWindowController;
	private Stage mainWindowStage;
	private Stage mapWindowStage;
	private MapWindowController mapWindowController;
	private Stage searchWindowStage;
	private SearchWindowController searchWindowController;
	private Stage updateWindowStage;
	private UpdateWindowController updateWindowController;
	private static final Rectangle2D PRIMARY_SCREEN_BOUNDS = Screen.getPrimary().getVisualBounds();
	private static final double WINDOW_WIDTH = PRIMARY_SCREEN_BOUNDS.getWidth() / 3.0;
	private static final double WINDOW_HEIGHT = PRIMARY_SCREEN_BOUNDS.getHeight() / 3.0;


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
		mainWindowStage.setWidth(WINDOW_WIDTH);
		mainWindowStage.setHeight(WINDOW_HEIGHT);
	}

	/**
	 * Sets the values of the controller associated with the respective files
	 * Makes sure the same instance of the controller is used everywhere
	 * @param analysisWindowController reference to the AnalysisWindowController in use
	 * @param dataWindowController reference to the DataWindowController in use
	 * @param exportWindowController reference to the ExportWindowController in use
	 * @param importWindowController reference to the ImportWindowController in use
	 * @param mapWindowController reference to the MapWindowController in use
	 * @param searchWindowController reference to the SearchWindowController in use
	 * @param updateWindowController reference to the UpdateWindowController in use
	 * @author Grant Fass
	 */
	public void setControllers(AnalysisWindowController analysisWindowController,
								DataWindowController dataWindowController,
								ExportWindowController exportWindowController,
								ImportWindowController importWindowController,
								MapWindowController mapWindowController,
								SearchWindowController searchWindowController,
								UpdateWindowController updateWindowController) {
		this.analysisWindowController = analysisWindowController;
		this.dataWindowController = dataWindowController;
		this.exportWindowController = exportWindowController;
		this.importWindowController = importWindowController;
		this.mapWindowController = mapWindowController;
		this.searchWindowController = searchWindowController;
		this.updateWindowController = updateWindowController;
	}
	//endregion

	//region displayed help information
	/**
	 * set the default values of the description
	 * @author Grant Fass
	 */
	public void setDefaultValues() {
		description.setText("This is the main window of the program. All functions can " +
				"be accessed from this window. Click on one of the buttons below to go to get started.");
	}

	/**
	 * Method to display help values to the program
	 * Activates when help menu button is clicked
	 * @author Grant Fass
	 */
	@FXML
	private void displayHelp() {
		displayAlert(Alert.AlertType.INFORMATION, "General Transit Feed Specification Tool Information",
				"Main Window Help", "This is the main window of the General Transit" +
						" Feed Specification Tool. To get started please click on one of the buttons" +
						" on the main page." +
						"\n'Import' will open a window used to import GTFS files into the program" +
						"\n'Update' will open a window used to update information in the GTFS files" +
						"\n'Export' will open a window used to export the data in the program as GTFS files" +
						"\n'Data' will open a window used to view the data stored in the program" +
						"\n'Analysis' will open a window used to view information calculated from the stored data" +
						"\n'Search' will open a window used to search for pieces of stored data" +
						"\n'Map' will open a window used to display some of the stored data" +
						"\n'About' will open an alert with information about the program" +
						"\n'Exit' will safely shut down the program");
	}
	//endregion

	/**
	 * returns a copy of the data object
	 * should not return the actual data object for security reasons
	 * @return copy of the data object
	 * @author Grant Fass
	 */
	public Data getData() {
		//TODO add copy constructors in all relevant classes so this method returns a deep copy instead of the actual values.
		return data;
	}

	/**
	 * display an alert with the specified format and values
	 * @param alertType the type of alert to display
	 * @param title the title of the alert
	 * @param header the header text to display in the alert
	 * @param content the content text to display in the alert
	 * @author Grant Fass
	 */
	 public static void displayAlert(Alert.AlertType alertType, String title, String header, String content) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

	//region methods for toggling stage visibility
	/**
	 * method to toggle the visibility of a given stage
	 * @param stage the stage to toggle the visibility of
	 * @param xOffset the number of pixels to offset the window. Positive values move the window right.
	 * @param yOffset the number of pixels to offset the window. Positive values move the window down.
	 * @author Grant Fass
	 */
	private void toggleStage(Stage stage, double xOffset, double yOffset){
		if (stage.isShowing()) {
			stage.hide();
		} else {
			final int windowOffset = 15;
			stage.setX(mainWindowStage.getX() + xOffset);
			stage.setY(mainWindowStage.getY() + yOffset);
			stage.setWidth(WINDOW_WIDTH);
			stage.setHeight(WINDOW_HEIGHT);
			stage.toFront();
			stage.show();
		}
	}

	/**
	 * toggle the visibility status of the AnalysisWindow
	 * @author Grant Fass
	 */
	@FXML
	private void toggleAnalysisWindow() {
		//open the analysis window under the main window and to the right two stages
		toggleStage(analysisWindowStage, 2 * mainWindowStage.getWidth(), mainWindowStage.getHeight());
	}

	/**
	 * toggle the visibility status of the DataWindow
	 * @author Grant Fass
	 */
	@FXML
	private void toggleDataWindow() {
		//open the data window under the main window and to the right one stage
		toggleStage(dataWindowStage, mainWindowStage.getWidth(), mainWindowStage.getHeight());
	}

	/**
	 * toggle the visibility status of the ExportWindow
	 * @author Grant Fass
	 */
	@FXML
	private void toggleExportWindow() {
		//open the export window 2 stages right of the main window
		toggleStage(exportWindowStage, 2 * mainWindowStage.getWidth(), 0);
	}

	/**
	 * toggle the visibility status of the ImportWindow
	 * @author Grant Fass
	 */
	@FXML
	private void toggleImportWindow() {
		//open the import window directly to the right of the main window
		toggleStage(importWindowStage, mainWindowStage.getWidth(), 0);
	}

	/**
	 * toggle the visibility status of the MapWindow
	 * @author Grant Fass
	 */
	@FXML
	private void toggleMapWindow() {
		//open the map window two stages down and one stage right from the main window
		toggleStage(mapWindowStage, mainWindowStage.getWidth(), 2 * mainWindowStage.getHeight());
	}

	/**
	 * toggle the visibility status of the SearchWindow
	 * @author Grant Fass
	 */
	@FXML
	private void toggleSearchWindow() {
		//open the search window directly under the main window
		toggleStage(searchWindowStage, 0, mainWindowStage.getHeight());
	}

	/**
	 * toggle the visibility status of the UpdateWindow
	 * @author Grant Fass
	 */
	@FXML
	private void toggleUpdateWindow() {
		//open the update window two stages down from the main window
		toggleStage(updateWindowStage, 0, 2 * mainWindowStage.getHeight());
	}
	//endregion

	//region menu commands that do not trigger stages
	/**
	 * display information to the user about the programs creators
	 * @author Grant Fass
	 */
	@FXML
	private void displayAbout() {
		displayAlert(Alert.AlertType.INFORMATION, "General Transit Feed Specification Tool Information: About",
				null, "This program is designed to allow users to import, manipulate, and export" +
						" GTFS files that are consistent with the specifications from the Google Transit GTFS" +
						" Reference pages. Files are expected to be named as 'stops.txt', 'stop_times.txt'," +
						" 'routes.txt', or 'trips.txt'.\nThis program was developed by Group G of the" +
						" Software Engineering Tools And Practices class (SE 2030 - 021) at the Milwaukee School" +
						" Of Engineering (MSOE) in the Fall term of 2020.\n" +
						"Members: Grant Fass, Joy Cross, Simon Erickson, & Ryan Becker.");
	}

	/**
	 * Method to safely close and exit the program.
	 * Any connections that have a possibility of being open are verified to be closed here.
	 * @author Grant Fass
	 */
	public void close() {
		System.exit(0);
	}
	//endregion
}
