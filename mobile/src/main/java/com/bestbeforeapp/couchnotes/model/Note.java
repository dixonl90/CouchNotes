/**
 * Created by Pasin Suriyentrakorn <pasin@couchbase.com> on 3/4/14.
 */

package com.bestbeforeapp.couchnotes.model;

import com.firebase.client.ServerValue;

import java.util.HashMap;

public class Note {

    private String title;
    private String content;
    private HashMap<String, Object> _created;
    private HashMap<String, Object> _updated;

    public Note() {
    }

    public Note(String title, String content, HashMap<String, Object> _created) {
        this.title = title;
        this.content = content;
        this._created = _created;

        HashMap<String, Object> timestampNowObject = new HashMap<String, Object>();
        timestampNowObject.put("timestamp", ServerValue.TIMESTAMP);
        this._updated = timestampNowObject;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public HashMap<String, Object> get_created() {
        return _created;
    }

    public HashMap<String, Object> get_updated() {
        return _updated;
    }
}
