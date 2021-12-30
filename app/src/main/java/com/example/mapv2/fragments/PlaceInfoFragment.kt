package com.example.mapv2.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import com.example.mapv2.R
import com.example.mapv2.data.network.Photo
import com.example.mapv2.data.network.RequestViewModel
import com.example.mapv2.data.network.Tip
import com.example.mapv2.databinding.PlaceInfoFragmentBinding
import com.squareup.picasso.Picasso
import java.lang.Exception

class PlaceInfoFragment : Fragment() {
    lateinit var binding: PlaceInfoFragmentBinding
    val viewModel: RequestViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PlaceInfoFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val token = getString(R.string.APIKey)
        val accept = getString(R.string.acceptJson)
        val textViewList: MutableList<TextView> = mutableListOf()
        textViewList.add(binding.tip1)
        textViewList.add(binding.tip2)
        textViewList.add(binding.tip3)

        viewModel.currentMarkerID.observe(viewLifecycleOwner, {
            try {
                viewModel.getPhotos(it,accept,token)
                viewModel.getTips(it,accept,token)
            } catch (e: Exception){}
        })
        viewModel.photosLiveData.observe(viewLifecycleOwner, {
            var url = it.prefix + "400x400" + it.suffix
            Picasso.get().load(url).into(binding.imageView)
        })
        viewModel.tipsLiveData.observe(viewLifecycleOwner, {
            var i: Int = 0
            try {
                textViewList.forEach { text ->
                    text.setText(it[i].text)
                    i++
                }
            } catch (e: Exception) {}
        })
    }

    override fun onResume() {
        super.onResume()
        Picasso.get().load(android.R.color.transparent).into(binding.imageView)
    }
}