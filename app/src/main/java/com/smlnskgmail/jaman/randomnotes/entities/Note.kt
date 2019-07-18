package com.smlnskgmail.jaman.randomnotes.entities

import com.j256.ormlite.field.DatabaseField
import com.parse.ParseACL
import com.parse.ParseObject
import com.parse.ParseUser
import com.smlnskgmail.jaman.randomnotes.db.support.DatabaseFactory

class Note(

    @DatabaseField
    var title: String? = null,

    @DatabaseField
    var subtitle: String? = null,

    @DatabaseField
    var parseObjectId: String? = null,

    var positionInList: Int = 0

) : Entity() {

    fun save() {
        DatabaseFactory.get().saveNote(this)
    }

    fun delete() {
        DatabaseFactory.get().deleteNote(this)
    }

    fun getParseObject(globalAccess: Boolean = true, user: ParseUser? = null): ParseObject {
        val parseObject = ParseObject(TABLE_NOTE)
        parseObject.objectId = parseObjectId
        parseObject.put(COLUMN_ID, id)
        parseObject.put(COLUMN_TITLE, title!!)
        parseObject.put(COLUMN_SUBTITLE, subtitle!!)
        if (!globalAccess) {
            if (user == null) {
                throw RuntimeException("User must not be null!")
            }
            parseObject.acl = ParseACL()
            parseObject.acl!!.publicReadAccess = false
            parseObject.acl!!.publicWriteAccess = false
            parseObject.acl!!.setReadAccess(user, true)
            parseObject.acl!!.setWriteAccess(user, true)
        }
        return parseObject
    }

    fun restoreFromParseObject(parseObject: ParseObject): ParseObject {
        if (parseObjectId == null) {
            parseObjectId = parseObject.objectId
        }
        title = parseObject.getString(COLUMN_TITLE)
        subtitle = parseObject.getString(COLUMN_SUBTITLE)
        return parseObject
    }

    companion object {

        const val TABLE_NOTE = "note"

        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_SUBTITLE = "subtitle"

        fun getAllNotes() = DatabaseFactory.get().allNotes

        fun deleteAllNotes() {
            DatabaseFactory.get().deleteAllNotes()
        }

    }

}