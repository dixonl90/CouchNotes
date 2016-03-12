package com.bestbeforeapp.couchnotes.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.bestbeforeapp.couchnotes.R;
import com.bestbeforeapp.couchnotes.add.AddNoteActivity;
import com.bestbeforeapp.couchnotes.details.NoteDetailActivity;
import com.bestbeforeapp.couchnotes.model.Note;
import com.bestbeforeapp.couchnotes.preferences.CouchNotesPreferences;
import com.firebase.client.Firebase;

/**
 * An activity representing a list of Notes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link NoteDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class NoteListActivity extends AppCompatActivity implements NoteListAdapter.OnItemClickListener,
        NoteListAdapter.OnActivatedListener, NoteListView.ViewActions {

    private static final String TAG = "NoteListActivity";
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    public static final String designDocName = "notes-local";
    public static final String byDateViewName = "byDate";

    private NoteListPresenter presenter;

    private NoteListAdapter adapter;

    private CouchNotesPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        presenter = createPresenter();
        preferences = new CouchNotesPreferences();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

//        if (preferences.getLastReceivedFbAccessToken() == null) {
//            Intent i = new Intent(this, LoginActivity.class);
//            startActivity(i);
//            finish();
//        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), AddNoteActivity.class));
            }
        });

        View recyclerView = findViewById(R.id.note_list);
        assert recyclerView != null;

        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.note_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    @NonNull
    public NoteListPresenter createPresenter() {
        return new NoteListPresenter(this);
    }

    @Override
    public void onItemClick(View view, int position) {

        Note note = adapter.getItem(position);
        Log.d(TAG, "Clicked " + note.getTitle());
        presenter.openNote(adapter.getRef(position).getKey());
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {

        Firebase ref = new Firebase("https://notes-app-test.firebaseio.com/notes");

        adapter = new NoteListAdapter(Note.class, R.layout.note_list_content, ref);

        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, null));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(this);
        adapter.setPositionActivatedListener(this);
    }

    @Override
    public void onActivated(int position) {
        adapter.setActivated(position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_notes_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_delete_all:
                    presenter.deleteAllNotes();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}