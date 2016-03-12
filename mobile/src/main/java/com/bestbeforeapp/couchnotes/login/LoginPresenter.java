package com.bestbeforeapp.couchnotes.login;

import android.support.annotation.NonNull;

import com.bestbeforeapp.couchnotes.preferences.CouchNotesPreferences;
import com.facebook.login.LoginResult;

public class LoginPresenter implements LoginContract.UserActions {

    private final LoginContract.ViewActions viewActions;
    private CouchNotesPreferences preferences;

    public LoginPresenter(@NonNull LoginContract.ViewActions viewActions) {
        this.viewActions = viewActions;
        preferences = new CouchNotesPreferences();
    }

    @Override
    public void facebookLogin(final LoginResult loginResult) {

        if (preferences.getCurrentUserId() == null) {

        }

    }
}
