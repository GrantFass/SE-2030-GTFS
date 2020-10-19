package SE2030TransitProject;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

class StopTimeTest {
    /**
     * Tests the constructor for StopTime
     * @author Grant Fass
     */
    @Test
    void StopTime(){
        /*
        Example valid fields
        trip_id,       arrival_time, departure_time, stop_id, stop_sequence, stop_headsign, pickup_type, drop_off_type
        21736564_2535, 08:51:00,     08:51:00,       9113,    1,              ,             0,           0
         */
        //make sure exception thrown when all arguments are empty
        assertThrows(IllegalArgumentException.class, () -> {
            StopTime stopTime = new StopTime("", "", "",
                    "", "" ,"", "", "",
                    "","","","");
        });
        //make sure exception thrown when stop_id missing
        assertThrows(IllegalArgumentException.class, () -> {
            StopTime stopTime = new StopTime("00:00:00", "0",
                    "0", "00:00:00", "0", "0",
                    "0", "sometext", "",
                    "1", "0", "11");
        });
        //make sure exception thrown when trip_id missing
        assertThrows(IllegalArgumentException.class, () -> {
            StopTime stopTime = new StopTime("00:00:00", "0",
                    "0", "00:00:00", "0", "0",
                    "0", "sometext", "11",
                    "1", "0", "");
        });
        //make sure exception thrown when stop_sequence missing
        assertThrows(IllegalArgumentException.class, () -> {
            StopTime stopTime = new StopTime("00:00:00", "0",
                    "0", "00:00:00", "0", "0",
                    "0", "sometext", "11",
                    "", "0", "11");
        });
        //make sure exception thrown when stop_id, stop_sequence, and trip_id are missing
        assertThrows(IllegalArgumentException.class, () -> {
            StopTime stopTime = new StopTime("00:00:00", "0",
                    "0", "00:00:00", "0", "0",
                    "0", "sometext", "",
                    "", "0", "");
        });
        //make sure exception thrown when arrival_time and departure_time is missing
        assertThrows(IllegalArgumentException.class, () -> {
            StopTime stopTime = new StopTime("", "0",
                    "0", "", "0", "0",
                    "0", "sometext", "11",
                    "1", "0", "11");
        });
        //make sure exception thrown when arrival_time is formatted wrong
        assertThrows(IllegalArgumentException.class, () -> {
            StopTime stopTime = new StopTime("0", "0",
                    "0", "00:00:00", "0", "0",
                    "0", "sometext", "11",
                    "1", "0", "11");
        });
        //make sure exception thrown when departure_time is formatted wrong
        assertThrows(IllegalArgumentException.class, () -> {
            StopTime stopTime = new StopTime("00:00:00", "0",
                    "0", "0", "0", "0",
                    "0", "sometext", "11",
                    "1", "0", "11");
        });
        //make sure no issues if either arrival_time or departure_time is missing but not both
        try {
            StopTime stopTime = new StopTime("", "0",
                    "0", "00:00:00", "0", "0",
                    "0", "sometext", "11",
                    "0", "0", "11");
            StopTime stopTime2 = new StopTime("00:00:00", "0",
                    "0", "", "0", "0",
                    "0", "sometext", "11",
                    "0", "0", "11");
            assertEquals(stopTime, stopTime2);
        } catch (IllegalArgumentException e) {
            fail();
        }
        //make sure stop_headsign can be empty and will not throw exception
        try {
            StopTime stopTime = new StopTime("00:00:00", "0",
                    "0", "00:00:00", "0", "0",
                    "0", "", "11",
                    "0", "0", "11");
        } catch (IllegalArgumentException e) {
            fail();
        }
    }

    /**
     * Make sure that the arrival time is parsed correctly
     * @author Grant Fass
     */
    @Test
    void getArrivalTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        StopTime stopTime = new StopTime("", "0",
                "0", "00:00:00", "0", "0",
                "0", "sometext", "11",
                "0", "0", "11");
        assertEquals(new Timestamp(70, 0, 1, 0, 0, 0, 0), stopTime.getArrivalTime());

