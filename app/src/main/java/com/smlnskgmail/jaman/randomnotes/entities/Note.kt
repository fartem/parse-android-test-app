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

    fun contentEquals(note: Note): Boolean {
        return note.title == title && note.subtitle == subtitle
    }

    override fun toString(): String {
        return "id: $id\n" +
                "subtitle\n" +
                "parseObjectId: $parseObjectId\n" +
                "Title: $title\n" +
                "Subtitle: $subtitle"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (javaClass != other?.javaClass) {
            return false
        }

        other as Note

        if (title != other.title) {
            return false
        }
        if (subtitle != other.subtitle) {
            return false
        }
        if (positionInList != other.positionInList) {
            return false
        }

        return true
    }

    override fun hashCode(): Int {
        var result = title?.hashCode() ?: 0
        result = 31 * result + (subtitle?.hashCode() ?: 0)
        result = 31 * result + positionInList
        return result
    }

    companion object {

        const val TABLE_NOTE = "note"

        const val COLUMN_ID = "note_id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_SUBTITLE = "subtitle"

        fun getAllNotes() = DatabaseFactory.get().allNotes

        fun deleteAllNotes() {
            DatabaseFactory.get().deleteAllNotes()
        }

    }

}