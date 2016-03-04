package com.bestbeforeapp.couchnotes.login;

import com.facebook.login.LoginResult;

/**
 * Created by luke on 22/12/15.
 */
public interface LoginContract {

    interface ViewActions {

        void showLoginError(String message);

        void finishActivity();

        void showProgress(boolean show);

    }

    interface UserActions {

        void facebookLogin(final LoginResult loginResult);

    }

}
