package com.smlnskgmail.jaman.randomnotes.db;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.smlnskgmail.jaman.randomnotes.db.entities.EntityWitId;

import java.sql.SQLException;
import java.util.List;

public class HelperFactory {

    @SuppressLint("StaticFieldLeak")
    private static DatabaseHelper databaseHelper;

    public static void set(Context context) {
        if (databaseHelper != null) {
            terminate();
        }
        databaseHelper = new DatabaseHelper(context);
    }

    public static Class[] dbClasses() {
        return DatabaseHelper.DATABASE_CLASSES;
    }

    public static<T extends EntityWitId> void save(Class<T> clazz, @NonNull T entity) throws SQLException {
        databaseHelper.getDao(clazz).create(entity);
    }

    public static<T extends EntityWitId> void delete(Class<T> clazz, @NonNull T entity) throws SQLException {
        databaseHelper.getDao(clazz).delete(entity);
    }

    public static<T extends EntityWitId> List<T> allOf(Class<T> clazz) throws SQLException  {
        return databaseHelper.getDao(clazz).queryForAll();
    }

    public static void terminate() {
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

}
