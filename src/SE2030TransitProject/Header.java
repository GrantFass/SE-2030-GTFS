/*
 * GTFS Transit Lab
 * File Header Contains Class Headers
 * Name: Grant
 * Created 10/13/2020
 */
package SE2030TransitProject;

/**
 * Headers Purpose: create headers object used to map the order of file headers
 *
 * @author Grant
 * @version Created on 10/13/2020 at 11:01 AM
 */
public class Header {
    private String headerName;
    private int headerIndex;

    /**
     * Constructor for the Headers class
     * @param headerName the name of the header to set
     * @param headerIndex the index of the header to set
     * @author Grant Fass
     */
    public Header(String headerName, int headerIndex) {
        this.headerName = headerName;
        this.headerIndex = headerIndex;
    }

    /**
     * @author Grant Fass
     * @return the value of the headerIndex
     */
    public int getHeaderIndex() {
        return headerIndex;
    }

    /**
     * @author Grant Fass
     * @return the value of the headerName
     */
    public String getHeaderName() {
        return headerName;
    }
}
