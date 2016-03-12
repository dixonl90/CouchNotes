package com.bestbeforeapp.couchnotes;

import android.app.Application;
import android.content.Context;

import com.firebase.client.Firebase;

/**
 * Created by Luke Dixon on 02/11/15.
 */
public class CouchNotesApplication extends Application {

    private static Context context;

    public static Context getAppContext() {
        return CouchNotesApplication.context;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        CouchNotesApplication.context = getApplicationContext();

        Firebase.setAndroidContext(this);
    }
}
