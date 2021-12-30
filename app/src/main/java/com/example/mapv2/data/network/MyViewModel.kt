package com.example.mapv2.data.network

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapv2.DialogueWindow
import com.example.mapv2.R
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MyViewModel : ViewModel() {

    private val repository: PlacesRepository = PlacesRepository()

    val placesLiveData = MutableLiveData<JsonData>()

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
                val places = repository.findPlaces(query, ll, radius, limit, accept, token)
                placesLiveData.postValue(places)
            } catch (e: Exception) {}
        }
    }

    fun makeNearbyRequest(
        path: String,
        accept: String,
        token: String
    ) {
        viewModelScope.launch {
            //val places = repository.getNearbyPlaces(path,accept,token)
            //placesLiveData.postValue(places)
        }
    }

}