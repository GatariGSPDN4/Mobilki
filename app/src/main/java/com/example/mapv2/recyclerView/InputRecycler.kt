package com.example.mapv2.recyclerView

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.mapv2.data.dataClasses.InputTypeItem
import com.example.mapv2.databinding.RecyclerItemBinding
import com.google.android.material.textfield.TextInputLayout

class InputRecycler(private val inputTypes: List<InputTypeItem>) :
    RecyclerView.Adapter<InputRecycler.MyViewHolder>() {

    private val inputedTexts = Array(inputTypes.size) { _ -> "" }

    inner class MyViewHolder(private val binding: RecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var editText: EditText? = null
        var layout: TextInputLayout? = null

        init {
            editText = binding.editText
            layout = binding.root
            editText?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    inputedTexts[adapterPosition] = p0.toString()
                }

                override fun afterTextChanged(p0: Editable?) {

                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(RecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.editText?.hint = inputTypes[position].hint
        holder.editText?.inputType = inputTypes[position].type
        if (inputTypes[position].eye) holder.layout?.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
    }

    override fun getItemCount() = inputTypes.size

    fun getInputedText(): Array<String> {
        return inputedTexts
    }
}