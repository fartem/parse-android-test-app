package com.smlnskgmail.jaman.randomnotes.parse.api

import android.content.Context
import com.parse.Parse
import com.parse.ParseObject
import com.parse.ParseQuery
import com.smlnskgmail.jaman.randomnotes.entities.note.Note
import com.smlnskgmail.jaman.randomnotes.entities.note.support.NoteFactory

object ParseApi {

    fun initialize(context: Context, serverAddress: String, applicationId: String, clientKey: String) {
        val parseConfig = Parse.Configuration.Builder(context)
            .server(serverAddress)
            .applicationId(applicationId)
            .clientKey(clientKey)
            .build()
        Parse.initialize(parseConfig)
    }

    fun saveAllNotes(notes: List<Note>, errorOnSave: (e: Exception) -> Unit) {
        val objectsToSave = mutableListOf<ParseObject>()
        for (note in notes) {
            objectsToSave.add(note.getParseObject())
        }
        ParseObject.saveAllInBackground(objectsToSave) {
            if (it == null) {
                for (savedNote in objectsToSave) {
                    val note = notes.firstOrNull { noteInList ->
                        noteInList.id == savedNote.get(Note.COLUMN_ID)
                    }
                    if (note != null) {
                        note.parseObjectId = savedNote.objectId
                        NoteFactory.save(note)
                    }
                }
            } else {
                errorOnSave(it)
            }
        }
    }

    fun restoreAllNotes(notes: List<Note>, afterRestore: (e: Exception?) -> Unit) {
        val parseQuery: ParseQuery<ParseObject> = ParseQuery.getQuery(Note.TABLE_NOTE)
        parseQuery.findInBackground { objects, e ->
            if (objects.isNotEmpty()) {
                for (parseData in objects) {
                    val id = parseData.getLong(Note.COLUMN_ID)
                    var note = notes.lastOrNull {
                        it.id == id
                    }
                    if (note == null) {
                        note = Note()
                        note.restoreFromParseObject(parseData)
                        if (note.isNew()) {
                            NoteFactory.save(note)
                        }
                    }
                }
                afterRestore(null)
            } else if (e != null) {
                afterRestore(e)
            }
        }
    }

}