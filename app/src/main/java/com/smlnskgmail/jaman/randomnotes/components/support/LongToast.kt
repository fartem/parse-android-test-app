package com.smlnskgmail.jaman.randomnotes.components.support

import android.content.Context
import android.widget.Toast

object LongToast {

    fun show(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }

}