package com.bestbeforeapp.couchnotes.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Luke Dixon on 18/11/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Note {

    @JsonProperty(value = "_id")
    private String documentId;

    @JsonProperty(value = "user_id")
    private int userId;

    private String title;
    private String content;
    private String createAt;

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreatedAt(String createAt) {
        this.createAt = createAt;
    }
}
