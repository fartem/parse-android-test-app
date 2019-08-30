package com.smlnskgmail.jaman.randomnotes.db.factory;

import android.annotation.SuppressLint;
import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.smlnskgmail.jaman.randomnotes.db.DatabaseHelper;

public class DatabaseFactory {

    @SuppressLint("StaticFieldLeak")
    private static DatabaseHelper databaseHelper;

    public static void set(Context context) {
        if (databaseHelper != null) {
            terminate();
        }
        databaseHelper = new DatabaseHelper(context);
    }

    public static DatabaseHelper getHelper() {
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
