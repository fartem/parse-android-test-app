package com.smlnskgmail.jaman.randomnotes.parse.auth.utils

import com.parse.ParseUser

class EmailAuthUtils {

    fun register(username: String, email: String, password: String,
                 afterLogin: (e: Exception?) -> Unit) {
        val parseUser = ParseUser()
        parseUser.username = username
        parseUser.email = email
        parseUser.setPassword(password)
        parseUser.signUpInBackground {
            afterLogin(it)
        }
    }

    fun logIn(username: String, password: String, afterRegister: (e: Exception?) -> Unit) {
        ParseUser.logInInBackground(username, password) { _, e ->
            afterRegister(e)
        }
    }

}