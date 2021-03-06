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
package data;

import interfaces.Observer;
import interfaces.Subject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Simon Erickson, Grant Fass,
 * @version 1.0
 * @created 06-Oct-2020 10:28:30 AM
 */
public class Data implements Subject {
	//region properties
	private final static double EARTH_RADIUS_MILES = 3959;
	private Routes routes;
	private StopTimes stop_times;
	private Stops stops;
	private Trips trips;
	private ArrayList<Observer> observerList;
	//endregion

	//region constructors
	public Data() {
		stops = new Stops();
		stop_times = new StopTimes();
		routes = new Routes();
		trips = new Trips();
		observerList = new ArrayList<>();
	}
	//endregion

	//region internal class getters
	public Routes getRoutes() {
		return routes;
	}

	public Stops getStops() {
		return stops;
	}

	public StopTimes getStopTimes() {
		return stop_times;
	}

	public Trips getTrips() {
		return trips;
	}
	//endregion

	//region methods for exporting files
	/**
	 * exports all files to the specified directory
	 * @param directory the directory to export the files to
	 * @param routesCheck represents if routes will be exported
	 * @param stopsCheck represents if stops will be exported
	 * @param stopTimesCheck represents if stopTimes will be exported
	 * @param tripsCheck represents if trips will be exported
	 * @return a message containing the status of the File export
	 * @author Grant Fass
	 */
	public String exportFiles(String directory, boolean routesCheck, boolean stopsCheck, boolean stopTimesCheck, boolean tripsCheck) {
		Callable<String> exportRoutes = () -> {
			String message;
			if (!directory.isEmpty() && routesCheck) {
				message = routes.exportRoutes(new File(directory)) ?
						String.format("???: Routes Exported Successfully\n  Time: %02d:%02d:%02d\n",
								LocalDateTime.now().getHour(), LocalDateTime.now().getMinute(),
								LocalDateTime.now().getSecond()) : "Routes Export Failed\n";
			} else if (directory.isEmpty()) {
				message = "Routes Export Failed\n  Empty Directory\n";
			} else {
				message = "Routes Export Skipped\n  Routes Not Selected\n";
			}
			return "EXPORT ROUTES:\n  " + message;
		};
		Callable<String> exportStops = () -> {
			String message;
			if (!directory.isEmpty() && stopsCheck) {
				message = stops.exportStops(new File(directory)) ?
						String.format("???: Stops Exported Successfully\n  Time: %02d:%02d:%02d\n",
								LocalDateTime.now().getHour(), LocalDateTime.now().getMinute(),
								LocalDateTime.now().getSecond()) : "Stops Export Failed\n";
			} else if (directory.isEmpty()) {
				message = "Stops Export Failed\n  Empty Directory\n";
			} else {
				message = "Stops Export Skipped\n  Stops Not Selected\n";
			}
			return "EXPORT STOPS:\n  " + message;
		};
		Callable<String> exportStopTimes = () -> {
			String message;
			if (!directory.isEmpty() && stopTimesCheck) {
				message = stop_times.exportStopTimes(new File(directory)) ?
						String.format("???: StopTimes Exported Successfully\n  Time: %02d:%02d:%02d\n",
								LocalDateTime.now().getHour(), LocalDateTime.now().getMinute(),
								LocalDateTime.now().getSecond()) : "StopTimes Export Failed\n";
			} else if (directory.isEmpty()) {
				message = "StopTimes Export Failed\n  Empty Directory\n";
			} else {
				message = "StopTimes Export Skipped\n  StopTimes Not Selected\n";
			}
			return "EXPORT STOP_TIMES:\n  " + message;
		};
		Callable<String> exportTrips = () -> {
			String message;
			if (!directory.isEmpty() && tripsCheck) {
				message = trips.exportTrips(new File(directory)) ?
						String.format("???: Trips Exported Successfully\n  Time: %02d:%02d:%02d\n",
								LocalDateTime.now().getHour(), LocalDateTime.now().getMinute(),
								LocalDateTime.now().getSecond()) : "Trips Export Failed\n";
			} else if (directory.isEmpty()) {
				message = "Trips Export Failed\n  Empty Directory\n";
			} else {
				message = "Trips Export Skipped\n  Trips Not Selected\n";
			}
			return "EXPORT TRIPS:\n  " + message;
		};
		ExecutorService executorService = Executors.newFixedThreadPool(4);
		Future<String> routesExportMessage = executorService.submit(exportRoutes);
		Future<String> stopsExportMessage = executorService.submit(exportStops);
		Future<String> stopTimesExportMessage = executorService.submit(exportStopTimes);
		Future<String> tripsExportMessage = executorService.submit(exportTrips);
		String completionTime = String.format("COMPLETE: %02d:%02d:%02d\n", LocalDateTime.now().getHour(), LocalDateTime.now().getMinute(), LocalDateTime.now().getSecond());
		String message = "Something Went Wrong During Export";
		try {
			message = routesExportMessage.get() + stopsExportMessage.get() + stopTimesExportMessage.get() + tripsExportMessage.get() + completionTime;
		} catch (InterruptedException | ExecutionException e) {
			message += "\n" + e.getMessage();
		}
		return message;
	}
	//endregion

