package com.bestbeforeapp.couchnotes.details;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bestbeforeapp.couchnotes.R;
import com.bestbeforeapp.couchnotes.list.NoteListActivity;
import com.couchbase.lite.Document;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A fragment representing a single Note detail screen.
 * This fragment is either contained in a {@link NoteListActivity}
 * in two-pane mode (on tablets) or a {@link NoteDetailActivity}
 * on handsets.
 */
public class NoteDetailFragment
        extends MvpLceFragment<LinearLayout, Document, NoteDetailView, NoteDetailPresenter>
        implements NoteDetailView {

    @Bind(R.id.note_content) TextView noteContent;

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    public NoteDetailFragment() {
    }

    @NonNull
    @Override
    public NoteDetailPresenter createPresenter() {
        return new NoteDetailPresenter();
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null;
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstance) {
        super.onViewCreated(view, savedInstance);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            loadData(false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.note_detail, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void editNote() {

    }

    @Override
    public void setData(Document note) {

        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle((String)  note.getProperty("text"));
        }

        noteContent.setText((String) note.getProperty("content"));

    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.loadNote(getArguments().getString(ARG_ITEM_ID));
    }
}
