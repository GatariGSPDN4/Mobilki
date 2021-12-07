package com.example.mapv2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.setContentView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mapv2.R
import com.example.mapv2.data.managers.*
import com.example.mapv2.recyclerView.MarkerRecycler
import com.example.mapv2.data.dataClasses.*
import com.example.mapv2.databinding.FragmentFinalBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson


class FinalFragment : Fragment() {
    lateinit var binding:FragmentFinalBinding

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

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

    }

    private fun getJson() : Array<Marker> {
        val jsonString: String =
        requireContext().assets
            .open("data.json")
            .bufferedReader().use {
                it.readText()
            }
        return Gson().fromJson(jsonString, Array<Marker>::class.java)
    }
}