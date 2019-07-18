package com.smlnskgmail.jaman.randomnotes.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.smlnskgmail.jaman.randomnotes.R;
import com.smlnskgmail.jaman.randomnotes.db.support.DatabaseMigration;
import com.smlnskgmail.jaman.randomnotes.entities.Note;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String LOG_TAG = "RNDB";

    private static final String DATABASE_NAME = "rn.db";

    private static final int DATABASE_VERSION_1 = 1;
    private static final int DATABASE_VERSION_2 = 2;

    private static final int DATABASE_VERSION_CURRENT = DATABASE_VERSION_2;

    public static final Class[] DATABASE_CLASSES = new Class[]{
            Note.class
    };

    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION_CURRENT);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        for (Class clazz: DATABASE_CLASSES) {
            try {
                TableUtils.createTable(connectionSource, clazz);
                createDefaultNote();
            } catch (SQLException e) {
                Log.e(LOG_TAG, "Failed create database tables", e);
            }
        }
    }

    private void createDefaultNote() {
        Note note = new Note();
        note.setTitle(context.getString(R.string.default_note_title));
        note.setSubtitle(context.getString(R.string.default_note_subtitle));
        note.save();
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        if (oldVersion < DATABASE_VERSION_2) {
            DatabaseMigration.addSyncStatusToNotes(this);
        }
    }

    public List<Note> getAllNotes() {
        try {
            return getDao(Note.class).queryForAll();
        } catch (SQLException e) {
            Log.e(LOG_TAG, "Failed get all notes\n", e);
        }
        return new ArrayList<>();
    }

    public void deleteAllNotes() {
        try {
            getDao(Note.class).deleteBuilder().delete();
        } catch (SQLException e) {
            Log.e(LOG_TAG, "Failed delete all notes\n", e);
        }
    }

    public void saveNote(Note note) {
        try {
            getDao(Note.class).createOrUpdate(note);
        } catch (SQLException e) {
            Log.e(LOG_TAG, "Failed save note:\n" + note.toString(), e);
        }
    }

    public void deleteNote(Note note) {
        try {
            getDao(Note.class).delete(note);
        } catch (SQLException e) {
            Log.e(LOG_TAG, "Failed delete note:\n" + note.toString(), e);
        }
    }

}
