package com.bestbeforeapp.couchnotes.add;

import com.bestbeforeapp.couchnotes.CouchNotesApplication;
import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by luke on 22/12/15.
 */
public class AddNotePresenter extends MvpBasePresenter<AddNoteView> {

    public void addNote(String title, String content) {
        try {

            Document doc = createNewNote(title, content);

            if (isViewAttached()) getView().finishActivity();

        } catch (CouchbaseLiteException e) {

            if (isViewAttached())
                getView().showErrorSnackBar("Could not save note! " + e.getCBLStatus());

        }
    }

    private Document createNewNote(String title, String content) throws CouchbaseLiteException {

        Calendar calendar = GregorianCalendar.getInstance();

        Document document = CouchNotesApplication.getDatabase().createDocument();

        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("text", title);
        properties.put("content", content);
        properties.put("created_at", calendar);

        document.putProperties(properties);

        return document;
    }

}
