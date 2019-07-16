package com.smlnskgmail.jaman.randomnotes.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.smlnskgmail.jaman.randomnotes.entities.Note;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "rn.db";

    private static final int DATABASE_VERSION_1 = 1;

    private static final int DATABASE_VERSION_CURRENT = DATABASE_VERSION_1;

    public static final Class[] DATABASE_CLASSES = new Class[]{
            Note.class
    };

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION_CURRENT);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        for (Class clazz: DATABASE_CLASSES) {
            try {
                TableUtils.createTable(connectionSource, clazz);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        if (oldVersion < DATABASE_VERSION_1) {
            // Do something
        }
    }

}
