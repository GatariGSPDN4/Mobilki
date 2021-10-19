package com.example.mapv2

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mapv2.databinding.FragmentRegistrationBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class RegistrationFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding : FragmentRegistrationBinding

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
        binding = FragmentRegistrationBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var userLoginManager: UserLoginManager = UserLoginManager(requireContext())
        if (userLoginManager.checkIsLogged()) {
            this.findNavController().navigate(R.id.action_registrationFragment_to_finalFragment2)
        }
        var recyclerView: RecyclerView? = binding.recyclerView
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        recyclerView?.adapter = CustomRecyclerAdapter(fillList())
        var recyclerAdapter = recyclerView?.adapter as CustomRecyclerAdapter

        binding.regBtn.setOnClickListener {
            if (binding.checkBox.isChecked) {
                val inputedText = recyclerAdapter.getInputedText()
                val userData : UserData = UserData(inputedText[0],inputedText[1],inputedText[2])
                if (DataValidator.validate(userData,inputedText[3],requireContext())) {
                    userLoginManager.register(userData)
                    this.findNavController().navigate(R.id.action_registrationFragment_to_finalFragment2)
                }
            }
        }

        binding.logBtn.setOnClickListener {
            this.findNavController().navigate(R.id.action_registrationFragment_to_loginFragment2)
        }
    }

    private fun fillList(): List<InputTypeItem> {
        val data = mutableListOf<InputTypeItem>()
        data.add(InputTypeItem("Name",
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PERSON_NAME))
        data.add(InputTypeItem("Email",
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS))
        data.add(InputTypeItem("Password",
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD))
        data.add(InputTypeItem("Password again",
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD))
        return data
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegistrationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}