	//region methods for loading files
	/**
	 * loads all of the files from the specified file locations.
	 * only loads the file if the string location is not empty
	 * @param routeFileLocation the file location in string format of routes.txt
	 * @param stopFileLocation the file location in string format of stops.txt
	 * @param stopTimeFileLocation the file location in string format of stop_times.txt
	 * @param tripFileLocation the file location in string format of trips.txt
	 * @return a message containing the import status of all files.
	 * @throws IOException for general File IO errors.
	 * @author Grant Fass
	 */
	public String loadFiles(String routeFileLocation, String stopFileLocation, String stopTimeFileLocation, String tripFileLocation) throws IOException{
		Callable<String> importRoutes = () -> {
			String routesImportMessage = "SKIP ROUTES:\n  File Location Empty\n";
			if (routeFileLocation != null && !routeFileLocation.isEmpty()) {
				routesImportMessage = routes.loadRoutes(new File(routeFileLocation));
			}
			return routesImportMessage;
		};
		Callable<String> importStops = () -> {
			String stopsImportMessage = "SKIP STOPS:\n  File Location Empty\n";
			if (stopFileLocation != null && !stopFileLocation.isEmpty()) {
				stopsImportMessage  = stops.loadStops(new File(stopFileLocation));
			}
			return stopsImportMessage;
		};
		Callable<String> importStopTimes = () -> {
			String stopTimesImportMessage = "SKIP STOP_TIMES:\n  File Location Empty\n";
			if (stopTimeFileLocation != null && !stopTimeFileLocation.isEmpty()) {
				stopTimesImportMessage = stop_times.loadStopTimes(new File(stopTimeFileLocation));
			}
			return stopTimesImportMessage;
		};
		Callable<String> importTrips = () -> {
			String tripsImportMessage = "SKIP TRIPS:\n  File Location Empty\n";
			if (tripFileLocation != null && !tripFileLocation.isEmpty()) {
				tripsImportMessage = trips.loadTrips(new File(tripFileLocation));
			}
			return tripsImportMessage;
		};
		ExecutorService executorService = Executors.newFixedThreadPool(4);
		Future<String> routesImportMessage = executorService.submit(importRoutes);
		Future<String> stopsImportMessage = executorService.submit(importStops);
		Future<String> stopTimesImportMessage = executorService.submit(importStopTimes);
		Future<String> tripsImportMessage = executorService.submit(importTrips);
		//wait to notify observers until all tasks are done
		while (!routesImportMessage.isDone() || !stopsImportMessage.isDone() || !stopTimesImportMessage.isDone() || !tripsImportMessage.isDone()) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//notify observers and return message
		notifyObservers();
		String completionTime = String.format("COMPLETE: %02d:%02d:%02d\n", LocalDateTime.now().getHour(), LocalDateTime.now().getMinute(), LocalDateTime.now().getSecond());
		String message = "Something Went Wrong During Import";
		try {
			message = routesImportMessage.get() + stopsImportMessage.get() + stopTimesImportMessage.get() + tripsImportMessage.get() + completionTime;
		} catch (InterruptedException | ExecutionException e) {
			message += "\n" + e.getMessage();
		}
		return message;
	}
	//endregion

