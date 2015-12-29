package com.bestbeforeapp.couchnotes.details;

import com.bestbeforeapp.couchnotes.CouchNotesApplication;
import com.bestbeforeapp.couchnotes.CouchbaseObservables;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NoteDetailPresenter extends MvpBasePresenter<NoteDetailView> {

    public void loadNote(String noteId) {

        getView().showLoading(false);

        CouchbaseObservables.getDocument(noteId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Document>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isViewAttached())
                            getView().showError(e, false);
                    }

                    @Override
                    public void onNext(Document note) {
                        if (note != null) {
                            if (isViewAttached()) {
                                getView().setData(note);
                                getView().showContent();
                            }
                        }
                    }
                });
    }
}
