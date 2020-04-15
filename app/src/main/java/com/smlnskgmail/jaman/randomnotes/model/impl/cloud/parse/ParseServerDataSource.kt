package com.smlnskgmail.jaman.randomnotes.model.impl.cloud.parse

import android.content.Context
import com.parse.Parse
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.facebook.ParseFacebookUtils
import com.smlnskgmail.jaman.randomnotes.model.api.cloud.CloudDataSource
import com.smlnskgmail.jaman.randomnotes.model.api.entities.Note
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class ParseServerDataSource(
    context: Context
) : CloudDataSource {

    companion object {

        private const val timeout = 5L

        private const val tableNote = "note"

        private const val columnNoteTitle = "title"
        private const val columnNoteSubtitle = "subtitle"

    }

    init {
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .writeTimeout(timeout, TimeUnit.SECONDS)
            .readTimeout(timeout, TimeUnit.SECONDS)
            .callTimeout(timeout, TimeUnit.SECONDS)
        val parseConfig = Parse.Configuration.Builder(context)
            .clientBuilder(httpClient)
            .server("http://192.168.1.8:1337/parse")
            .applicationId("0b65a26a-98e7-46c0-93dc-a162418da5a2")
            .clientKey("26e92573-897f-42e9-b9e1-6cb51505990d")
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
            objectsToSave.add(
                parseObjectForNote(
                    note
                )
            )
        }
        ParseObject.saveAllInBackground(objectsToSave) {
            if (it == null) {
                objectsToSave.forEach { savedNote ->
                    notes.forEach { note ->
                        note.remoteId = savedNote.objectId
                    }
                }
            }
            errorOnSave(it)
        }
    }

    private fun parseObjectForNote(note: Note): ParseObject {
        val parseObject = ParseObject(tableNote)
        parseObject.objectId = note.remoteId
        parseObject.put(columnNoteTitle, note.title!!)
        parseObject.put(columnNoteSubtitle, note.subtitle!!)
        return parseObject
    }

    override fun restoreAllNotes(
        afterRestore: (notes: List<Note>, e: Exception?) -> Unit
    ) {
        val parseQuery: ParseQuery<ParseObject> = ParseQuery.getQuery(tableNote)
        parseQuery.findInBackground { objects, e ->
            if (objects.isNotEmpty()) {
                val notes = mutableListOf<Note>()
                for (parseData in objects) {
                    notes.add(
                        noteFromParseObject(
                            parseData
                        )
                    )
                }
                afterRestore(notes, null)
            } else if (e != null) {
                afterRestore(emptyList(), e)
            }
        }
    }

    private fun noteFromParseObject(
        parseObject: ParseObject
    ): Note {
        val note = Note()
        note.remoteId = parseObject.objectId
        note.title = parseObject.getString(columnNoteTitle)
        note.subtitle = parseObject.getString(columnNoteSubtitle)
        return note
    }

}
