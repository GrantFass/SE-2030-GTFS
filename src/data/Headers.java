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

import java.util.ArrayList;
import java.util.Objects;

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

    /**
     * @return the number of headers
     * @author Grant Fass
     */
    public int length(){
        return headers.size();
    }

    /**
     * check if the two objects are equal
     * @param o the object to compare to this object
     * @return if the two objects are equal
     * @author Grant Fass
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        Headers headers1 = (Headers) o;
        if (headers1.length() != this.length()) {
            return false;
        }
        for (int i = 0; i < this.length(); i++) {
            if (!headers.get(i).equals(headers1.headers.get(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * override default hashCode method
     * @return the hashcode
     * @author Grant Fass
     */
    @Override
    public int hashCode() {
        return Objects.hash(headers);
    }
}
