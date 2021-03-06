package com.smlnskgmail.jaman.randomnotes.model.api.entities

import com.j256.ormlite.field.DatabaseField

class Note(

    @DatabaseField
    var title: String? = null,

    @DatabaseField
    var subtitle: String? = null,

    @DatabaseField
    var remoteId: String? = null

) : EntityWithId() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Note

        if (title != other.title) return false
        if (subtitle != other.subtitle) return false
        if (remoteId != other.remoteId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title?.hashCode() ?: 0
        result = 31 * result + (subtitle?.hashCode() ?: 0)
        result = 31 * result + (remoteId?.hashCode() ?: 0)
        return result
    }

}
