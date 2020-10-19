<!--
    @author Grant Fass
-->
<h1>SE2030TeamG</h1>
<hr>
<h3>Program Status:</h3>
<ul>
    <li>Stops loads and displays</li>
    <li>Routes loads and displays</li>
    <li>Stop_Times loads and displays 1000 entries</li>
    <li>Trips loads and displays</li>
    <li>Java SDK: 1.8.0_242</li>
    <li>Junit 5.4.2 used for testing</li>
</ul>
<hr>
<h3 style="color:red">TODO:</h3>
<h5>Joy Cross:</h5>
<h6>TODO</h6>
<ul>
    <li>Implement stop Export</li>
    <li>
        Implement Feature 4: Display the number of trips each stop is found on
        <ul>
            <li>Calculate the number of trips each stop is found on</li>
        </ul>
    </li>
    <li>
        Harden Stop Import Validation
        <ul>
            <li>Create validation method for header line</li>
            <li>Create validation method for input line</li>
            <li>Create unit test for header line validation method</li>
            <li>Create unit test for input line validation method</li>
            <li>Ensure individual file formatting is handled appropriately</li>
            <li>
                Ensure invalid data in files is handled appropriately
                <ul>
                    <li>If file has a corrupted line then skip it</li>
                    <li>Update user that there were corrupted lines and they were skipped</li>
                </ul>    
            </li>
            <li>Add Javadoc comments to Junit tests</li>
            <li>Add Javadoc comments to validation methods</li>
        </ul>
    </li>
</ul>
<h6>Completed</h6>
<s>
    <ul>
        <li>
            Implement Stop, StopTime, Stops, & StopTimes
            <ul>
                <li>Implement getters</li>
                <li>Implement setters</li>
                <li>Implement constructors</li>
                <li>Implement file loading and parsing</li>
                <li>Implement toString method override</li>
                <li>Add JavaDoc comments</li>
                <li>update constructor</li>
                <li>Add ability to load multiple GTFS files in succession (Delete old data)</li>
                <li>Display alert to user when overwriting data during file load, Done by throwing DataFormatException</li>
            </ul>
        </li>
    </ul>
</s>
<h5>Ryan Becker:</h5>
<h6>TODO</h6>
<ul>
    <li>Implement route Export</li>
    <li>
        Harden Route Import Validation
        <ul>
            <li>Create validation method for header line</li>
            <li>Create validation method for input line</li>
            <li>Create unit test for header line validation method</li>
            <li>Create unit test for input line validation method</li>
            <li>Ensure individual file formatting is handled appropriately</li>
            <li>
                Ensure invalid data in files is handled appropriately
                <ul>
                    <li>If file has a corrupted line then skip it</li>
                    <li>Update user that there were corrupted lines and they were skipped</li>
                </ul>    
            </li>
            <li>Add Javadoc comments to Junit tests</li>
            <li>Add Javadoc comments to validation methods</li>
        </ul>
    </li>
</ul>
<h6>Completed</h6>
<s>
    <ul>
        <li>
            Implement Route & Routes
            <ul>
                <li>Add ability to load multiple GTFS files in succession (Delete old data)</li>
                <li>Add JavaDoc comments</li>
                <li>Implement setters</li>
                <li>Implement file loading and parsing</li>
                <li>Implement constructors</li>
                <li>Implement getters</li>
                <li>Display alert to user when overwriting data during file load, Done by throwing DataFormatException</li>
                <li>Implement toString method override</li>
            </ul>
        </li>
    </ul>
</s>
<h5>Simon Erickson:</h5>
<h6>TODO</h6>
<ul>
    <li>Implement trip Export</li>
    <li>
        Harden Trip Import Validation
        <ul>
            <li>Create validation method for header line</li>
            <li>Create validation method for input line</li>
            <li>Create unit test for header line validation method</li>
            <li>Create unit test for input line validation method</li>
            <li>Ensure individual file formatting is handled appropriately</li>
            <li>
                Ensure invalid data in files is handled appropriately
                <ul>
                    <li>If file has a corrupted line then skip it</li>
                    <li>Update user that there were corrupted lines and they were skipped</li>
                </ul>    
            </li>
            <li>Add Javadoc comments to Junit tests</li>
            <li>Add Javadoc comments to validation methods</li>
        </ul>
    </li>
</ul>
<h6>Completed</h6>
<s>
    <ul>
        <li>
            Implement Trip & Trips
            <ul>
                <li>Update File Parsing</li>
                <li>Display alert to user when overwriting data during file load, Done by throwing DataFormatException</li>
                <li>update toString method override</li>
                <li>Add JavaDoc comments</li>
                <li>Implement file loading and parsing</li>
                <li>Add ability to load multiple GTFS files in succession (Delete old data)</li>
                <li>Implement getters</li>
                <li>Implement setters</li>
                <li>Implement constructors</li>
                <li>Implement toString method override</li>
            </ul>
        </li>
    </ul>
