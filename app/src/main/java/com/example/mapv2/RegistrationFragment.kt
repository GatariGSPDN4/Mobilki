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
import com.example.mapv2.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userLoginManager: UserLoginManager = UserLoginManager(requireContext())
        if (userLoginManager.checkIsLogged()) {
            this.findNavController().navigate(R.id.action_registrationFragment_to_finalFragment2)
        }

        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = CustomRecyclerAdapter(fillList())
        val recyclerAdapter = recyclerView.adapter as CustomRecyclerAdapter
        lateinit var inputedText : Array<String>
        var enableButton = false;

        val users: List<User> = (activity as MainActivity).userDao.getAll()

        binding.checkBox.setOnClickListener {
            binding.regBtn.isEnabled = binding.checkBox.isChecked
        }

        binding.regBtn.setOnClickListener {
            inputedText = recyclerAdapter.getInputedText()
            val userData: User = User(inputedText[0], inputedText[1], inputedText[2])
            if (DataValidator.validateReg(
                    userData,
                    inputedText[3],
                    requireContext(),
                    (activity as MainActivity).userDao
                )
            ) {
                userLoginManager.register(userData)
                (activity as MainActivity).userDao.insert(userData)
                this.findNavController()
                    .navigate(R.id.action_registrationFragment_to_finalFragment2)
            }

        }

        binding.logBtn.setOnClickListener {
            this.findNavController().navigate(R.id.action_registrationFragment_to_loginFragment2)
        }
    }

    private fun fillList(): List<InputTypeItem> {
        val data = mutableListOf<InputTypeItem>()
        data.add(
            InputTypeItem(
                getString(R.string.NameString),
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PERSON_NAME,
                false
            )
        )
        data.add(
            InputTypeItem(
                getString(R.string.EmailString),
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS,
                false
            )
        )
        data.add(
            InputTypeItem(
                getString(R.string.PasswordString),
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD,
                true
            )
        )
        data.add(
            InputTypeItem(
                getString(R.string.PasswordRepString),
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD,
                true
            )
        )
        return data
    }

}