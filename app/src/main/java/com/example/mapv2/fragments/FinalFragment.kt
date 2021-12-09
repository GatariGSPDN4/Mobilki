package com.example.mapv2.fragments

import android.graphics.Point
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mapv2.R
import com.example.mapv2.data.managers.*
import com.example.mapv2.data.dataClasses.*
import com.example.mapv2.databinding.FragmentFinalBinding
import com.example.mapv2.network.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.common.api.Response
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.Request


class FinalFragment : Fragment() {
    lateinit var binding:FragmentFinalBinding
    lateinit var moshi: Moshi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFinalBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        super.onViewCreated(view, savedInstanceState)

        val dataManager: DataManager = DataManager(requireContext())
        val userData: User = dataManager.readData()

        binding.nameText.setText("${binding.nameText.text} ${userData.name}")
        binding.mailText.setText("${binding.mailText.text} ${userData.mail}")
        binding.passwordText.setText("${binding.passwordText.text} ${userData.password}")

        val userLoginManager: UserLoginManager = UserLoginManager(requireContext())

        binding.unLogBtn.setOnClickListener {
            userLoginManager.unLogin()
            this.findNavController().navigate(R.id.action_finalFragment2_to_registrationFragment)
        }

        var places = getJson()
        mapFragment?.getMapAsync {
            for(place in places.placeList) {
                val coords = LatLng(place!!.geoCodes.main.latitude, place.geoCodes.main.longitude)
                it.addMarker(MarkerOptions().position(coords).title(place.name))
                it.animateCamera(CameraUpdateFactory.newLatLngZoom(coords, 12f))
            }
        }
    }

    private fun getJson() : PlacesList {
        moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<PlacesList> = moshi.adapter(
            PlacesList::class.java
        )
        val jsonString: String =
            requireContext().assets
                .open("data.json")
                .bufferedReader().use {
                    it.readText()
                }
        val place = jsonAdapter.fromJson(jsonString)
        return place!!
    }

    private val callback = OnMapReadyCallback { googleMap ->
        val sevastopol = LatLng(44.629650, 33.535667)
        googleMap.addMarker(MarkerOptions().position(sevastopol).title("Marker in Sevastopol"))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sevastopol,12f))
    }

    /*private fun findNearbyPlaces() {
        val client = OkHttpClient()

        val request: Request = Request.Builder()
            .url("https://api.foursquare.com/v3/places/nearby?ll=44.59478%2C33.47508&limit=10")
            .get()
            .addHeader("Accept", "application/json")
            .addHeader("Authorization", "fsq33y64+/crEjTkhK1Lw783LIEWZd4KlWQW0BQZpqxQH8E=")
            .build()

        val response: okhttp3.Response = client.newCall(request).execute()
    }*/

}