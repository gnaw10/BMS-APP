package com.example.gnaw.bms;

/**
 * Created by gnaw on 2016/10/31.
 */

public class ApiToken {
    private String apiToken;
    void setApiToken(String apiToken)
    {
        this.apiToken = apiToken;
    }
    String getApiToken()
    {
        return apiToken;
    }
    public String toString() {
        return "ApiToken [ apiToken=" + apiToken+ "]";
    }
}
