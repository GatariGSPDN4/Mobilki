package com.example.mapv2

import android.content.Context

class UserLoginManager(context: Context) {

    private val dataManager = DataManager(context)

    fun register(data: User) {
        dataManager.writeData(data)
        login()
    }

    fun login() {
        dataManager.writeState(true)
    }

    fun unLogin() {
        dataManager.writeState(false)
    }

    fun checkData(name : String, password : String) : Boolean {
        val userData = dataManager.readData()
        if (userData.name.equals(name) && userData.password.equals(password)) return true
        return false
    }

    fun checkIsLogged() : Boolean {
        return dataManager.readState()
    }
}