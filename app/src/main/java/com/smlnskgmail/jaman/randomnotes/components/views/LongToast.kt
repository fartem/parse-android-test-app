package com.smlnskgmail.jaman.randomnotes.components.views

import android.content.Context
import android.widget.Toast

class LongToast(
    private val context: Context,
    private val text: String
) {

    fun show() {
        Toast.makeText(
            context,
            text,
            Toast.LENGTH_LONG
        ).show()
    }

}
