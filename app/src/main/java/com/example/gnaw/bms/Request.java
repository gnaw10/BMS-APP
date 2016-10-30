package com.example.gnaw.bms;

import java.util.List;

/**
 * Created by gnaw on 2016/10/30.
 */

public class Request {

    private String code;
    public static List<Book> response;

    /**
     * 默认的构造方法必须不能省，不然不能解析
     */

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public List<Book> getResponse() {
        return response;
    }
    public void setResponse(List<Book> response) {
        this.response = response;
    }
    @Override
    public String toString() {
        return "Request [ code=" +code + ", response=" + response + "]";
    }


}
