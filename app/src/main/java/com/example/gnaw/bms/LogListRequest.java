package com.example.gnaw.bms;

import java.util.List;

/**
 * Created by gnaw on 2016/11/1.
 */

public class LogListRequest {
    private String code;
    private List<LogList> response;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<LogList> getResponse() {
        return response;
    }

    public void setResponse(List<LogList> response) {
        this.response = response;
    }
    @Override
    public String toString()
    {
        return "LogRequest [ code" + code + "response" + response +"]";
    }
}
