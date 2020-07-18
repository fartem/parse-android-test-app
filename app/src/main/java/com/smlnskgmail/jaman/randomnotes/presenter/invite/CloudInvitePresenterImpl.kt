package com.smlnskgmail.jaman.randomnotes.presenter.invite

import com.smlnskgmail.jaman.randomnotes.model.api.cloud.CloudInvite
import com.smlnskgmail.jaman.randomnotes.view.invite.CloudInviteView

class CloudInvitePresenterImpl : CloudInvitePresenter {

    private lateinit var cloudInvite: CloudInvite
    private lateinit var cloudInviteView: CloudInviteView

    override fun init(
        cloudInvite: CloudInvite,
        cloudInviteView: CloudInviteView
    ) {
        this.cloudInvite = cloudInvite
        this.cloudInviteView = cloudInviteView
    }

    override fun invite(email: String) {
        cloudInvite.invite(email) { success ->
            if (success) {
                cloudInviteView.inviteSuccess()
            } else {
                cloudInviteView.inviteError()
            }
        }
    }

}
