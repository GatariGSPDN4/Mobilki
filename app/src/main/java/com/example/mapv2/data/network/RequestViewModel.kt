package com.example.mapv2.data.network

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class RequestViewModel : ViewModel() {

    private val repository: PlacesRepository = PlacesRepository()

    val placesLiveData = MutableLiveData<JsonData>()
    val photosLiveData = MutableLiveData<Photo>()
    val tipsLiveData = MutableLiveData<List<Tip>>()
    val placesMap = HashMap<String,Place>()
    var currentMarkerID = MutableLiveData<String>()

    fun makeFindRequest(
        query: String,
        ll: String,
        radius: Int,
        limit: Int,
        accept: String,
        token: String
    ) {
        viewModelScope.launch {
            try {
                val request = repository.findPlaces(query, ll, radius, limit, accept, token)
                placesMap.clear()
                for (place in request!!.places) {
                    placesMap.put(place.fsq_id,place)
                }
                placesLiveData.postValue(request!!)
            } catch (e: Exception) {}
        }
    }

    fun makeNearbyRequest(
        ll: String,
        accept: String,
        token: String
    ) {
        viewModelScope.launch {
            val request = repository.getNearbyPlaces(ll,accept,token)
            placesMap.clear()
            for (place in request!!.places) {
                placesMap.put(place.fsq_id,place)
            }
            placesLiveData.postValue(request!!)
        }
    }

    fun getTips(
        id: String,
        accept: String,
        token: String
    ) {
        viewModelScope.launch {
            try {
                val request = repository.getTips(id, accept, token)
                tipsLiveData.postValue(request!!)
            } catch (e: Exception) {e.printStackTrace()}

        }
    }

    fun getPhotos(
        id: String,
        accept: String,
        token: String
    ) {
        viewModelScope.launch {
            try {
                val request = repository.getPhotos(id, accept, token)
                photosLiveData.postValue(request!!)
            } catch (e: Exception) {e.printStackTrace()}
        }
    }
}