package com.example.mapv2

import android.content.Context
import android.content.SharedPreferences

class DataManager(context: Context) {
    private val APP_PREFERENCES = "Login"
    private val APP_PREFERENCES_IS_LOGGED = "IsLogged"
    private var sharedPrefer = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
    private var editor : SharedPreferences.Editor = sharedPrefer.edit()

    fun writeData(userData: User) {
        editor.putString(APP_PREFERENCES,"${userData.name} ${userData.mail} ${userData.password} ${userData.id}")
        editor.apply()
    }

    fun readData(): User {
        val tempString:List<String>? =
            sharedPrefer.getString(APP_PREFERENCES,"")?.split(" ")
        val userData: User
        if  (!tempString!!.isEmpty()) {
            userData = User(
                tempString.get(0),
                tempString.get(1),
                tempString.get(2),
                tempString.get(3).toInt()
            )
        } else {
            userData = User("","","")
        }
        return userData
    }

    fun writeState(state : Boolean) {
        editor.putBoolean(APP_PREFERENCES_IS_LOGGED,state)
        editor.apply()
    }

    fun readState() : Boolean {
        return sharedPrefer.getBoolean(APP_PREFERENCES_IS_LOGGED,false)
    }
}