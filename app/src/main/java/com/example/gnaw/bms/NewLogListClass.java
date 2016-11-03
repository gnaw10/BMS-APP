package com.example.gnaw.bms;

/**
 * Created by gnaw on 2016/11/1.
 */

public class NewLogListClass {
    private int bid;
    private String title,body;


    public int getBid() { return bid; }

    public void setBid(int bid) { this.bid = bid; }

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
        return "Log [  title=" + title + ", body=" + body + " bid =" + bid + "]";
    }


}
/*
        "id": 1,
      "title": "zzasd",
      "body": "sadfawieulthalsfalsieuhaifrwirapeorjewoeiraejfasod;if;woeiejr",
      "user_id": "1",
      "book_id": "0"

 */