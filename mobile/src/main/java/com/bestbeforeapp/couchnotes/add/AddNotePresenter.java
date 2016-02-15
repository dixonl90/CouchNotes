package com.bestbeforeapp.couchnotes.add;

import android.support.annotation.NonNull;

import com.bestbeforeapp.couchnotes.data.CouchbaseObservables;
import com.couchbase.lite.Document;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AddNotePresenter implements AddNoteContract.UserActions {

    private final AddNoteContract.ViewActions addNoteView;

    public AddNotePresenter(@NonNull AddNoteContract.ViewActions viewActions) {
        addNoteView = viewActions;
    }

    public void addNote(String title, String content) {

        CouchbaseObservables.addDocument(title, content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Document>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                            addNoteView.showErrorSnackBar("Could not save note! " + e.getMessage());
                    }

                    @Override
                    public void onNext(Document note) {
                        if (note != null) {
                            addNoteView.finishActivity();
                        }
                    }
                });
    }

}
