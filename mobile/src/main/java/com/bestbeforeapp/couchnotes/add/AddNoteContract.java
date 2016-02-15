package com.bestbeforeapp.couchnotes.add;

/**
 * Created by luke on 22/12/15.
 */
public interface AddNoteContract {

    interface ViewActions {

        void showErrorSnackBar(String message);

        void finishActivity();

    }

    interface UserActions {

        void addNote(String title, String content);

    }

}
