package com.smlnskgmail.jaman.randomnotes.components.views

import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.smlnskgmail.jaman.randomnotes.R

class LongSnackbar(
    private val view: View,
    private val text: String
) {

    fun show() {
        val snackbar = Snackbar.make(
            view,
            text,
            Snackbar.LENGTH_LONG
        )
        snackbar.setAction(
            view.context.getString(
                R.string.common_ok
            )
        ) { snackbar.dismiss() }
        snackbar.show()
    }

}