	//region methods for updating files
	/**
	 * Updates attribute in specific class based on selection
	 * @param type stop_id, trip_id, stopTime, route_id
	 * @param id String of id
	 * @param attributeType attribute specific to class
	 * @param value String of value
	 * @author Joy Cross
	 * @return 0 if failure 1 if success
	 */
	public int update(String type, String id, String attributeType, String value) {
		int success = 1;
		switch (type) {
			case "stop_id":
				Stops stops = this.stops;
				Stop stop = stops.getStop(id);
				if(stop == null){ // check to see if stop exists
					success = 0;
					break;
				}

				stops.removeStop(id);
				Stop newStop = stop;

				//update stop
				String timezone = "";
				if(stop.getStopTimezone() != null){
					timezone = stop.getStopTimezone().getID();
				}
				String url = "";
				if(stop.getStopURL() != null){
					url = stop.getStopURL().toString();
				}
				switch (attributeType) {
					case "stop_id":
						newStop = new Stop(stop.getLevelID(), (stop.getLocationType().getValue() + ""), stop.getParentStation(),
								stop.getPlatformCode(), stop.getStopCode(), stop.getStopDescription(), value,
								(stop.getStopLatitude() + ""), (stop.getStopLongitude() + ""), stop.getStopName(),
								timezone, url, (stop.getWheelchairBoarding().getValue() + ""));
						break;
					case "stop_lon":
					case "stop_longitude":
						newStop = new Stop(stop.getLevelID(), (stop.getLocationType().getValue() + ""), stop.getParentStation(),
								stop.getPlatformCode(), stop.getStopCode(), stop.getStopDescription(), stop.getStopID(),
								(stop.getStopLatitude() + ""), value, stop.getStopName(),
								timezone, url, (stop.getWheelchairBoarding().getValue() + ""));
						break;
					case "stop_latitude":
					case "stop_lat":
						newStop = new Stop(stop.getLevelID(), (stop.getLocationType().getValue() + ""), stop.getParentStation(),
								stop.getPlatformCode(), stop.getStopCode(), stop.getStopDescription(), stop.getStopID(),
								value, (stop.getStopLongitude() + ""), stop.getStopName(),
								timezone, url, (stop.getWheelchairBoarding().getValue() + ""));
						break;
					case "stop_name":
						newStop = new Stop(stop.getLevelID(), (stop.getLocationType().getValue() + ""), stop.getParentStation(),
								stop.getPlatformCode(), stop.getStopCode(), stop.getStopDescription(), stop.getStopID(),
								(stop.getStopLatitude() + ""), (stop.getStopLongitude() + ""), value,
								timezone, url, (stop.getWheelchairBoarding().getValue() + ""));
						break;
					case "stop_description":
					case "stop_desc":
						newStop = new Stop(stop.getLevelID(), (stop.getLocationType().getValue() + ""), stop.getParentStation(),
								stop.getPlatformCode(), stop.getStopCode(), value, stop.getStopID(),
								(stop.getStopLatitude() + ""), (stop.getStopLongitude() + ""), stop.getStopName(),
								timezone, url, (stop.getWheelchairBoarding().getValue() + ""));
						break;
					case "stop_code":
						newStop = new Stop(stop.getLevelID(), (stop.getLocationType().getValue() + ""), stop.getParentStation(),
								stop.getPlatformCode(), value, stop.getStopDescription(), stop.getStopID(),
								(stop.getStopLatitude() + ""), (stop.getStopLongitude() + ""), stop.getStopName(),
								timezone, url, (stop.getWheelchairBoarding().getValue() + ""));
						break;
					case "platform_code":
						newStop = new Stop(stop.getLevelID(), (stop.getLocationType().getValue() + ""), stop.getParentStation(),
								value, stop.getStopCode(), stop.getStopDescription(), stop.getStopID(),
								(stop.getStopLatitude() + ""), (stop.getStopLongitude() + ""), stop.getStopName(),
								timezone, url, (stop.getWheelchairBoarding().getValue() + ""));
						break;
					case "level_id":
						newStop = new Stop(value, (stop.getLocationType().getValue() + ""), stop.getParentStation(),
								stop.getPlatformCode(), stop.getStopCode(), stop.getStopDescription(), stop.getStopID(),
								(stop.getStopLatitude() + ""), (stop.getStopLongitude() + ""), stop.getStopName(),
								timezone, url, (stop.getWheelchairBoarding().getValue() + ""));
						break;
					case "location_type":
						newStop = new Stop(stop.getLevelID(), value, stop.getParentStation(),
								stop.getPlatformCode(), stop.getStopCode(), stop.getStopDescription(), stop.getStopID(),
								(stop.getStopLatitude() + ""), (stop.getStopLongitude() + ""), stop.getStopName(),
								timezone, url, (stop.getWheelchairBoarding().getValue() + ""));
						break;
					case "stop_timezone":
						newStop = new Stop(stop.getLevelID(), (stop.getLocationType().getValue() + ""), stop.getParentStation(),
								stop.getPlatformCode(), stop.getStopCode(), stop.getStopDescription(), stop.getStopID(),
								(stop.getStopLatitude() + ""), (stop.getStopLongitude() + ""), stop.getStopName(),
								value, url, (stop.getWheelchairBoarding().getValue() + ""));
						break;
					case "wheelchair_boarding":
						newStop = new Stop(stop.getLevelID(), (stop.getLocationType().getValue() + ""), stop.getParentStation(),
								stop.getPlatformCode(), stop.getStopCode(), stop.getStopDescription(), stop.getStopID(),
								(stop.getStopLatitude() + ""), (stop.getStopLongitude() + ""), stop.getStopName(),
								timezone, url, (stop.getWheelchairBoarding().getValue() + ""));
						break;
					case "stop_url":
						newStop = new Stop(stop.getLevelID(), (stop.getLocationType().getValue() + ""), stop.getParentStation(),
								stop.getPlatformCode(), stop.getStopCode(), stop.getStopDescription(), stop.getStopID(),
								(stop.getStopLatitude() + ""), (stop.getStopLongitude() + ""), stop.getStopName(),
								timezone, value, (stop.getWheelchairBoarding().getValue() + ""));
						break;
					case "parent_station":
						newStop = new Stop(stop.getLevelID(), (stop.getLocationType().getValue() + ""), value,
								stop.getPlatformCode(), stop.getStopCode(), stop.getStopDescription(), stop.getStopID(),
								(stop.getStopLatitude() + ""), (stop.getStopLongitude() + ""), stop.getStopName(),
								timezone, url, (stop.getWheelchairBoarding().getValue() + ""));
						break;
				}
				stops.addStop(newStop);
				break;
			case "route_id":
				Routes routes = this.routes;
				Route route = routes.getRoute(id);
				if(route == null){
					success = 0;
					break;
				}
				routes.removeRoute(id);
				Route newRoute = route;

				// update route
				int r = (int)(route.getRouteColor().getRed()*256);
				int b = (int)(route.getRouteColor().getBlue()*256);
				int g = (int)(route.getRouteColor().getGreen()*256);
				String route_color = String.format("%02x%02x%02x", r, g, b);
				r = (int)(route.getRouteTextColor().getRed()*256);
				b = (int)(route.getRouteTextColor().getBlue()*256);
				g = (int)(route.getRouteTextColor().getGreen()*256);
				String route_text_color = String.format("%02x%02x%02x", r, g, b);
				if(route.getRouteURL() != null){
					url = route.getRouteURL().toString();
				} else {
					url = "";
				}
				switch (attributeType) {
					case "route_id":
						newRoute = new Route(value, route.getAgencyID(), route.getRouteShortName(),
								route.getRouteLongName(), route.getRouteDesc(), (route.getRouteType().getValue() + ""), url, route_color,
								route_text_color, (route.getRouteSortOrder() + ""), (route.getContinuousPickup().getValue() + ""),
								(route.getContinuousDropOff().getValue() + ""));
						break;
					case "agency_id":
						newRoute = new Route(route.getRouteID(), value, route.getRouteShortName(),
								route.getRouteLongName(), route.getRouteDesc(), (route.getRouteType().getValue() + ""), url, route_color,
								route_text_color, (route.getRouteSortOrder() + ""), (route.getContinuousPickup().getValue() + ""),
								(route.getContinuousDropOff().getValue() + ""));
						break;
					case "route_short_name":
						newRoute = new Route(route.getRouteID(), route.getAgencyID(), value,
								route.getRouteLongName(), route.getRouteDesc(), (route.getRouteType().getValue() + ""), url, route_color,
								route_text_color, (route.getRouteSortOrder() + ""), (route.getContinuousPickup().getValue() + ""),
								(route.getContinuousDropOff().getValue() + ""));
						break;
					case "route_long_name":
						newRoute = new Route(route.getRouteID(), route.getAgencyID(), route.getRouteShortName(),
								value, route.getRouteDesc(), (route.getRouteType().getValue() + ""), url, route_color,
								route_text_color,(route.getRouteSortOrder() + ""), (route.getContinuousPickup().getValue() + ""),
								(route.getContinuousDropOff().getValue() + ""));
						break;
					case "route_desc":
					case "route_description":
						newRoute = new Route(route.getRouteID(), route.getAgencyID(), route.getRouteShortName(),
								route.getRouteLongName(), value, (route.getRouteType().getValue() + ""), url, route_color,
								route_text_color, (route.getRouteSortOrder() + ""), (route.getContinuousPickup().getValue() + ""),
								(route.getContinuousDropOff().getValue() + ""));
						break;
					case "route_type":
						newRoute = new Route(route.getRouteID(), route.getAgencyID(), route.getRouteShortName(),
								route.getRouteLongName(), route.getRouteDesc(), value, url, route_color,
								route_text_color, (route.getRouteSortOrder() + ""), (route.getContinuousPickup().getValue() + ""),
								(route.getContinuousDropOff().getValue() + ""));
						break;
					case "route_color":
						newRoute = new Route(route.getRouteID(), route.getAgencyID(), route.getRouteShortName(),
								route.getRouteLongName(), route.getRouteDesc(), (route.getRouteType().getValue() + ""), url, value,
								route_text_color, (route.getRouteSortOrder() + ""), (route.getContinuousPickup().getValue() + ""),
								(route.getContinuousDropOff().getValue() + ""));
						break;
					case "route_text_color":
						newRoute = new Route(route.getRouteID(), route.getAgencyID(), route.getRouteShortName(),
								route.getRouteLongName(), route.getRouteDesc(), (route.getRouteType().getValue() + ""), url, route_color,
								value, (route.getRouteSortOrder() + ""), (route.getContinuousPickup().getValue() + ""),
								(route.getContinuousDropOff().getValue() + ""));
						break;
					case "route_sort_order":
						newRoute = new Route(route.getRouteID(), route.getAgencyID(), route.getRouteShortName(),
								route.getRouteLongName(), route.getRouteDesc(), (route.getRouteType().getValue() + ""), url, route_color,
								route_text_color, value, (route.getContinuousPickup().getValue() + ""),
								(route.getContinuousDropOff().getValue() + ""));
						break;
					case "continuous_pickup":
						newRoute = new Route(route.getRouteID(), route.getAgencyID(), route.getRouteShortName(),
								route.getRouteLongName(), route.getRouteDesc(), (route.getRouteType().getValue() + ""), url, route_color,
								route_text_color, (route.getRouteSortOrder() + ""), value,
								(route.getContinuousDropOff().getValue() + ""));
						break;
					case "route_url":
						newRoute = new Route(route.getRouteID(), route.getAgencyID(), route.getRouteShortName(),
								route.getRouteLongName(), route.getRouteDesc(), (route.getRouteType().getValue() + ""), value, route_color,
								route_text_color, (route.getRouteSortOrder() + ""), (route.getContinuousPickup().getValue() + ""),
								(route.getContinuousDropOff().getValue() + ""));
						break;
					case "continuous_drop_off":
						newRoute = new Route(route.getRouteID(), route.getAgencyID(), route.getRouteShortName(),
								route.getRouteLongName(), route.getRouteDesc(), (route.getRouteType().getValue() + ""), url, route_color,
								route_text_color, (route.getRouteSortOrder() + ""), (route.getContinuousPickup().getValue() + ""),
								value);
						break;
				}
				routes.addRoute(newRoute);
				break;
			case "trip_id":
				Trips trips = this.trips;
				Trip trip = trips.getTrip(id);
				if(trip == null){
					success = 0;
					break;
				}
				trips.removeTrip(id);
				Trip newTrip = trip;

				// update trip
				switch (attributeType) {
					case "route_id":
						newTrip = new Trip((trip.getBikesAllowed().getValue() + ""), trip.getBlockID(), (trip.getDirectionID().getValue() + ""),
								value, trip.getServiceID(), trip.getShapeID(), trip.getTripHeadsign(),
								trip.getTripID(), (trip.getWheelchairAccessible().getValue() + ""));
						break;
					case "service_id":
						newTrip = new Trip((trip.getBikesAllowed().getValue() + ""), trip.getBlockID(), (trip.getDirectionID().getValue() + ""),
								trip.getRouteID(), value, trip.getShapeID(), trip.getTripHeadsign(),
								trip.getTripID(), (trip.getWheelchairAccessible().getValue() + ""));
						break;
					case "trip_id":
						newTrip = new Trip((trip.getBikesAllowed().getValue() + ""), trip.getBlockID(), (trip.getDirectionID().getValue() + ""),
								trip.getRouteID(), trip.getServiceID(), trip.getShapeID(), trip.getTripHeadsign(),
								value, (trip.getWheelchairAccessible().getValue() + ""));
						break;
					case "trip_headsign":
						newTrip = new Trip((trip.getBikesAllowed().getValue() + ""), trip.getBlockID(), (trip.getDirectionID().getValue() + ""),
								trip.getRouteID(), trip.getServiceID(), trip.getShapeID(), value,
								trip.getTripID(), (trip.getWheelchairAccessible().getValue() + ""));
						break;
					case "direction_id":
						newTrip = new Trip((trip.getBikesAllowed().getValue() + ""), trip.getBlockID(), value,
								trip.getRouteID(), trip.getServiceID(), trip.getShapeID(), trip.getTripHeadsign(),
								trip.getTripID(), (trip.getWheelchairAccessible().getValue() + ""));
						break;
					case "block_id":
						newTrip = new Trip((trip.getBikesAllowed().getValue() + ""), value, (trip.getDirectionID().getValue() + ""),
								trip.getRouteID(), trip.getServiceID(), trip.getShapeID(), trip.getTripHeadsign(),
								trip.getTripID(), (trip.getWheelchairAccessible().getValue() + ""));
						break;
					case "shape_id":
						newTrip = new Trip((trip.getBikesAllowed().getValue() + ""), trip.getBlockID(), (trip.getDirectionID().getValue() + ""),
								trip.getRouteID(), trip.getServiceID(), value, trip.getTripHeadsign(),
								trip.getTripID(), (trip.getWheelchairAccessible().getValue() + ""));
						break;
				}
				trips.addTrip(newTrip);
				break;
			case "stopTime":
				StopTimes stopTimes = this.stop_times;
				StopTime stopTime;
				String[] stopTrip = id.split(",");
				try {
					stopTime = stopTimes.getStopTime(stopTrip[0], stopTrip[1]);
					if(stopTime == null) {
						throw new IndexOutOfBoundsException();
					}
				} catch (IndexOutOfBoundsException ibe) {
					success = 0;
					break;
				}
				stopTimes.removeStopTime(stopTrip[0], stopTrip[1]);
				StopTime newStopTime = stopTime;

				// update StopTime
				SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
				switch (attributeType) {
					case "trip_id":
						newStopTime = new StopTime(dateFormat.format(stopTime.getArrivalTime()),
								(stopTime.getContinuousDropOff().getValue() + ""),
								(stopTime.getContinuousPickup().getValue() + ""), dateFormat.format(stopTime.getDepartureTime()),
								(stopTime.getDropOffType().getValue() + ""), (stopTime.getPickupType().getValue() + ""),
								(stopTime.getShapeDistTraveled() + ""), stopTime.getStopHeadsign(), stopTime.getStopID(),
								(stopTime.getStopSequence() + ""), (stopTime.getTimepoint().getValue() + ""), value);
						break;
					case "stop_id":
						newStopTime = new StopTime(dateFormat.format(stopTime.getArrivalTime()),
								(stopTime.getContinuousDropOff().getValue() + ""),
								(stopTime.getContinuousPickup().getValue() + ""), dateFormat.format(stopTime.getDepartureTime()),
								(stopTime.getDropOffType().getValue() + ""), (stopTime.getPickupType().getValue() + ""),
								(stopTime.getShapeDistTraveled() + ""), stopTime.getStopHeadsign(), value,
								(stopTime.getStopSequence() + ""), (stopTime.getTimepoint().getValue() + ""), stopTime.getTripID());
						break;
					case "stop_sequence":
						newStopTime = new StopTime(dateFormat.format(stopTime.getArrivalTime()),
								(stopTime.getContinuousDropOff().getValue() + ""),
								(stopTime.getContinuousPickup().getValue() + ""), dateFormat.format(stopTime.getDepartureTime()),
								(stopTime.getDropOffType().getValue() + ""), (stopTime.getPickupType().getValue() + ""),
								(stopTime.getShapeDistTraveled() + ""), stopTime.getStopHeadsign(), stopTime.getStopID(),
								value, (stopTime.getTimepoint().getValue() + ""), stopTime.getTripID());
						break;
					case "arrival_time":
						newStopTime = new StopTime(value,
								(stopTime.getContinuousDropOff().getValue() + ""),
								(stopTime.getContinuousPickup().getValue() + ""), dateFormat.format(stopTime.getDepartureTime()),
								(stopTime.getDropOffType().getValue() + ""), (stopTime.getPickupType().getValue() + ""),
								(stopTime.getShapeDistTraveled() + ""), stopTime.getStopHeadsign(), stopTime.getStopID(),
								(stopTime.getStopSequence() + ""), (stopTime.getTimepoint().getValue() + ""), stopTime.getTripID());
						break;
					case "departure_time":
						newStopTime = new StopTime(dateFormat.format(stopTime.getArrivalTime()),
								(stopTime.getContinuousDropOff().getValue() + ""),
								(stopTime.getContinuousPickup().getValue() + ""), value,
								(stopTime.getDropOffType().getValue() + ""), (stopTime.getPickupType().getValue() + ""),
								(stopTime.getShapeDistTraveled() + ""), stopTime.getStopHeadsign(), stopTime.getStopID(),
								(stopTime.getStopSequence() + ""), (stopTime.getTimepoint().getValue() + ""), stopTime.getTripID());
						break;
					case "continuous_drop_off":
						newStopTime = new StopTime(dateFormat.format(stopTime.getArrivalTime()),
								value, (stopTime.getContinuousPickup().getValue() + ""), dateFormat.format(stopTime.getDepartureTime()),
								(stopTime.getDropOffType().getValue() + ""), (stopTime.getPickupType().getValue() + ""),
								(stopTime.getShapeDistTraveled() + ""), stopTime.getStopHeadsign(), stopTime.getStopID(),
								(stopTime.getStopSequence() + ""), (stopTime.getTimepoint().getValue() + ""), stopTime.getTripID());
						break;
					case "continuous_pickup":
						newStopTime = new StopTime(dateFormat.format(stopTime.getArrivalTime()),
								(stopTime.getContinuousDropOff().getValue() + ""),
								value, dateFormat.format(stopTime.getDepartureTime()),
								(stopTime.getDropOffType().getValue() + ""), (stopTime.getPickupType().getValue() + ""),
								(stopTime.getShapeDistTraveled() + ""), stopTime.getStopHeadsign(), stopTime.getStopID(),
								(stopTime.getStopSequence() + ""), (stopTime.getTimepoint().getValue() + ""), stopTime.getTripID());
						break;
					case "drop_off_type":
						newStopTime = new StopTime(dateFormat.format(stopTime.getArrivalTime()),
								(stopTime.getContinuousDropOff().getValue() + ""),
								(stopTime.getContinuousPickup().getValue() + ""), dateFormat.format(stopTime.getDepartureTime()),
								value, (stopTime.getPickupType().getValue() + ""),
								(stopTime.getShapeDistTraveled() + ""), stopTime.getStopHeadsign(), stopTime.getStopID(),
								(stopTime.getStopSequence() + ""), (stopTime.getTimepoint().getValue() + ""), stopTime.getTripID());
						break;
					case "pickup_type":
						newStopTime = new StopTime(dateFormat.format(stopTime.getArrivalTime()),
								(stopTime.getContinuousDropOff().getValue() + ""),
								(stopTime.getContinuousPickup().getValue() + ""), dateFormat.format(stopTime.getDepartureTime()),
								(stopTime.getDropOffType().getValue() + ""), value,
								(stopTime.getShapeDistTraveled() + ""), stopTime.getStopHeadsign(), stopTime.getStopID(),
								(stopTime.getStopSequence() + ""), (stopTime.getTimepoint().getValue() + ""), stopTime.getTripID());
						break;
					case "shape_dist_traveled":
						newStopTime = new StopTime(dateFormat.format(stopTime.getArrivalTime()),
								(stopTime.getContinuousDropOff().getValue() + ""),
								(stopTime.getContinuousPickup().getValue() + ""), dateFormat.format(stopTime.getDepartureTime()),
								(stopTime.getDropOffType().getValue() + ""), (stopTime.getPickupType().getValue() + ""),
								value, stopTime.getStopHeadsign(), stopTime.getStopID(),
								(stopTime.getStopSequence() + ""), (stopTime.getTimepoint().getValue() + ""), stopTime.getTripID());
						break;
					case "stop_headsign":
						newStopTime = new StopTime(dateFormat.format(stopTime.getArrivalTime()),
								(stopTime.getContinuousDropOff().getValue() + ""),
								(stopTime.getContinuousPickup().getValue() + ""), dateFormat.format(stopTime.getDepartureTime()),
								(stopTime.getDropOffType().getValue() + ""), (stopTime.getPickupType().getValue() + ""),
								(stopTime.getShapeDistTraveled() + ""), value, stopTime.getStopID(),
								(stopTime.getStopSequence() + ""), (stopTime.getTimepoint().getValue() + ""), stopTime.getTripID());
						break;
					case "timepoint":
						newStopTime = new StopTime(dateFormat.format(stopTime.getArrivalTime()),
								(stopTime.getContinuousDropOff().getValue() + ""),
								(stopTime.getContinuousPickup().getValue() + ""), dateFormat.format(stopTime.getDepartureTime()),
								(stopTime.getDropOffType().getValue() + ""), (stopTime.getPickupType().getValue() + ""),
								(stopTime.getShapeDistTraveled() + ""), stopTime.getStopHeadsign(), stopTime.getStopID(),
								(stopTime.getStopSequence() + ""), value, stopTime.getTripID());
						break;
				}
				stopTimes.addStopTime(newStopTime);
				break;
		}
		notifyObservers();
		return success;
	}
	//endregion

