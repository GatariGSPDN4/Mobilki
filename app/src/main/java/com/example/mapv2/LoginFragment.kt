package com.example.mapv2

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Dao
import com.example.mapv2.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    lateinit var binding:FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userLoginManager: UserLoginManager = UserLoginManager(requireContext())
        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = CustomRecyclerAdapter(fillList())
        val recyclerAdapter = recyclerView.adapter as CustomRecyclerAdapter

        binding.logBtn.setOnClickListener {
            val inputedText = recyclerAdapter.getInputedText()

            if (DataValidator.validateLog(inputedText[0], inputedText[1],requireContext(),(activity as MainActivity).userDao)) {
                userLoginManager.register((activity as MainActivity).userDao.findByMail(inputedText[0]))
                userLoginManager.login()
                this.findNavController().navigate(R.id.action_loginFragment2_to_finalFragment2)
            }
        }

        binding.regBtn.setOnClickListener {
            this.findNavController().navigate(R.id.action_loginFragment2_to_registrationFragment)
        }
    }

    private fun fillList(): List<InputTypeItem> {
        val data = mutableListOf<InputTypeItem>()
        data.add(InputTypeItem(getString(R.string.EmailString),
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PERSON_NAME,false))
        data.add(InputTypeItem(getString(R.string.PasswordString),
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD,false))
        return data
    }

}