        stopTime = new StopTime("00:00:00", "0",
                "0", "00:00:00", "0", "0",
                "0", "sometext", "11",
                "0", "0", "11");
        assertEquals(new Timestamp(70, 0, 1, 0, 0, 0, 0), stopTime.getArrivalTime());

        stopTime = new StopTime("01:00:00", "0",
                "0", "00:00:00", "0", "0",
                "0", "sometext", "11",
                "0", "0", "11");
        assertEquals(new Timestamp(70, 0, 1, 1, 0, 0, 0), stopTime.getArrivalTime());

        stopTime = new StopTime("00:01:00", "0",
                "0", "00:00:00", "0", "0",
                "0", "sometext", "11",
                "0", "0", "11");
        assertEquals(new Timestamp(70, 0, 1, 0, 1, 0, 0), stopTime.getArrivalTime());

        stopTime = new StopTime("00:00:01", "0",
                "0", "00:00:00", "0", "0",
                "0", "sometext", "11",
                "0", "0", "11");
        assertEquals(new Timestamp(70, 0, 1, 0, 0, 1, 0), stopTime.getArrivalTime());

        stopTime = new StopTime("01:01:01", "0",
                "0", "00:00:00", "0", "0",
                "0", "sometext", "11",
                "0", "0", "11");
        assertEquals(new Timestamp(70, 0, 1, 1, 1, 1, 0), stopTime.getArrivalTime());

