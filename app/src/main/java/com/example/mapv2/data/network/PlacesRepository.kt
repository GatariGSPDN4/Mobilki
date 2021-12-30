package com.example.mapv2.data.network

import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Query

class PlacesRepository {

    private val moshi = Moshi.Builder()
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl("https://api.foursquare.com/v3/places/").build()

    private val requester = retrofit.create(FoursquarePlacesService::class.java)

    suspend fun findPlaces(
        query: String,
        ll: String,
        radius: Int,
        limit: Int,
        accept: String,
        token: String
    ): JsonData? {
        return try {
            requester.findPlaces(query,ll,radius,limit,accept,token)
        } catch (exception: HttpException) {
            null
        }
    }

    suspend fun getNearbyPlaces(
        ll: String,
        accept: String,
        token: String
    ): JsonData? {
        return try {
            requester.getNearbyPlaces(ll,accept,token)
        } catch (exception: HttpException) {
            null
        }
    }
    suspend fun getPhotos(
        id: String,
        accept: String,
        token: String
    ): Photo? {
        return try {
            var photos = requester.getPhotos(id,accept,token)
            photos[0]
        } catch (exception: HttpException) {
            null
        }
    }
    suspend fun getTips(
        id: String,
        accept: String,
        token: String
    ) : List<Tip>? {
        return try {
            var tip = requester.getTips(id,accept,token)
            tip
        } catch (e: Exception) {
            null
        }
    }
}