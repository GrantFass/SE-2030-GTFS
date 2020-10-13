<!--
    @author Grant Fass
-->
<h1>SE2030TeamG</h1>
<hr>
<h3>Program Status:</h3>
<ul>
    <li>Stops Loads and Displays</li>
    <li>Stop_Times does not load the full length file, but will load shorter versions</li>
    <li>Java SDK: 1.8.0_242</li>
    <li>Junit 5.4.2 used for testing</li>
</ul>
<hr>
<h3 style="color:red">TODO:</h3>
<h5>Joy Cross:</h5>
<h6>TODO</h6>
<ul>
    <li>
        Implement Stop, StopTime, Stops, & StopTimes
        <ul>
            <li>Add JavaDoc comments</li>
            <li>update constructor</li>
            <li>Add ability to load multiple GTFS files in succession (Delete old data)</li>
            <li>Display alert to user when overwriting data during file load, Done by throwing DataFormatException</li>
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
            </ul>
        </li>
    </ul>
</s>
<h5>Ryan Becker:</h5>
<h6>TODO</h6>
<ul>
    <li>
        Implement Route & Routes
        <ul>
            
            <li>Add ability to load multiple GTFS files in succession (Delete old data)</li>
            
        </ul>
    </li>
</ul>
<h6>Completed</h6>
<s>
    <ul>
        <li>
            Implement Route & Routes
            <ul>
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
    <li>
        Implement Trip & Trips
        <ul>
            <li>Add JavaDoc comments</li>
            <li>Implement file loading and parsing</li>
            <li>Add ability to load multiple GTFS files in succession (Delete old data)</li>
            <li>Display alert to user when overwriting data during file load, Done by throwing DataFormatException</li>
            <li>update toString method override</li>
        </ul>
    </li>
</ul>
<h6>Completed</h6>
<s>
    <ul>
        <li>
            Implement Trip & Trips
            <ul>
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
    <li>
        Implement Data View Window
        <ul>
            <li>Implement Data View FXML file</li>
            <li>Implement Data View controller</li>
            <li>Implement observer interface into data view controller</li>
        </ul>
    </li>
</ul>
<h6>Completed</h6>
<s>
    <ul>
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
                <li>Show snapshot of data structures in GUI</li>
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
    </ul>
</s>
<h5>Unassigned:</h5>
<ul>
    <li>
        Implement Data class
        <ul>
            <li>Add JavaDoc comments</li>
            <li>Implement subject interface</li>
            <li>Implement getters</li>
            <li>Implement constructors</li>
            <li>Update toString method override</li>
        </ul>
    </li>
    <li>
        Implement GTFS file load capabilities
        <ul>
            <li>Update displayed snapshot of data structures in GUI</li>
            <li>Verify GTFS file load passes acceptance tests</li>
            <li>Allow loading of multiple files at same time</li>
        </ul>
    </li>
    <li>
        Update Help Menu
        <ul>
            <li>Update introduction information</li>
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
