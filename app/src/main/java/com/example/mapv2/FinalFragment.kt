package com.example.mapv2

import android.content.res.AssetManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mapv2.databinding.FragmentFinalBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FinalFragment : Fragment() {
    lateinit var binding:FragmentFinalBinding

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
        super.onViewCreated(view, savedInstanceState)
        val dataManager:DataManager = DataManager(requireContext())
        val userData:User = dataManager.readData()

        binding.nameText.setText("${binding.nameText.text} ${userData.name}")
        binding.mailText.setText("${binding.mailText.text} ${userData.mail}")
        binding.passwordText.setText("${binding.passwordText.text} ${userData.password}")

        val userLoginManager:UserLoginManager = UserLoginManager(requireContext())

        val markerRecycler: RecyclerView = binding.MarkersRecycler
        markerRecycler.layoutManager = LinearLayoutManager(requireContext())
        markerRecycler.adapter = MarkerRecycler(getJson())
        val recyclerAdapter = markerRecycler.adapter as MarkerRecycler

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