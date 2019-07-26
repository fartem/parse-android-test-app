package com.smlnskgmail.jaman.randomnotes.components.dialogs.invite

import android.content.Context
import com.parse.FunctionCallback
import com.parse.ParseCloud
import com.smlnskgmail.jaman.randomnotes.R
import com.smlnskgmail.jaman.randomnotes.components.dialogs.BaseDialog
import kotlinx.android.synthetic.main.dialog_invite.*

class InviteDialog(context: Context) : BaseDialog(context) {

    private var inviteCallback: InviteCallback? = null

    override fun initializeDialog() {
        dialog_invite_cancel.setOnClickListener {
            cancel()
        }
        dialog_invite_request.setOnClickListener {
            cancel()
            share()
        }
        dialog_invite_email.requestFocus()
    }

    private fun share() {
        val inviteData = hashMapOf<String, String>()
        inviteData["email"] = dialog_invite_email.text.toString()
        ParseCloud.callFunctionInBackground("invite", inviteData,
            FunctionCallback<Boolean> { success, e ->
                inviteCallback?.onInviteAction(success)
            }
        )
    }

    fun setInviteCallback(inviteCallback: InviteCallback) {
        this.inviteCallback = inviteCallback
    }

    override fun getLayoutResId() = R.layout.dialog_invite

}