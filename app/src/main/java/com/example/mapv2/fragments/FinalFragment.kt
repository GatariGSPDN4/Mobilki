package com.example.mapv2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
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


class FinalFragment : Fragment() {
    lateinit var binding: FragmentFinalBinding
    lateinit var moshi: Moshi
    lateinit var geoLocation: Coords
    val viewModel: RequestViewModel by activityViewModels()

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

        var json: JsonData? = null

        val dataManager = DataManager(requireContext())
        val userData: User = dataManager.readData()

        val userLoginManager = UserLoginManager(requireContext())

        viewModel.placesLiveData.observe(viewLifecycleOwner, {
            json = it
            mapFragment?.getMapAsync {
                for (place in json!!.places) {
                    it.addMarker(
                        MarkerOptions().position(
                            LatLng(place.geoCodes.main.latitude, place.geoCodes.main.longitude)
                        )
                            .title(place.fsq_id)
                    )
                }
            }
        })

        binding.findButton.setOnClickListener {
            try {
                mapFragment?.getMapAsync { it.clear() }
                var placeName = binding.placeNameText.text.toString()
                var ll = "${geoLocation.latitude},${geoLocation.longitude}"
                viewModel.makeFindRequest(placeName, ll, 5000, 10, accept, token)
            } catch (e: Exception) {
                DialogueWindow.showText(getString(R.string.thereAroNoPlace), requireContext())
            }
        }

        binding.findNearbyBtn.setOnClickListener {
            try {
                mapFragment?.getMapAsync { it.clear() }
                var ll = "${geoLocation.latitude},${geoLocation.longitude}"
                viewModel.makeNearbyRequest(ll,accept,token)
            } catch (e: Exception) {
                DialogueWindow.showText("Видимо вы находитесь в глуши!", requireContext())
            }
        }

        /*var data = getJson()
        mapFragment?.getMapAsync {
            for (place in data.places) {
                val coords = LatLng(place.geoCodes.main.latitude, place.geoCodes.main.longitude)
                it.addMarker(MarkerOptions().position(coords).title(place.fsq_id))
                viewModel.placesMap.put(place.fsq_id,place)
            }
        }*/
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
        googleMap.setOnMapClickListener {
            googleMap.clear()
        }
        googleMap.setOnMarkerClickListener {
            viewModel.currentMarkerID.postValue(it.title)
            findNavController().navigate(R.id.action_finalFragment2_to_placeInfoFragment)
            true
        }
    }

}


