package com.bestbeforeapp.couchnotes.data;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * Created by Luke Dixon on 18/11/15.
 */
public class ModelHelper {

    public static Document save(Database database, Object object) throws CouchbaseLiteException {
        ObjectMapper m = new ObjectMapper();
        Map<String, Object> props = m.convertValue(object, Map.class);
        String id = (String) props.get("_id");

        Document document;
        if (id == null) {
            document = database.createDocument();
        } else {
            document = database.getExistingDocument(id);
            if (document == null) {
                document = database.getDocument(id);
            } else {
                props.put("_rev", document.getProperty("_rev"));
            }
        }

        document.putProperties(props);

        return document;
    }

    public static <T> T modelForDocument(Document document, Class<T> aClass) {
        ObjectMapper m = new ObjectMapper();
        return m.convertValue(document.getProperties(), aClass);
    }

}