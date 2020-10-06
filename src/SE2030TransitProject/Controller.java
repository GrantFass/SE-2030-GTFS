package SE2030TransitProject;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Time;
import java.util.Collection;
import java.util.InputMismatchException;

public class Controller {
	private Data data;
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
	public void exportGTFSfiles(Path outputLocation){

	}

	/**
	 * 
	 * @param gtfsFileLocation
	 */
	public void importGTFSfiles(Path gtfsFileLocation){

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

    public void close() {
    }

	public void save() {
	}

	/**
	 * Method to handle the loading of GTFS files
	 * Activates when load menu button is clicked
	 * Handles exceptions relating to loading files
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
		} catch (IllegalArgumentException e) {
			errorAlert("IllegalArgumentException", e.getMessage());
		} catch (InputMismatchException e) {
			errorAlert("InputMismatchException", e.getMessage());
		} catch (FileNotFoundException e) {
			errorAlert("FileNotFoundException",
					e.getMessage() + " or operation was canceled");
		} catch (IOException e) {
			errorAlert("IOException", e.getMessage());
		}
	}

	public void undo() {
	}

	public void reload() {
	}

	public void displayHelp() {
		String aboutInfo = String.format("");
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
	 * @return the extension on the file
	 * @throws IllegalArgumentException if extension is not one of the listed ones.
	 */
	private void checkFileExtension(String extension) throws IllegalArgumentException {
		if(!extension.toLowerCase().equals(".txt")) {
			throw new IllegalArgumentException("The file of type "
					+ extension + " is not supported." +
					"Supported file types are \'.txt\'.");
		}
	}

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
	 * method to display an alert with the error format
	 * @author Grant Fass
	 * @param header the header text to be displayed
	 * @param content the content text to be displayed
	 */
	private void errorAlert(String header, String content) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error Dialog");
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
}
