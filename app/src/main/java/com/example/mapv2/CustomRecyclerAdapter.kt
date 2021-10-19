package com.example.mapv2

import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomRecyclerAdapter(private val inputTypes: List<InputTypeItem>) :
    RecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder>() {

    private val inputedTexts = Array(inputTypes.size) { _ -> "" }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var editText: EditText? = null

        init {
            editText = itemView.findViewById(R.id.editText)
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
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.editText?.hint = inputTypes[position].hint
        holder.editText?.inputType = inputTypes[position].type!!
    }

    override fun getItemCount() = inputTypes.size

    fun getInputedText(): Array<String> {
        return inputedTexts
    }
}