	//region methods for clearing files

	/**
	 * clears all of the stored data in all classes
	 * @author Grant Fass
	 */
	public void clearAllData() {
		stop_times.clearStopTimes();
		stops.clearStops();
		routes.clearRoutes();
		trips.clearTrips();
		notifyObservers();
	}
	//endregion

	//region methods for data GUI
	/**
	 * outputs all of the data for a class to the specified listView
	 * @param dataType selects which data to output.
	 *             0 = Routes
	 *             1 = Stops
	 *             2 = StopTimes
	 *             3 = Trips
	 * @param stringType selects which string format to output in.
	 *                   0 = One Line Simple String
	 *                   1 = Full Expanded Data
	 * @param listView the list view to add the entries to
	 * @author Grant Fass
	 */
	public void displayData(int dataType, int stringType, ListView listView) {
		int count = 0;
		Iterator mapIterator;
		ObservableList<String> items = FXCollections.observableArrayList();
		switch (dataType) {
			case 0:
				mapIterator = routes.getRoutes().entrySet().iterator();
				if (stringType == 1) {
					while (mapIterator.hasNext()) {
						Map.Entry mapElement = (Map.Entry) mapIterator.next();
						items.add(routes.getRoute(mapElement.getKey().toString()).toString() + "\n");
						count++;
					}
				} else {
					while (mapIterator.hasNext()) {
						Map.Entry mapElement = (Map.Entry) mapIterator.next();
						items.add(routes.getRoute(mapElement.getKey().toString()).toSimpleString() + "\n");
						count++;
					}
				}
				break;
			case 1:
				mapIterator = stops.getStops().entrySet().iterator();
				if (stringType == 1) {
					while (mapIterator.hasNext()) {
						Map.Entry mapElement = (Map.Entry) mapIterator.next();
						items.add(stops.getStop(mapElement.getKey().toString()).toString() + "\n");
						count++;
					}
				} else {
					while (mapIterator.hasNext()) {
						Map.Entry mapElement = (Map.Entry) mapIterator.next();
						items.add(stops.getStop(mapElement.getKey().toString()).toSimpleString() + "\n");
						count++;
					}
				}
				break;
			case 2:
				mapIterator = stop_times.getStop_times().entrySet().iterator();
				if (stringType == 1) {
					while (mapIterator.hasNext()) {
						Map.Entry mapElement = (Map.Entry) mapIterator.next();
						items.add(stop_times.getStop_times().get(mapElement.getKey()).toString() + "\n");
						count++;
					}
				} else {
					while (mapIterator.hasNext()) {
						Map.Entry mapElement = (Map.Entry) mapIterator.next();
						items.add(stop_times.getStop_times().get(mapElement.getKey()).toSimpleString() + "\n");
						count++;
					}
				}
				break;
			case 3:
				mapIterator = trips.getTrips().entrySet().iterator();
				if (stringType == 1) {
					while (mapIterator.hasNext()) {
						Map.Entry mapElement = (Map.Entry) mapIterator.next();
						items.add(trips.getTrip(mapElement.getKey().toString()).toString() + "\n");
						count++;
					}
				} else {
					while (mapIterator.hasNext()) {
						Map.Entry mapElement = (Map.Entry) mapIterator.next();
						items.add(trips.getTrip(mapElement.getKey().toString()).toSimpleString() + "\n");
						count++;
					}
				}
				break;
		}
		if (items.isEmpty()) {
			items.add("No Data Yet");
		}
		if(Platform.isFxApplicationThread()) {
			listView.setItems(items);
		} else {
			Platform.runLater(() -> listView.setItems(items));
		}
	}
	//endregion

