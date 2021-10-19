package com.example.mapv2

data class UserData(var _name: String, var _mail : String, var _password : String) {
    var name : String? = null
    var mail : String? = null
    var password : String? = null

    init {
        name = _name
        mail = _mail
        password = _password
    }
}