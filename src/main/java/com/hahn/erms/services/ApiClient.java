package com.hahn.erms.services;

import java.net.http.HttpClient;

public class ApiClient {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    public static HttpClient getClient() {
        return CLIENT;
    }
}