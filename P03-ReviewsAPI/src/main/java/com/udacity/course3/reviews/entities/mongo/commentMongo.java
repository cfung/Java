package com.udacity.course3.reviews.entities.mongo;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("comment")
public class commentMongo {

    private Integer id;
    private String title;
    private String text;


    public commentMongo(Integer id, String title, String text) {
        this.id = id;
        this.title = title;
        this.text = text;
    }

    public commentMongo() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
