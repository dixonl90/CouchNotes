package com.bestbeforeapp.couchnotes.repoistory;

import com.bestbeforeapp.couchnotes.model.Note;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.List;

import timber.log.Timber;

/**
 * Created by luke on 07/03/16.
 */
public class FirebaseApi implements NotesServiceApi {

    private static final String BASE_URL = "https://notes-app-test.firebaseio.com";
    private static final String NOTES_URL = "/notes";

    @Override
    public void getAllNotes(NotesServiceCallback<List<Note>> callback) {

        Firebase ref = new Firebase(BASE_URL+NOTES_URL);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Note> notes = dataSnapshot.getValue(List.class);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    @Override
    public void getNote(final String noteId, final NotesServiceCallback<Note> callback) {
        Firebase ref = new Firebase(BASE_URL+NOTES_URL+"/"+noteId);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Note note = dataSnapshot.getValue(Note.class);

                if(note != null) {
                    callback.onLoaded(note);
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Timber.e("Error getting note %s: %s",noteId, firebaseError.getMessage());
            }
        });
    }

    @Override
    public void saveNote(Note note) {

        Firebase ref = new Firebase(BASE_URL+NOTES_URL);

        Firebase newNoteRef = ref.push();
        final String newNoteKey = newNoteRef.getKey();

        Timber.d("Created new note with key %s", newNoteKey);

        newNoteRef.setValue(note, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if(firebaseError == null) {
                    Timber.d("Note %s saved!", newNoteKey);
                }
                else
                    Timber.e("Error saving note %s: %s", newNoteKey, firebaseError.getMessage());
            }
        });

    }
}
