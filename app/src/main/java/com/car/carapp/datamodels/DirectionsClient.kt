package com.car.carapp.datamodels

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DirectionsClient {
    companion object {

        private const val URL = "https://maps.googleapis.com/"

        private var retrofit: Retrofit? = null
        fun getClient(): Retrofit? {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
    }
}