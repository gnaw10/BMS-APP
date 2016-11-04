package com.example.gnaw.bms;

/**
 * Created by gnaw on 2016/11/1.
 */

public class LogList {
    private int id,book_id;
    private String title,body,user_id;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public String getUser_id()
    {
        return user_id;
    }
    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
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

    @Override
    public String toString() {
        return "Log [ id=" + id + ", title=" + title + ", body=" + body + ", user_id=" + user_id + ", book_id =" + book_id + "]";
    }


}
/*
        "id": 1,
      "title": "zzasd",
      "body": "sadfawieulthalsfalsieuhaifrwirapeorjewoeiraejfasod;if;woeiejr",
      "user_id": "1",
      "book_id": "0"

 */