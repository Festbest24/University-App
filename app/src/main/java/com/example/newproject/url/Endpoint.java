package com.example.newproject.url;

public class Endpoint {

    public static final String END_URL = "https://maps.googleapis.com/maps/api/";

    /**
     * server query
     */
    public static final String NEARBY_PLACES = END_URL + "place/nearbysearch/json?";
    public static final String GET_SPECIFIC_CATEGORY = END_URL + "place/textsearch/json?";

}
