package com.smlnskgmail.jaman.randomnotes.presenter.invite

import com.smlnskgmail.jaman.randomnotes.model.api.cloud.CloudInvite
import com.smlnskgmail.jaman.randomnotes.view.invite.CloudInviteView

interface CloudInvitePresenter {

    fun init(
        cloudInvite: CloudInvite,
        cloudInviteView: CloudInviteView
    )

    fun invite(email: String)

}
