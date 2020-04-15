package com.smlnskgmail.jaman.randomnotes.view.invite

import android.content.Context
import android.os.Bundle
import com.smlnskgmail.jaman.randomnotes.App
import com.smlnskgmail.jaman.randomnotes.R
import com.smlnskgmail.jaman.randomnotes.components.BaseDialog
import com.smlnskgmail.jaman.randomnotes.model.api.cloud.CloudInvite
import com.smlnskgmail.jaman.randomnotes.presenter.invite.CloudInvitePresenter
import com.smlnskgmail.jaman.randomnotes.presenter.invite.CloudInvitePresenterImpl
import kotlinx.android.synthetic.main.dialog_invite.*
import javax.inject.Inject

class CloudInviteDialog(
    context: Context
) : BaseDialog(context), CloudInviteView {

    @Inject
    lateinit var cloudInvite: CloudInvite

    private lateinit var cloudCloudInvitePresenter: CloudInvitePresenter

    private var cloudInviteTarget: CloudInviteTarget? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        (context.applicationContext as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        cloudCloudInvitePresenter = CloudInvitePresenterImpl()
        cloudCloudInvitePresenter.init(
            cloudInvite,
            this
        )

        dialog_invite_cancel.setOnClickListener {
            cancel()
        }
        dialog_invite_request.setOnClickListener {
            cancel()
            cloudCloudInvitePresenter.invite(
                dialog_invite_email.text.toString()
            )
        }
    }

    override fun inviteSuccess() {
        cloudInviteTarget?.onInviteAction(
            true
        )
    }

    override fun inviteError() {
        cloudInviteTarget?.onInviteAction(
            false
        )
    }

    fun setInviteCallback(cloudInviteTarget: CloudInviteTarget) {
        this.cloudInviteTarget = cloudInviteTarget
    }

    override fun layoutResId() = R.layout.dialog_invite

}
