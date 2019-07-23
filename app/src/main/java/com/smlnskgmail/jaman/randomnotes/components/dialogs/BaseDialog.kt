package com.smlnskgmail.jaman.randomnotes.components.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle

abstract class BaseDialog(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        initializeDialog()
    }

    abstract fun initializeDialog()

    abstract fun getLayoutResId(): Int

}