package com.bestbeforeapp.couchnotes.add;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.hannesdorfmann.mosby.mvp.delegate.ActivityMvpDelegateCallback;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddNoteActivity
        extends MvpActivity<AddNoteView, AddNotePresenter>
        implements AddNoteView {

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

                presenter.addNote(noteTitle.getText().toString(),
                        noteContent.getText().toString());

            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @NonNull
    @Override
    public AddNotePresenter createPresenter() {
        return new AddNotePresenter();
    }

    public void showErrorSnackBar(String message, CouchbaseLiteException e) {
        Snackbar.make(getCurrentFocus(), message + e.getCBLStatus(),
                Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    public void showSuccessSnackBar(String message) {
        Snackbar.make(getCurrentFocus(), message,
                Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

}
