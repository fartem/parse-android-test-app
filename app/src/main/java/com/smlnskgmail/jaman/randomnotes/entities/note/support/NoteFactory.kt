package com.smlnskgmail.jaman.randomnotes.entities.note.support

import android.util.Log
import com.smlnskgmail.jaman.randomnotes.db.HelperFactory
import com.smlnskgmail.jaman.randomnotes.entities.note.Note
import java.sql.SQLException

object NoteFactory {

    private const val LOG_TAG = "RNNF"

    fun all(): List<Note> {
        try {
            return HelperFactory.allOf(Note::class.java)
        } catch (e: SQLException) {
            Log.e(LOG_TAG, "Failed getHelper all notes\n", e)
        }
        return emptyList()
    }

    fun save(note: Note) {
        try {
            HelperFactory.save(Note::class.java, note)
        } catch (e: SQLException) {
            Log.e(LOG_TAG, "Failed save note:\n$note", e)
        }
    }

    fun delete(note: Note) {
        try {
            HelperFactory.delete(Note::class.java, note)
        } catch (e: SQLException) {
            Log.e(LOG_TAG, "Failed delete note:\n$note", e)
        }

    }

}