	//region methods for analysis GUI
	/**
	 * outputs all of the data for a class to the specified listView
	 * @param analysisType the type of data to output
	 *                     0 = Trip Distance
	 *                     1 = Trip Speed
	 *                     2 = Trips Per Stop
	 * @param listView the listview to output the data to
	 * @author Grant Fass
	 */
	public void displayAnalysis(int analysisType, ListView listView) {
		int count = 0;
		Iterator mapIterator;
		ObservableList<String> items = FXCollections.observableArrayList();
		switch (analysisType) {
			case 0:
				mapIterator = tripDistances().entrySet().iterator();
				while (mapIterator.hasNext()) {
					Map.Entry mapElement = (Map.Entry) mapIterator.next();
					items.add(String.format("Trip ID: %s | %s miles\n", mapElement.getKey().toString(), mapElement.getValue().toString()));
					count++;
				}
				break;
			case 1:
				mapIterator = tripSpeeds().entrySet().iterator();
				while (mapIterator.hasNext()) {
					Map.Entry mapElement = (Map.Entry) mapIterator.next();
					items.add(String.format("%s | %s\n", mapElement.getKey().toString(), mapElement.getValue().toString()));
					count++;
				}
				break;
			case 2:
				Object[] keys = stop_times.getStop_times().keySet().toArray();

				// separate stop_id from trip_id
				for(int i = 0; i < keys.length; i++){
					String value = keys[i].toString();
					keys[i] = value.substring(0, value.indexOf(';'));
				}

				// count distinct stops which returns number of how much a stop is used by trips
				Arrays.stream(keys).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
						.entrySet()
						.forEach(object -> {
							String stop_id = object.toString().substring(0, object.toString().indexOf('='));
							int tripCount = Integer.parseInt(object.toString().substring(object.toString().indexOf('=') + 1));
								items.add(String.format("%s Trips contain Stop ID: %s\n", tripCount, stop_id));
							if (stops.getStop(stop_id) != null) {
								stops.getStop(stop_id).setTripsPerStop(tripCount);
							}
						});
				break;
		}
		if (items.isEmpty()) {
			items.add("No Data Yet");
		}
		if (Platform.isFxApplicationThread()) {
			listView.setItems(items);
		} else {
			Platform.runLater(() -> listView.setItems(items));
		}
	}

