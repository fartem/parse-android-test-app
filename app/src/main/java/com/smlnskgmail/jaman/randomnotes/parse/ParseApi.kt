package com.smlnskgmail.jaman.randomnotes.parse

import com.parse.ParseObject
import com.parse.ParseQuery
import com.smlnskgmail.jaman.randomnotes.entities.Note

object ParseApi {

    fun syncNotesWithServer(notes: List<Note>) {
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
            }
        }
    }

    fun restoreNotesFromServer(notes: List<Note>, complete: (done: Boolean) -> Unit) {
        val parseQuery: ParseQuery<ParseObject> = ParseQuery.getQuery(Note.TABLE_NOTE)
        val parseToSave = mutableListOf<ParseObject>()
        parseQuery.findInBackground { objects, e ->
            if (objects.isNotEmpty()) {
                Note.deleteAllNotes()
                for (parseData in objects) {
                    val id = parseData.getLong(Note.COLUMN_ID)
                    var note = notes.lastOrNull {
                        it.id == id
                    }
                    if (note == null) {
                        note = Note()
                    }
                    if (note.parseObjectId == null) {
                        note.parseObjectId = parseData.objectId
                    }
                    val parseNote = note.restoreFromParseObject(parseData)
                    note.save()
                    parseNote.put(Note.COLUMN_ID, note.id)
                    parseToSave.add(parseNote)
                }
                ParseObject.saveAllInBackground(parseToSave) {
                    complete(it == null)
                }
            }
        }
    }

}