package SE2030TransitProject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Time;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.zip.DataFormatException;

public class Controller {
	@FXML
	private Label topLabel;
	@FXML
	private TextArea textArea;
	public MenuItem viewDataDisplayButton;
	private Data data = new Data();
	private Stage stage;
	private Stage dataDisplayStage;
	private DataDisplayController dataDisplayController;

	/**
	 * used to set some default values
	 * called from Main
	 * @author Grant Fass
	 */
	public void setDefaultValues() {
		textArea.setText("How To Use:\n\n" +
				"Load Files:\n\t" +
				"Go to File -> Load. this will open a FileChooser.\n\t" +
				"Browse to the directory where your GTFS files are stored.\n\t" +
				"By default strict file matching is enabled, as such use the extension selector dropdown.\n\t\t" +
				"in the bottom right of the FileChooser to chose the file type you would like to import.\n\t" +
				"After selecting your file type double click on your file to load it.\n\t" +
				"The FileChooser will close and a confirmation that files were loaded will be displayed.\n\t" +
				"A warning will be displayed if previous files were overwritten.\n\n" +
				"View Files:\n\t" +
				"Go to View -> Show Data Display. this will open the Data Display Window.\n\t" +
				"In the Data Display Window go to View then the file you would like to view.\n\n" +
				"Export Files:\n\t" +
				"Go to the Export menu and then select the file type that you would like to export.\n\t" +
				"A directory chooser will then appear. Use this window to select where to save the exported file to.\n\t");
	}

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
	 * Set the value of the stage associated with this file
	 * @param stage the stage value to use
	 * @author Grant Fass
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Set the value of the stage associated with the Data Display
	 * @param dataDisplayStage the data display stage value to use
	 * @author Grant Fass
	 */
	public void setDataDisplayStage(Stage dataDisplayStage) {
		this.dataDisplayStage = dataDisplayStage;
	}

	/**
	 * Set the value of the controller associated with the Data Display
	 * Should make sure that the same instance of the controller is used everywhere
	 * @param dataDisplayController the data display controller value to use
	 * @author Grant Fass
	 */
	public void setDataDisplayController(DataDisplayController dataDisplayController) {
		this.dataDisplayController = dataDisplayController;
	}

	/**
	 * @param trip_id
	 */
	public void displayDistance(String trip_id){

	}

	/**
	 * 
	 * @param trip_id
	 */
	public void displaySpeeds(String trip_id){

	}

	/**
	 * 
	 * @param stop_id
	 */
	public void displayTrips(String stop_id){

	}

	/**
	 * 
	 * @param outputLocation
	 */
	public void exportMultipleGTFSFiles(Path outputLocation){

	}

	/**
	 * 
	 * @param gtfsFileLocation
	 */
	public void importMultipleGTFSFiles(Path gtfsFileLocation){

	}

	/**
	 * 
	 * @param route_id
	 */
	public void plotBus(String route_id){

	}

	/**
	 * 
	 * @param route_id
	 */
	public void plotGPSCoordinates(String route_id){

	}

	/**
	 * 
	 * @param stop_id: String
	 */
	public Collection<Route> searchRoutesByStop(String stop_id){
		return null;
	}

	/**
	 * 
	 * @param route_id: String
	 */
	public Collection<Stop> searchStopsByRoute(String route_id){
		return null;
	}

	/**
	 * 
	 * @param route_id: String
	 */
	public Collection<Trip> searchTripsByRoute(String route_id){
		return null;
	}

	/**
	 * 
	 * @param stop_id: String
	 */
	public Collection<Trip> searchTripsByStop(String stop_id){
		return null;
	}

	/**
	 * 
	 * @param route_id: String, attributes
	 */
	public void updateRoute(String route_id){

	}

	/**
	 * 
	 * @param stop_id: String, attributes
	 */
	public void updateStop(String stop_id){

	}

	/**
	 * 
	 * @param stop_ids: List<Strings>, attributes
	 */
	public void updateStops(Collection<String> stop_ids){

	}

	/**
	 * 
	 * @param stop_id: String, trip_id: String, time
	 */
	public void updateStopTime(String stop_id, String trip_id, Time time){

	}

	/**
	 * 
	 * @param trip_id: String, attributes
	 */
	public void updateTrip(String trip_id){

	}

	/**
	 * Method to safely close and exit the program.
	 * Any connections that have a possibility of being open are verified to be closed here.
	 * @author Grant Fass
	 */
    public void close() {
		System.exit(0);
    }

	public void save() {
		displayAlert(Alert.AlertType.INFORMATION, "Information", null, "Not Yet Implemented!");
	}

