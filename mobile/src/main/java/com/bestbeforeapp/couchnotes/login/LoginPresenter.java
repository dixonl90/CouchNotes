package com.bestbeforeapp.couchnotes.login;

import android.support.annotation.NonNull;

import com.bestbeforeapp.couchnotes.CouchNotesApplication;
import com.bestbeforeapp.couchnotes.model.Profile;
import com.bestbeforeapp.couchnotes.preferences.CouchNotesPreferences;
import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Document;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

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
            GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {

                            if (preferences.getCurrentUserId() == null) {
                                String userId = null;
                                String name = null;
                                try {
                                    userId = jsonObject.getString("id");
                                    name = jsonObject.getString("name");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                try {
                                    Document profile = Profile.getUserProfileById(
                                            CouchNotesApplication.getDatabase(), userId);

                                    if (profile == null)
                                        profile = Profile.createProfile(
                                                CouchNotesApplication.getDatabase(), userId, name);

                                } catch (CouchbaseLiteException e) {
                                    e.printStackTrace();
                                }

                                preferences.setCurrentUserId(userId);
                                preferences.setLastReceivedFbAccessToken(
                                        loginResult.getAccessToken().getToken());

                                viewActions.showProgress(false);
                                viewActions.finishActivity();
                            } else {
                                viewActions.showProgress(false);
                                viewActions.finishActivity();
                            }

                        }
                    }).executeAsync();
        }

    }
}
