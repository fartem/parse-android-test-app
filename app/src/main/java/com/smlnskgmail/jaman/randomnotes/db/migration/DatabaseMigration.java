package com.smlnskgmail.jaman.randomnotes.db.migration;

import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.smlnskgmail.jaman.randomnotes.entities.note.Note;

import java.sql.SQLException;

public class DatabaseMigration {

    private static final String LOG_TAG = "RNDBM";

    public static void addSyncStatusToNotes(OrmLiteSqliteOpenHelper helper) {
        try {
            String query = "ALTER TABLE note ADD COLUMN parseObjectId VARCHAR";
            helper.getDao(Note.class).executeRaw(query);
        } catch (SQLException e) {
            Log.e(LOG_TAG, "Failed add parseObjectId to notes", e);
        }
    }

}