	/**
	 * Method that creates a HashMap with all trips with there lengths.
	 * @author Simon Erickson
	 * @return tripAndDistance The HashMap with all trips with there lengths.
	 */
	private HashMap<String, Integer> tripDistances(){
		HashMap<String, Integer> returnHashMap = new HashMap<>();
		if(!(stop_times == null | stops == null)){
			//HashMap<trip_id, first_time--first_stop_id--last_time--last_stop_id>
			HashMap<String, String> tripStartAndEnd = stop_times.getTripStartAndEnd();

			//HashMap<trip_id, distance in miles>
			HashMap<String, Integer> tripAndDistance = new HashMap<>();

			tripStartAndEnd.forEach((k,v)->{
				String[] value = v.split("--");
				tripAndDistance.put(k, tripDistance(stops.getStop(value[1]), stops.getStop(value[3])));
			});
			returnHashMap = tripAndDistance;
		}

		return returnHashMap;
	}

	/**
	 * Method that calculates the average speed based on the start and end times of a trip
	 *
	 * @author Simon Erickson, Grant Fass
	 * @return tripAndSpeed The HashMap with all trips with there average speeds.
	 */
	private HashMap<String, String> tripSpeeds(){
		HashMap<String, String> returnHashMap = new HashMap<>();
		if(!(stop_times == null | stops == null)){
			//HashMap<trip_id, first_time--first_stop_id--last_time--last_stop_id>
			HashMap<String, String> tripStartAndEnd = stop_times.getTripStartAndEnd();

			//HashMap<trip_id, speed in miles per hour>
			HashMap<String, String> tripAndSpeed = new HashMap<>();

			tripStartAndEnd.forEach((k,v)->{
				String[] value = v.split("--", -1);
				long time = Long.parseLong(value[2]) - Long.parseLong(value[0]);

				int miles = tripDistance(stops.getStop(value[1]), stops.getStop(value[3]));

				final double timeConstant = 3600000.0;
				double hours = time/timeConstant;
				tripAndSpeed.put(k, String.format("%.2f mph", miles/hours));

			});
			returnHashMap = tripAndSpeed;
		}

		return returnHashMap;
	}

