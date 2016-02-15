package com.bestbeforeapp.couchnotes.list;

/**
 * Created by luke on 21/12/15.
 */
public interface NotesListContract {

    interface UserActions {

        void openNote(String documentId);

        void deleteAllNotes();

    }

    interface ViewActions {

    }

}
