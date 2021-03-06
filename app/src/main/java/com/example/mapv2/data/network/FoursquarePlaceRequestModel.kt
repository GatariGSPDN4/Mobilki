package com.example.mapv2.data.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class JsonData (
    @Json(name = "results")
    public val places: List<Place>
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
    val main: Coords
)

@JsonClass(generateAdapter = true)
data class Coords(
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

@JsonClass(generateAdapter = true)
data class JsonPhotos(
    @Json(name = "")
    public val photos: List<Photo>
)

@JsonClass(generateAdapter = true)
data class Photo(
    @Json(name = "prefix")
    val prefix:String,
    @Json(name = "suffix")
    val suffix:String,
)

@JsonClass(generateAdapter = true)
data class Tip(
    @Json(name = "text")
    val text: String
)