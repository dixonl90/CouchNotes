package com.bestbeforeapp.couchnotes.details;

import com.couchbase.lite.Document;

/**
 * Created by luke on 28/12/15.
 */
public interface NoteDetailContract {

    interface ViewActions {

        void showLoading(boolean loading);

        void setData(Document note);

        void showError(Throwable e);

    }

    interface UserActions {

        void editNote();

        void loadNote(String noteId);

    }

}
