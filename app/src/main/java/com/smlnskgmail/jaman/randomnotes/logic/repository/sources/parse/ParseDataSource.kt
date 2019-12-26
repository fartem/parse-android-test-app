package com.smlnskgmail.jaman.randomnotes.logic.repository.sources.parse

import android.content.Context
import com.parse.Parse
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.facebook.ParseFacebookUtils
import com.smlnskgmail.jaman.randomnotes.logic.repository.CloudDataSource
import com.smlnskgmail.jaman.randomnotes.logic.repository.entities.Note

class ParseDataSource(
    context: Context
) : CloudDataSource {

    private val tableNote = "note"

    private val columnId = "note_id"
    private val columnTitle = "title"
    private val columnSubtitle = "subtitle"

    init {
        val parseConfig = Parse.Configuration.Builder(context)
            .server("SERVER_ADDRESS")
            .applicationId("APPLICATION_ID")
            .clientKey("CLIENT_KEY")
            .build()
        Parse.initialize(parseConfig)
        ParseFacebookUtils.initialize(context)
    }

    override fun saveAllNotes(notes: List<Note>, errorOnSave: (e: Exception) -> Unit) {
        val objectsToSave = mutableListOf<ParseObject>()
        for (note in notes) {
            objectsToSave.add(parseObjectForNote(note))
        }
        ParseObject.saveAllInBackground(objectsToSave) {
            if (it == null) {
                for (savedNote in objectsToSave) {
                    val note = notes.firstOrNull { noteInList ->
                        noteInList.id == savedNote.get(columnId)
                    }
                    if (note != null) {
                        note.parseObjectId = savedNote.objectId
                    }
                }
            } else {
                errorOnSave(it)
            }
        }
    }

    private fun parseObjectForNote(note: Note): ParseObject {
        val parseObject = ParseObject(tableNote)
        parseObject.objectId = note.parseObjectId
        parseObject.put(columnId, note.id)
        parseObject.put(columnTitle, note.title!!)
        parseObject.put(columnSubtitle, note.subtitle!!)
        return parseObject
    }

    override fun restoreAllNotes(
        notes: List<Note>,
        afterRestore: (newNotes: List<Note>, e: Exception?) -> Unit
    ) {
        val parseQuery: ParseQuery<ParseObject> = ParseQuery.getQuery(tableNote)
        parseQuery.findInBackground { objects, e ->
            if (objects.isNotEmpty()) {
                val newNotes = mutableListOf<Note>()
                for (parseData in objects) {
                    val id = parseData.getLong(columnId)
                    var note = notes.lastOrNull {
                        it.id == id
                    }
                    if (note == null) {
                        note = noteFromParseObject(parseData)
                        newNotes.add(note)
                    }
                }
                afterRestore(newNotes, null)
            } else if (e != null) {
                afterRestore(emptyList(), e)
            }
        }
    }

    private fun noteFromParseObject(parseObject: ParseObject): Note {
        val note = Note()
        note.parseObjectId = parseObject.objectId
        note.title = parseObject.getString(columnTitle)
        note.subtitle = parseObject.getString(columnSubtitle)
        return note
    }

}
