package com.smlnskgmail.jaman.randomnotes.parse

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import com.parse.facebook.ParseFacebookUtils
import com.smlnskgmail.jaman.randomnotes.entities.Note

object ParseApi {

    fun isAuthorized() = ParseUser.getCurrentUser() != null

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

    fun register(username: String, password: String, afterRegister: (e: Exception?) -> Unit) {
        ParseUser.logInInBackground(username, password) { user, e ->
            afterRegister(e)
        }
    }

    fun loginWithEmail(username: String, email: String, password: String,
                       afterLogin: (e: Exception?) -> Unit) {
        val parseUser = ParseUser()
        parseUser.username = username
        parseUser.email = email
        parseUser.setPassword(password)
        parseUser.signUpInBackground {
            afterLogin(it)
        }
    }

    fun loginWithFacebook(fragment: Fragment, afterFacebookLogin: (success: Boolean) -> Unit) {
        val emailField = "email"
        ParseFacebookUtils.logInWithReadPermissionsInBackground(fragment,
            listOf("public_profile", emailField)) { user, e ->
            if (user != null) {
                if (user.email == null) {
                    val graphRequest = GraphRequest.newMeRequest(
                        AccessToken
                            .getCurrentAccessToken()) { data, response ->
                        if (data != null) {
                            user.email = data.getString(emailField)
                            user.saveInBackground {
                                afterFacebookLogin(it == null)
                            }
                        } else {
                            afterFacebookLogin(false)
                        }
                    }
                    val parameters = Bundle()
                    parameters.putString("fields", emailField)
                    graphRequest.parameters = parameters
                    graphRequest.executeAsync()
                } else {
                    afterFacebookLogin(false)
                }
            } else if (e != null) {
                afterFacebookLogin(false)
            }
        }
    }

    fun logout(afterLogout: (e: Exception) -> Unit) {
        val parseUser = ParseUser.getCurrentUser()
        if (ParseFacebookUtils.isLinked(parseUser)) {
            AccessToken.setCurrentAccessToken(null)
            if (LoginManager.getInstance() != null) {
                LoginManager.getInstance().logOut()
            }
        }
        ParseUser.logOutInBackground {
            afterLogout(it)
        }
    }

}