package com.smlnskgmail.jaman.randomnotes.model.impl.ormlite.config;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;
import com.smlnskgmail.jaman.randomnotes.model.impl.ormlite.OrmLiteDataSource;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

@SuppressWarnings("ALL")
public class OrmLiteDatabaseConfigurator {

    public static void main(String[] args) throws Exception {
        // About: https://github.com/j256/ormlite-android/issues/79
        File file = new File(
                "../../build/tmp/kotlin-classes/randomNotesDebug"
        );
        URL url = file.toURL();
        URLClassLoader urlClassLoader
                = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Class urlClass = URLClassLoader.class;
        Method method = urlClass.getDeclaredMethod(
                "addURL",
                new Class[]{
                        URL.class
                }
        );
        method.setAccessible(true);
        method.invoke(
                urlClassLoader,
                new Object[] {
                        url
                }
        );

        OrmLiteConfigUtil.writeConfigFile(
                "db_config.txt",
                OrmLiteDataSource.Companion.getDatabaseEntities()
        );
    }

}
