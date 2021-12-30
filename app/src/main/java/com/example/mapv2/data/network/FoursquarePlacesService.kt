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
        @Query("ll") ll: String,
        @Header("Accept") accept: String,
        @Header("Authorization") token: String
    ): JsonData

    @GET("{id}/photos")
    suspend fun getPhotos(
        @Path("id") id: String,
        //@Query("limit") limit: String,
        //@Query("sort") sort: String,
        @Header("Accept") accept: String,
        @Header("Authorization") token: String
    ): List<Photo>

    @GET("{id}/tips")
    suspend fun getTips(
        @Path("id") id: String,
        @Header("Accept") accept: String,
        @Header("Authorization") token: String
    ): List<Tip>
}