	/**
	 * Method to find the distance between two stops
	 * @author Simon Erickson
	 * @param  fromThisStop the first stop
	 * @param  toThisStop the second stop
	 * @return The distance between two stops in miles
	 */
	private int tripDistance(Stop fromThisStop, Stop toThisStop) {
		if(fromThisStop == null || toThisStop == null) {
			return -1;
		}
		double fromThisLat = fromThisStop.getStopLatitude();
		double toThisLat = toThisStop.getStopLatitude();
		double fromThisLng = fromThisStop.getStopLongitude();
		double toThisLng = toThisStop.getStopLongitude();

		double distanceLat = Math.toRadians(fromThisLat - toThisLat);
		double distanceLng = Math.toRadians(fromThisLng - toThisLng);

		//Haversine formula
		double a = Math.pow(Math.sin(distanceLat / 2),2);
		double b = Math.pow(Math.sin(distanceLng / 2),2);
		double c = Math.cos(Math.toRadians(fromThisLat));
		double d = Math.cos(Math.toRadians(toThisLat));
		double e = (a + c * d * b);
		double f = 2 * Math.atan2(Math.sqrt(e), Math.sqrt(1 - e));
		double miles = (Math.round(EARTH_RADIUS_MILES * f));

		return (int) miles;
	}
	//endregion

	//region methods for map GUI


    //START FEATURE 10
	/**
	 * returns all of the coordinates of buses
	 * @return an arraylist of bus location coordinate pairs with longitude first then latitude
	 * @author Grant Fass, Ryan Becker
	 */
	public ArrayList<double[]> getBusCoordinates() {

		//Used for initial comparison
	    final LocalDateTime INITIAL_TIME = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
		double[] coordinatePair;

		HashMap<String, LocalDateTime> testMap = new HashMap<>();

		HashMap<String, double[]> testCoordinates = new HashMap<>();

	    for (StopTime stopTime : stop_times.getStop_times().values()) {

            String newTripID = stopTime.getTripID();
            Stop stop = stops.getStop(stopTime.getStopID());
			if(!testMap.containsKey(newTripID)){
				LocalDateTime dateTime = createTime(stopTime.getDepartureTime());
				coordinatePair = getCoordinatePair(stop, INITIAL_TIME, dateTime);
				if(coordinatePair != null){
					testMap.put(newTripID, dateTime);
					testCoordinates.put(newTripID, coordinatePair);
				}

			} else {
				LocalDateTime oldDateTime = testMap.get(newTripID);
				LocalDateTime dateTime = createTime(stopTime.getDepartureTime());
				if(dateTime.isBefore(oldDateTime)){
					coordinatePair = getCoordinatePair(stop, dateTime, oldDateTime);
					if(coordinatePair != null){
						testMap.replace(newTripID, dateTime);
						testCoordinates.replace(newTripID, coordinatePair);
					}
				}
			}

        }
		return new ArrayList<>(testCoordinates.values());
	}

	/**
	 * Creates LocalTimeObject from TimeStamp retrieved from Stop_Time departure time
	 * @author Ryan Becker
	 * @param timestamp TimeStamp retrieved from Stop_Time departure time
	 * @return LocalDateTime object of current date, coupled with the time listed in timestamp
	 */
	private LocalDateTime createTime(Timestamp timestamp){
		return LocalDateTime.of(LocalDate.now(), LocalTime.from(timestamp.toLocalDateTime()));
	}

	/**
	 * Retrieves latitude and longitude for estimated stop that bus is at
	 * @author Ryan Becker
	 * @param stop Object used to retrieve the longitude and latitude from
	 * @param earlierTime lower end threshold that currentTime is compared to
	 * @param laterTime higher end threshold that currentTime is compared to
	 * @return double list where first double is latitude, and second double is longitude
	 */
	private double[] getCoordinatePair(Stop stop, LocalDateTime earlierTime, LocalDateTime laterTime){
		if (stop != null) {
			LocalDateTime currentTime = LocalDateTime.now();

			//TESTING PURPOSE
			//currentTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0));
			//TESTING PURPOSE

			if(currentTime.isAfter(earlierTime) && currentTime.isBefore(laterTime)){
				double latitude = stop.getStopLatitude();
				double longitude = stop.getStopLongitude();

				return new double[] {latitude, longitude};
			}
		}
	    return null;
    }

	//END FEATURE 10

	/**
	 * retrieves all of the stops that are associated with all routes
	 * @return a HashMap containing all of the Stops associated with all Routes
	 * @author Grant Fass
	 */
	public HashMap<Route, ArrayList<Stop>> getStopsPerRoute() {
		//Create a list of all of the keys in StopTimes. Key Format = 'stop_id;trip_id'
		String[] stopTimeKeys = stop_times.getStop_times().keySet().toArray(new String[0]);
		//separate stopTimeKeys into stop_id values and trip_id values
		ArrayList<String> stop_ids = new ArrayList<>();
		ArrayList<String> trip_ids = new ArrayList<>();
		for(String s:stopTimeKeys) {
			stop_ids.add(s.substring(0, s.indexOf(';')));
			trip_ids.add(s.substring(s.indexOf(';') + 1));
		}
		//convert stop_ids to Stops
		ArrayList<Stop> allStops = new ArrayList<>();
		for(String stop_id:stop_ids) {
			allStops.add(stops.getStop(stop_id));
		}
		//convert trip_ids to Routes
		ArrayList<Route> allRoutes = new ArrayList<>();
		for(String trip_id:trip_ids) {
			Trip trip = trips.getTrip(trip_id);
			Route route = new Route("-1", "", "-1", "Null Route", "Null Route",
					"", "", Color.web("black").toString(), "",
					"", "", "");
			if (trip != null) {
				route = routes.getRoute(trip.getRouteID());
			}
			allRoutes.add(route);
		}
		//transfer data into HashMap
		HashMap<Route, ArrayList<Stop>> stopsPerRoute = new HashMap<>();
		for (int i = 0; i < allRoutes.size(); i++) {
			//If the Route already exists in the map then add the stop to the list of stops
			//Otherwise create a new list of stops and add the route and stop to the map
			//Do not include the stop if it already exists for a route
			if (stopsPerRoute.containsKey(allRoutes.get(i)) && !stopsPerRoute.get(allRoutes.get(i)).contains(allStops.get(i))) {
				stopsPerRoute.get(allRoutes.get(i)).add(allStops.get(i));
			} else if (allRoutes.get(i) != null) {
				ArrayList<Stop> stopsInRoute = new ArrayList<>();
				stopsInRoute.add(allStops.get(i));
				stopsPerRoute.put(allRoutes.get(i), stopsInRoute);
			}
		}
		//Return Map
		return stopsPerRoute;
	}
	//endregion

