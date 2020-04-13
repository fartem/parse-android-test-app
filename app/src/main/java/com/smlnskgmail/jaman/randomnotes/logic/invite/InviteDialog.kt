package com.smlnskgmail.jaman.randomnotes.logic.invite

import android.content.Context
import android.os.Bundle
import com.smlnskgmail.jaman.randomnotes.App
import com.smlnskgmail.jaman.randomnotes.R
import com.smlnskgmail.jaman.randomnotes.components.BaseDialog
import com.smlnskgmail.jaman.randomnotes.logic.repository.api.cloud.CloudInvite
import kotlinx.android.synthetic.main.dialog_invite.*
import javax.inject.Inject

class InviteDialog(context: Context) : BaseDialog(context) {

    @Inject
    lateinit var cloudInvite: CloudInvite

    private var inviteUserTarget: InviteUserTarget? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        (context.applicationContext as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        dialog_invite_cancel.setOnClickListener {
            cancel()
        }
        dialog_invite_request.setOnClickListener {
            cancel()
            cloudInvite.invite(
                dialog_invite_email.text.toString()
            ) {
                inviteUserTarget?.onInviteAction(
                    it == null
                )
            }
        }
        dialog_invite_email.requestFocus()
    }

    fun setInviteCallback(inviteUserTarget: InviteUserTarget) {
        this.inviteUserTarget = inviteUserTarget
    }

    override fun layoutResId() = R.layout.dialog_invite

}
