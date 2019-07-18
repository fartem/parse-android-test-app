package com.smlnskgmail.jaman.randomnotes.parse

import com.parse.ParseUser

object ParseUtils {

    fun isAuthorized() = ParseUser.getCurrentUser() != null

}