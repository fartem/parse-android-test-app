package com.smlnskgmail.jaman.randomnotes.components.dialogs

import android.content.Context
import com.parse.FunctionCallback
import com.parse.ParseCloud
import com.smlnskgmail.jaman.randomnotes.R
import kotlinx.android.synthetic.main.dialog_share.*

class ShareDialog(context: Context) : BaseDialog(context) {

    override fun initializeDialog() {
        dialog_share_cancel.setOnClickListener {
            cancel()
        }
        dialog_share_action.setOnClickListener {
            cancel()
            share()
        }
    }

    private fun share() {
        val shareData = hashMapOf<String, String>()
        shareData["email"] = dialog_share_email.text.toString()
        ParseCloud.callFunctionInBackground("invite", shareData,
            FunctionCallback<Boolean> { success, e ->
                if (success) {

                } else {

                }
            }
        )
    }

    override fun getLayoutResId() = R.layout.dialog_share

}