package com.smlnskgmail.jaman.randomnotes.logger

import android.util.Log

object L {

    fun e(exception: Exception) {
        Log.e(
            "Random Notes",
            "---",
            exception
        )
    }

}