	/**
	 * Method to handle the loading of GTFS files
	 * Activates when load menu button is clicked
	 * Handles exceptions relating to loading files
	 * Displays a snapshot of data after the files are loaded.
	 * @author Grant Fass
	 */
	public void load() {
		try {
			File file = getGTFSFileUsingFileChooser();
			checkNullFile(file);
			checkFileExtension(file.toString().substring(file.toString().indexOf('.')));
			String prefix = checkFilePrefix(file.toString().substring(0,
					file.toString().indexOf('.')));
			boolean wasLineSkipped = false;
			switch (prefix) {
				case "stop_times":
					wasLineSkipped = data.getStopTimes().loadStopTimes(file);
					break;
				case "stops":
					wasLineSkipped = data.getStops().loadStops(file);
					break;
				case "routes":
					wasLineSkipped = data.getRoutes().loadRoutes(file);
					break;
				case "trips":
					wasLineSkipped = data.getTrips().loadTrips(file);
					break;
			}

			dataDisplayController.resetTextToDefaultValues();
			if (!wasLineSkipped) {
				displayAlert(Alert.AlertType.INFORMATION, "Information", null, "Data Imported Successfully");
			} else {
				displayAlert(Alert.AlertType.WARNING, "Warning", null, "One or more lines in the file that was loaded were incorrectly formatted and skipped");
			}
		} catch (IllegalArgumentException e) {
			displayAlert(Alert.AlertType.ERROR, "Error","IllegalArgumentException", e.getMessage());
			dataDisplayController.resetTextToDefaultValues();
		} catch (InputMismatchException e) {
			displayAlert(Alert.AlertType.ERROR, "Error","InputMismatchException", e.getMessage());
			dataDisplayController.resetTextToDefaultValues();
		} catch (FileNotFoundException e) {
			displayAlert(Alert.AlertType.ERROR, "Error","FileNotFoundException",
					e.getMessage() + " or operation was canceled");
			dataDisplayController.resetTextToDefaultValues();
		} catch (IOException e) {
			displayAlert(Alert.AlertType.ERROR, "Error","IOException", e.getMessage());
			dataDisplayController.resetTextToDefaultValues();
		} catch (DataFormatException e) {
			displayAlert(Alert.AlertType.WARNING, "Warning","Data Overwritten", String.format("The data from the" +
					" previous '%s' file was overwritten with the new data. The program" +
					" may work unexpectedly if the new data from '%s' does not match" +
					" the existing data in the remaining files.", e.getMessage(), e.getMessage()));
			dataDisplayController.resetTextToDefaultValues();
		}
	}

	public void undo() {
		displayAlert(Alert.AlertType.INFORMATION, "Information", null, "Not Yet Implemented!");
	}

	public void reload() {
		displayAlert(Alert.AlertType.INFORMATION, "Information", null, "Not Yet Implemented!");
	}

	/**
	 * Toggle the view status of the Data Display window.
	 * If the window is showing hide it, if it is hidden then show it.
	 * When the window becomes visible set the top label and the text area back
	 * to default values so that old data is not inadvertently displayed to the user
	 * leading to confusion.
	 * @author Grant Fass
	 */
	public void toggleDataDisplay() {
		if (dataDisplayStage.isShowing()) {
			dataDisplayStage.hide();
			viewDataDisplayButton.setText("Show Data Display");
		} else {
			dataDisplayStage.setX(stage.getX() + stage.getWidth());
			dataDisplayStage.setY(stage.getY());
			dataDisplayController.resetTextToDefaultValues();
			dataDisplayStage.show();
			viewDataDisplayButton.setText("Hide Data Display");
		}
	}

	/**
	 * Method to display help values to the program
	 * Activates when help menu button is clicked
	 * @author Grant Fass
	 */
	public void displayHelp() {
		String aboutInfo = "Authors: SE 2030 - 021 Group G\n" +
				"Members: Grant Fass, Joy Cross, Simon Erickson, & Ryan Becker\n" +
				"Affiliation: Milwaukee School of Engineering (MSOE)\n" +
				"Term: Fall 2020\n\n";
		String acceptedFiles = "This program currently accepts four files:\n" +
				"'stops.txt', 'stop_times.txt', 'routes.txt', & 'trips.txt'.\n" +
				"All other files will not work with the program.\n" +
				"Files are expected to be formatted matching the documentation found\n" +
				"here: https://developers.google.com/transit/gtfs/reference and\n" +
				"here: https://developers.google.com/transit/gtfs/examples/gtfs-feed.\n\n";
		String introductionInformation = "This program is designed to allow " +
				"the user to import, view, and manipulate GTFS files for a General " +
				"Transit Feed Specification Tool.\n\n";
		displayAlert(Alert.AlertType.INFORMATION, "General Transit Feed Specification Tool Information",
				null, introductionInformation + acceptedFiles + aboutInfo);
	}

