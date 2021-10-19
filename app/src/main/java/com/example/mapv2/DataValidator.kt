package com.example.mapv2

import android.app.Dialog
import android.content.Context

object DataValidator {
    fun validate(data: UserData, repPassword:String,context:Context) : Boolean {
        if (!validateName(data.name!!,context)) return false
        if (!validateMail(data.mail!!,context)) return false
        if (!validatePassword(data.password!!,repPassword,context)) return false
        return true
    }

    private fun validateName(name : String,context:Context) : Boolean {
        if (name.isEmpty()) {
            DialogueWindow.showText("Введите имя!", context)
            return false;
        }
        return true
    }

    private fun validateMail(mail : String, context: Context) : Boolean {
        if (mail.isEmpty()) {
            DialogueWindow.showText("Введите email!", context)
            return false
        }
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            DialogueWindow.showText("Неправильный email!", context)
            return false
        }
        return true
    }

    private fun validatePassword(password:String, repPassword:String, context: Context ) : Boolean {
        if (password.isEmpty()) {
            DialogueWindow.showText("Введите пароль!", context)
            return false
        }
        if (repPassword.isEmpty()) {
            DialogueWindow.showText("Введите пароль ещё раз!", context)
            return false
        }
        if (!password.equals(repPassword)) {
            DialogueWindow.showText("Пароли не совпадают!", context)
            return false
        }
        return true
    }
}