package com.example.gnaw.bms;

/**
 * Created by gnaw on 2016/10/31.
 */

public class NowUser {
    private String code;
    private ApiToken response;

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    void setResponse(ApiToken response)
    {
        this.response = response;
    }
    ApiToken getResponse()
    {
        return response;
    }

    @Override
    public String toString() {
        return "NowUser [ code=" + code + ", response=" + response.toString()+ "]";
    }
}