	/**
	 * Method to query the user to retrieve a GTFS file from the computer using a FileChooser
	 * @author Grant Fass
	 * @return The selected GTFS file from the program
	 */
	private File getGTFSFileUsingFileChooser() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("ROUTES", "routes.txt".toLowerCase()),
				new FileChooser.ExtensionFilter("STOPS", "stops.txt".toLowerCase()),
				new FileChooser.ExtensionFilter("STOP_TIMES", "stop_times.txt".toLowerCase()),
				new FileChooser.ExtensionFilter("TRIPS", "trips.txt".toLowerCase()),
				new FileChooser.ExtensionFilter("TXT", "*.txt".toLowerCase()),
				new FileChooser.ExtensionFilter("ALL FILES", "*.*".toLowerCase()));
		fileChooser.setTitle("Load GTFS files");
		fileChooser.setInitialDirectory(new File("C:\\\\users\\\\" +
				System.getProperty("user.name") + "\\\\Documents"));
		return fileChooser.showOpenDialog(stage);
	}

	/**
	 * error if no file is loaded or file is null
	 * @author Grant Fass
	 * @param file the file to check
	 * @throws FileNotFoundException null file
	 */
	private void checkNullFile(File file) throws FileNotFoundException {
		if (file == null) {
			throw new FileNotFoundException("File was not found at specified address");
		}
	}

	/**
	 * method to check the extension of a file and make sure it matches one of the valid formats
	 * @author Grant Fass
	 * @param extension the extension to check
	 * @throws IllegalArgumentException if extension is not one of the listed ones.
	 */
	private void checkFileExtension(String extension) throws IllegalArgumentException {
		if(!extension.toLowerCase().equals(".txt")) {
			throw new IllegalArgumentException("The file of type "
					+ extension + " is not supported." +
					"Supported file types are '.txt'.");
		}
	}

	/**
	 * method to check the name of a file and make sure it matches the expected format
	 * so that the file can be passed on to the correct class for parsing.
	 * @author Grant Fass
	 * @param prefix the name of the file
	 * @return a simplified name of the file to be used for comparing
	 * @throws InputMismatchException if the file does not match the expected name
	 */
	private String checkFilePrefix(String prefix) throws InputMismatchException {
		if (prefix.toLowerCase().contains("stop_times")) {
			return "stop_times";
		} else if (prefix.toLowerCase().contains("stops")) {
			return "stops";
		} else if (prefix.toLowerCase().contains("trips")) {
			return "trips";
		} else if (prefix.toLowerCase().contains("routes")) {
			return "routes";
		} else {
			throw new InputMismatchException(String.format("The file with name %s is not " +
					"supported. Please make sure that your file matches the expected naming" +
					"convention of \'stops.txt\', \'stop_times.txt\', \'trips.txt\', &" +
					" \'routes.txt\'."));
		}
	}

	/**
	 * display an alert with the specified format and values
	 * @param alertType the type of alert to display
	 * @param title the title of the alert
	 * @param header the header text to display in the alert
	 * @param content the content text to display in the alert
	 * @author Grant Fass
	 */
	public void displayAlert(Alert.AlertType alertType, String title, String header, String content) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

	/**
	 * export Routes to a specified file directory
	 * @author Grant Fass
	 */
	@FXML
	public void exportRoutes() {
		try {
			File file = getExportDirectory();
			data.getRoutes().exportRoutes(file);
			displayAlert(Alert.AlertType.INFORMATION, "Data Export", null, "Data Export Was Successful");
		} catch (IOException e) {
			displayAlert(Alert.AlertType.ERROR, "Error", null, "Something went wrong while exporting file");
		}
	}

	/**
	 * export Stops to a specified file directory
	 * @author Grant Fass
	 */
	@FXML
	public void exportStops() {
		try {
			File file = getExportDirectory();
			data.getStops().exportStops(file);
			displayAlert(Alert.AlertType.INFORMATION, "Data Export", null, "Data Export Was Successful");
		} catch (IOException e) {
			displayAlert(Alert.AlertType.ERROR, "Error", null, "Something went wrong while exporting file");
		}
	}

	/**
	 * export StopTimes to a specified file directory
	 * @author Grant Fass
	 */
	@FXML
	public void exportStopTimes() {
		try {
			File file = getExportDirectory();
			data.getStopTimes().exportStopTimes(file);
			displayAlert(Alert.AlertType.INFORMATION, "Data Export", null, "Data Export Was Successful");
		} catch (IOException e) {
			displayAlert(Alert.AlertType.ERROR, "Error", null, "Something went wrong while exporting file");
		}
	}

	/**
	 * export Trips to a specified file directory
	 * @author Grant Fass
	 */
	@FXML
	public void exportTrips() {
		try {
			File file = getExportDirectory();
			data.getTrips().exportTrips(file);
			displayAlert(Alert.AlertType.INFORMATION, "Data Export", null, "Data Export Was Successful");
		} catch (IOException e) {
			displayAlert(Alert.AlertType.ERROR, "Error", null, "Something went wrong while exporting file");
		}
	}

	/**
	 * Method to query the user to retrieve a GTFS file from the computer using a FileChooser
	 * @author Grant Fass
	 * @return The selected GTFS file from the program
	 */
	private File getExportDirectory() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Select Export Directory");
		directoryChooser.setInitialDirectory(new File("C:\\\\users\\\\" +
				System.getProperty("user.name") + "\\\\Documents"));
		return directoryChooser.showDialog(stage);
	}
}
