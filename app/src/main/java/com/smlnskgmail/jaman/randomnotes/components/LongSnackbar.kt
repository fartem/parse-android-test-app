package com.smlnskgmail.jaman.randomnotes.components

import android.view.View
import com.google.android.material.snackbar.Snackbar

class LongSnackbar(
    private val view: View,
    private val text: String
) {

    fun show() {
        Snackbar.make(
            view,
            text,
            Snackbar.LENGTH_LONG
        ).show()
    }

}
