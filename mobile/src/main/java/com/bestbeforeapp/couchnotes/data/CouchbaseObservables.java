package com.bestbeforeapp.couchnotes.data;

import com.bestbeforeapp.couchnotes.CouchNotesApplication;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by luke on 28/12/15.
 */
public class CouchbaseObservables {

    public static Observable<Document> getDocument(final String noteId) {
        return Observable.create(new Observable.OnSubscribe<Document>() {

            @Override
            public void call(Subscriber<? super Document> subscriber) {
                try {
                    Database database = CouchNotesApplication.getDatabase();
                    Document note = database.getExistingDocument(noteId);
                    subscriber.onNext(note);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        }).delay(1, TimeUnit.SECONDS);
    }

    public static Observable<Document> addDocument (final String title, final String content) {
        return Observable.create(new Observable.OnSubscribe<Document>() {

            @Override
            public void call(Subscriber<? super Document> subscriber) {
                try {
                    Database database = CouchNotesApplication.getDatabase();

                    Calendar calendar = GregorianCalendar.getInstance();

                    Document document = database.createDocument();

                    Map<String, Object> properties = new HashMap<String, Object>();
                    properties.put("text", title);
                    properties.put("content", content);
                    properties.put("created_at", calendar.getTimeInMillis());

                    document.putProperties(properties);

                    subscriber.onNext(document);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

}
