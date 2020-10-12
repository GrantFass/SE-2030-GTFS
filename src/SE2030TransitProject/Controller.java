package SE2030TransitProject;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
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
	public Label topLabel;
	public VBox vBox;
	public TextArea textArea;
	private final Data data = new Data();
	private Stage stage;

	/**
	 * 
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
	public void exportGTFSFiles(Path outputLocation){

	}

	/**
	 * 
	 * @param gtfsFileLocation
	 */
	public void importGTFSFiles(Path gtfsFileLocation){

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

			switch (prefix) {
				case "stop_times":
					data.getStopTimes().loadStopTimes(file);
					break;
				case "stops":
					data.getStops().loadStops(file);
					break;
				case "routes":
					data.getRoutes().loadRoutes(file);
					break;
				case "trips":
					data.getTrips().loadTrips(file);
					break;
			}
			displayDataSnapshot();
		} catch (IllegalArgumentException e) {
			displayAlert(Alert.AlertType.ERROR, "Error","IllegalArgumentException",
					e.getMessage());
		} catch (InputMismatchException e) {
			displayAlert(Alert.AlertType.ERROR, "Error","InputMismatchException",
					e.getMessage());
		} catch (FileNotFoundException e) {
			displayAlert(Alert.AlertType.ERROR, "Error","FileNotFoundException",
					e.getMessage() + " or operation was canceled");
		} catch (IOException e) {
			displayAlert(Alert.AlertType.ERROR, "Error","IOException",
					e.getMessage());
		} catch (DataFormatException e) {
			displayAlert(Alert.AlertType.WARNING, "Warning", "Data Overwritten",
					String.format("The data from the" +
					" previous '%s' file was overwritten with the new data. The program" +
					" may work unexpectedly if the new data from '%s' does not match" +
					" the existing data in the remaining files.", e.getMessage(), e.getMessage()));
		}
	}

	public void undo() {
	}

	public void reload() {
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
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("General Transit Feed Specification Tool");
		alert.setHeaderText(null);
		alert.setContentText(introductionInformation + acceptedFiles + aboutInfo);
		alert.showAndWait();
	}

	/**
	 * Method to query the user to retrieve a GTFS file from the computer using a FileChooser
	 * @author Grant Fass
	 * @return The selected GTFS file from the program
	 */
	private File getGTFSFileUsingFileChooser() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("STOPS", "stops.txt".toLowerCase()),
				new FileChooser.ExtensionFilter("TRIPS", "trips.txt".toLowerCase()),
				new FileChooser.ExtensionFilter("ROUTES", "routes.txt".toLowerCase()),
				new FileChooser.ExtensionFilter("STOP_TIMES", "stop_times.txt".toLowerCase()),
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
					"convention of 'stops.txt', 'stop_times.txt', 'trips.txt', &" +
					" 'routes.txt'.", prefix));
		}
	}

	/**
	 * method to display an alert with the warning format
	 * @author Grant Fass
	 * @param alertType The type of alert to generate
	 * @param title The title of the alert
	 * @param header the header text to be displayed
	 * @param content the content text to be displayed
	 */
	private void displayAlert(Alert.AlertType alertType, String title, String header, String content) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

	/**
	 * Method to output a snapshot of the information in the Data class
	 * @author Grant Fass
	 */
	private void displayDataSnapshot() {
		topLabel.setText("Data Snapshot:");
		textArea.setText(data.toString());
	}
}
