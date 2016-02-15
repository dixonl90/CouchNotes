package com.bestbeforeapp.couchnotes.list;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bestbeforeapp.couchnotes.CouchNotesApplication;
import com.bestbeforeapp.couchnotes.details.NoteDetailActivity;
import com.bestbeforeapp.couchnotes.details.NoteDetailFragment;
import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;

import java.util.Iterator;

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
        try {
            Query query = CouchNotesApplication.getDatabase().createAllDocumentsQuery();
            query.setAllDocsMode(Query.AllDocsMode.ALL_DOCS);
            QueryEnumerator result = query.run();
            for (Iterator<QueryRow> it = result; it.hasNext(); ) {
                QueryRow row = it.next();
                Log.d("DeleteAll", "Deleting " + row.getDocumentId() + " - " + row.getDocument().getProperty("text"));
                row.getDocument().delete();
            }
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
    }
}
