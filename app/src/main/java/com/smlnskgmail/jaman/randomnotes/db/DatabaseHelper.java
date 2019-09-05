package com.smlnskgmail.jaman.randomnotes.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.smlnskgmail.jaman.randomnotes.R;
import com.smlnskgmail.jaman.randomnotes.db.migration.DatabaseMigration;
import com.smlnskgmail.jaman.randomnotes.entities.note.Note;
import com.smlnskgmail.jaman.randomnotes.entities.note.support.NoteDefaultData;

import java.sql.SQLException;

class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String LOG_TAG = "RNDB";

    private static final String DATABASE_NAME = "rn.db";

    private static final int DATABASE_VERSION_2 = 2;

    private static final int DATABASE_VERSION_CURRENT = DATABASE_VERSION_2;

    static final Class[] DATABASE_CLASSES = new Class[]{
            Note.class
    };

    private final Context context;

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION_CURRENT, R.raw.db_config);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        for (Class clazz: DATABASE_CLASSES) {
            try {
                TableUtils.createTable(connectionSource, clazz);
                createDefaultData();
            } catch (SQLException e) {
                Log.e(LOG_TAG, "Failed create database tables", e);
            }
        }
    }

    private void createDefaultData() {
        new NoteDefaultData(context).create();
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion,
                          int newVersion) {
        if (oldVersion < DATABASE_VERSION_2) {
            DatabaseMigration.addSyncStatusToNotes(this);
        }
    }

}
