package com.bestbeforeapp.couchnotes.add;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.bestbeforeapp.couchnotes.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddNoteActivity extends AppCompatActivity
        implements AddNoteContract, FloatingActionButton.OnClickListener, AddNoteContract.ViewActions {

    @Bind(R.id.note_title)
    EditText noteTitle;
    @Bind(R.id.note_content)
    EditText noteContent;
    @Bind(R.id.add_note_fab)
    FloatingActionButton fab;

    private AddNotePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        presenter = createPresenter();

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_1);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        fab.setOnClickListener(this);
    }

    @NonNull
    public AddNotePresenter createPresenter() {
        return new AddNotePresenter(this);
    }

    public void showErrorSnackBar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message,
                Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    public void finishActivity() {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.add_note_fab:
                presenter.addNote(noteTitle.getText().toString(),
                        noteContent.getText().toString());
                break;
        }
    }
}
