package com.smlnskgmail.jaman.randomnotes.repository

import android.content.Intent
import androidx.fragment.app.Fragment
import com.smlnskgmail.jaman.randomnotes.repository.entities.Note
import com.smlnskgmail.jaman.randomnotes.repository.model.cloud.CloudDataSource
import com.smlnskgmail.jaman.randomnotes.repository.model.local.LocalDataSource

class DataRepository(
    private val localDataSource: LocalDataSource,
    private val cloudDataSource: CloudDataSource
) {

    fun allNotes(): List<Note> {
        return localDataSource.allNotes()
    }

    fun saveNote(note: Note) {
        localDataSource.createOrUpdateNote(note)
    }

    fun saveNotes(notes: List<Note>) {
        localDataSource.createNotes(notes)
    }

    fun isAuthorized(): Boolean {
        return cloudDataSource.isAuthorized()
    }

    fun signUpWithEmail(
        username: String,
        email: String,
        password: String,
        afterLogin: (e: Exception?) -> Unit
    ) {
        cloudDataSource.signUpWithEmail(username, email, password, afterLogin)
    }

    fun signInWithEmail(
        username: String,
        password: String,
        afterRegister: (e: Exception?) -> Unit
    ) {
        cloudDataSource.signInWithEmail(username, password, afterRegister)
    }

    fun signInWithFacebook(fragment: Fragment, afterFacebookLogin: (e: Exception) -> Unit) {
        cloudDataSource.signInWithFacebook(fragment, afterFacebookLogin)
    }

    fun bindForAuth(requestCode: Int, resultCode: Int, data: Intent?) {
        cloudDataSource.bindForAuth(requestCode, resultCode, data)
    }

    fun logOut(afterLogOut: (e: Exception?) -> Unit) {
        cloudDataSource.logOut(afterLogOut)
    }

    fun syncNotes(notes: List<Note>, errorOnSave: (e: Exception) -> Unit) {
        cloudDataSource.saveAllNotes(notes, errorOnSave)
    }

    fun destroy() {
        localDataSource.destroy()
    }

    fun restoreAllNotes(
        notes: List<Note>,
        afterRestore: (newNotes: List<Note>, e: Exception?) -> Unit
    ) {
        cloudDataSource.restoreAllNotes(notes, afterRestore)
    }

    fun delete(note: Note) {
        localDataSource.delete(note)
    }

}