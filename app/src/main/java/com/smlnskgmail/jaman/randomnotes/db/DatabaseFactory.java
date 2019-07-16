package com.smlnskgmail.jaman.randomnotes.db;

import android.content.Context;
import com.j256.ormlite.android.apptools.OpenHelperManager;

public class DatabaseFactory {

    private static DatabaseHelper databaseHelper;

    public static void set(Context context) {
        if (databaseHelper != null) {
            terminate();
        }
        databaseHelper = new DatabaseHelper(context);
    }

    public static DatabaseHelper get() {
        if (databaseHelper == null) {
            throw new RuntimeException("Database must be initialized in *Application class");
        }
        return databaseHelper;
    }

    public static void terminate() {
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

}
