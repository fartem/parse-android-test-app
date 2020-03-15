package com.smlnskgmail.jaman.randomnotes.logic.repository.impl.cloud.parse

import android.content.Context
import com.parse.Parse
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.facebook.ParseFacebookUtils
import com.smlnskgmail.jaman.randomnotes.logic.repository.api.cloud.CloudDataSource
import com.smlnskgmail.jaman.randomnotes.logic.repository.api.entities.Note
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class ParseDataSource(
    context: Context
) : CloudDataSource {

    private val tableNote = "note"

    private val columnNoteId = "note_id"
    private val columnNoteTitle = "title"
    private val columnNoteSubtitle = "subtitle"

    init {
        @Suppress("MagicNumber")
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .callTimeout(5, TimeUnit.SECONDS)
        val parseConfig = Parse.Configuration.Builder(context)
            .clientBuilder(httpClient)
            .server("SERVER_ADDRESS")
            .applicationId("APPLICATION_ID")
            .clientKey("CLIENT_KEY")
            .build()
        Parse.initialize(parseConfig)
        ParseFacebookUtils.initialize(context)
    }

    override fun saveAllNotes(
        notes: List<Note>,
        errorOnSave: (e: Exception?) -> Unit
    ) {
        val objectsToSave = mutableListOf<ParseObject>()
        for (note in notes) {
            objectsToSave.add(parseObjectForNote(note))
        }
        ParseObject.saveAllInBackground(objectsToSave) {
            if (it == null) {
                for (savedNote in objectsToSave) {
                    val note = notes.firstOrNull { noteInList ->
                        noteInList.id == savedNote.get(columnNoteId)
                    }
                    if (note != null) {
                        note.parseObjectId = savedNote.objectId
                    }
                }
            }
            errorOnSave(it)
        }
    }

    private fun parseObjectForNote(note: Note): ParseObject {
        val parseObject = ParseObject(tableNote)
        parseObject.objectId = note.parseObjectId
        parseObject.put(columnNoteId, note.id)
        parseObject.put(columnNoteTitle, note.title!!)
        parseObject.put(columnNoteSubtitle, note.subtitle!!)
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
                    val id = parseData.getLong(columnNoteId)
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

    private fun noteFromParseObject(
        parseObject: ParseObject
    ): Note {
        val note = Note()
        note.parseObjectId = parseObject.objectId
        note.title = parseObject.getString(columnNoteTitle)
        note.subtitle = parseObject.getString(columnNoteSubtitle)
        return note
    }

}
