package com.bestbeforeapp.couchnotes.details;

import android.support.annotation.NonNull;

import com.bestbeforeapp.couchnotes.data.CouchbaseObservables;
import com.couchbase.lite.Document;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NoteDetailPresenter implements NoteDetailContract.UserActions {

    private final NoteDetailContract.ViewActions noteDetailView;

    public NoteDetailPresenter(@NonNull NoteDetailContract.ViewActions viewActions) {
        noteDetailView = viewActions;
    }

    public void loadNote(String noteId) {

        noteDetailView.showLoading(true);

        CouchbaseObservables.getDocument(noteId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Document>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        noteDetailView.showLoading(false);
                        noteDetailView.showError(e);
                    }

                    @Override
                    public void onNext(Document note) {
                        if (note != null) {
                            noteDetailView.showLoading(false);
                            noteDetailView.setData(note);
                        }
                    }
                });
    }

    @Override
    public void editNote() {

    }
}
