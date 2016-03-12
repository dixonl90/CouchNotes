package com.bestbeforeapp.couchnotes.list;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.bestbeforeapp.couchnotes.details.NoteDetailActivity;
import com.bestbeforeapp.couchnotes.details.NoteDetailFragment;

/**
 * Created by luke on 13/02/16.
 */
public class NoteListPresenter implements NotesListContract.UserActions {

    Context context;
    private final NoteListView.ViewActions noteListView;

    public NoteListPresenter(@NonNull NoteListView.ViewActions viewActions) {
        this.context = (Context) viewActions;
        noteListView = viewActions;

    }

    @Override
    public void openNote(String documentId) {
        Intent intent = new Intent(context, NoteDetailActivity.class);
        intent.putExtra(NoteDetailFragment.ARG_ITEM_ID, documentId);
        context.startActivity(intent);
    }

    @Override
    public void deleteAllNotes() {

    }
}
