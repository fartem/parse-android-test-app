package com.smlnskgmail.jaman.randomnotes.parse

import com.parse.ParseObject
import com.parse.ParseQuery
import com.smlnskgmail.jaman.randomnotes.entities.Note

object ParseApi {

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
                        note.save()
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
                            note.save()
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