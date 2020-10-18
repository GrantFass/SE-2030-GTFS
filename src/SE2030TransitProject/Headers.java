/*
 * GTFS Transit Program
 * File Header Contains Class Headers
 * Name: Grant
 * Created 10/13/2020
 */
package SE2030TransitProject;

import java.util.ArrayList;

/**
 * Headers Purpose: create a list of headers used to map the order of file headers
 *
 * @author Grant
 * @version Created on 10/13/2020 at 11:06 AM
 */
public class Headers {
    private ArrayList<Header> headers;

    /**
     * Constructor for the header class used to initialize variables
     * @author Grant Fass
     */
    public Headers() {
        headers = new ArrayList<>();
    }

    /**
     * Add a new header to the list of header objects
     * @param header the header to add
     * @return true if the header was added
     * @author Grant Fass
     */
    public boolean addHeader(Header header) {
        return headers.add(header);
    }

    /**
     * returns the headerName associated with the header at the specified index
     * @param headerIndex the index of the header to find
     * @return the name of the header at the index
     * @author Grant Fass
     */
    public String getHeaderName(int headerIndex) {
        if (headerIndex >= 0 && headerIndex < headers.size()) {
            return headers.get(headerIndex).getHeaderName();
        }
        return null;
    }

    /**
     * returns the headerIndex associated with the header of the specified name
     * @param headerName the name of the header to search for
     * @return -1 if the header was not found, otherwise return the headerIndex associated with the header
     * @author Grant Fass
     */
    public int getHeaderIndex(String headerName) {
        for (Header header: headers) {
            if (header.getHeaderName().toLowerCase().equals(headerName)) {
                return header.getHeaderIndex();
            }
        }
        return -1;
    }

    public int length(){
        return headers.size();
    }
}
