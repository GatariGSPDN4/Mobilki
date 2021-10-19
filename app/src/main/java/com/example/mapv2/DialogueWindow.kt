package com.example.mapv2

import android.content.Context
import android.widget.Toast

object DialogueWindow {
    fun showText(message: String, context: Context) {
        val toast = Toast.makeText(context,message, Toast.LENGTH_SHORT)
        toast.show()
    }
}