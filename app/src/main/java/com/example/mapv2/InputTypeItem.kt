package com.example.mapv2

data class InputTypeItem (private val _hint : String, private val _type : Int) {
    var hint : String? = null
    var type : Int? = null

    init {
        hint = _hint
        type = _type
    }
}