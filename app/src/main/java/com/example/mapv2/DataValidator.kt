package com.example.mapv2

import android.content.Context

object DataValidator {
    lateinit var userLoginManager : UserLoginManager

    fun validateReg(data: User, repPassword:String, context:Context,dao: UserDao) : Boolean {
        if (!validateName(data.name!!,context)) return false
        if (!validateMail(data.mail!!,context)) return false
        if (!validatePassword(data.password!!,repPassword,context)) return false
        if (isUserExist(data.mail!!, context,dao)) {
            DialogueWindow.showText(context.getString(R.string.ThisAccountAlreadyExistString),context)
            return false
        }
        return true
    }

    fun validateLog(mail: String,password: String, context: Context, dao: UserDao) : Boolean {
        if (!validateMail(mail,context)) return false
        if (!validatePassword(password,context)) return false
        if (!isUserExist(mail,context,dao)) {
            DialogueWindow.showText(context.getString(R.string.ThisAccountNotExistString),context)
            return false
        }
        if (!dao.findByMail(mail).password.equals(password)) {
            DialogueWindow.showText(context.getString(R.string.PasswordNotMatchString), context)
            return false
        }
        return true
    }

    private fun isUserExist(mail: String , context: Context, dao: UserDao) : Boolean {
        if (dao.isExistByMail(mail)) {
            return true
        }
        return false
    }

    private fun validateName(name : String,context:Context) : Boolean {
        if (name.isEmpty()) {
            DialogueWindow.showText(context.getString(R.string.EnterNameString), context)
            return false
        }
        if (name.contains(" ")) {
            DialogueWindow.showText(context.getString(R.string.NameSpacesString),context)
            return false
        }
        return true
    }

    private fun validateMail(mail : String, context: Context) : Boolean {
        if (mail.isEmpty()) {
            DialogueWindow.showText(context.getString(R.string.EnterEmailString), context)
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            DialogueWindow.showText(context.getString(R.string.WrongEmailString), context)
            return false
        }
        return true
    }

    private fun validatePassword(password: String, context: Context) : Boolean {
        if (password.isEmpty()) {
            DialogueWindow.showText(context.getString(R.string.EnterPasswordString), context)
            return false
        }
        if (password.contains(" ")) {
            DialogueWindow.showText(context.getString(R.string.PasswordSpacesString),context)
            return false
        }
        return true
    }

    private fun validatePassword(password:String, repPassword:String, context: Context ) : Boolean {
        if (!validatePassword(password,context)) return false
        if (repPassword.isEmpty()) {
            DialogueWindow.showText(context.getString(R.string.EnterPasswordAgainString), context)
            return false
        }
        if (!password.equals(repPassword)) {
            DialogueWindow.showText(context.getString(R.string.PasswordsNotMatchString), context)
            return false
        }
        return true
    }
}