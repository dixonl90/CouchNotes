package com.bestbeforeapp.couchnotes;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by luke on 21/02/16.
 */
public class Prefs {

    private static final String PREFERENCES = "preferences";

    private static final String USER_ID = "user_id";
    private static long userId = 0;

    public static long getUserId() {
        if (userId == 0) {
            SharedPreferences sp = CouchNotesApplication.getAppContext()
                    .getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

            userId = sp.getLong(USER_ID, 0);
        }
        return userId;
    }

    public static void setUserId(long userId) {
        SharedPreferences sp = CouchNotesApplication.getAppContext()
                .getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();

        if (userId != 0 && !"".equals(userId)) {
            Prefs.userId = userId;
            editor.putLong(USER_ID, userId).apply();
        } else {
            Prefs.userId = 0;
        }
    }

}
