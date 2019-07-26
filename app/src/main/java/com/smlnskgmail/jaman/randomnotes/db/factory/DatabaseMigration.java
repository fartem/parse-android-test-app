package com.smlnskgmail.jaman.randomnotes.db.factory;

import android.util.Log;
import com.smlnskgmail.jaman.randomnotes.db.DatabaseHelper;
import com.smlnskgmail.jaman.randomnotes.entities.Note;

import java.sql.SQLException;

public class DatabaseMigration {

    private static final String LOG_TAG = "RNDBM";

    public static void addSyncStatusToNotes(DatabaseHelper databaseHelper) {
        try {
            String query = "ALTER TABLE note ADD COLUMN parseObjectId VARCHAR";
            databaseHelper.getDao(Note.class).executeRaw(query);
        } catch (SQLException e) {
            Log.e(LOG_TAG, "Failed add parseObjectId to notes", e);
        }
    }

}
