package SE2030TransitProject;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class RouteTest {

    Route test_route;
    //Start Valid Fields
    final String route_id = "44";
    final String agency_id = "MCTS";
    final String route_short_name = "44";
    final String route_long_name = "National Flyer";
    final String route_desc = "This, is a description";
    final String route_type = "3";
    final String route_url = "http://website";
    final String route_color = "ff0000";
    final String route_text_color = "0000ff";
    final String route_sort_order = "0";
    final String continuous_pickup = "0";
    final String continuous_drop_off = "0";
    //End Valid Fields


    /**
     * Tests constructor for Routes
     * @author Ryan Becker
     */
    @Test
    void Route(){
        //Exception thrown when all fields are empty
        assertThrows(IllegalArgumentException.class, () -> {
            Route route = new Route("", "", "", "", "",
                    "", "", "", "", "", "",
                    "");
        });

        //Exception thrown when route_id is empty
        assertThrows(IllegalArgumentException.class, () -> {
            Route route = new Route("", agency_id, route_short_name, route_long_name, route_desc, route_type,
                    route_url, route_color, route_text_color, route_sort_order, continuous_pickup, continuous_drop_off);
        });

        //Exception thrown when route_color is invalid
        assertThrows(IllegalArgumentException.class, () -> {
            Route route = new Route(route_id, agency_id, route_short_name, route_long_name, route_desc, route_type,
                    route_url, "IM_INVALID", route_text_color, route_sort_order, continuous_pickup, continuous_drop_off);
        });

        //Exception thrown when route_text_color is invalid
        assertThrows(IllegalArgumentException.class, () -> {
            Route route = new Route(route_id, agency_id, route_short_name, route_long_name, route_desc, route_type,
                    route_url, route_color, "IM_INVALID", route_sort_order, continuous_pickup, continuous_drop_off);
        });

        //Exception thrown when route_sort_order is negative
        assertThrows(IllegalArgumentException.class, () -> {
            Route route = new Route(route_id, agency_id, route_short_name, route_long_name, route_desc, route_type,
                    route_url, route_color, route_text_color, "-1", continuous_pickup, continuous_drop_off);
        });

        //Exception thrown when route_url is malformed
        assertThrows(IllegalArgumentException.class, () ->{
            Route route = new Route(route_id, agency_id, route_short_name, route_long_name, route_desc, route_type,
                    "invalid", route_color, route_text_color, route_sort_order, continuous_pickup, continuous_drop_off);
        });

        //Tests if class can be made as long as route_id exists
        try{
            Route route = new Route(route_id, "", "", "", "",
                    "", "", "", "", "", "",
                    "");
        } catch (IllegalArgumentException invalidClass){
            fail();
        }



    }

    /**
     * Sets up initial constructor to use in tests
     * @author Ryan Becker
     */
    @BeforeEach
    void setUp() {
        test_route = new Route(route_id, agency_id, route_short_name, route_long_name, route_desc, route_type,
                route_url, route_color, route_text_color, route_sort_order, continuous_pickup, continuous_drop_off);
    }

    /**
     * Makes sure route_id is returned correctly
     * Makes sure route_id cannot be empty
     * @author Ryan Becker
     */
    @Test
    void getRouteID() {
        //Makes sure route_id is returned
        assertEquals("44", test_route.getRouteID());

        //Makes sure route_id cannot be empty
        assertThrows(IllegalArgumentException.class, () -> {
            test_route = new Route("", agency_id, route_short_name, route_long_name, route_desc, route_type,
                    route_url, route_color, route_text_color, route_sort_order, continuous_pickup, continuous_drop_off);
        });
    }

    /**
     * Makes sure agency_id is returned correctly
     * Makes sure agency_id can be empty
     * @author Ryan Becker
     */
    @Test
    void getAgencyID() {
        //Makes sure agency_id is returned
        assertEquals("MCTS", test_route.getAgencyID());

        //Makes sure empty field returns default value
        test_route = new Route(route_id, "", route_short_name, route_long_name, route_desc, route_type,
                route_url, route_color, route_text_color, route_sort_order, continuous_pickup, continuous_drop_off);
        assertEquals("", test_route.getAgencyID());
    }

    /**
     * Makes sure route_short_name is returned correctly
     * Makes sure route_short_name can be empty
     * @author Ryan Becker
     */
    @Test
    void getRouteShortName() {
        //Makes sure route_short_name is returned
        assertEquals("44", test_route.getRouteShortName());

        //Makes sure route_short_name can be empty
        test_route = new Route(route_id, agency_id, "", route_long_name, route_desc, route_type,
                route_url, route_color, route_text_color, route_sort_order, continuous_pickup, continuous_drop_off);
        assertEquals("", test_route.getRouteShortName());
    }

    /**
     * Makes sure route_long_name is returned correctly
     * Makes sure route_long_name can be empty
     * @author Ryan Becker
     */
    @Test
    void getRouteLongName() {
        //Makes sure route_long_name is returned
        assertEquals("National Flyer", test_route.getRouteLongName());

        //Makes sure route_long_name can be empty
        test_route = new Route(route_id, agency_id, route_short_name, "", route_desc, route_type,
                route_url, route_color, route_text_color, route_sort_order, continuous_pickup, continuous_drop_off);
        assertEquals("", test_route.getRouteLongName());
    }

    /**
     * Makes sure route_desc is returned properly
     * Makes sure route_desc can be empty
     * @author Ryan Becker
     */
    @Test
    void getRouteDesc() {
        //Makes sure route_desc is returned
        assertEquals("This, is a description", test_route.getRouteDesc());

        //Makes sure route_desc can be empty
        test_route = new Route(route_id, agency_id, route_short_name, route_long_name, "", route_type,
                route_url, route_color, route_text_color, route_sort_order, continuous_pickup, continuous_drop_off);
        assertEquals("", test_route.getRouteDesc());
    }

    /**
     * Makes sure route_type is returned properly
     * Makes sure route_type is defaulted to 3 (BUS) if empty
     * Makes sure route_type is defaulted to 3 (BUS) if "invalid"
     * @author Ryan Becker
     */
    @Test
    void getRouteType() {
        //Makes sure route_type is returned
        assertEquals(RouteTypeEnum.BUS, test_route.getRouteType());

        //Makes sure route_type defaults when empty
        test_route = new Route(route_id, agency_id, route_short_name, route_long_name, route_desc, "",
                route_url, route_color, route_text_color, route_sort_order, continuous_pickup, continuous_drop_off);
        assertEquals(RouteTypeEnum.BUS, test_route.getRouteType());

        //Makes sure route_type defaults when "invalid"
        test_route = new Route(route_id, agency_id, route_short_name, route_long_name, route_desc, "-1",
                route_url, route_color, route_text_color, route_sort_order, continuous_pickup, continuous_drop_off);
        assertEquals(RouteTypeEnum.BUS, test_route.getRouteType());


    }

    /**
     * Makes sure route_url is returned properly
     * Makes sure route_url can be empty
     * @author Ryan Becker
     */
    @Test
    void getRouteURL() {
        //Makes sure route_url is returned
        try{
            assertEquals(new URL("http://website"), test_route.getRouteURL());
        }catch (MalformedURLException invalidURL){
            fail();
        }

        //Makes sure route_url can be empty
        test_route = new Route(route_id, agency_id, route_short_name, route_long_name, route_desc, route_type,
                "", route_color, route_text_color, route_sort_order, continuous_pickup, continuous_drop_off);
        try{
            assertEquals(new URL("http://NULL"), test_route.getRouteURL());
        }catch (MalformedURLException invalidURL){
            fail();
        }
    }

    /**
     * Makes sure route_color is returned correctly
     * Makes sure route_color is defaulted to white if empty
     * @author Ryan Becker
     */
    @Test
    void getRouteColor() {
        //Makes sure route_color is returned
        assertEquals(Color.valueOf("ff0000"), test_route.getRouteColor());

        //Makes sure route_color defaults to white ("ffffff") if empty
        test_route = new Route(route_id, agency_id, route_short_name, route_long_name, route_desc, route_type,
                route_url, "", route_text_color, route_sort_order, continuous_pickup, continuous_drop_off);
        assertEquals(Color.valueOf("ffffff"), test_route.getRouteColor());
    }

    /**
     * Makes sure route_text_color is returned correctly
     * Makes sure route_text_color is defaulted to black if empty
     * @author Ryan Becker
     */
    @Test
    void getRouteTextColor() {
        //Makes sure route_text_color is returned
        assertEquals(Color.valueOf("0000ff"), test_route.getRouteTextColor());

        //Makes sure route_text_color defaults to black ("000000") if empty
        test_route = new Route(route_id, agency_id, route_short_name, route_long_name, route_desc, route_type,
                route_url, route_color, "", route_sort_order, continuous_pickup, continuous_drop_off);
        assertEquals(Color.valueOf("000000"), test_route.getRouteTextColor());
    }

    /**
     * Makes sure route_sort_order is returned correctly
     * Makes sure route_sort_order is defaulted to 0 if empty
     * Makes sure route_sort_order cannot be negative
     * @author Ryan Becker
     */
    @Test
    void getRouteSortOrder() {
        //Makes sure route_sort_order is returned
        assertEquals(0, test_route.getRouteSortOrder());

        //Makes sure route_sort_order cannot be negative
        assertThrows(IllegalArgumentException.class, () -> {
            test_route = new Route(route_id, agency_id, route_short_name, route_long_name, route_desc, route_type,
                    route_url, route_color, route_text_color, "-1", continuous_pickup, continuous_drop_off);
            test_route.getRouteSortOrder();
        });

        //Makes sure route_sort_order defaults to "0" if empty
        test_route = new Route(route_id, agency_id, route_short_name, route_long_name, route_desc, route_type,
                route_url, route_color, route_text_color, "", continuous_pickup, continuous_drop_off);
        assertEquals(0, test_route.getRouteSortOrder());


    }

    /**
     * Makes sure continuous_pickup is returned correctly
     * Makes sure continuous_pickup defaults when "invalid"
     * Makes sure continuous_pickup defaults when empty
     * @author Ryan Becker
     */
    @Test
    void getContinuousPickup() {
        assertEquals(ContinuousPickupEnum.CONTINUOUS_PICKUP, test_route.getContinuousPickup());

        test_route = new Route(route_id, agency_id, route_short_name, route_long_name, route_desc, route_type,
                route_url, route_color, route_text_color, route_sort_order, "-1", continuous_drop_off);
        assertEquals(ContinuousPickupEnum.NO_CONTINUOUS_PICKUP, test_route.getContinuousPickup());

        test_route = new Route(route_id, agency_id, route_short_name, route_long_name, route_desc, route_type,
                route_url, route_color, route_text_color, route_sort_order, "", continuous_drop_off);
        assertEquals(ContinuousPickupEnum.NO_CONTINUOUS_PICKUP, test_route.getContinuousPickup());
    }

    /**
     * Makes sure continuous_drop_off is returned correctly
     * Makes sure continuous_drop_off defaults when "invalid"
     * Makes sure continuous_drop_off defaults when empty
     * @author Ryan Becker
     */
    @Test
    void getContinuousDropOff() {
        assertEquals(ContinuousDropOffEnum.CONTINUOUS_DROP_OFF, test_route.getContinuousDropOff());

        test_route = new Route(route_id, agency_id, route_short_name, route_long_name, route_desc, route_type,
                route_url, route_color, route_text_color, route_sort_order, continuous_pickup, "-1");
        assertEquals(ContinuousDropOffEnum.NO_CONTINUOUS_DROP_OFF, test_route.getContinuousDropOff());

        test_route = new Route(route_id, agency_id, route_short_name, route_long_name, route_desc, route_type,
                route_url, route_color, route_text_color, route_sort_order, continuous_pickup, "");
        assertEquals(ContinuousDropOffEnum.NO_CONTINUOUS_DROP_OFF, test_route.getContinuousDropOff());
    }

}