package com.bestbeforeapp.couchnotes.details;

import com.couchbase.lite.Document;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

/**
 * Created by luke on 28/12/15.
 */
public interface NoteDetailView extends MvpLceView<Document> {

    void editNote();
}
