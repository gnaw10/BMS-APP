package com.example.gnaw.bms;

/**
 * Created by gnaw on 2016/10/30.
 */

public class Book {
    private int id;
    private String name;
    private int user_id;
    private String coverUrl;
    private String borrowUser;


    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCoverUrl() {
        return coverUrl;
    }
    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getBorrowUser() {
        return borrowUser;
    }
    public void setBorrowUser(String borrowUser) {
        this.borrowUser = borrowUser;
    }

    @Override
    public String toString() {
        return "Book [id=" + id + ", name=" + name + ", borrowUser=" + borrowUser + ", coverUrl=" + coverUrl + ", user_id=" + user_id + "]";
    }
}

