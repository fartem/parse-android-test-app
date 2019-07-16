package com.smlnskgmail.jaman.randomnotes.entities

import com.j256.ormlite.field.DatabaseField
import com.smlnskgmail.jaman.randomnotes.db.DatabaseFactory

class Note(

    @DatabaseField
    var title: String? = null,

    @DatabaseField
    var subtitle: String? = null,

    var positionInList: Int = 0

) : Entity() {

    fun save() {
        DatabaseFactory.get().saveNote(this)
    }

    fun delete() {
        DatabaseFactory.get().deleteNote(this)
    }

    companion object {

        fun getAllNotes() = DatabaseFactory.get().allNotes

    }

}