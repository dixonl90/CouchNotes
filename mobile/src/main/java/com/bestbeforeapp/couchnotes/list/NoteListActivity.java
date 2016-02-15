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
import android.widget.Toast;

import com.bestbeforeapp.couchnotes.CouchNotesApplication;
import com.bestbeforeapp.couchnotes.R;
import com.bestbeforeapp.couchnotes.add.AddNoteActivity;
import com.bestbeforeapp.couchnotes.details.NoteDetailActivity;
import com.couchbase.lite.Document;
import com.couchbase.lite.Emitter;
import com.couchbase.lite.Mapper;
import com.couchbase.lite.replicator.Replication;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * An activity representing a list of Notes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link NoteDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class NoteListActivity extends AppCompatActivity implements ListAdapter.OnItemClickListener,
        ListAdapter.OnActivatedListener, Replication.ChangeListener, NoteListView.ViewActions {

    private static final String TAG = "NoteListActivity";
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    public static final String designDocName = "notes-local";
    public static final String byDateViewName = "byDate";

    private NoteListPresenter presenter;

    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        presenter = createPresenter();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

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

        startSync();

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
        Document document = (Document) adapter.getItem(position);
        Log.d(TAG, "Clicked " + document.getProperty("text"));
        presenter.openNote(document.getId());
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {

        com.couchbase.lite.View viewItemsByDate = CouchNotesApplication.getDatabase().getView(
                String.format("%s/%s", designDocName, byDateViewName));

        viewItemsByDate.setMap(new Mapper() {
            @Override
            public void map(Map<String, Object> document, Emitter emitter) {
                Object createdAt = document.get("created_at");
                if (createdAt != null) {
                    emitter.emit(createdAt.toString(), null);
                }
            }
        }, "1.0");

        adapter = new ListAdapter(this, viewItemsByDate.createQuery().toLiveQuery());

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

    private void startSync() {

        URL syncUrl;

        try {
            syncUrl = new URL(CouchNotesApplication.SYNC_URL);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        Replication pullReplication = CouchNotesApplication.getDatabase().createPullReplication(syncUrl);
        pullReplication.setContinuous(true);

        Replication pushReplication = CouchNotesApplication.getDatabase().createPushReplication(syncUrl);
        pushReplication.setContinuous(true);

        pullReplication.start();
        pushReplication.start();

        pullReplication.addChangeListener(this);
        pushReplication.addChangeListener(this);

    }

    @Override
    public void changed(Replication.ChangeEvent event) {

        Replication replication = event.getSource();
        com.couchbase.lite.util.Log.d(TAG, "Replication : " + replication + " changed.");
        if (!replication.isRunning()) {
            String msg = String.format("Replicator %s not running", replication);
            com.couchbase.lite.util.Log.d(TAG, msg);
        }
        else {
            int processed = replication.getCompletedChangesCount();
            int total = replication.getChangesCount();
            String msg = String.format("Replicator processed %d / %d", processed, total);
            com.couchbase.lite.util.Log.d(TAG, msg);
        }

        if (event.getError() != null) {
            showError("Sync error", event.getError());
        }

    }

    public void showError(final String errorMessage, final Throwable throwable) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String msg = String.format("%s: %s", errorMessage, throwable);
                com.couchbase.lite.util.Log.e(TAG, msg, throwable);
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });

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