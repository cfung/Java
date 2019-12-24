package com.udacity.course3.reviews.entities.mongo;

import com.udacity.course3.reviews.entities.Product;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.List;

@Document("review")
public class ReviewMongo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String title;
    private String text;
    private Product product;
    private List<commentMongo> comments;

    public ReviewMongo(String title, String text, Product product, List<commentMongo> comments) {
        this.title = title;
        this.text = text;
        this.product = product;
        this.comments = comments;
    }

    public ReviewMongo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<commentMongo> getComments() {
        return comments;
    }

    public void setComments(List<commentMongo> comments) {
        this.comments = comments;
    }
}
