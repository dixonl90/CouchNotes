package com.bestbeforeapp.couchnotes.add;

import com.bestbeforeapp.couchnotes.CouchbaseObservables;
import com.couchbase.lite.Document;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AddNotePresenter extends MvpBasePresenter<AddNoteView> {

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
                        if (isViewAttached())
                            getView().showErrorSnackBar("Could not save note! " + e.getMessage());
                    }

                    @Override
                    public void onNext(Document note) {
                        if (note != null) {
                            if (isViewAttached()) {
                                getView().finishActivity();
                            }
                        }
                    }
                });
    }

}
