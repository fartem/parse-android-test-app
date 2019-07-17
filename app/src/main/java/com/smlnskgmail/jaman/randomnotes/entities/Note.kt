package com.smlnskgmail.jaman.randomnotes.entities

import com.j256.ormlite.field.DatabaseField
import com.parse.ParseObject
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

    fun getParseObject(): ParseObject {
        val parseObject = ParseObject(TABLE_NOTE)
        parseObject.objectId = parseObjectId
        parseObject.put(COLUMN_ID, id)
        parseObject.put(COLUMN_TITLE, title!!)
        parseObject.put(COLUMN_SUBTITLE, subtitle!!)
        return parseObject
    }

    fun restoreFromParseObject(parseObject: ParseObject): ParseObject {
        if (parseObjectId == null) {
            parseObjectId = parseObject.objectId
        }
        title = parseObject.getString(COLUMN_TITLE)
        subtitle = parseObject.getString(COLUMN_SUBTITLE)
        save()
        parseObject.put(COLUMN_ID, id)
        return parseObject
    }

    companion object {

        private const val TABLE_NOTE = "note"

        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_SUBTITLE = "subtitle"

        fun getAllNotes() = DatabaseFactory.get().allNotes

    }

}