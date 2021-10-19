package com.example.mapv2

import android.content.Context
import android.content.SharedPreferences

class DataManager(context: Context) {
    private val APP_PREFERENCES = "Login"
    private val APP_PREFERENCES_IS_LOGGED = "IsLogged"
    private var sharedPrefer = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
    private var editor : SharedPreferences.Editor = sharedPrefer.edit()

    fun writeData(userData: UserData) {
        editor.putString(APP_PREFERENCES,"${userData.name} ${userData.mail} ${userData.password}")
        editor.apply()
    }

    fun readData(): UserData {
        val tempString:List<String>? =
            sharedPrefer.getString(APP_PREFERENCES,"")?.split(" ")
        val userData: UserData
        if  (!tempString!!.isEmpty()) {
            userData = UserData(
                tempString.get(0),
                tempString.get(1),
                tempString.get(2)
            )
        } else {
            userData = UserData("","","")
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