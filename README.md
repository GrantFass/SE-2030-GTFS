<!--
    @author Grant Fass
-->
<h1>SE2030TeamG</h1>
<hr>
<h3>Program Info:</h3>
<ul>
    <li>Java SDK: 1.8.0_242</li>
    <li>Junit 5.4.2 used for testing</li>
</ul>
<hr>
<h3 style="color:red">TODO:</h3>
<h5>Joy Cross:</h5>
<h6>TODO</h6>
<ul>
    <li>
        Implement Feature 8: Search for a stop by stop_id and display the next trip_id(s) (closest to the current time)
        <ul>
            <li>Implement method to search</li>
        </ul>
    </li>
</ul>
<h6>Completed</h6>
<s>
    <ul>
        <li></li>
    </ul>
</s>
<hr>
<h5>Ryan Becker:</h5>
<h6>TODO</h6>
<ul>
    <li>
        Implement Feature 5: Search for a stop by stop_id and display all route_id(s) that contain the stop
        <ul>
            <li>Implement method to search</li>
        </ul>
    </li>
    <li>Implement route Export</li>
    <li>
        Harden Route Import Validation
        <ul>
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
        <li></li>
    </ul>
</s>
<hr>
<h5>Simon Erickson:</h5>
<h6>TODO</h6>
<h6>Completed</h6>
<ul>
    <li>
        Implement Feature 2: Display the Distance of each Trip
        <ul>
            <li>Implement method to calculate distance</li>
        </ul>
    </li>
    <li>Implement trip Export</li>
    <li>
        Harden Trip Import Validation
        <ul>
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
<s>
    <ul>
        <li></li>
    </ul>
</s>
<hr>
<h5>Grant Fass:</h5>
<h6>TODO</h6>
<ul>
    <li>
        Implement Feature 2: Display the Distance of each Trip
        <ul>
            <li>Implement gui</li>
            <li>Implement Observer Pattern</li>
        </ul>
    </li>
    <li>
        Implement Feature 5: Search for a stop by stop_id and display all route_id(s) that contain the stop
        <ul>
            <li>Implement gui</li>
            <li>Implement Observer Pattern</li>
        </ul>
    </li>
    <li>
        Implement Feature 8: Search for a stop by stop_id and display the next trip_id(s) (closest to the current time)
        <ul>
            <li>Implement gui</li>
            <li>Implement Observer Pattern</li>
        </ul>
    </li>
    <li>
        Update data display
        <ul>
            <li>Change display of files to only display a certain number of results</li>
            <li>Page results (ie: see results 1 - 100, 101-200)</li>
            <li>Show last updated time</li>
        </ul>
    </li>
    <li>
        Implement Observer Pattern
        <ul>
            <li>Observer pattern should work when loading files</li>
            <li>Observer pattern should work for any features implemented</li>
            <li>Observer pattern should work for displaying average speed (Feature 3)</li>
            <li>Observer pattern should work for Trips per Stop (Feature 4)</li>
            <li>Observer pattern should work for features implemented this week</li>
        </ul>
    </li>
    <li>
        Update StopTime
        <ul>
            <li>Update StopTime constructor with helper methods to make code more readable (use private setters)</li>
            <li>Fix issues in stop_time loading EauClaire + LAX gtfs files</li>
            <li>Reimplement setters in StopTime and make them private</li>
        </ul>
    </li>
    <li>Update Data View Window help section</li>
    <li>Implement Deep Copy in controller getData() method</li>
    <li>Update Trips per Stop to use observer pattern to display results</li>
</ul>
<h6>Completed</h6>
<s>
    <ul>
        <li></li>
    </ul>
</s>
<hr>
<h5>Lab 7 Assignments:</h5>
<ul>
    <li>
        Create Lab 7 PDF submission
        <ul>
            <li>Create PDF document with what features were implemented</li>
            <li>Generate EA class diagram snapshot update</li>
        </ul>
    </li>
    <li>
        Implement Observer Pattern
        <ul>
            <li>Observer pattern should work when loading files</li>
            <li>Observer pattern should work for any features implemented</li>
            <li>Observer pattern should work for displaying average speed (Feature 3)</li>
            <li>Observer pattern should work for Trips per Stop (Feature 4)</li>
            <li>Observer pattern should work for features implemented this week</li>
        </ul>
    </li>
    <li>
        (Optional for lab 6) Implement Feature 3: Display the average speed based on the start and end times of a trip
        <ul>
            <li>Display output to gui</li>
            <li>Use observer pattern to display results</li>
            <li>Implement method to calculate average speed</li>
        </ul>
    </li>
    <li>
        Implement Feature 2: Display the Distance of each Trip
        <ul>
            <li>Implement gui</li>
            <li>Implement Observer Pattern</li>
            <li>Implement method to calculate distance</li>
        </ul>
    </li>
    <li>
        Implement Feature 5: Search for a stop by stop_id and display all route_id(s) that contain the stop
        <ul>
            <li>Implement gui</li>
            <li>Implement Observer Pattern</li>
            <li>Implement method to search</li>
        </ul>
    </li>
    <li>
        Implement Feature 8: Search for a stop by stop_id and display the next trip_id(s) (closest to the current time)
        <ul>
            <li>Implement gui</li>
            <li>Implement Observer Pattern</li>
            <li>Implement method to search</li>
        </ul>
    </li>
    <li>
        Implement semantic correctness check
        <ul>
            <li>Test that each ID used in the application is unique</li>
            <li>Check that all referenced IDs are found in another file (ex: stop_ids from StopTimes can be found in the stops structure)</li>
            <li>Each trip (in stop_times) should validate that the time listed for a given stop is after the time listed for a previous stop and before a time listed for the next stop</li>
        </ul>
    </li>
    <li>
        Update data display
        <ul>
            <li>Change display of files to only display a certain number of results</li>
            <li>Page results (ie: see results 1 - 100, 101-200)</li>
            <li>Show last updated time</li>
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