        stopTime = new StopTime("99:00:00", "0",
                "0", "00:00:00", "0", "0",
                "0", "sometext", "11",
                "0", "0", "11");
        assertEquals(new Timestamp(70, 0, 1, 99, 0, 0, 0), stopTime.getArrivalTime());
    }

    /**
     * Make sure default value is returned when empty and expected value returned with integer input
     * @author Grant Fass
     */
    @Test
    void getContinuousDropOff() {
        //make sure that enum returns default value when empty
        StopTime stopTime = new StopTime("00:00:00", "", "0",
                "00:00:00", "0", "0", "0",
                "sometext", "11", "1", "0", "11");
        assertEquals(ContinuousDropOffEnum.NO_CONTINUOUS_DROP_OFF, stopTime.getContinuousDropOff());
        stopTime = new StopTime("00:00:00", "0", "0",
                "00:00:00", "0", "0", "0",
                "sometext", "11", "1", "0", "11");
        assertEquals(ContinuousDropOffEnum.CONTINUOUS_DROP_OFF, stopTime.getContinuousDropOff());
        stopTime = new StopTime("00:00:00", "1", "0",
                "00:00:00", "0", "0", "0",
                "sometext", "11", "1", "0", "11");
        assertEquals(ContinuousDropOffEnum.NO_CONTINUOUS_DROP_OFF, stopTime.getContinuousDropOff());
        stopTime = new StopTime("00:00:00", "2", "0",
                "00:00:00", "0", "0", "0",
                "sometext", "11", "1", "0", "11");
        assertEquals(ContinuousDropOffEnum.CALL_AGENCY_TO_ARRANGE_CONTINUOUS_DROP_OFF, stopTime.getContinuousDropOff());
        stopTime = new StopTime("00:00:00", "3", "0",
                "00:00:00", "0", "0", "0",
                "sometext", "11", "1", "0", "11");
        assertEquals(ContinuousDropOffEnum.COORDINATE_WITH_DRIVER_FOR_CONTINUOUS_DROP_OFF, stopTime.getContinuousDropOff());
    }

    /**
     * Make sure default value is returned when empty and expected value returned with integer input
     * @author Grant Fass
     */
    @Test
    void getContinuousPickup() {
        //make sure that enum returns default value when empty
        StopTime stopTime = new StopTime("00:00:00", "0", "",
                "00:00:00", "0", "0", "0",
                "sometext", "11", "1", "0", "11");
        assertEquals(ContinuousPickupEnum.NO_CONTINUOUS_PICKUP, stopTime.getContinuousPickup());
        stopTime = new StopTime("00:00:00", "0", "0",
                "00:00:00", "0", "0", "0",
                "sometext", "11", "1", "0", "11");
        assertEquals(ContinuousPickupEnum.CONTINUOUS_PICKUP, stopTime.getContinuousPickup());
        stopTime = new StopTime("00:00:00", "0", "1",
                "00:00:00", "0", "0", "0",
                "sometext", "11", "1", "0", "11");
        assertEquals(ContinuousPickupEnum.NO_CONTINUOUS_PICKUP, stopTime.getContinuousPickup());
        stopTime = new StopTime("00:00:00", "0", "2",
                "00:00:00", "0", "0", "0",
                "sometext", "11", "1", "0", "11");
        assertEquals(ContinuousPickupEnum.CALL_AGENCY_TO_ARRANGE_CONTINUOUS_PICKUP, stopTime.getContinuousPickup());
        stopTime = new StopTime("00:00:00", "0", "3",
                "00:00:00", "0", "0", "0",
                "sometext", "11", "1", "0", "11");
        assertEquals(ContinuousPickupEnum.COORDINATE_WITH_DRIVER_FOR_CONTINUOUS_PICKUP, stopTime.getContinuousPickup());
    }

    /**
     * Make sure that the departure time is parsed correctly
     * @author Grant Fass
     */
    @Test
    void getDepartureTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        StopTime stopTime = new StopTime("00:00:00", "0",
                "0", "", "0", "0",
                "0", "sometext", "11",
                "0", "0", "11");
        assertEquals(new Timestamp(70, 0, 1, 0, 0, 0, 0), stopTime.getDepartureTime());

        stopTime = new StopTime("00:00:00", "0",
                "0", "", "0", "0",
                "0", "sometext", "11",
                "0", "0", "11");
        assertEquals(new Timestamp(70, 0, 1, 0, 0, 0, 0), stopTime.getDepartureTime());

        stopTime = new StopTime("00:00:00", "0",
                "0", "01:00:00", "0", "0",
                "0", "sometext", "11",
                "0", "0", "11");
        assertEquals(new Timestamp(70, 0, 1, 1, 0, 0, 0), stopTime.getDepartureTime());

        stopTime = new StopTime("00:00:00", "0",
                "0", "00:01:00", "0", "0",
                "0", "sometext", "11",
                "0", "0", "11");
        assertEquals(new Timestamp(70, 0, 1, 0, 1, 0, 0), stopTime.getDepartureTime());

        stopTime = new StopTime("00:00:00", "0",
                "0", "00:00:01", "0", "0",
                "0", "sometext", "11",
                "0", "0", "11");
        assertEquals(new Timestamp(70, 0, 1, 0, 0, 1, 0), stopTime.getDepartureTime());

        stopTime = new StopTime("00:00:00", "0",
                "0", "01:01:01", "0", "0",
                "0", "sometext", "11",
                "0", "0", "11");
        assertEquals(new Timestamp(70, 0, 1, 1, 1, 1, 0), stopTime.getDepartureTime());

        stopTime = new StopTime("00:00:00", "0",
                "0", "99:00:00", "0", "0",
                "0", "sometext", "11",
                "0", "0", "11");
        assertEquals(new Timestamp(70, 0, 1, 99, 0, 0, 0), stopTime.getDepartureTime());
    }

    /**
     * Make sure default value is returned when empty and expected value returned with integer input
     * @author Grant Fass
     */
    @Test
    void getDropOffType() {
        //make sure that enum returns default value when empty
        StopTime stopTime = new StopTime("00:00:00", "0", "0",
                "00:00:00", "", "0", "0",
                "sometext", "11", "1", "0", "11");
        assertEquals(DropOffTypeEnum.REGULARLY_SCHEDULED_DROP_OFF, stopTime.getDropOffType());
        stopTime = new StopTime("00:00:00", "0", "0",
                "00:00:00", "0", "0", "0",
                "sometext", "11", "1", "0", "11");
        assertEquals(DropOffTypeEnum.REGULARLY_SCHEDULED_DROP_OFF, stopTime.getDropOffType());
        stopTime = new StopTime("00:00:00", "0", "0",
                "00:00:00", "1", "0", "0",
                "sometext", "11", "1", "0", "11");
        assertEquals(DropOffTypeEnum.NO_DROP_OFF_AVAILABLE, stopTime.getDropOffType());
        stopTime = new StopTime("00:00:00", "0", "0",
                "00:00:00", "2", "0", "0",
                "sometext", "11", "1", "0", "11");
        assertEquals(DropOffTypeEnum.PHONE_AGENCY_FOR_DROP_OFF, stopTime.getDropOffType());
        stopTime = new StopTime("00:00:00", "0", "0",
                "00:00:00", "3", "0", "0",
                "sometext", "11", "1", "0", "11");
        assertEquals(DropOffTypeEnum.COORDINATE_WITH_DRIVER_FOR_DROP_OFF, stopTime.getDropOffType());
    }

    /**
     * Make sure default value is returned when empty and expected value returned with integer input
     * @author Grant Fass
     */
    @Test
    void getPickupType() {
        //make sure that enum returns default value when empty
        StopTime stopTime = new StopTime("00:00:00", "0", "0",
                "00:00:00", "0", "", "0",
                "sometext", "11", "1", "0", "11");
        assertEquals(PickupTypeEnum.REGULARLY_SCHEDULED_PICKUP, stopTime.getPickupType());
        stopTime = new StopTime("00:00:00", "0", "0",
                "00:00:00", "0", "0", "0",
                "sometext", "11", "1", "0", "11");
        assertEquals(PickupTypeEnum.REGULARLY_SCHEDULED_PICKUP, stopTime.getPickupType());
        stopTime = new StopTime("00:00:00", "0", "0",
                "00:00:00", "0", "1", "0",
                "sometext", "11", "1", "0", "11");
        assertEquals(PickupTypeEnum.NO_PICKUP_AVAILABLE, stopTime.getPickupType());
        stopTime = new StopTime("00:00:00", "0", "0",
                "00:00:00", "0", "2", "0",
                "sometext", "11", "1", "0", "11");
        assertEquals(PickupTypeEnum.PHONE_AGENCY_FOR_PICKUP, stopTime.getPickupType());
        stopTime = new StopTime("00:00:00", "0", "0",
                "00:00:00", "0", "3", "0",
                "sometext", "11", "1", "0", "11");
        assertEquals(PickupTypeEnum.COORDINATE_WITH_DRIVER_FOR_PICKUP, stopTime.getPickupType());
    }

    @Test
    void getShapeDistTraveled() {
        fail();
    }

    @Test
    void getStopHeadsign() {
        fail();
    }

    @Test
    void getStopID() {
        fail();
    }

    @Test
    void getStopSequence() {
        fail();
    }

    /**
     * Make sure default value is returned when empty and expected value returned with integer input
     * @author Grant Fass
     */
    @Test
    void getTimepoint() {
        //make sure that enum returns default value when empty
        StopTime stopTime = new StopTime("00:00:00", "0", "0",
                "00:00:00", "0", "0", "0",
                "sometext", "11", "1", "", "11");
        assertEquals(TimepointEnum.EXACT_TIME, stopTime.getTimepoint());
        stopTime = new StopTime("00:00:00", "0", "0",
                "00:00:00", "0", "0", "0",
                "sometext", "11", "1", "0", "11");
        assertEquals(TimepointEnum.APPROXIMATE_TIME, stopTime.getTimepoint());
        stopTime = new StopTime("00:00:00", "0", "0",
                "00:00:00", "0", "0", "0",
                "sometext", "11", "1", "1", "11");
        assertEquals(TimepointEnum.EXACT_TIME, stopTime.getTimepoint());
    }

    @Test
    void getTripID() {
        fail();
    }
}