package com.bestbeforeapp.couchnotes.add;

import com.couchbase.lite.CouchbaseLiteException;
import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by luke on 22/12/15.
 */
public interface AddNoteView extends MvpView {

    void showErrorSnackBar(String message, CouchbaseLiteException e);

    void showSuccessSnackBar(String message);

}
