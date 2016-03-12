package com.bestbeforeapp.couchnotes.details;

import com.bestbeforeapp.couchnotes.model.Note;

/**
 * Created by luke on 28/12/15.
 */
public interface NoteDetailContract {

    interface ViewActions {

        void showLoading(boolean loading);

        void setData(Note note);

        void showError(String e);

    }

    interface UserActions {

        void editNote();

        void loadNote(String noteId);

    }

}
