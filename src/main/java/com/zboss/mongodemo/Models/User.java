package com.zboss.mongodemo.Models;

import com.mongodb.lang.Nullable;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.QueryAnnotation;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.repository.Query;

@Document
public class User {
    @Id
    private String id;

    @Field
//    @Indexed(unique = true)
    private String email;

    @Field
    private String password;

    @Field
    private String firstName;

    @Field
    @Indexed(unique = true)
    private String username;

    @Field
    @Nullable
    private String avatar = "https://i.pinimg.com/474x/65/25/a0/6525a08f1df98a2e3a545fe2ace4be47.jpg";

    public User(String email, String username, String password, String firstName, String lastName) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Field
    String lastName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {return this.avatar;}
    public void setAvatar(String avatar) { this.avatar = avatar; }



    @Override
    public String toString() {
        return String.format("User[id=%s, firstName=%s, lastName=%s, email=%s, username=%s, password=%s]",
                this.id,
                this.firstName,
                this.lastName, 
                this.email,
                this.username,
                this.password);
    }

}
