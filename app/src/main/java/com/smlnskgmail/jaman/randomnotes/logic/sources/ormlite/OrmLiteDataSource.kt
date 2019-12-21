package com.smlnskgmail.jaman.randomnotes.logic.sources.ormlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.j256.ormlite.android.apptools.OpenHelperManager
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import com.smlnskgmail.jaman.randomnotes.R
import com.smlnskgmail.jaman.randomnotes.repository.entities.Note
import com.smlnskgmail.jaman.randomnotes.repository.model.local.LocalDataSource
import com.smlnskgmail.jaman.randomnotes.tools.L
import java.sql.SQLException

class OrmLiteDataSource(
    private var context: Context
) : OrmLiteSqliteOpenHelper(
    context,
    databaseFileName,
    null,
    currentDatabaseVersion,
    R.raw.db_config
), LocalDataSource {

    companion object {

        private const val databaseFileName = "rn.db"

        private const val databaseVersion1 = 1

        private const val currentDatabaseVersion = databaseVersion1

        val databaseEntities = arrayOf<Class<*>>(
            Note::class.java
        )

    }

    override fun onCreate(
        database: SQLiteDatabase?,
        connectionSource: ConnectionSource?
    ) {
        for (clazz in databaseEntities) {
            try {
                TableUtils.createTable(connectionSource, clazz)
                createDefaultData()
            } catch (e: SQLException) {
                L.e(e)
            }
        }
    }

    private fun createDefaultData() {
        val note = Note()
        note.title = context.getString(R.string.default_note_title)
        note.subtitle = context.getString(R.string.default_note_subtitle)
        createOrUpdateNote(note)
    }

    override fun onUpgrade(
        database: SQLiteDatabase?,
        connectionSource: ConnectionSource?,
        oldVersion: Int,
        newVersion: Int
    ) {

    }

    override fun allNotes(): List<Note> {
        try {
            return getDao(Note::class.java).queryForAll()
        } catch (e: SQLException) {
            L.e(e)
        }
        return emptyList()
    }

    override fun createOrUpdateNote(note: Note) {
        try {
            getDao(Note::class.java).createOrUpdate(note)
        } catch (e: SQLException) {
            L.e(e)
        }
    }

    override fun createNotes(notes: List<Note>) {
        try {
            getDao(Note::class.java).create(notes)
        } catch (e: SQLException) {
            L.e(e)
        }
    }

    override fun delete(note: Note) {
        try {
            getDao(Note::class.java).delete(note)
        } catch (e: SQLException) {
            L.e(e)
        }
    }

    override fun destroy() {
        OpenHelperManager.releaseHelper()
    }

}