</s>
<h5>Grant Fass:</h5>
<h6>TODO</h6>
<ul>
    <li>verify what exception StopTime throws in overloaded constructor</li>
    <li>verify that the validate methods in StopTimes throw the correct exceptions</li>
    <li>Implement stop_time Export</li>
    <li>
        Implement Feature 4: Display the number of trips each stop is found on
        <ul>
            <li>Display output to GUI</li>
            <li>Optional for lab 6: Use observer pattern to display results</li>
        </ul>
    </li>
    <li>
        Harden Stop_Time Import Validation
        <ul>
            <li>Create validation method for header line</li>
            <li>Create validation method for input line</li>
            <li>Create unit test for header line validation method</li>
            <li>Create unit test for input line validation method</li>
            <li>Ensure individual file formatting is handled appropriately</li>
            <li>
                Ensure invalid data in files is handled appropriately
                <ul>
                    <li>If file has a corrupted line then skip it</li>
                    <li>Update user that there were corrupted lines and they were skipped</li>
                </ul>    
            </li>
            <li>Add Javadoc comments to Junit tests</li>
            <li>Add Javadoc comments to validation methods</li>
        </ul>
    </li>
</ul>
<ul>
    <li>Override .equals methods for classes</li>
    <li>Compress Lab 5 Data in README</li>
    <li>Add lab 6 Data in README</li>
    <li>Update Data View Window help section</li>
    <li>Implement Deep Copy in controller getData() method</li>
</ul>
<h6>Completed</h6>
<s>
    <ul>
        <li>Update help documentation in Main window</li>
        <li>Create initial test suites and directory</li>
        <li>
            Implement Data View Window
                <ul>
                    <li>Implement Data View FXML file</li>
                    <li>Implement Data View controller</li>
                    <li>Implement observer interface into data view controller</li>
                    <li>Add method in Stops, Routes, Trips, and StopTimes that is passed a textArea to display to</li>
                    <li>Update help section</li>
                </ul>
        </li>
        <li>Refactor the alert methods to take an AlertType parameter for less repeat code</li>
        <li>Add controller method for DataFormatException</li>
        <li>
            Implement all enumerators
            <ul>
                <li>Add JavaDoc comments</li>
                <li>Implement constructors</li>
            </ul>
        </li>
        <li>
            Setup fxml view
            <ul>
                <li>Implement fxml main window</li>
                <li>Implement fxml menu</li>
                <li>Implement fxml menu buttons</li>
                <li>Implement onAction commands for menu buttons</li>
            </ul>
        </li>
        <li>
            Implement GTFS file load capabilities
            <ul>
                <li>Show snapshot of data structures in GUI in new window</li>
                <li>Implement FileChooser dialogue to load files</li>
                <li>Implement File Load Exception handling</li>
            </ul>
        </li>
        <li>
            Implement Help Menu
            <ul>
                <li>Implement about information</li>
                <li>Implement accepted files information</li>
                <li>Implement introduction information</li>
            </ul>
        </li>
        <li>Implement initial subject and observer framework</li>
    </ul>
</s>
<h5>Unassigned:</h5>
<ul>
    <li>
        Create Lab 6 PDF submission
        <ul>
            <li>Include how many tests were written per person</li>
            <li>Include who wrote which tests</li>
            <li>Generate EA class diagram snapshot update</li>
        </ul>
    </li>
    <li>
        Create JUnit tests
        <ul>
            <li>Create at least 2 test cases per unit test</li>
            <li>test import validation methods (should handle any type of input line)</li>
            <li>each team member should write one unit test</li>
            <li>comment each unit test with what its intended purpose is and who wrote it</li>
        </ul>
    </li>
    <li>
        (Optional for lab 6) Implement Feature 3: Display the average speed based on the start and end times of a trip
        <ul>
            <li>Display output to GUI</li>
            <li>Use observer pattern to display results</li>
            <li>Implement method to calculate average speed</li>
        </ul>
    </li>
    <li>
        Implement Feature 15: Export the GTFS files in the correct format from the data structure
        <ul>
            <li>Implement routes.txt export</li>
            <li>Implement stops.txt export</li>
            <li>Implement stop_times.txt export</li>
            <li>Implement trips.txt export</li>
        </ul>
    </li>
    <li>
        Implement Feature 4: Display the number of trips each stop is found on.
        <ul>
            <li>Display output to GUI</li>
            <li>(Optional for lab 6) Use observer pattern to display results</li>
        </ul>
    </li>
    <li>
        Harden Import Functionality
        <ul>
            <li>Ensure invalid file formatting is handled appropriately</li>
            <li>Ensure invalid data in files is handled appropriately</li>
            <li>
                Add helper methods to import to validate individual lines
                <ul>
                    <li>Add method to validate a single file header line</li>
                    <li>Add method to validate a single data line</li>
                    <li>Add method to validate individual data type if necessary / set to default value</li>
                </ul>
            </li>
            <li>
                Updated Import Specifications
                <ul>
                    <li>Routes only requires a route_id and route_color (all others are optional)</li>
                    <li>Trips does not require a service_id</li>
                    <li>Calendar information is ignored. (Schedule assumed to be same every day)</li>
                    <li>Only 4 files are required routes, stops, stop_times, and trips</li>
                    <li>Other than the aforementioned differences the import should match <a href = "https://developers.google.com/transit/gtfs/reference/">here</a></li>
                </ul>
            </li>
        </ul>
    </li>
    <li>
        Implement Data class
        <ul>
            <li>Update getters</li>
            <li>Update constructors</li>
        </ul>
    </li>
    <li>
        Implement GTFS file load capabilities
        <ul>
            <li>Verify GTFS file load passes acceptance tests</li>
            <li>Allow loading of multiple files at same time</li>
        </ul>
    </li>
    <li>
        Implement second window for map
        <ul>
            <li>Implement second stage for map in Main</li>
            <li>Implement Second controller for map</li>
        </ul>
    </li>
</ul>
<hr>
