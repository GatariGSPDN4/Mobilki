package com.example.mapv2.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlacesList (
    @Json(name = "results")
    val placeList: List<Place>
)

@JsonClass(generateAdapter = true)
data class Place (
    @Json(name = "fsq_id")
    val fsq_id: String,
    @Json(name = "categories")
    val categories: List<Categories>,
    @Json(name = "distance")
    val distanceInMetr: Int,
    @Json(name = "geocodes")
    val geoCodes: GeoCodes,
    @Json(name = "location")
    val location: Location,
    @Json(name = "name")
    val name: String
)

@JsonClass(generateAdapter = true)
data class GeoCodes(
    @Json(name = "main")
    val main: Main
)

@JsonClass(generateAdapter = true)
data class Main(
    @Json(name = "latitude")
    val latitude: Double,
    @Json(name = "longitude")
    val longitude: Double)

@JsonClass(generateAdapter = true)
data class Location(
    @Json(name = "address")
    val address: String,
    @Json(name = "country")
    val country: String,
    @Json(name = "region")
    val region: String
)

@JsonClass(generateAdapter = true)
data class Categories(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
)