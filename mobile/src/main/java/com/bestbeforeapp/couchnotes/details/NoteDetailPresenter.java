package com.bestbeforeapp.couchnotes.details;

import android.support.annotation.NonNull;

import com.bestbeforeapp.couchnotes.model.Note;
import com.bestbeforeapp.couchnotes.repoistory.FirebaseApi;
import com.bestbeforeapp.couchnotes.repoistory.FirebaseRepositoryImp;
import com.bestbeforeapp.couchnotes.repoistory.NotesRepository;

public class NoteDetailPresenter implements NoteDetailContract.UserActions {

    private final NoteDetailContract.ViewActions noteDetailView;
    private final NotesRepository repository;

    public NoteDetailPresenter(@NonNull NoteDetailContract.ViewActions viewActions) {
        noteDetailView = viewActions;
        repository = new FirebaseRepositoryImp(new FirebaseApi());
    }

    public void loadNote(String noteId) {

        noteDetailView.showLoading(true);

        repository.getNote(noteId, new NotesRepository.GetNoteCallback() {
            @Override
            public void onNoteLoaded(Note note) {
                if(note != null) {
                    noteDetailView.showLoading(false);
                    noteDetailView.setData(note);
                }
                else {
                    noteDetailView.showLoading(false);
                    noteDetailView.showError("Error!");
                }
            }
        });
    }

    @Override
    public void editNote() {

    }
}
