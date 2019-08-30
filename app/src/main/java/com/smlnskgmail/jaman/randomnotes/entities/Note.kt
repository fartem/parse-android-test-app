package com.smlnskgmail.jaman.randomnotes.entities

import com.j256.ormlite.field.DatabaseField
import com.parse.ParseObject
import com.smlnskgmail.jaman.randomnotes.db.factory.DatabaseFactory

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
        DatabaseFactory.getHelper().saveNote(this)
    }

    fun delete() {
        DatabaseFactory.getHelper().deleteNote(this)
    }

    fun getParseObject(): ParseObject {
        val parseObject = ParseObject(TABLE_NOTE)
        parseObject.objectId = parseObjectId
        parseObject.put(COLUMN_ID, id)
        parseObject.put(COLUMN_TITLE, title!!)
        parseObject.put(COLUMN_SUBTITLE, subtitle!!)
        return parseObject
    }

    fun restoreFromParseObject(parseObject: ParseObject) {
        if (parseObjectId == null) {
            parseObjectId = parseObject.objectId
        }
        title = parseObject.getString(COLUMN_TITLE)
        subtitle = parseObject.getString(COLUMN_SUBTITLE)
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

        fun getAllNotes(): MutableList<Note> = DatabaseFactory.getHelper().allNotes

    }

}