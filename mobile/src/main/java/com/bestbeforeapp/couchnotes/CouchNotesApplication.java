package com.bestbeforeapp.couchnotes;

import android.content.Context;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;
import com.couchbase.lite.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Luke Dixon on 02/11/15.
 */
public class CouchNotesApplication extends android.app.Application {

    /**
     * This URL should not be used, it is taken from the Grocery Sync demo...
     */
    public static final String DEFAULT_SYNC_URL= "http://demo-mobile.couchbase.com/grocery-sync";

    public static final String DATABASE_NAME = "notes-sync";
    private static final String TAG = "CouchNotesApplication";

    public static String SYNC_URL;

    private static Manager manager;
    private static Database database;

    private static Context context;

    public static Context getAppContext() {
        return CouchNotesApplication.context;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        CouchNotesApplication.context = getApplicationContext();

        /**
         * Get the sync gateway from a local properties file. I don't want everyone putting data
         * on my server! :)
         */

        try {
            InputStream is = getAssets().open("server.properties");
            Properties properties = new Properties();
            properties.load(is);
            SYNC_URL = properties.getProperty("sync_gateway");
            if(SYNC_URL == null) {
                SYNC_URL = DEFAULT_SYNC_URL;
            }
            else
                Log.d(TAG, "Loaded properties file, using: " + SYNC_URL);
        } catch (IOException e) {
            Log.d(TAG, "Could not open properties file, using default gateway...");
            SYNC_URL = DEFAULT_SYNC_URL;
        }

        initDatabase();

    }

    public static Database initDatabase() {

        if ((database == null)) {
            try {
                database = getManagerInstance().getDatabase(DATABASE_NAME);
            } catch (CouchbaseLiteException e) {
                Log.e(TAG, "Cannot get Database", e);
            }
        }

        return database;
    }

    private static Manager getManagerInstance() {
        if (manager == null) {

            Manager.enableLogging("CouchNotes", Log.VERBOSE);
            Manager.enableLogging(Log.TAG, Log.VERBOSE);
//            Manager.enableLogging(Log.TAG_SYNC_ASYNC_TASK, Log.VERBOSE);
//            Manager.enableLogging(Log.TAG_SYNC, Log.VERBOSE);
//            Manager.enableLogging(Log.TAG_QUERY, Log.VERBOSE);
//            Manager.enableLogging(Log.TAG_VIEW, Log.VERBOSE);
//            Manager.enableLogging(Log.TAG_DATABASE, Log.VERBOSE);

            try {
                manager = new Manager(new AndroidContext(context), Manager.DEFAULT_OPTIONS);
            } catch (IOException e) {
                Log.e(TAG, "Cannot create Manager object", e);
            }
        }
        return manager;
    }

    public static Database getDatabase() {
        return initDatabase();
    }
}
