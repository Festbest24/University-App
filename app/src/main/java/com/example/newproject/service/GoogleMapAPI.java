package com.example.newproject.service;

import com.example.newproject.model.PlacesResults;
import com.example.newproject.url.Endpoint;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleMapAPI {
    @GET(Endpoint.NEARBY_PLACES)
    Call<PlacesResults> getNearBy(
            @Query("location") String location,
            @Query("radius") int radius,
            @Query("type") String type,
            @Query("keyword") String keyword,
            @Query("key") String key
    );
}
