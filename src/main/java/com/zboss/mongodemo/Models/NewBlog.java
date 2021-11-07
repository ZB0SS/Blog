package com.zboss.mongodemo.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class NewBlog {
    @Id
    private String id;
    @Field
    @Indexed(unique = true)
    private String title;
    @Field
    private String body;
    @Field
    private String author;
    @Field
    private String blogImage;

    public NewBlog(String title, String body, String blogImage, String author) {
        this.title = title;
        this.body = body;
        this.blogImage = blogImage;
        this.author = author;
    }

    public NewBlog() {}

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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBlogImage() {
        return blogImage;
    }

    public void setBlogImage(String blogImage) {
        this.blogImage = blogImage;
    }

    public String getAuthor() { return this.author; }
    public void setAuthor(String author) { this.author = author; }
}
