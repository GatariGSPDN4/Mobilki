package com.example.mapv2.data.network

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface FoursquarePlacesService {
    @GET("search?")
    suspend fun findPlaces(
        @Query("query") query: String,
        @Query("ll") ll: String,
        @Query("radius") radius: Int,
        @Query("limit") limit: Int,
        @Header("Accept") accept: String,
        @Header("Authorization") token: String
    ): JsonData

    @GET("nearby?")
    suspend fun getNearbyPlaces(
        @Query("ll") ll: Double,
        @Header("Accept") accept: String,
        @Header("Authorization") token: String
    ): JsonData
}