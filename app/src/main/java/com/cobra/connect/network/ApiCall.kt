package com.cobra.connect.network

import com.cobra.connect.datamodels.DirectionsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiCall {
    @GET("maps/api/directions/json")
    fun getDirections(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("sensor") sensor: Boolean,
        @Query("mode") mode: String,
        @Query("key") apiKey: String
    ): Call<DirectionsResponse>
}