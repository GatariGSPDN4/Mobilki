package com.example.mapv2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mapv2.DialogueWindow
import com.example.mapv2.R
import com.example.mapv2.data.managers.*
import com.example.mapv2.data.dataClasses.*
import com.example.mapv2.databinding.FragmentFinalBinding
import com.example.mapv2.data.network.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlin.concurrent.thread


class FinalFragment : Fragment() {
    lateinit var binding: FragmentFinalBinding
    lateinit var moshi: Moshi
    lateinit var geoLocation: Coords

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFinalBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        geoLocation = Coords(55.751340654, 37.608813383)
        val token = getString(R.string.APIKey)
        val accept = getString(R.string.acceptJson)
        mapFragment?.getMapAsync(setup)

        val model: MyViewModel by viewModels()
        var json: JsonData? = null

        val dataManager = DataManager(requireContext())
        val userData: User = dataManager.readData()

        binding.accountInfo.setText("name:${userData.name}, mail:${userData.mail}, pass:${userData.password}")

        val userLoginManager = UserLoginManager(requireContext())

        model.placesLiveData.observe(viewLifecycleOwner, {
            json = it
            mapFragment?.getMapAsync {
                for (place in json!!.places) {
                    it.addMarker(
                        MarkerOptions().position(
                            LatLng(place.geoCodes.main.latitude, place.geoCodes.main.longitude)
                        )
                            .title(place.name)
                    )
                }
            }
        })

        binding.unLogBtn.setOnClickListener {
            userLoginManager.unLogin()
            this.findNavController().navigate(R.id.action_finalFragment2_to_registrationFragment)
        }

        binding.findButton.setOnClickListener {
            try {
                var placeName = binding.placeNameText.text.toString()
                var ll = "${geoLocation.latitude},${geoLocation.longitude}"
                model.makeFindRequest(placeName, ll, 5000, 10, accept, token)
            } catch (e: Exception) {
                DialogueWindow.showText(getString(R.string.thereAroNoPlace), requireContext())
            }
        }

        var data = getJson()
        mapFragment?.getMapAsync {
            for (place in data.places) {
                val coords = LatLng(place.geoCodes.main.latitude, place.geoCodes.main.longitude)
                it.addMarker(MarkerOptions().position(coords).title(place.name))
            }
        }
    }

    private fun getJson(): JsonData {
        moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<JsonData> = moshi.adapter(
            JsonData::class.java
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

    private val setup = OnMapReadyCallback { googleMap ->
        val currentLoc = LatLng(geoLocation.latitude, geoLocation.longitude)
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 12f))
        googleMap.setOnMarkerClickListener(GoogleMap.OnMarkerClickListener {
            it.showInfoWindow()
            true
        })
    }

    private fun createPoints(jsonData: JsonData, map: SupportMapFragment) {
        map.getMapAsync {
            for (place in jsonData.places) {
                it.addMarker(
                    MarkerOptions().position(
                        LatLng(place.geoCodes.main.latitude, place.geoCodes.main.latitude)
                    )
                        .title(place.name)
                )
            }
        }
    }

}


