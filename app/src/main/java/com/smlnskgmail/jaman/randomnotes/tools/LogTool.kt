package com.smlnskgmail.jaman.randomnotes.tools

import android.util.Log

object LogTool {

    fun e(exception: Exception) {
        Log.e(
            "Random Notes",
            "---",
            exception
        )
    }

}
