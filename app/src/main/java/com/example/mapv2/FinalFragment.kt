package com.example.mapv2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mapv2.databinding.FragmentFinalBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FinalFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding:FragmentFinalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
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

        binding.unLogBtn.setOnClickListener {
            userLoginManager.unLogin()
            this.findNavController().navigate(R.id.action_finalFragment2_to_registrationFragment)
        }
        
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FinalFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}