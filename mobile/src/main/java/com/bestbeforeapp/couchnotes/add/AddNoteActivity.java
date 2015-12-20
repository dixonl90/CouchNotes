package com.bestbeforeapp.couchnotes.add;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.bestbeforeapp.couchnotes.CouchNotesApplication;
import com.bestbeforeapp.couchnotes.R;
import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddNoteActivity extends AppCompatActivity {

    @Bind (R.id.note_title) EditText noteTitle;
    @Bind (R.id.note_content) EditText noteContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_1);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_note_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    Document doc = createNewNote(noteTitle.getText().toString(),
                            noteContent.getText().toString());

                    Snackbar.make(view, "Saved note! " + doc.getId(),
                            Snackbar.LENGTH_LONG).setAction("Action", null).show();

                } catch (CouchbaseLiteException e) {

                    Snackbar.make(view, "Could not save note! " + e.getCBLStatus(),
                            Snackbar.LENGTH_LONG).setAction("Action", null).show();

                }

            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private Database getDatabase() {
        CouchNotesApplication application = (CouchNotesApplication) getApplication();
        return application.getDatabase();
    }

    private Document createNewNote(String title, String content) throws CouchbaseLiteException {

        Calendar calendar = GregorianCalendar.getInstance();

        Document document = getDatabase().createDocument();

        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("text", title);
        properties.put("content", content);
        properties.put("created_at", calendar);

        document.putProperties(properties);

        return document;
    }

}
