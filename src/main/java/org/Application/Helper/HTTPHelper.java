package org.Application.Helper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

public class HTTPHelper {
    public static HttpEntity<String> getHTTPRequest()
    {
        HttpHeaders httpHeader = new HttpHeaders();
        httpHeader.set("Content-Type","application/json");
        return new HttpEntity<>(httpHeader);
    }

}
