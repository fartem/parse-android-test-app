package com.smlnskgmail.jaman.randomnotes.logic.invite

import android.content.Context
import android.os.Bundle
import com.parse.FunctionCallback
import com.parse.ParseCloud
import com.smlnskgmail.jaman.randomnotes.R
import com.smlnskgmail.jaman.randomnotes.components.dialogs.BaseDialog
import kotlinx.android.synthetic.main.dialog_invite.*

class InviteDialog(context: Context) : BaseDialog(context) {

    private var inviteUserTarget: InviteUserTarget? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                inviteUserTarget?.onInviteAction(success && e == null)
            }
        )
    }

    fun setInviteCallback(inviteUserTarget: InviteUserTarget) {
        this.inviteUserTarget = inviteUserTarget
    }

    override fun getLayoutResId() = R.layout.dialog_invite

}
