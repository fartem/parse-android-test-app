package com.smlnskgmail.jaman.randomnotes.utils

import android.content.Context
import android.widget.Toast

object UIUtils {

    fun toast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }

}