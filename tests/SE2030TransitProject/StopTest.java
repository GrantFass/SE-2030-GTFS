package SE2030TransitProject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StopTest {

    Stop correctStop;

    /**
     * Set up correct Stop object to be used for comparison later
     * @author Joy Cross
     */
    @BeforeEach
    void setUp() {
        correctStop = new Stop("", "0", "", "",
                "", "", "6712", "43.0444475", "-87.9779369",
                "STATE & 5101 #6712", "CST", "https://www.google.com/",
                "0");
    }

    /**
     * Test for Stop constructor, same tests in Stops validate data
     * throw exception when all arguments are empty -
     * throw no exception when all arguments are full -
     * throw exception when stop_id is missing -
     * throw exception when stop_lat is missing -
     * throw exception when stop_lon is missing -
     * throw exception if stop_lon or stop_lat not double -
     * throw exception if wheelchair enum is not int number -
     * throw exception if location type is not int number -
     * throw exception if timezone in wrong format -
     * make sure timezone is parsed correctly -
     * throw exception if URL is malformed (spaces or illegal chars in url) -
     * throw no exception if description is missing (only need to check this one since the other values (stop_name) are all strings and don't need to be parsed)
     * @author Joy Cross
     */
    @Test
    void Stop(){
        // check if exception is thrown with no arguments passed
        assertThrows(IllegalArgumentException.class, () -> {
            Stop stop = new Stop("", "","","",
                    "","","","","","",
            "","", "");
        });
        // check that no exception is thrown with all valid data
        assertDoesNotThrow(() -> {
            Stop stop = new Stop("test", "0", "test", "test",
                    "test", "test", "6712", "43.0444475", "-87.9779369",
                    "STATE & 5101 #6712", "CST", "https://www.google.com/",
                    "0");
        });
        // check if exception is thrown with stop_id missing
        assertThrows(IllegalArgumentException.class, () -> {
            Stop stop = new Stop("test", "0", "test", "test",
                    "test", "test", "", "43.0444475", "-87.9779369",
                    "STATE & 5101 #6712", "CST", "https://www.google.com/",
                    "0");
        });
        // check if exception is thrown with stop_lat missing
        assertThrows(IllegalArgumentException.class, () -> {
            Stop stop = new Stop("test", "0", "test", "test",
                    "test", "", "6712", "", "-87.9779369",
                    "STATE & 5101 #6712", "CST", "https://www.google.com/",
                    "0");
        });
        // check if exception is thrown with stop_lon missing
        assertThrows(IllegalArgumentException.class, () -> {
            Stop stop = new Stop("test", "0", "test", "test",
                    "test", "test", "6712", "43.0444475", "",
                    "STATE & 5101 #6712", "CST", "https://www.google.com/",
                    "0");
        });
        // check if exception is thrown with putting string for stop_lat
        assertThrows(IllegalArgumentException.class, () -> {
            Stop stop = new Stop("test", "0", "test", "test",
                    "test", "test", "6712", "test", "-87.9779369",
                    "STATE & 5101 #6712", "CST", "https://www.google.com/",
                    "0");
        });
        // check if exception is thrown with putting string for stop_lon
        assertThrows(IllegalArgumentException.class, () -> {
            Stop stop = new Stop("test", "0", "test", "test",
                    "test", "test", "6712", "43.0444475", "test",
                    "STATE & 5101 #6712", "CST", "https://www.google.com/",
                    "0");
        });
        // check if exception is thrown with putting string for wheelchair_boarding enum
        assertThrows(IllegalArgumentException.class, () -> {
            Stop stop = new Stop("test", "0", "test", "test",
                    "test", "test", "6712", "43.0444475", "-87.9779369",
                    "STATE & 5101 #6712", "CST", "https://www.google.com/",
                    "test");
        });
        // check if exception is thrown with putting string for location_type enum
        assertThrows(IllegalArgumentException.class, () -> {
            Stop stop = new Stop("test", "test", "test", "test",
                    "test", "test", "6712", "43.0444475", "-87.9779369",
                    "STATE & 5101 #6712", "CST", "https://www.google.com/",
                    "0");
        });
        // check if exception is thrown with putting wrong format for timezone
        assertThrows(IllegalArgumentException.class, () -> {
            Stop stop = new Stop("test", "0", "test", "test",
                    "test", "", "6712", "43.0444475", "-87.9779369",
                    "STATE & 5101 #6712", "test", "https://www.google.com/",
                    "0");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Stop stop = new Stop("test", "0", "test", "test",
                    "test", "Description", "6712", "43.0444475", "-87.9779369",
                    "STATE & 5101 #6712", "00:00:01 CST", "https://www.google.com/",
                    "0");
        });
        // check if timezone is parsed correctly
        assertEquals(correctStop.getStopTimezone().getID().trim(), "CST");

        // check if exception is thrown with putting wrong format for URL
        assertThrows(IllegalArgumentException.class, () -> {
            Stop stop = new Stop("test", "0", "test", "test",
                    "test", "Description", "6712", "43.0444475", "-87.9779369",
                    "STATE & 5101 #6712", "CST", ".com/",
                    "0");
        });

        // check to make sure doesn't throw exception if description field is empty
        assertDoesNotThrow(() -> {
            Stop stop = new Stop("test", "0", "test", "test",
                    "test", "", "6712", "43.0444475", "-87.9779369",
                    "STATE & 5101 #6712", "CST", "https://www.google.com/",
                    "0");
        });

    }

    /**
     * Verify Level ID returns correct value
     * @author Joy Cross
     */
    @Test
    void getLevelID() {
        Stop stop = new Stop("test", "0", "test", "test",
                "test", "test", "6712", "43.0444475", "-87.9779369",
                "STATE & 5101 #6712", "CST", "https://www.google.com/",
                "0");
        assertEquals("test", stop.getLevelID());
    }

    /**
     * Verify Location Type is default type when empty or non correct input
     * Verify Location Type returns the other types when in range 1-4
     * @author Joy Cross
     */
    @Test
    void getLocationType() {
        Stop stop = new Stop("test", "0", "test", "test",
                "test", "test", "6712", "43.0444475", "-87.9779369",
                "STATE & 5101 #6712", "CST", "https://www.google.com/",
                "0");
        assertEquals(LocationTypeEnum.STOP_OR_PLATFORM, stop.getLocationType());
        stop = new Stop("test", "1", "test", "test",
                "test", "test", "6712", "43.0444475", "-87.9779369",
                "STATE & 5101 #6712", "CST", "https://www.google.com/",
                "0");
        assertEquals(LocationTypeEnum.STATION, stop.getLocationType());
        stop = new Stop("test", "2", "test", "test",
                "test", "test", "6712", "43.0444475", "-87.9779369",
                "STATE & 5101 #6712", "CST", "https://www.google.com/",
                "0");
        assertEquals(LocationTypeEnum.ENTRANCE, stop.getLocationType());
        stop = new Stop("test", "3", "test", "test",
                "test", "test", "6712", "43.0444475", "-87.9779369",
                "STATE & 5101 #6712", "CST", "https://www.google.com/",
                "0");
        assertEquals(LocationTypeEnum.GENERIC, stop.getLocationType());
        stop = new Stop("test", "4", "test", "test",
                "test", "test", "6712", "43.0444475", "-87.9779369",
                "STATE & 5101 #6712", "CST", "https://www.google.com/",
                "0");
        assertEquals(LocationTypeEnum.BOARDING_AREA, stop.getLocationType());

        // Should return default value
        stop = new Stop("test", "-1", "test", "test",
                "test", "test", "6712", "43.0444475", "-87.9779369",
                "STATE & 5101 #6712", "CST", "https://www.google.com/",
                "0");
        assertEquals(LocationTypeEnum.STOP_OR_PLATFORM, stop.getLocationType());
        stop = new Stop("test", "5", "test", "test",
                "test", "test", "6712", "43.0444475", "-87.9779369",
                "STATE & 5101 #6712", "CST", "https://www.google.com/",
                "0");
        assertEquals(LocationTypeEnum.STOP_OR_PLATFORM, stop.getLocationType());
    }

    /**
     * Verify parent station returns correct value
     * @author Joy Cross
     */
    @Test
    void getParentStation() {
        Stop stop = new Stop("test", "0", "test", "test",
                "test", "test", "6712", "43.0444475", "-87.9779369",
                "STATE & 5101 #6712", "CST", "https://www.google.com/",
                "0");
        assertEquals("test", stop.getParentStation());
    }

    /**
     * Verify platform code returns correct value
     * @author Joy Cross
     */
    @Test
    void getPlatformCode() {
        Stop stop = new Stop("test", "0", "test", "test",
                "test", "test", "6712", "43.0444475", "-87.9779369",
                "STATE & 5101 #6712", "CST", "https://www.google.com/",
                "0");
        assertEquals("test", stop.getPlatformCode());
    }

    /**
     * Verify stop code returns correct value
     * @author Joy Cross
     */
    @Test
    void getStopCode() {
        Stop stop = new Stop("test", "0", "test", "test",
                "test", "test", "6712", "43.0444475", "-87.9779369",
                "STATE & 5101 #6712", "CST", "https://www.google.com/",
                "0");
        assertEquals("test", stop.getStopCode());
    }

    /**
     * Verify stop desc returns correct value
     * @author Joy Cross
     */
    @Test
    void getStopDescription() {
        Stop stop = new Stop("test", "0", "test", "test",
                "test", "test", "6712", "43.0444475", "-87.9779369",
                "STATE & 5101 #6712", "CST", "https://www.google.com/",
                "0");
        assertEquals("test", stop.getStopDescription());
    }

    /**
     * Verify stop id returns correct value
     * @author Joy Cross
     */
    @Test
    void getStopID() {
        Stop stop = new Stop("test", "0", "test", "test",
                "test", "test", "6712", "43.0444475", "-87.9779369",
                "STATE & 5101 #6712", "CST", "https://www.google.com/",
                "0");
        assertEquals("6712", stop.getStopID());
    }

    /**
     * Verify stop lat returns correct value
     * @author Joy Cross
     */
    @Test
    void getStopLatitude() {
        Stop stop = new Stop("test", "0", "test", "test",
                "test", "test", "6712", "43.0444475", "-87.9779369",
                "STATE & 5101 #6712", "CST", "https://www.google.com/",
                "0");
        assertEquals(43.0444475, stop.getStopLatitude());
    }

    /**
     * Verify stop lon returns correct value
     * @author Joy Cross
     */
    @Test
    void getStopLongitude() {
        Stop stop = new Stop("test", "0", "test", "test",
                "test", "test", "6712", "43.0444475", "-87.9779369",
                "STATE & 5101 #6712", "CST", "https://www.google.com/",
                "0");
        assertEquals(-87.9779369, stop.getStopLongitude());
    }

    /**
     * Verify stop name returns correct value
     * @author Joy Cross
     */
    @Test
    void getStopName() {
        Stop stop = new Stop("test", "0", "test", "test",
                "test", "test", "6712", "43.0444475", "-87.9779369",
                "STATE & 5101 #6712", "CST", "https://www.google.com/",
                "0");
        assertEquals("STATE & 5101 #6712", stop.getStopName());
    }

    /**
     * Verify timezone returns correct value
     * @author Joy Cross
     */
    @Test
    void getStopTimezone() {
        Stop stop = new Stop("test", "0", "test", "test",
                "test", "test", "6712", "43.0444475", "-87.9779369",
                "STATE & 5101 #6712", "CST", "https://www.google.com/",
                "0");
        assertEquals("CST", stop.getStopTimezone().getID());
    }

    /**
     * Verify stop url returns correct value
     * @author Joy Cross
     */
    @Test
    void getStopURL() {
        Stop stop = new Stop("test", "0", "test", "test",
                "test", "test", "6712", "43.0444475", "-87.9779369",
                "STATE & 5101 #6712", "CST", "https://www.google.com/",
                "0");
        assertEquals("https://www.google.com/", stop.getStopURL().toString());
    }

    /**
     * Verify WheelChair Type is default type when empty
     * @author Joy Cross
     */
    @Test
    void getWheelchairBoarding() {
        Stop stop = new Stop("test", "0", "test", "test",
                "test", "test", "6712", "43.0444475", "-87.9779369",
                "STATE & 5101 #6712", "CST", "https://www.google.com/",
                "0");
        assertEquals(WheelchairBoardingEnum.PARENTLESS_NO_INFORMATION, stop.getWheelchairBoarding());
        stop = new Stop("test", "0", "test", "test",
                "test", "test", "6712", "43.0444475", "-87.9779369",
                "STATE & 5101 #6712", "CST", "https://www.google.com/",
                "1");
        assertEquals(WheelchairBoardingEnum.PARENTLESS_WHEELCHAIR_BOARDING_POSSIBLE, stop.getWheelchairBoarding());
        stop = new Stop("test", "0", "test", "test",
                "test", "test", "6712", "43.0444475", "-87.9779369",
                "STATE & 5101 #6712", "CST", "https://www.google.com/",
                "2");
        assertEquals(WheelchairBoardingEnum.PARENTLESS_WHEELCHAIR_BOARDING_NOT_POSSIBLE, stop.getWheelchairBoarding());

        // Should return default value
        stop = new Stop("test", "0", "test", "test",
                "test", "test", "6712", "43.0444475", "-87.9779369",
                "STATE & 5101 #6712", "CST", "https://www.google.com/",
                "0");
        assertEquals(WheelchairBoardingEnum.PARENTLESS_NO_INFORMATION, stop.getWheelchairBoarding());
        stop = new Stop("test", "0", "test", "test",
                "test", "test", "6712", "43.0444475", "-87.9779369",
                "STATE & 5101 #6712", "CST", "https://www.google.com/",
                "0");
        assertEquals(WheelchairBoardingEnum.PARENTLESS_NO_INFORMATION, stop.getWheelchairBoarding());
    }

    @Disabled
    @Test
    void testToString() {
    }
}