	//region search functions
	/**
	 * Searches for every route_id associated with a Stop given stop_id
     * @author Ryan Becker
	 * @param stop_id of Stop being searched
	 * @return String of formatted route_ids associated with stop_id
	 */
	public String getRouteIDs_fromStopID(String stop_id){
	    ArrayList<String> route_ids = searchStopForRoute_IDs(stop_id);
	    return formatIDs(route_ids);
	}
	/**
	 * Helper method for getRouteIDs_fromStopID() that gets all route_ids associated with a given stop_id
	 * @author Ryan Becker
	 * @param stop_id for a Stop used in searching for all route_ids that are paired with the given stop_id
	 * @return ArrayList of every route_id that is associated with stop_id
	 */
	private ArrayList<String> searchStopForRoute_IDs(String stop_id){
		ArrayList<String> trip_ids = stop_times.getTripIDs_fromStop_ID(stop_id);

		ArrayList<String> all_route_ids = new ArrayList<>();

		for(String trip_id : trip_ids){
			ArrayList<String> route_ids = trips.getRouteIDs_fromTripIDs(trip_id);
			all_route_ids.addAll(onlyAddNew(all_route_ids, route_ids));
		}

		return all_route_ids;
	}




	//Start feature 6

	/**
	 * Searches for every stop_id associated with a Route, given the route_id
	 * @author Ryan Becker
	 * @param route_id of Route being searched
	 * @return String of formatted stop_ids associated with route_id
	 */
	public String getStopIDs_fromRouteID(String route_id){
		ArrayList<String> stop_ids = searchRouteForStop_IDS(route_id);
		return formatIDs(stop_ids);
	}

	/**
	 * Helper method for getStopIDs_fromRouteID() that gets all stop_ids associated with a given route_id
	 * @author Ryan Becker
	 * @param route_id for a Route used in searching for all stop_ids that are paired with the given route_id
	 * @return ArrayList of every stop_id that is associated wtih route_id
	 */
	private ArrayList<String> searchRouteForStop_IDS(String route_id){
		ArrayList<String> trip_ids = trips.getTripIDs_fromRouteID(route_id);

		ArrayList<String> all_stop_ids = new ArrayList<>();

		for(String trip_id : trip_ids){
			ArrayList<String> stop_ids = stop_times.getStopIDs_fromTripID(trip_id);
			all_stop_ids.addAll(onlyAddNew(all_stop_ids, stop_ids));
		}

		return all_stop_ids;
	}
	//End feature 6

	//Start feature 7

    /**
     * Gets all trip_ids with a time occurring in the future
     * @author Ryan Becker
     * @param route_id to be searched and find related trip_ids
     * @return ArrayList of all future trip_ids associated with route_id
     */
	public String getFutureTripIDs_fromRouteID(String route_id){
		ArrayList<String> all_trip_ids = trips.getTripIDs_fromRouteID(route_id);
		ArrayList<String> future_trip_ids = stop_times.getFutureTripIDs_fromAllTripIDs(all_trip_ids);

		return formatIDs(future_trip_ids);
	}

	//End feature 7


	//Start feature 5/6/7 helpers
	/**
	 * Helper method that formats a string of all stop_ids, route_ids, or trip_ids (in future) depending on search type
	 * to display every found associated with a searched id
	 * @param ids ArrayList of every found id associated with searched id
	 * @return String formatting every found id associated with searched on new lines
	 */
	private String formatIDs(ArrayList<String> ids){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < ids.size(); ++i){
			sb.append(i + 1).append(": ").append(ids.get(i)).append("\n");
		}

		return sb.toString();
	}


	/**
	 * Helper method that will help remove duplicate occurrences of an id
	 * @author Ryan Becker
	 * @param allIDs ArrayList of all current unique ids associated with stop_id
	 * @param ids all newly read ids, which are only added to returned list if they do not
	 *                  already occur within allIDs
	 * @return ArrayList of all ids currently not found in allIDs
	 */
	private ArrayList<String> onlyAddNew(ArrayList<String> allIDs, ArrayList<String> ids){
		ArrayList<String> uniqueIDs = new ArrayList<>();
		for(String id : ids){
			if(!allIDs.contains(id)){
				uniqueIDs.add(id);
			}
		}
		return uniqueIDs;
	}
	//End features 5/6/7 helpers


	//endregion

	//region Observer Pattern Methods
	/**
	 * add an observer to the list of observers to update
	 * Based on a guide from GeeksForGeeks
	 * found here: https://www.geeksforgeeks.org/observer-pattern-set-2-implementation/
	 * @param o the observer to add
	 * @author Grant Fass
	 */
	@Override
	public void attach(Observer o) {
		observerList.add(o);
	}
	/**
	 * remove an observer from the list of observers to update
	 * Based on a guide from GeeksForGeeks
	 * found here: https://www.geeksforgeeks.org/observer-pattern-set-2-implementation/
	 * @param o the observer to remove
	 * @author Grant Fass
	 */
	@Override
	public void detach(Observer o) {
		observerList.remove(o);
	}

	/**
	 * notify all observers that information was changed
	 * Based on a guide from GeeksForGeeks
	 * found here: https://www.geeksforgeeks.org/observer-pattern-set-2-implementation/
	 * @author Grant Fass
	 */
	@Override
	public void notifyObservers() {
		for (Observer o : observerList) {
			o.update(this);
		}
	}
	//endregion

	//region toString
	/**
	 * Method to output data as a single concatenated string
	 * @author GrantFass,
	 * @return string of data
	 */
	@Override
	public String toString() {
		return getStopTimes().toString() + getStops().toString()
				+ getTrips().toString() + getRoutes().toString();
	}
	//endregion
}//end Data