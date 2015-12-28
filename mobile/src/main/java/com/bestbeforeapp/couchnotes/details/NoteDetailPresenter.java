package com.bestbeforeapp.couchnotes.details;

import android.os.SystemClock;

import com.bestbeforeapp.couchnotes.CouchNotesApplication;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

/**
 * Created by luke on 28/12/15.
 */
public class NoteDetailPresenter extends MvpBasePresenter<NoteDetailView> {

    public void loadNote(String noteId) {

        getView().showLoading(false);

        Database database = CouchNotesApplication.getDatabase();

        SystemClock.sleep(10000);

        Document note = database.getExistingDocument(noteId);

        if(note != null) {
            if (isViewAttached()) {
                getView().setData(note);
                getView().showContent();
            }
        }
        else {
            if(isViewAttached())
                getView().showError(null, false);
        }

    }
}
