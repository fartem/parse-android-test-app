package com.smlnskgmail.jaman.randomnotes.logic.sources.parse

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.parse.Parse
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import com.parse.facebook.ParseFacebookUtils
import com.smlnskgmail.jaman.randomnotes.repository.entities.Note
import com.smlnskgmail.jaman.randomnotes.repository.model.cloud.CloudDataSource

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

    override fun signInWithFacebook(
        fragment: Fragment,
        afterFacebookLogin: (e: Exception) -> Unit
    ) {
        val emailField = "email"
        ParseFacebookUtils.logInWithReadPermissionsInBackground(fragment,
            listOf("public_profile", emailField)) { user, e ->
            if (user != null) {
                if (user.email == null) {
                    val graphRequest = GraphRequest.newMeRequest(
                        AccessToken.getCurrentAccessToken()) { data, response ->
                        if (data != null) {
                            user.email = data.getString(emailField)
                            user.saveInBackground {
                                afterFacebookLogin(it)
                            }
                        } else {
                            afterFacebookLogin(e)
                        }
                    }
                    val parameters = Bundle()
                    parameters.putString("fields", emailField)
                    graphRequest.parameters = parameters
                    graphRequest.executeAsync()
                } else {
                    afterFacebookLogin(e)
                }
            } else if (e != null) {
                afterFacebookLogin(e)
            }
        }
    }

    override fun isAuthorized(): Boolean {
        return ParseUser.getCurrentUser() != null
    }

    override fun bindForAuth(requestCode: Int, resultCode: Int, data: Intent?) {
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data)
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

    override fun logOut(afterLogOut: (e: Exception?) -> Unit) {
        val parseUser = ParseUser.getCurrentUser()
        if (ParseFacebookUtils.isLinked(parseUser)) {
            AccessToken.setCurrentAccessToken(null)
            if (LoginManager.getInstance() != null) {
                LoginManager.getInstance().logOut()
            }
        }
        ParseUser.logOutInBackground {
            afterLogOut(it)
        }
    }

    override fun signUpWithEmail(
        username: String,
        email: String,
        password: String,
        afterLogin: (e: Exception?) -> Unit
    ) {
        val parseUser = ParseUser()
        parseUser.username = username
        parseUser.email = email
        parseUser.setPassword(password)
        parseUser.signUpInBackground {
            afterLogin(it)
        }
    }

    override fun signInWithEmail(
        username: String,
        password: String,
        afterRegister: (e: Exception?) -> Unit
    ) {
        ParseUser.logInInBackground(username, password) { _, e ->
            afterRegister(e)
        }
    }

}