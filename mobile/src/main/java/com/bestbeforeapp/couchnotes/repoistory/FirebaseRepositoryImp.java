package com.bestbeforeapp.couchnotes.repoistory;

import android.support.annotation.NonNull;

import com.bestbeforeapp.couchnotes.model.Note;

/**
 * Created by luke on 07/03/16.
 */
public class FirebaseRepositoryImp implements NotesRepository {

    private final NotesServiceApi mNotesServiceApi;

    public FirebaseRepositoryImp(@NonNull NotesServiceApi notesServiceApi) {
        mNotesServiceApi = notesServiceApi;
    }

    @Override
    public void getNotes(@NonNull LoadNotesCallback callback) {

    }

    @Override
    public void getNote(@NonNull String noteId, @NonNull final GetNoteCallback callback) {
        mNotesServiceApi.getNote(noteId, new NotesServiceApi.NotesServiceCallback<Note>() {
            @Override
            public void onLoaded(Note note) {
                callback.onNoteLoaded(note);
            }
        });
    }

    @Override
    public void saveNote(@NonNull Note note) {

        mNotesServiceApi.saveNote(note);

    }

}
