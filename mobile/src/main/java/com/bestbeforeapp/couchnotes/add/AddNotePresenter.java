package com.bestbeforeapp.couchnotes.add;

import android.support.annotation.NonNull;

import com.bestbeforeapp.couchnotes.model.Note;
import com.bestbeforeapp.couchnotes.repoistory.FirebaseApi;
import com.bestbeforeapp.couchnotes.repoistory.FirebaseRepositoryImp;
import com.bestbeforeapp.couchnotes.repoistory.NotesRepository;
import com.firebase.client.ServerValue;

import java.util.HashMap;

public class AddNotePresenter implements AddNoteContract.UserActions {

    private final AddNoteContract.ViewActions addNoteView;
    private final NotesRepository repository;

    public AddNotePresenter(@NonNull AddNoteContract.ViewActions viewActions) {
        addNoteView = viewActions;
        repository = new FirebaseRepositoryImp(new FirebaseApi());
    }

    public void addNote(String title, String content) {

        /* Make the timestamp for last changed */
        HashMap<String, Object> createdTimestamp = new HashMap<>();
        createdTimestamp.put("timestamp", ServerValue.TIMESTAMP);

        Note newNote = new Note(title, content, createdTimestamp);

        repository.saveNote(newNote);

        addNoteView.finishActivity();

    }

}
